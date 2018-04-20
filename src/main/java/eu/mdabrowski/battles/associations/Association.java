package eu.mdabrowski.battles.associations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Association {
    String name();
    String ourMultiplicity() default "*";
    String theirMultiplicity() default "*";
}
