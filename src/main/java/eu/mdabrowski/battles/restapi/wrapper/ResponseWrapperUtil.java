package eu.mdabrowski.battles.restapi.wrapper;

import java.util.HashMap;
import java.util.Map;

public class ResponseWrapperUtil {

    public static <T> Map<String, T> createResponseMap(String name, T body){
        Map<String, T> map = new HashMap<>();
        map.put(name, body);
        return map;
    }
}
