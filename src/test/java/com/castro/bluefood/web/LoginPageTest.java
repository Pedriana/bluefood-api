package com.castro.bluefood.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
//pra simular de toda a parte mvc da aplicação
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginPageTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginRedirect() throws Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location",containsString("login")));
    }

    @Test
    public void testLoginPage() throws Exception{
//        vai retornar o conteúdo html (body)
        String content = mockMvc.perform(get("/login"))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn().getResponse().getContentAsString();
        assertThat(content).contains("<h1>Autenticação</h1>");
    }

}
