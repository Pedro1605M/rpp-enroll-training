package br.com.rezultz.rpp.enroll.validator;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipantValidator {
    private final ParticipantRepository participantRepository;

    public void validateDocumentExists(String document){
        boolean existsDocument = participantRepository.existsByDocument(document);
        if (!existsDocument) {
            throw new IllegalArgumentException("Nenhum participante encontrado com o documento: " + document);
        }
    }

    public void validateDocumentDoesNotExist(String document) {
        if (participantRepository.existsByDocument(document)){
            throw new IllegalArgumentException("Já existe um participante cadastrado com o documento: " + document);
        }
    }

    public void validateIfNameIsDifferent(String document, String newName){
        Participant participant = participantRepository.findByDocument(document)
                .orElseThrow(() -> new IllegalArgumentException("Nenhum participante encontrado com o documento: " + document));
        if (participant.getName().trim().equalsIgnoreCase(newName.trim())) {
            throw new IllegalArgumentException("O novo nome informado é idêntico ao nome já cadastrado.");
        }
    }
}
