package eu.mdabrowski.battles.restapi.wrapper;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import static eu.mdabrowski.battles.restapi.wrapper.ResponseWrapperUtil.createResponseMap;

public class ResponseListWrapper<T> extends ResponseEntity<Map<String, List<T>>> {
    public ResponseListWrapper(HttpStatus status) {
        super(status);
    }

    public ResponseListWrapper(Map<String, List<T>> body, HttpStatus status) {
        super(body, status);
    }

    public ResponseListWrapper(MultiValueMap<String, String> headers,
            HttpStatus status) {
        super(headers, status);
    }

    public ResponseListWrapper(Map<String, List<T>> body,
            MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public ResponseListWrapper(List<T> body, String name) {
        super(createResponseMap(name, body), HttpStatus.OK);
    }


}
