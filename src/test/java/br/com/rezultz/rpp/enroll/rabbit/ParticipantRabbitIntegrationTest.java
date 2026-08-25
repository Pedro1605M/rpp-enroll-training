package br.com.rezultz.rpp.enroll.rabbit;

import br.com.rezultz.rpp.enroll.entity.Participant;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.message.producer.ParticipantProducerRabbit;
import br.com.rezultz.rpp.enroll.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Optional;

@SpringBootTest
public class ParticipantRabbitIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", () -> "localhost");
        registry.add("spring.rabbitmq.port", () -> 5672);
        registry.add("spring.rabbitmq.username", () -> "guest");
        registry.add("spring.rabbitmq.password", () -> "guest");

        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.H2Dialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private ParticipantProducerRabbit participantProducerRabbit;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    @DisplayName("Deve disparar mensagem no RabbitMQ real e salvar o participante via Consumer")
    void shouldProcessParticipantCreateUntilEnd(){
        String documento = "123456789003333";
        ParticipantCreateMessage message = new ParticipantCreateMessage(
                "Pedro Costa", "Pedro Tech", documento, "CPF", "Empresa X"
        );

        participantProducerRabbit.sendCreateParticipant(message);

        await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            Optional<Participant> participantOpt = participantRepository.findByDocument(documento);

            assertTrue(participantOpt.isPresent());
            assertEquals("Pedro Costa", participantOpt.get().getName());
        });
    }
}