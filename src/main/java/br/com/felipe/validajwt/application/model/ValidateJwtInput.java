package br.com.felipe.validajwt.application.model;

import java.math.BigDecimal;

import br.com.felipe.validajwt.application.model.enumerator.RoleEnum;

public record ValidateJwtInput(
    RoleEnum role,
    BigDecimal seed,
    String name
) {
    public RoleEnum getRole() {
        return role;
    }
    public BigDecimal getSeed() {
        return seed;
    }
    public String getName() {
        return name;
    }
}
