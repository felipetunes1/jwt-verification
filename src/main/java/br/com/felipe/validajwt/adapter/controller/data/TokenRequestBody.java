package br.com.felipe.validajwt.adapter.controller.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRequestBody {
    @JsonProperty("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenRequestBody(String token) {
        this.token = token;
    }
    
}
