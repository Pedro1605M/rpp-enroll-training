package br.com.rezultz.rpp.enroll.service;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantServiceRabbit {
    private final ParticipantRepository participantRepository;

    public void createRabbit(ParticipantCreateMessage participantCreateMessage){
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

        log.info("Participante salvo com sucesso via Rabbit");
        participantRepository.save(participant);
    }

    public void updateNameRabbit(ParticipantCreateMessage participantCreateMessage) {
        participantRepository.updateNameByDocument(
                participantCreateMessage.name(),
                LocalDateTime.now(),
                participantCreateMessage.document()
        );

        log.info("Nome do participante do documento:{} foi atualizado via Rabbit para o nome: {}", participantCreateMessage.document(), participantCreateMessage.name());
    }

    public void logicDelete(ParticipantCreateMessage participantCreateMessage){
        participantRepository.logicDelete(
                LocalDateTime.now(),
                participantCreateMessage.document()
        );

        log.info("O participante do documento:{} foi deletado via Rabbit", participantCreateMessage.document());
    }

}
