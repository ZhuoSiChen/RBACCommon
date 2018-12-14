package com.example.valid;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ParamValidException extends Exception{
    private String method;
    private String url;
    private List<FieldError> fieldErrors;

    public ParamValidException(List<FieldError> errors) {
        super();
        this.fieldErrors = errors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}