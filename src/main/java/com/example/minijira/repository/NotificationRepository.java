package com.example.minijira.repository;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minijira.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
    Optional<List<Notification>> findAllByUserId(Long userId);
}
