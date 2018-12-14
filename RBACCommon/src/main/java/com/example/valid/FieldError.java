package com.example.valid;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
class FieldError implements Serializable {
    private String name;
    private String message;
}