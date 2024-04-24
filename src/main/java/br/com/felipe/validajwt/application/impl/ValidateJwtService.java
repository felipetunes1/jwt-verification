package br.com.felipe.validajwt.application.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.adapter.controller.data.JwtError;
import br.com.felipe.validajwt.application.ValidateJwtUseCase;
import br.com.felipe.validajwt.application.exceptions.ValidateTokenException;
import br.com.felipe.validajwt.application.model.ValidateJwtInput;

@Service
public class ValidateJwtService implements ValidateJwtUseCase {
    private final Logger logger = LoggerFactory.getLogger(ValidateJwtService.class);


    private final Pattern validateName = Pattern.compile("[0-9]");

    @Override
    public void validate(ValidateJwtInput input) throws ValidateTokenException {
        logger.info("start service validate");

        var errors = new ErrorList();

        if(input.getRole() == null) {
            logger.info("INVALID_ROLE");
            errors.add(ErrorList.INVALID_ROLE);
        }

        errors.add(nameValidate(input.getName()));

        errors.add(validateSeed(input.getSeed()));

        if(!errors.stream().filter(Objects::nonNull).toList().isEmpty()) {
            logger.info("error on validade");
            throw ValidateTokenException.build().addAll(errors);
        }
    }

    private JwtError nameValidate(String name) {
        if(Objects.isNull(name)) {
            logger.info("NAME_IS_NULL");
            return ErrorList.NAME_IS_NULL;
        }
        if(name.isBlank()) {
            logger.info("NAME_IS_BLANK");
            return ErrorList.NAME_IS_BLANK;
        }
        if(name.length() > 256) {
            logger.info("INVALID_NAME");
            return ErrorList.INVALID_NAME;
        }
        if(validateName.matcher(name).find()){
            logger.info("INVALID_NAME");
            return ErrorList.INVALID_NAME;
        }
        
        return null;
    }

    private JwtError validateSeed(BigDecimal seed) {
        if(seed.compareTo(BigDecimal.valueOf(2)) == 0 || seed.compareTo(BigDecimal.valueOf(3)) == 0) {
            return null;
        }
        if(seed.compareTo(BigDecimal.valueOf(2)) < 0) {
            logger.info("SEED_IS_INVALID LOWER THAN 2");
            return ErrorList.SEED_IS_INVALID;
        }
        if(BigDecimal.ZERO.compareTo(seed.remainder(BigDecimal.valueOf(2))) == 0) {
            logger.info("SEED_IS_INVALID IS PAR");
            return ErrorList.SEED_IS_INVALID;
        }

        if(seed.compareTo(seed.sqrt(new MathContext(1)).pow(2)) == 0) {
            logger.info("SEED_IS_INVALID SQRT");
            return ErrorList.SEED_IS_INVALID;
        }
        
        BigDecimal divisor = BigDecimal.valueOf(3);
        List<BigDecimal> values = new ArrayList<>();
        do {
            if(BigDecimal.ZERO.compareTo(divisor.remainder(BigDecimal.valueOf(2))) != 0) {
                if(!hasDivisorInList(values, divisor)) {
                    var resto = seed.remainder(divisor);
                    if(BigDecimal.ZERO.compareTo(resto) == 0) {
                        logger.info("SEED_IS_INVALID");
                        return ErrorList.SEED_IS_INVALID;
                    }
                    var quociente = seed.subtract(resto).divide(divisor);
                    if(divisor.compareTo(quociente) > 0) {
                        return null;
                    }
                }
            }
            divisor = divisor.add(BigDecimal.ONE);
        } while(true);
    }

    private boolean hasDivisorInList(List<BigDecimal> values, final BigDecimal divisor) {
        return values.stream().anyMatch(it -> BigDecimal.ZERO.compareTo(divisor.remainder(it)) == 0);
    }
    
}
