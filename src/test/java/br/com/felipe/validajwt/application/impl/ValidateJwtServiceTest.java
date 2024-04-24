package br.com.felipe.validajwt.application.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.application.exceptions.ValidateTokenException;
import br.com.felipe.validajwt.application.model.ValidateJwtInput;
import br.com.felipe.validajwt.application.model.enumerator.RoleEnum;

public class ValidateJwtServiceTest {

    private ValidateJwtService validateJwtService;

    @BeforeEach
    public void setUp() {
        validateJwtService = new ValidateJwtService();
    }

    @Test
    public void testValidate_ValidInput_NoExceptionThrown() {
        // Arrange
        var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(3), "Felipe Tunes");

        // Act & Assert
        assertDoesNotThrow(() -> validateJwtService.validate(input));
    }

    @Test
    public void testValidate_NullRole_ExceptionThrown() {
        // Arrange
        var input = new ValidateJwtInput(null, BigDecimal.valueOf(10), "Felipe Tunes");

        // Act & Assert
        ValidateTokenException exception = assertThrows(ValidateTokenException.class, () -> validateJwtService.validate(input));
        assertTrue(exception.errors.contains(ErrorList.INVALID_ROLE));
    }

    @Test
    public void testValidate_NullName_ExceptionThrown() {
        // Arrange
        var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(10), null);

        // Act & Assert
        ValidateTokenException exception = assertThrows(ValidateTokenException.class, () -> validateJwtService.validate(input));
        assertTrue(exception.errors.contains(ErrorList.NAME_IS_NULL));
    }

    
    @Test
    public void testValidateSeed_SeedIsEven_ExceptionThrown() {
        // Arrange
        var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(4), "Felipe Tunes");

        // Act & Assert
        ValidateTokenException exception = assertThrows(ValidateTokenException.class, () -> validateJwtService.validate(input));
        assertTrue(exception.errors.contains(ErrorList.SEED_IS_INVALID));
    }

    @Test
    public void testValidateSeed_SeedIsPerfectSquare_ExceptionThrown() {
        // Arrange
        var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(9), "Felipe Tunes");

        // Act & Assert
        ValidateTokenException exception = assertThrows(ValidateTokenException.class, () -> validateJwtService.validate(input));
        assertTrue(exception.errors.contains(ErrorList.SEED_IS_INVALID));
    }


    @Test
    public void testValidateSeed_SeedIsPOdd_DoesNotThrowException() {
        // Arrange
        var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(83), "Felipe Tunes");

        // Act & Assert
        assertDoesNotThrow(() -> validateJwtService.validate(input));
    }
    
    
    @Test
    public void testValidateSeed_ValidSeed_NoExceptionThrown() {
        // Arrange

        int[] seeds = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

        for (int seed : seeds) {
            var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(seed), "Felipe Tunes");

            // Act & Assert
            assertDoesNotThrow(() -> validateJwtService.validate(input));
        }
    }

    @Test
    public void testValidateSeed_InvalidSeed_ExceptionThrown() {
        // Arrange

        int[] invalidSeeds = {1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35,
                36, 38, 39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54, 55, 56, 57, 58, 60, 62, 63, 64, 65, 66, 68, 69,
                70, 72, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95, 96, 98, 99, 100};

        for (int invalidSeed : invalidSeeds) {
            var input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(invalidSeed), "Felipe Tunes");

            // Act & Assert
            ValidateTokenException exception = assertThrows(ValidateTokenException.class, () -> validateJwtService.validate(input));
            assertTrue(exception.errors.contains(ErrorList.SEED_IS_INVALID));
        }
    }

    @Test
    public void testValidate_NameTooLong_ExceptionThrown() {
        // Arrange
        var name = "";

        for(var i = 1; i < 200; i++) {
            name = name.concat(String.valueOf(i));
        }
        ValidateJwtInput input = new ValidateJwtInput(RoleEnum.ADMIN, BigDecimal.valueOf(3), name);

        // Act & Assert
        ValidateTokenException exception = assertThrows(ValidateTokenException.class, () -> validateJwtService.validate(input));
        assertTrue(exception.errors.contains(ErrorList.INVALID_NAME));
    }
}