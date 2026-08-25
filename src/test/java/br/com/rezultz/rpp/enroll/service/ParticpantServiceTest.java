package br.com.rezultz.rpp.enroll.service;

import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.message.producer.ParticipantProducerRabbit;
import br.com.rezultz.rpp.enroll.validator.ParticipantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantProducerRabbit participantProducerRabbit;

    @Mock
    private ParticipantValidator participantValidator;

    @InjectMocks
    private ParticipantService participantService;

    private ParticipantCreateMessage participantCreateMessage;

    @BeforeEach
    void setUp() {
        participantCreateMessage = new ParticipantCreateMessage(
                "Pedro Costa", "Pedro Tech", "12345678900", "CPF", "Empresa X"
        );
    }

    @Test
    @DisplayName("Deve validar documento e enviar para o RabbitMQ com sucesso")
    void shouldValidateAndSendToRabbitSuccessfully() {
        participantService.create(participantCreateMessage);
        verify(participantValidator, times(1)).validateDocumentDoesNotExist(participantCreateMessage.document());
        verify(participantProducerRabbit, times(1)).sendCreateParticipant(participantCreateMessage);
    }

    @Test
    @DisplayName("Não deve enviar para o RabbitMQ quando o documento já existir no banco")
    void shouldNotSendToRabbitWhenDocumentAlreadyExists() {
        doThrow(new IllegalArgumentException("Documento já cadastrado"))
                .when(participantValidator).validateDocumentDoesNotExist(participantCreateMessage.document());
        assertThrows(IllegalArgumentException.class, () -> participantService.create(participantCreateMessage));
        verify(participantValidator, times(1)).validateDocumentDoesNotExist(participantCreateMessage.document());
        verifyNoInteractions(participantProducerRabbit);
    }

    @Test
    @DisplayName("Deve retornar uma página de participantes cadastrados com sucesso")
    void shouldReturnPagedParticipantsWithSuccess() {

    }

    @Test
    @DisplayName("Deve marcar o participante como deletado no delete lógico")
    void shouldLogicDeleteParticipantWithSuccess() {

    }

    @Test
    @DisplayName("Deve atualizar o nome do participante com sucesso")
    void shouldUpdateNameParticipantWithSuccess(){

    }

    @Test
    @DisplayName("Deve atualizar o documento e tipo do documento de um participante existente")
    void shouldUpdateParticipantUseDPO(){

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um participante inexistente")
    void shouldThrowExceptionOnUpdateWhenNotFound() {

    }



}

