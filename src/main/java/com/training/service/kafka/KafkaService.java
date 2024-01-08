package com.training.service.kafka;

import com.training.dto.kafka.Messages;

public interface KafkaService {

    void sendMessage(Messages msg);

}
