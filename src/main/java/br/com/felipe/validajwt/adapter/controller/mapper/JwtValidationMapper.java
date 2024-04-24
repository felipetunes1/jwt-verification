package br.com.felipe.validajwt.adapter.controller.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;

import com.google.gson.Gson;

import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.adapter.controller.exception.ValidateJwtHttpException;
import br.com.felipe.validajwt.application.model.ValidateJwtInput;
import br.com.felipe.validajwt.application.model.enumerator.RoleEnum;


public class JwtValidationMapper {

    private static Logger looger = LoggerFactory.getLogger(JwtValidationMapper.class);

    private final static Gson gson = new Gson();
    private final static ParameterizedTypeReference<Map<String, Object>> parameterizedType = new ParameterizedTypeReference<>() {};

    private final static List<String> claims = new ArrayList<>() {{
        add("Role");
        add("Seed");
        add("Name");
    }};

    public static ValidateJwtInput toDomain(String autorizarion) throws ValidateJwtHttpException {
        looger.info("start parse to domain");
        
        if(autorizarion == null) {
            looger.info("token is null");
            throw ValidateJwtHttpException.AUTHORIZATION_IS_NULL;
        }
        if(autorizarion.isBlank()) {
            looger.info("token is blank");
            throw ValidateJwtHttpException.AUTHORIZATION_IS_BLANK;
        }
        var values = autorizarion.split("\\.");

        if(values.length < 2) {
            looger.info("split size {}", values.length);
            throw ValidateJwtHttpException.AUTHORIZATION_LENGTH_ERROR;
        }

        var decoded = decodeBase64(values);

        var claims = parseObject(decoded);

        return validateClaims(claims);
    }

    private static String decodeBase64(String[] values) throws ValidateJwtHttpException {
        try{
            return new String(Base64.getDecoder().decode(values[1]));
        } catch(Exception ex) {
            looger.error("error on decode {}", ex.getMessage());
            throw ValidateJwtHttpException.DECODE_BASE_64_ERROR;
        }
    }

    private static Map<String, Object> parseObject(String json) throws ValidateJwtHttpException {
        try{
            return gson.fromJson(json, parameterizedType.getType());
        } catch(Exception ex) {
            looger.error("error on parse {}", ex.getMessage());
            throw ValidateJwtHttpException.PARSE_OBJECT_ERROR;
        }
    }

    private static ValidateJwtInput validateClaims(Map<String, Object> input) throws ValidateJwtHttpException {
        var errors = validateClaimsProperties(input);

        if(!errors.isEmpty()) {
            looger.error("errors was encountred");
            throw ValidateJwtHttpException.build().addAll(errors);
        }

        var name = (String) input.getOrDefault("Name", null);
        if(Objects.isNull(name)) {
            looger.info("name is null");
            errors.add(ErrorList.NAME_IS_NULL);
        }

        var role = formatRole((String) input.getOrDefault("Role", null), errors);
        var seed = formatSeed((String) input.getOrDefault("Seed", null), errors);


        if(!errors.isEmpty()) {
            looger.error("errors was encountred");
            throw ValidateJwtHttpException.build().addAll(errors);
        }

        return new ValidateJwtInput(role, seed, name);
    }

    private static ErrorList validateClaimsProperties(Map<String, Object> input) {
        var errors = new ErrorList();
        for(final var entry : input.entrySet()) {
            if(claims.stream().noneMatch(it -> it.equals(entry.getKey()))) {
                looger.info("{} not expected", entry.getKey() );
                errors.add(ErrorList.CONTAINS_CLAIMS);
            } else if(!(entry.getValue() instanceof String)) {
                looger.info("{} format incorrect", entry.getKey() );
                errors.add(ErrorList.CLAIMS_FORMAT_INCORRECT);
            }
        }

        return errors;
    }

    private static RoleEnum formatRole(String role, ErrorList errors) {
        if(Objects.isNull(role)) {
            looger.info("role is null");
            errors.add(ErrorList.ROLE_IS_NULL);
        }
        var roleFormatted = RoleEnum.findRole(role);

        if(Objects.isNull(roleFormatted)) {
            looger.info("role is invalid");
            errors.add(ErrorList.INVALID_ROLE);
        }

        return roleFormatted;
    }
    private static BigDecimal formatSeed(String seed, ErrorList errors) {
        if(Objects.isNull(seed)) {
            looger.info("seed is null");
            errors.add(ErrorList.SEED_IS_NULL);
        }
        try {
            return new BigDecimal(seed);
        } catch(Exception ex) {
            looger.error("seed is invalid - {}", ex.getMessage());
            errors.add(ErrorList.SEED_IS_INVALID);
        }
        return null;
    }
}
