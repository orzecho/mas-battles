package eu.mdabrowski.battles.restapi.wrapper;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import static eu.mdabrowski.battles.restapi.wrapper.ResponseWrapperUtil.createResponseMap;

public class ResponseWrapper<T> extends ResponseEntity<Map<String, T>> {
    public ResponseWrapper(HttpStatus status) {
        super(status);
    }

    public ResponseWrapper(Map<String, T> body, HttpStatus status) {
        super(body, status);
    }

    public ResponseWrapper(MultiValueMap<String, String> headers,
            HttpStatus status) {
        super(headers, status);
    }

    public ResponseWrapper(Map<String, T> body,
            MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public ResponseWrapper(T body, String name) {
        super(createResponseMap(name, body), HttpStatus.OK);
    }


}
