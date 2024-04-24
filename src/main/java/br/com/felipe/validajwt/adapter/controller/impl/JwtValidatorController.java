package br.com.felipe.validajwt.adapter.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.felipe.validajwt.adapter.controller.JwtValidatorOpenApi;
import br.com.felipe.validajwt.adapter.controller.exception.ValidateJwtHttpException;
import br.com.felipe.validajwt.adapter.controller.mapper.JwtValidationMapper;
import br.com.felipe.validajwt.application.ValidateJwtUseCase;
import br.com.felipe.validajwt.application.exceptions.ValidateTokenException;

@RestController
public class JwtValidatorController implements JwtValidatorOpenApi {

    private final ValidateJwtUseCase useCase;

    public JwtValidatorController(ValidateJwtUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public ResponseEntity<Void> validateJwt(String token) throws ValidateJwtHttpException {
        try {
            useCase.validate(
                JwtValidationMapper.toDomain(token)
            );
            return ResponseEntity.status(202).build();
        } catch(ValidateTokenException ex) {
            throw ValidateJwtHttpException.build().addAll(ex.errors);
        }
    }
    
}
