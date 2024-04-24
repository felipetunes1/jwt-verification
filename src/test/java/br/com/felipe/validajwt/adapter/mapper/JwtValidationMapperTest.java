package br.com.felipe.validajwt.adapter.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.adapter.controller.exception.ValidateJwtHttpException;
import br.com.felipe.validajwt.adapter.controller.mapper.JwtValidationMapper;
import br.com.felipe.validajwt.application.model.ValidateJwtInput;
import br.com.felipe.validajwt.application.model.enumerator.RoleEnum;

public class JwtValidationMapperTest {
    
    @Test
    public void testToDomain_ValidAuthorization_NoExceptionThrown() {
        // Arrange
        String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6IjEyMzQ1NiIsIk5hbWUiOiJKb2huIn0";

        // Act & Assert
        assertDoesNotThrow(() -> JwtValidationMapper.toDomain(authorization));
    }

    @Test
    public void testToDomain_NullAuthorization_ExceptionThrown() {
        // Arrange
        String authorization = null;

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertEquals(ValidateJwtHttpException.AUTHORIZATION_IS_NULL, exception);
    }

    @Test
    public void testToDomain_BlankAuthorization_ExceptionThrown() {
        // Arrange
        String authorization = "";

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertEquals(ValidateJwtHttpException.AUTHORIZATION_IS_BLANK, exception);
    }

    @Test
    public void testToDomain_InvalidAuthorizationLength_ExceptionThrown() {
        // Arrange
        String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertEquals(ValidateJwtHttpException.AUTHORIZATION_LENGTH_ERROR, exception);
    }

    @Test
    public void testToDomain_DecodeBase64Error_ExceptionThrown() {
        // Arrange
        String authorization = "invalid_token.invalid_token";

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertEquals(ValidateJwtHttpException.DECODE_BASE_64_ERROR, exception);
    }

    @Test
    public void testToDomain_ParseObjectError_ExceptionThrown() {
        // Arrange
        String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.ZmtsZGpnbGtqc2tsZ2pzZA";

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertEquals(ValidateJwtHttpException.PARSE_OBJECT_ERROR, exception);
    }

    @Test
    public void testToDomain_ContainsClaimsError_ExceptionThrown() {
        // Arrange
        String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzb21lIjoiU2VhZCIsInNlZWQiOiIxMjM0NTYiLCJuYW1lIjoiSm9obiJ9";

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertTrue(exception.getErrors().contains(ErrorList.CONTAINS_CLAIMS));
    }

    @Test
    public void testToDomain_ClaimsFormatError_ExceptionThrown() {
        // Arrange
        String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjpbIjEyMzQ1NiJdLCJTZWVkIjoiMTIzNDU2IiwiTmFtZSI6IkpvaG4ifQ";

        // Act & Assert
        ValidateJwtHttpException exception = assertThrows(ValidateJwtHttpException.class, () -> JwtValidationMapper.toDomain(authorization));
        assertTrue(exception.getErrors().contains(ErrorList.CLAIMS_FORMAT_INCORRECT));
    }

    @Test
    public void testToDomain_ValidInput_CorrectOutput() throws ValidateJwtHttpException {
        // Arrange
        String authorization = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6IjEyMzQ1NiIsIk5hbWUiOiJKb2huIn0";
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", "admin");
        claims.put("Seed", "123456");
        claims.put("Name", "John");

        // Act
        ValidateJwtInput input = JwtValidationMapper.toDomain(authorization);

        // Assert
        assertEquals(RoleEnum.MEMBER, input.getRole());
        assertEquals(new BigDecimal("123456"), input.getSeed());
        assertEquals("John", input.getName());
    }
}
