package br.com.rezultz.rpp.enroll.Repository;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParticipantRepositoryTest {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private ParticipantCreateMessage participantCreateMessage;

    @BeforeEach
    void setUp() {
        participantCreateMessage = new ParticipantCreateMessage(
                "Pedro Costa",
                "Pedro Tech",
                "12345678000129",
                "CPF",
                "Empresa X"
        );
    }

    @Test
    @DisplayName("Deve salvar o participante com sucesso quando o documento não existir no banco (retornar false)")
    void ShouldCreateParticipantAndSaveSuccessfully(){
        boolean existsDocumentBefore =  participantRepository.existsByDocument(participantCreateMessage.document());
        assertThat(existsDocumentBefore).isFalse();

        Participant participant = Participant.builder()
                .uuid(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .deleted(false)
                .status(1)
                .statusName("ACTIVE")
                .name(participantCreateMessage.name())
                .tradeName(participantCreateMessage.tradeName())
                .document(participantCreateMessage.document())
                .documentType(participantCreateMessage.documentType())
                .companyName(participantCreateMessage.companyName()).build();

        testEntityManager.persistAndFlush(participant);

        assertThat(participant.getId()).isNotNull();
        assertThat(participant.getDocument()).isEqualTo(participantCreateMessage.document());
    }

}

