package com.ngng.api.chat.repository;

import com.ngng.api.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllChatByProductProductId(Long productId);
}
