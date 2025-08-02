package com.qiaochu.aceai.service;

import com.qiaochu.aceai.model.dto.conversation.ConversationAddRequest;
import com.qiaochu.aceai.model.entity.Conversation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ConversationService extends IService<Conversation> {

    String addConversation(ConversationAddRequest conversationAddRequest, Long id);

    List<Conversation> getByUserId(Long id);
}
