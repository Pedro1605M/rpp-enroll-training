package br.com.rezultz.rpp.enroll.service;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.message.producer.ParticipantProducerRabbit;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import br.com.rezultz.rpp.enroll.validator.ParticipantValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final ParticipantValidator participantValidator;
    private final ParticipantProducerRabbit participantProducerRabbit;

    public List<Participant> list(){
        return participantRepository.findAllByDeletedFalseOrderByNameAsc();
    }

    public Page<Participant> listAll(Pageable pageable){
        return participantRepository.findAll(pageable);
    }

    public void create(ParticipantCreateMessage participantCreateMessage) {
        participantValidator.validateDocumentDoesNotExist(participantCreateMessage.document());
        participantProducerRabbit.sendCreateParticipant(participantCreateMessage);
    }

    public void updateName(ParticipantCreateMessage participantCreateMessage) {
        participantValidator.validateDocumentExists(participantCreateMessage.document());
        participantValidator.validateIfNameIsDifferent(participantCreateMessage.document(), participantCreateMessage.name());
        participantProducerRabbit.sendUpdateNameParticipant(participantCreateMessage);
    }

    public void delete(ParticipantCreateMessage participantCreateMessage) {
        participantValidator.validateDocumentExists(participantCreateMessage.document());
        participantProducerRabbit.sendDeleteParticipant(participantCreateMessage);
    }

}
