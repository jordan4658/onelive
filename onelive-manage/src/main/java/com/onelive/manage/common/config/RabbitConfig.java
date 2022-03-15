package com.onelive.manage.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: rabbitmq配置
 * @author: muyu
 * @create: 2020-06-26 17:01
 **/
@Configuration
public class RabbitConfig {

    //广播模式交换机
    public static final String YD_EXCHANGE_FANOUT = "fl_sport_exchange_fanout";

    //主题订阅交换机
    public static final String EXCHANGE_TOPIC = "fl_sport_exchange_topic";

    //点对点队列交换机
    public static final String EXCHANGE_FANOUT_CHAT = "fl_sport_exchange_direct";

    //国家地址生成 交换机
    public static final String COUNTRY_EXCHANGE_TOPIC = "fl_sport_country_exchange_topic";

    /**
     * 队列多线程消费工厂，用户对消息顺序没有要求情况下使用
     *
     * @param configurer
     * @param connectionFactory
     * @return
     */
    @Bean("customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        int core = Runtime.getRuntime().availableProcessors();
        factory.setConcurrentConsumers(core);  //设置核心线程数
        factory.setMaxConcurrentConsumers(core * 2 + 1); //设置最大线程数
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
