package br.com.rezultz.rpp.enroll.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "rpp.enroll.exchange";

    public static final String QUEUE_UPDATE = "rpp.enroll.participant.update";
    public static final String ROUTING_KEY_UPDATE = "participant.update";
    public static final String QUEUE_DELETE = "rpp.enroll.participant.delete";
    public static final String ROUTING_KEY_DELETE = "participant.delete";
    public static final String QUEUE_CREATE = "rpp.enroll.participant.create";
    public static final String ROUTING_KEY_CREATE = "participant.create";


    @Bean
    public DirectExchange participantExchange(){
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Binding participantCreateBinding(DirectExchange participantExchange){
        return BindingBuilder.bind(participantQueueCreate()).to(participantExchange).with(ROUTING_KEY_CREATE);
    }

    @Bean
    public Binding participantUpdateBinding(DirectExchange participantExchange){
        return BindingBuilder.bind(participantQueueUpdate()).to(participantExchange).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding participantDeleteBinding(DirectExchange participantExchange){
        return BindingBuilder.bind(participantQueueDelete()).to(participantExchange).with(ROUTING_KEY_DELETE);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Queue participantQueueCreate() {
        return new Queue(QUEUE_CREATE, true);
    }

    @Bean
    public Queue participantQueueUpdate() {
        return new Queue(QUEUE_UPDATE, true);
    }

    @Bean
    public Queue participantQueueDelete() {
        return new Queue(QUEUE_DELETE, true);
    }

    @Bean
    public Queue queueDelete() {
        return new Queue(QUEUE_DELETE, true);
    }

}