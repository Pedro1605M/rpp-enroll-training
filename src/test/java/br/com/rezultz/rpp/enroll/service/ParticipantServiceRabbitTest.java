package br.com.rezultz.rpp.enroll.service;

import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ParticipantServiceRabbitTest {
    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantServiceRabbit participantServiceRabbit;

    private ParticipantCreateMessage participantCreateMessage;

    /*
    @Test
    @DisplayName("")
    void should

     */
}
