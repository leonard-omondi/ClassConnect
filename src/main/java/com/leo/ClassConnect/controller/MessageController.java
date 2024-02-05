/*
- POST a message
Users can submit a new post by making a POST request to localhost:9000/classconnect/messages endpoint. The request body
must include a JSON representation of a message, excluding the message_id, and this message will be saved in the
database.
Successful message creation requires that the message_text is non-empty, has a maximum length of 255 characters, and
that the "posted_by" field references an existing user. When successful, the response will include a JSON representation
of the message, including its message_id, with a response status of 200. The newly created message will also be stored
in the database. If message creation fails, the response status will be 400 (Client error).

- GET all messages
Users can retrieve all messages by sending a GET request to the endpoint localhost:9000/classconnect/messages. The
response will contain a JSON representation of a list that includes all messages retrieved from the database. If there
are no messages, the list will be empty. The response status will consistently be 200 (the default).

- GET message by message_id
sers can retrieve a specific message by sending a GET request to the endpoint localhost:9000/classconnect/{message_id}.
The response will include a JSON representation of the message corresponding to the provided message_id. The response
body will be empty if the requested message does not exist. Regardless of whether the message is found or not, the
response status will consistently be 200 (the default).

- GET all messages by user_id
Users can retrieve all messages written by a specific user by sending a GET request to the
endpoint localhost:9000/accounts/{account_id}/messages. The response will include a JSON representation of a list
containing all messages posted by that user, retrieved from the database. The list will be empty if the user has not
posted any messages. The response status will consistently be 200 (the default).

- DELETE message by ID
Users can delete a message identified by its message ID through a DELETE request to the endpoint
DELETE localhost:9000/classconnect/{message_id}. Deleting an existing message will remove it from the database, and the
response will contain the number of updated rows, typically one if the message existed. The response status will be 200
(the default) in this case. If the message does not exist, the response status will still be 200, but the response body
will be empty. This aligns with the idempotent nature of the DELETE verb, ensuring that multiple calls to the DELETE
endpoint result in consistent responses.

 */
package com.leo.ClassConnect.controller;

import com.leo.ClassConnect.entity.Account;
import com.leo.ClassConnect.entity.Message;
import com.leo.ClassConnect.service.AccountService;
import com.leo.ClassConnect.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("classconnect")
public class MessageController {

    private final MessageService messageService;
    private final AccountService accountService;

    @Autowired
    public MessageController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<?> updateMessage(@PathVariable("message_id") Long message_id, @RequestBody Message messageToUpdate) {
        Message existingMessage = messageService.findMessageById(message_id);

        if (existingMessage != null) {
            String newMessageText = messageToUpdate.getMessage_text();
            if (newMessageText != null && !newMessageText.isBlank() && newMessageText.length() <= 255) {
                existingMessage.setMessage_text(newMessageText);
                messageService.saveMessage(existingMessage);
                return ResponseEntity.ok(existingMessage);
            } else {
                return ResponseEntity.badRequest().body("Invalid message_text");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("message/{message_id}")
    public ResponseEntity<?> updateMessage2(@PathVariable("message_id") Long message_id, @RequestBody Message messageToUpdate) {
        // Retrieve the existing message by its ID
        Message existingMessage = messageService.findMessageById(message_id);

        if (existingMessage != null) {
            String newMessageText = messageToUpdate.getMessage_text();

            if (newMessageText != null && !newMessageText.isBlank() && newMessageText.length() <= 255) {
                // Update the message text
                existingMessage.setMessage_text(newMessageText);

                // Save the updated message
                Message updatedMessage = messageService.saveMessage(existingMessage);

                // Return a response with the updated message
                return ResponseEntity.ok(updatedMessage);
            } else {
                // Return a 400 Bad Request response for invalid message_text
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid message_text");
            }
        } else {
            // Return a 404 Not Found response if the message ID does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");
        }
    }


    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable("message_id") Long message_id) {

        boolean deleted = messageService.deleteMessageById(message_id);

        if (deleted) {
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.ok("");
        }
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Object> findMessageById(@PathVariable("message_id") Long message_id) {
        Message message = messageService.findMessageById(message_id);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("accounts/{posted_by}/messages")
    public ResponseEntity<List<Message>> findAllMessagesByPosted_by(@PathVariable("posted_by") Long posted_by) {
        List<Message> messageList = messageService.findAllMessagesByPosted_by(posted_by);
        return ResponseEntity.ok(messageList);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> findAllMessages() {

        List<Message> messageList = messageService.findAllMessages();

        if (messageList.isEmpty()) {
            return ResponseEntity.ok(messageList);
        } else {
            return ResponseEntity.ok(messageList);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Object> createMessage(@RequestBody Message message) {

        if (message.getMessage_text() == null || message.getMessage_text().isBlank()
                || message.getMessage_text().length() > 255) {
            return ResponseEntity.status(400).body("The message body cannot be blank and must be under 255 characters");
        }

        Account postedByAccount = accountService.findAccountById(message.getPosted_by());
        if (postedByAccount == null) {
            return ResponseEntity.status(400).body("The posted_by user does not exist.");
        }

        message.setTime_posted(System.currentTimeMillis());
        Message savedMessage = messageService.saveMessage(message);
        return ResponseEntity.status(200).body(savedMessage);
    }

}


