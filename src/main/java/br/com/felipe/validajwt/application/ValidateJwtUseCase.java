package br.com.felipe.validajwt.application;

import br.com.felipe.validajwt.application.exceptions.ValidateTokenException;
import br.com.felipe.validajwt.application.model.ValidateJwtInput;

public interface ValidateJwtUseCase {

    void validate(ValidateJwtInput input) throws ValidateTokenException;
    
}
