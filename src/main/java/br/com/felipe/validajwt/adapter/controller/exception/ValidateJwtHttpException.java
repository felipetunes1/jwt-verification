package br.com.felipe.validajwt.adapter.controller.exception;

import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.adapter.controller.data.JwtError;

public class ValidateJwtHttpException extends Exception {
    public final static ValidateJwtHttpException AUTHORIZATION_IS_NULL = build().addError(ErrorList.AUTHORIZATION_IS_NULL);
    public final static ValidateJwtHttpException AUTHORIZATION_IS_BLANK = build().addError(ErrorList.AUTHORIZATION_IS_BLANK);
    public final static ValidateJwtHttpException AUTHORIZATION_LENGTH_ERROR = build().addError(ErrorList.AUTHORIZATION_LENGTH_ERROR);
    public final static ValidateJwtHttpException DECODE_BASE_64_ERROR = build().addError(ErrorList.DECODE_BASE_64_ERROR);
    public final static ValidateJwtHttpException PARSE_OBJECT_ERROR = build().addError(ErrorList.PARSE_OBJECT_ERROR);
    
    final String error = "Ocorreram os seguintes errors na validação do JWT";
    final ErrorList errors = new ErrorList();

    public static ValidateJwtHttpException build() {
        return new ValidateJwtHttpException();
    }

    public ValidateJwtHttpException addError(JwtError error) {
        errors.add(error);
        return this;
    }

    public ValidateJwtHttpException addError(String description, String code) {
        return addError(new JwtError(description, code));
    }

    public ValidateJwtHttpException addAll(ErrorList errors) {
        this.errors.addAll(errors);
        return this;
    }
}
