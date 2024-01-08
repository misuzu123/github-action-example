package com.training.service.kafka;

import com.training.dto.kafka.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService{

    @Autowired
    private KafkaTemplate<String, Messages> kafkaTemplate;

    public void sendMessage(Messages msg) {
        kafkaTemplate.send("hello", msg);
    }

}
