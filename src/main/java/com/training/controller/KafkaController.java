package com.training.controller;

import com.training.common.constant.Constant;
import com.training.dto.ResponseJson;
import com.training.dto.kafka.Messages;
import com.training.service.kafka.KafkaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaService kafkaService;

    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseJson<Boolean>> sendMessage(@RequestBody Messages msg) {
        kafkaService.sendMessage(msg);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

}
