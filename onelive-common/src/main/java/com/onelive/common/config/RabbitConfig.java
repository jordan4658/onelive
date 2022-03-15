package com.onelive.common.config;


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

    //公共广播模式交换机
    public static final String EXCHANGE_FANOUT = "fl_sport_exchange_fanout";

    //公共主题订阅交换机
    public static final String EXCHANGE_TOPIC = "fl_sport_exchange_topic";

    //公共点对点队列交换机
    public static final String EXCHANGE_DIRECT = "fl_sport_exchange_direct";

    //短信订阅交换机
    public static final String SMS_EXCHANGE_TOPIC = "fl_sport_sms_exchange_topic";

    public static final String LIVE_360_EXCHANGE_TOPIC = "fl_sport_live_360_exchange_topic";

    //代理邀请用户关系信息处理队列交换机
    public static final String AGENT_INVITE_USER_EXCHANGE_TOPIC = "fl_sport_invite_user_exchange_topic";

    //统计用户经验值 交换机
    public static final String EMPIRICAL_VALUE_EXCHANGE_TOPIC = "fl_sport_empirical_value_exchange_topic";
    //统计消费总金额 交换机
    public static final String EXPENSE_AMOUNT_EXCHANGE_TOPIC =  "fl_sport_expense_amount_exchange_topic";
    //重新检查系统消息, 看是否需要重新推送给用户
    public static final String REPUSH_USER_ID_MSG_EXCHANGE_TOPIC =  "repush_user_id_msg_exchange_topic";
    //重新检查系统消息, 看是否需要重新推送给用户
    public static final String REPUSH_USER_INFO_MSG_EXCHANGE_TOPIC =  "fl_sport_repush_user_info_msg_exchange_topic";


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
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        converter.setAlwaysConvertToInferredType(true);//支持序列化list
        return converter;
    }
}
