package eu.mdabrowski.battles.associations;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.vavr.Tuple3;
import io.vavr.control.Try;

@Service
public class AssociationService {

    private Pattern somethingPattern = Pattern.compile("^(\\d+)$");
    private Pattern zeroToManyPattern = Pattern.compile("^0[.]{2}(\\d+)$");
    private Pattern somethingToManyPattern = Pattern.compile("^(\\d+)[.]{2}(\\d+)$");
    private Predicate<Map.Entry<Field, Association>> haveNonTrivialMultiplicity = e -> !e.getValue().theirMultiplicity()
            .equals("*");

    public boolean validate(Object ourObject){
        Class klass = ourObject.getClass();
        return getFieldAssociationMap(klass).entrySet().stream()
                .filter(haveNonTrivialMultiplicity)
                .allMatch(e -> validateEntry(ourObject, e));
    }

    public String getAssociationsAsString(Class klass) {
        StringBuilder sb = new StringBuilder();
        getFieldAssociationMap(klass).forEach((e, f) -> {
            getAssociationAsString(klass, sb, e, f);
        });
        return sb.toString();
    }

    public String getAssociationsAsString(Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append(getAssociationsAsString(object.getClass()));
        getFieldAssociationMap(object).forEach((e,f) -> {
            sb.append("Our object \n:");
            sb.append(f._1());
            sb.append("\n");
            sb.append("Their object \n:");
            sb.append(f._2());
        });
        return sb.toString();
    }

    public Map<Field, Association> getFieldAssociationMap(Class klass) {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(e -> Arrays.stream(e.getAnnotations())
                        .anyMatch(a -> a instanceof Association))
                .collect(Collectors.toMap(f -> f, f -> f.getAnnotation(Association.class)));
    }

    public Map<String, Tuple3> getFieldAssociationMap(Object ourObject) {
        Class klass = ourObject.getClass();
        return getFieldAssociationMap(klass).entrySet().stream()
                .map(e -> new Tuple3<>(ourObject, getObjectWithGetter(ourObject, e), e.getValue()))
                .collect(Collectors.toMap(t -> t._3().name(), t -> t));
    }

    private Object getObjectWithGetter(Object ourObject, Map.Entry<Field, Association> e) {
        String getter = "get" + e.getKey().getName().substring(0, 1).toUpperCase() + e.getKey().getName().substring(1);
        return Try.of(() -> ourObject.getClass().getMethod(getter).invoke(ourObject)).get();
    }

    private void getAssociationAsString(Class klass, StringBuilder sb, Field e, Association f) {
        sb.append(klass.getName());
        sb.append("--");
        sb.append(f.ourMultiplicity());
        sb.append("----");
        sb.append(f.name());
        sb.append("----");
        sb.append(f.theirMultiplicity());
        sb.append("--");
        sb.append(getTypeName(e));

        sb.append("\n");
    }

    private String getTypeName(Field e) {
        return isCollection(e) ?
                ((ParameterizedType) e.getGenericType()).getActualTypeArguments()[0].getTypeName()
                : e.getType().getTypeName();
    }

    private boolean isCollection(Field e) {
        return Arrays.asList(Collection.class, Set.class, List.class).contains(e.getType());
    }

    private boolean validateEntry(Object ourObject, Map.Entry<Field, Association> entry) {
        Matcher zeroToManyMatcher = zeroToManyPattern.matcher(entry.getValue().theirMultiplicity());
        Matcher somethingToManyMatcher = somethingToManyPattern.matcher(entry.getValue().theirMultiplicity());
        Matcher somethingMatcher = somethingPattern.matcher(entry.getValue().theirMultiplicity());
        Object theirObject = getObjectWithGetter(ourObject, entry);
        if(entry.getValue().theirMultiplicity().equals("1")){
            return validateNotNullSingular(theirObject);
        } else if(somethingMatcher.find()) {
            return validateSomething(theirObject, Integer.valueOf(somethingMatcher.group(1)));
        } else if(entry.getValue().theirMultiplicity().equals("0..1")) {
            return validateOptional(theirObject);
        } else if(zeroToManyMatcher.find()) {
            return validateZeroToMany(theirObject, Integer.valueOf(zeroToManyMatcher.group(1)));
        } else if(somethingToManyMatcher.find()) {
            return validateSomethingToMany(theirObject, Integer.valueOf(zeroToManyMatcher.group(1)),
                    Integer.valueOf(zeroToManyMatcher.group(2)));
        }
        return true;
    }

    private boolean validateSomething(Object object, int something) {
        if(object instanceof Collection) {
            if(((Collection) object).size() == something){
                return true;
            }
        } else {
            return something == 1;
        }
        return false;
    }

    private boolean validateSomethingToMany(Object object, int lowerBound, int uppperBound) {
        if(object instanceof Collection) {
            if(((Collection) object).size() > uppperBound && ((Collection) object).size() < lowerBound){
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean validateZeroToMany(Object object, int uppperBound) {
        if(object instanceof Collection) {
            if(((Collection) object).size() > uppperBound){
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean validateOptional(Object object) {
        if(object instanceof Collection) {
            if(((Collection) object).size() > 1){
                return false;
            }
        }
        return true;
    }

    private boolean validateNotNullSingular(Object object) {
        if(object instanceof Collection) {
            if(((Collection) object).size() != 1){
                return false;
            }
        } else {
            if(object == null){
                return false;
            }
        }
        return true;
    }
}
