package br.com.rezultz.rpp.enroll.service;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import br.com.rezultz.rpp.enroll.validator.ParticipantValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private ParticipantValidator participantValidator;

    @InjectMocks
    private ParticipantService participantService;

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

    @Test
    @DisplayName("Deve criar um novo participante com sucesso")
    void shouldCreateParticipantWithSuccess() {
        ParticipantCreateMessage participantCreateMessage = new ParticipantCreateMessage("pedro", "pedro tech2", "112334342", "cnpj", "empresa");

        participantService.createRabbit(participantCreateMessage);

        verify(participantValidator).validateDocumentDoesNotExist("112334342");
        verify(participantRepository).save(any(Participant.class));


    }

}

