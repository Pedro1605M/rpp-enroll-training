package br.com.rezultz.rpp.enroll.message.consumer;

import br.com.rezultz.rpp.enroll.config.RabbitMQConfig;
import br.com.rezultz.rpp.enroll.message.content.ParticipantCreateMessage;
import br.com.rezultz.rpp.enroll.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ParticipantConsumerRabbit {
    private final ParticipantService participantService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CREATE)
    public void consumerParticipantCreate(ParticipantCreateMessage participantCreateMessage){
        log.info("Partcipante criado do documento: = {} foi consumido", participantCreateMessage.document());
        participantService.createRabbit(participantCreateMessage);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_UPDATE)
    public void consumerParticipantUpdate(ParticipantCreateMessage participantCreateMessage){
        log.info("Partcipante atualizado do documento: = {} foi consumido", participantCreateMessage.document());
        participantService.updateNameRabbit(participantCreateMessage);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELETE)
    public void consumerParticipantDelete(ParticipantCreateMessage participantCreateMessage){
        log.info("Partcipante deletado do documento: = {} foi consumido", participantCreateMessage.document());
        participantService.logicDelete(participantCreateMessage);
    }


}
