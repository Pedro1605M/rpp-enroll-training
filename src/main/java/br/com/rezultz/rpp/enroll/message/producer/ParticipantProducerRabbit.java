package br.com.rezultz.rpp.enroll.message.producer;

import br.com.rezultz.rpp.enroll.config.RabbitMQConfig;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ParticipantProducerRabbit {
    private final RabbitTemplate rabbitTemplate;

    public void sendCreateParticipant(ParticipantCreateMessage participantCreateMessage){
        log.info("Publicando Participante, documento = {}", participantCreateMessage.document());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_CREATE, participantCreateMessage);
    }

    public void sendUpdateNameParticipant(ParticipantCreateMessage participantCreateMessage){
        log.info("Atualizando o nome para ({}) do particpante com o documento = {}", participantCreateMessage.name(), participantCreateMessage.document());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_UPDATE, participantCreateMessage);
    }

    public void sendDeleteParticipant(ParticipantCreateMessage participantCreateMessage){
        log.info("Deletando Participante do documento = {}", participantCreateMessage.document());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY_DELETE, participantCreateMessage);
    }
}
