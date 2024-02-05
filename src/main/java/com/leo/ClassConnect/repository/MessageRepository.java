package com.leo.ClassConnect.repository;

import com.leo.ClassConnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message m WHERE m.posted_by= :posted_by", nativeQuery = true)
    List<Message> findMessagesByPosted_by(Long posted_by);

}
