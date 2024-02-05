# ClassConnect SOCIAL MEDIA APP

## Getting Started

### Reference Documentation

This README provides an overview of the ClassConnect Social Media App project, a backend for a hypothetical social media platform built using the Spring framework.

## Background
ClassConnect Social Media App serves as the backend for a social media platform. It leverages the Spring framework, providing user account management and message handling features. The Spring framework offers automatic configuration and injection of various functionalities, including data persistence, endpoints, and CRUD operations.
In this hypothetical social media app, users can view all messages posted on the platform and messages posted by specific users. The backend is responsible for delivering the necessary data and handling user registrations, logins, message creation, updates, and deletions.

## Database Tables
Upon project startup, the following tables are initialized in the built-in MySQL database using the configuration details in the application.properties file.  

### Account Table
```
account_id integer primary key auto_increment, username varchar(255) not null unique, password varchar(255)

### Message Table
```
message_id integer primary key auto_increment, posted_by integer, message_text varchar(255), time_posted_epoch long, foreign key (posted_by) references Account(account_id)

## Spring Technical Requirements
To meet project requirements, the following Spring-related criteria must be fulfilled:
The project must leverage the Spring Boot Framework.
The project must include beans for AccountService, MessageService, AccountRepository, MessageRepository, and SocialMediaController classes.
AccountRepository and MessageRepository must be functional JPARepositories for the Account and Message entities.
The Spring Boot application must adhere to Spring MVC's default error message structure.
The project starts as a Spring Boot application with a valid application.properties file and database entities.

# User Stories

## User Story 1: User Registration
As a user, I can create a new account via a POST request to localhost:9000/register. The request body should contain a JSON representation of an Account, excluding the account_id.
Registration succeeds if the username is not blank, the password is at least four characters long, and no account with the same username already exists. Successful registration returns a JSON representation of the Account, including its account_id, with a response status of 200 OK.
In case of a duplicate username, the response status is 409 (Conflict).
If registration fails for any other reason, the response status is 400 (Client error).

## User Story 2: User Login
As a user, I can verify my login via a POST request to localhost:9000/login. The request body should contain a JSON representation of an Account.
A login succeeds if the provided username and password match an existing account in the database. A successful login returns a JSON representation of the Account, including its account_id, with a response status of 200 OK.
If login fails, the response status is 401 (Unauthorized).

## User Story 3: Message Creation
As a user, I can submit a new post via a POST request to localhost:9000/messages. The request body should contain a JSON representation of a message, excluding the message_id.
Message creation succeeds if the message_text is not blank, is under 255 characters, and the posted_by refers to an existing user. A successful creation returns a JSON representation of the message, including its message_id, with a response status of 200.
If message creation fails, the response status is 400 (Client error).

## User Story 4: Retrieve All Messages
As a user, I can retrieve all messages via a GET request to localhost:9000/messages.
The response body contains a JSON representation of a list containing all messages retrieved from the database. If there are no messages, the list is empty. The response status is always 200.

## User Story 5: Retrieve Message by ID
As a user, I can retrieve a message by its ID via a GET request to localhost:9000/messages/{message_id}.
The response body contains a JSON representation of the message identified by the message_id. If no such message exists, the response body is empty. The response status is always 200.

## User Story 6: Delete Message by ID
As a user, I can delete a message identified by its ID via a DELETE request to localhost:9000/messages/{message_id}.
Deleting an existing message removes it from the database. If the message exists, the response body contains the number of rows updated (1), and the response status is 200.
If the message does not exist, the response status is 200, and the response body is empty. DELETE is idempotent.

## User Story 7: Update Message Text by ID
As a user, I can update a message's text identified by its ID via a PATCH request to localhost:9000/messages/{message_id}. The request body should contain the new message_text.
Message update succeeds if the message_id exists and the new message_text is not blank and is not over 255 characters. A successful update returns the number of rows updated (1) in the response body, with a response status 200.
If the message update fails, the response status is 400 (Client error).

## User Story 8: Retrieve Messages by User
As a user, I can retrieve all messages posted by a particular user via a GET request to localhost:9000/accounts/{account_id}/messages.
The response body contains a JSON representation of a list containing all messages the specified user posts, retrieved from the database. If there are no messages, the list is empty. The response status is always 200.

## Spring Framework Utilization
The project was developed using the Spring framework, including dependency injection, autowiring functionality, and Spring annotations to meet the Spring Technical Requirements.