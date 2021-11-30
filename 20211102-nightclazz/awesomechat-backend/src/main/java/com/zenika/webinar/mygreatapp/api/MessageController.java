package com.zenika.webinar.mygreatapp.api;

import com.zenika.webinar.mygreatapp.model.Message;
import com.zenika.webinar.mygreatapp.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/*
 * TODO-05 : Review this class
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public Iterable<Message> readAllMessages() {
        return messageRepository.findAllByOrderByCreationDateDesc();
    }

    @PostMapping
    public void createMessage(@RequestBody String body) {
        LocalDateTime creationDate = LocalDateTime.now();
        Message message = new Message(body, creationDate);
        messageRepository.save(message);
    }
}
