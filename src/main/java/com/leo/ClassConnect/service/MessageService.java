package com.leo.ClassConnect.service;

import com.leo.ClassConnect.entity.Message;

import java.util.List;

public interface MessageService {


    Message saveMessage(Message message);

    List<Message> findAllMessages();

    Message findMessageById(Long message_id);

    boolean deleteMessageById(Long message_id);

    List<Message> findAllMessagesByPosted_by(Long posted_by);
}
