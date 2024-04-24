package br.com.felipe.validajwt.adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.felipe.validajwt.adapter.controller.exception.ValidateJwtHttpException;
import br.com.felipe.validajwt.adapter.controller.impl.JwtValidatorController;
import br.com.felipe.validajwt.application.ValidateJwtUseCase;
import br.com.felipe.validajwt.application.exceptions.ValidateTokenException;

public class JwtValidatorControllerTest {
    
    private JwtValidatorController jwtValidatorController;
    private ValidateJwtUseCase validateJwtUseCase;

    @BeforeEach
    public void setUp() {
        validateJwtUseCase = mock(ValidateJwtUseCase.class);
        jwtValidatorController = new JwtValidatorController(validateJwtUseCase);
    }

    @Test
    public void testValidateJwt_ValidToken_ReturnsAccepted() throws ValidateJwtHttpException, ValidateTokenException {
        // Arrange
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6IjEwMDAwNyIsIk5hbWUiOiJKb2huIn0";
        doNothing().when(validateJwtUseCase).validate(any());

        // Act
        ResponseEntity<Void> response = jwtValidatorController.validateJwt(token);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testValidateJwt_InvalidToken_ThrowsValidateJwtHttpException() throws ValidateTokenException {
        // Arrange
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6IjEyMzQ1NiIsIk5hbWUiOiJKb2huIn0";
        ValidateTokenException exception = ValidateTokenException.build();
        doThrow(exception).when(validateJwtUseCase).validate(any());

        // Act & Assert
        assertThrows(ValidateJwtHttpException.class, () -> jwtValidatorController.validateJwt(token));
    }

}
