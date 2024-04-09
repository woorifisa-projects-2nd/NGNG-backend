package com.ngng.api.category.service;

import com.ngng.api.chat.ReadChatResponseDTO.ReadChatResponseDTO;
import com.ngng.api.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;
    public List<ReadChatResponseDTO> readAllChatByProductId(Long productId){
        return chatRepository.findAllChatByProductId(productId).stream().map(chat -> {
            return ReadChatResponseDTO.builder()
                    .id(chat.getId())
                    .message(chat.getMessage())
                    .userId(chat.getUser().getUserId())
                    .userName(chat.getUser().getName())
                    .userNickName(chat.getUser().getNickname())
                    .createdAt(chat.getCreatedAt())
                    .build();
        }).toList();
    }

}
