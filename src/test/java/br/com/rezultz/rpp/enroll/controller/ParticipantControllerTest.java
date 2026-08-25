package br.com.rezultz.rpp.enroll.controller;

import br.com.rezultz.rpp.enroll.exception.GeneralExceptionHandler;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.service.ParticipantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest({ParticipantController.class, GeneralExceptionHandler.class})
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ParticipantService participantService;

    private ParticipantCreateMessage participantCreateMessage;

    @BeforeEach
    void setUp() {
        participantCreateMessage = new ParticipantCreateMessage(
                "Pedro Costa",
                "Pedro Tech",
                "1123343425",
                "CPF",
                "Empresa X"
        );
    }

    @Test
    @DisplayName("Deve retornar Status 202 Accepted ao enviar requisição de criação válida")
    void shouldReturn202WhenCreateParticipantSuccess() throws Exception {
        mockMvc.perform(post("/api/v1/participant/rabbit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantCreateMessage)))
                .andDo(print())
                .andExpect(status().isAccepted());

        verify(participantService).create(any(ParticipantCreateMessage.class));
    }

    @Test
    @DisplayName("Deve retornar Status 400 Bad Request e JSON padronizado quando regra de negócio falhar no service")
    void shouldReturn400WhenBusinessValidationFails() throws Exception {
        String errorMessage = "Já existe um participante cadastrado com o documento: 1123343425";

        doThrow(new IllegalArgumentException(errorMessage))
                .when(participantService).create(any(ParticipantCreateMessage.class));

        mockMvc.perform(post("/api/v1/participant/rabbit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantCreateMessage)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Deve retornar Status 400 Bad Request e mostrar mensagem do @Valid quando campos obrigatórios faltarem")
    void shouldReturn400WhenRequestBodyIsInvalid() throws Exception {
        // Criando mensagem sem o campo obrigatório 'nome' para disparar o @NotBlank
        ParticipantCreateMessage invalidMessage = new ParticipantCreateMessage(
                null,
                "Pedro Tech",
                "1123343425",
                "CPF",
                "Empresa X"
        );

        mockMvc.perform(post("/api/v1/participant/rabbit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidMessage)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("O nome é obrigatório")));
    }
}