package br.com.felipe.validajwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
class ValidaJwtApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidateJwt_ValidToken_ReturnsAccepted() throws Exception {
        String validToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6IjEwMDA3IiwiTmFtZSI6IkpvaG4ifQ";

        mockMvc.perform(post("/v1/validate-jwt")
                .header("token", validToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testValidateJwt_InvalidToken_ReturnsPreconditionFailed() throws Exception {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJSb2xlIjoiTWVtYmVyIiwiU2VlZCI6IjEyMzQ1NiIsIk5hbWUiOiJKb2huIn0";

        mockMvc.perform(post("/v1/validate-jwt")
                .header("token", invalidToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }


}
