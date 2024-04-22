package br.com.felipe.validajwt.adapter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import br.com.felipe.validajwt.adapter.controller.data.ErrorList;
import br.com.felipe.validajwt.adapter.controller.exception.ValidateJwtHttpException;

@Controller
@RequestMapping("/v1")
public interface JwtValidatorOpenApi {
    @Operation(
        summary = "Validate JWT Token",
        operationId = "validateJwt",
        responses = {
            @ApiResponse( 
                responseCode = "200",
                description =  "Success", 
                content = {
                    @Content(
                        mediaType = "application/json", 
                        schema = @Schema
                    )
                }
            ),
            @ApiResponse( 
                responseCode = "412",
                description =  "Precondition Failed", 
                content = {
                    @Content(
                        mediaType = "application/json", 
                        schema = @Schema(implementation = ErrorList.class)
                    )
                }
            ),
        }

    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/validate-jwt",
        produces = "application/json",
        consumes = "application/json"
    
    )
    ResponseEntity<Void> validateJwt(
        @Parameter(name = "token", in = ParameterIn.HEADER) @RequestHeader(name = "token") String token
    ) throws ValidateJwtHttpException;
    
} 