package com.zenika.nightclazz.awesomechat.api;

import com.zenika.nightclazz.awesomechat.model.Message;
import com.zenika.nightclazz.awesomechat.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public Iterable<Message> readAllMessages() {
        return messageRepository.findAll();
    }

    @PostMapping
    public void createMessage(@RequestBody String body) {
        LocalDateTime creationDate = LocalDateTime.now();
        Message message = new Message(body, creationDate);
        messageRepository.save(message);
    }
}
