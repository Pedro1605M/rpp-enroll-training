
package br.com.rezultz.rpp.enroll.validator;

import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ParticipantValidatorTest {
    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private ParticipantValidator participantValidator;

    private final String DOCUMENT = "1123343425";

    @Nested
    @DisplayName("Validação: validateDocumentDoesNotExist")
    class ValidateDocumentDoesNotExist {

        @Test
        @DisplayName("Deve passar sem exceção quando o documento NÃO existir no banco (pode criar)")
        void shouldNotThrowExceptionWhenDocumentDoesNotExist() {
            when(participantRepository.existsByDocument(DOCUMENT)).thenReturn(false);

            assertThatCode(() -> participantValidator.validateDocumentDoesNotExist(DOCUMENT))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Deve lançar exceção quando o documento JÁ existir no banco (não pode criar)")
        void shouldThrowExceptionWhenDocumentAlreadyExists() {
            when(participantRepository.existsByDocument(DOCUMENT)).thenReturn(true);

            assertThatThrownBy(() -> participantValidator.validateDocumentDoesNotExist(DOCUMENT))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Já existe um participante cadastrado com o documento: " + DOCUMENT);
        }
    }

    @Nested
    @DisplayName("Validação: validateDocumentExists")
    class ValidateDocumentExists {

        @Test
        @DisplayName("Deve passar sem exceção quando o documento existir no banco")
        void shouldNotThrowExceptionWhenDocumentExists() {

        }

        @Test
        @DisplayName("Deve lançar exceção quando o documento NÃO existir no banco")
        void shouldThrowExceptionWhenDocumentDoesNotExist() {

        }
    }

    @Nested
    @DisplayName("Validação: validateIfNameIsDifferent")
    class ValidateIfNameIsDifferent {

        @Test
        @DisplayName("Deve passar quando o novo nome for diferente do nome atual no banco")
        void shouldNotThrowExceptionWhenNameIsDifferent() {

        }

        @Test
        @DisplayName("Deve lançar exceção quando o novo nome for IDÊNTICO ao nome cadastrado (ignorando case/espaços)")
        void shouldThrowExceptionWhenNameIsIdentical() {

        }

        @Test
        @DisplayName("Deve lançar exceção caso o participante nem seja encontrado pelo documento")
        void shouldThrowExceptionWhenParticipantNotFoundForNameValidation() {

        }
    }
}




