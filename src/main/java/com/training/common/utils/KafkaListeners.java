package com.training.common.utils;

import com.training.dto.kafka.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(
            topics = "hello",
            groupId = "groupId",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenGroupFoo(Messages message) {
        logger.info("Received Message in topic hello, username: {}", message.getName());
        logger.info("Received Message in topic hello, msg: {}", message.getMsg());
    }

}
