package com.cict.iamtracing.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {

    private String message;
    private T data;
    private Boolean status;

    private GenericResponse() {

    }

    public static <T> GenericResponse<T> constructResponse(T data, String message, Boolean status) {
        GenericResponse<T> genericResponse = new GenericResponse<>();
        genericResponse.data = data;
        genericResponse.message = message;
        genericResponse.status = status;
        return genericResponse;
    }

}
