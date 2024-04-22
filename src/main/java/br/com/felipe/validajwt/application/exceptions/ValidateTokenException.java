package br.com.felipe.validajwt.application.exceptions;

import java.util.Objects;

import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.adapter.controller.data.JwtError;

public class ValidateTokenException extends Exception {

    public final ErrorList errors = new ErrorList();

    public static ValidateTokenException build() {
        return new ValidateTokenException();
    }

    public ValidateTokenException addError(JwtError error) {
        errors.add(error);
        return this;
    }

    public ValidateTokenException addError(String description, String code) {
        return addError(new JwtError(description, code));
    }
    
    public ValidateTokenException addAll(ErrorList errors) {
        this.errors.addAll(errors.stream().filter(Objects::nonNull).toList());
        return this;
    }
    
}
