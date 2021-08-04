package kz.roma.fortsbackoffice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitConfig {


    public static final String fortsBackOfficeExch = "forts_backOffice";

    public static final String instrQueue = "instrument_queue";
    public static final String ordersQueue = "order_queue";
    public static final String dealsQueue = "deals_queue";

    public static final String instrRoutingKEY = "FORTS.BACKOFFICE.INSTRUMENTS";
    public static final String ordersRoutingKEY = "FORTS.BACKOFFICE.ORDERS";
    public static final String dealsRoutingKEY = "FORTS.BACKOFFICE.DEALS";


    @Bean
    public Queue instrQueue(){
        return new Queue(instrQueue, false);
    }

    @Bean
    public Queue ordersQueue(){
        return new Queue(ordersQueue, false);
    }

    @Bean
    public Queue dealsQueue(){
        return new Queue(dealsQueue, false);
    }

    @Bean
    public TopicExchange fortsBackOfficeExch() {
        return new TopicExchange(fortsBackOfficeExch, false, false);
    }

    @Bean
    public Binding FortsBackOfficeExchBindInstrQueue(Queue instrQueue, TopicExchange fortsBackOfficeExch) {
        return BindingBuilder.bind(instrQueue).to(fortsBackOfficeExch).with(instrRoutingKEY);
    }

    @Bean
    public Binding FortsBackOfficeExchBindOrdersQueue(Queue ordersQueue, TopicExchange fortsBackOfficeExch) {
        return BindingBuilder.bind(ordersQueue).to(fortsBackOfficeExch).with(ordersRoutingKEY);
    }

    @Bean
    public Binding FortsBackOfficeExchBindDealsQueue(Queue dealsQueue, TopicExchange fortsBackOfficeExch) {
        return BindingBuilder.bind(dealsQueue).to(fortsBackOfficeExch).with(dealsRoutingKEY);}
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

