package br.com.felipe.validajwt.adapter.controller.data;

public record JwtError(
    String description,
    String code
) {
}