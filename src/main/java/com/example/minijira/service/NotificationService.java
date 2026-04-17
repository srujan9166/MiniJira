package com.example.minijira.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.minijira.dto.notificationDTO.NotificationResponseDTO;
import com.example.minijira.dto.notificationDTO.NotificationSendDTO;
import com.example.minijira.model.Notification;
import com.example.minijira.model.User;
import com.example.minijira.repository.NotificationRepository;
import com.example.minijira.repository.UserRepository;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final AuthServiceImpl authServiceImpl;
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, AuthServiceImpl authServiceImpl) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.authServiceImpl = authServiceImpl;
    }
    @Async
    public  String sendNotification(Long userId, NotificationSendDTO notificationSendDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Notification notification = new Notification();
        notification.setMessage(notificationSendDTO.getMessage());
        notification.setRead(false);
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);

       
        return "Notification sent to " + user.getUsername();
    }
    public  String markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
        return "Notification marked as read";
       
    }
    public  String markAllAsRead(Long notificationId) {
        User currentUser = authServiceImpl.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No authenticated user found");
        }
       List<Notification> notifications = notificationRepository.findAllByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No notifications found for user"));
       
                 notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);

        return "All notifications marked as read";



        
    }
    public  List<NotificationResponseDTO> getAllNotifications() {
        User currentUser = authServiceImpl.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No authenticated user found");
        }
        List<Notification> notifications = notificationRepository.findAllByUserId(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("No notifications found for user"));
        return notifications.stream()
                .map(notification -> new NotificationResponseDTO(
                        notification.getId(),
                        notification.getUser().getUsername(),
                        notification.getMessage(),
                        notification.isRead(),
                        notification.getCreatedAt()
                )).toList();
    }


}
