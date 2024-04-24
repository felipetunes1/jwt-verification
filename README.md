Claro! Aqui está um exemplo de README.md para as classes que você compartilhou:

---

# Documentação das Classes

Este documento fornece uma visão geral das classes relacionadas à validação de tokens JWT.

## JwtValidationMapper

A classe `JwtValidationMapper` é responsável por mapear e validar os claims de um token JWT.

### Métodos

- `toDomain(String autorization)`: Este método converte um token JWT em um objeto `ValidateJwtInput` para validação posterior.

- `private decodeBase64(String[] values)` realiza o decode base 64 para o segundo parametro do token que deve conter as claims, em caso de erro no decode para a execução do processo

- `private parseObject(String json)` realiza a conversão do segundo valor já decodado para verificar se é compativel com um objeto java

- `private validateClaims(Map<String, Object> input)` realiza a a validação dos campos enviados nas claims e formato para o objeto domain

- `private validateClaimsProperties(Map<String, Object> input)` verifica se os campos enviados no objeto claims esta no formato correto e se não foi enviado nenhum campo não esperado

- `private formatRole(String role, ErrorList errors)` realiza validações na role enviada e formato para o objeto conforme esperado

- `private formatSeed(String seed, ErrorList errors)` realiza validações na seed enviada e formato para o objeto conforme esperado


## ValidateJwtService

A classe `ValidateJwtService` é responsável por validar os inputs do token JWT.

### Métodos

- `validate(ValidateJwtInput input)`: Este método valida os inputs do token JWT, como a role, o nome e a seed.

- `private nameValidate(String input)`: esse método realiza a validações a respeito do Nome enviado no token
    - Se contem caracter numérico
    - Se o Nome possui mais de 256 caracteres
    - Se o Nome foi enviado

- `private validateSeed(BigDecimal seed)`: esse método realiza validações a respeito da Seed enviada no token:
    - Se a seed é um numero primo

- `private hasDivisorInList(List<BigDecimal> values, BigDecimal divisor)`: esse método realiza verifica se o novo divisor a ser usado na validação do validateSeed não é número primo para não precisar recalcular

## JwtValidatorController

A classe `JwtValidatorController` é um controlador Spring que lida com a validação de tokens JWT.

### Métodos

- `validateJwt(String token)`: Este método recebe um token JWT e o valida, retornando uma resposta HTTP apropriada.

## JwtValidatorOpenApi

A interface `JwtValidatorOpenApi` define um contrato para validar tokens JWT.

### Métodos

- `validateJwt(String token)`: Este método valida um token JWT recebido como parâmetro.

---

## CURL para Execução do Projeto

```
curl --location --request POST 'localhost:8080/v1/validate-jwt' \
--header 'Content-Type: application/json' \
--header 'token: eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg' \
--data-raw ''
```