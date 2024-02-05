package com.leo.ClassConnect.service;

import com.leo.ClassConnect.entity.Message;
import com.leo.ClassConnect.repository.AccountRepository;
import com.leo.ClassConnect.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;


    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;

    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message findMessageById(Long message_id) {
        return messageRepository.findById(message_id).get();
    }

    @Override
    public boolean deleteMessageById(Long message_id) {
        Optional<Message> messageOptional = messageRepository.findById(message_id);
        if (messageOptional.isPresent()) {
            messageRepository.deleteById(message_id);
            return true; // Message found and deleted
        } else {
            return false; // Message not found
        }
    }

    @Override
    public List<Message> findAllMessagesByPosted_by(Long posted_by) {
        return messageRepository.findMessagesByPosted_by(posted_by);
    }


}
