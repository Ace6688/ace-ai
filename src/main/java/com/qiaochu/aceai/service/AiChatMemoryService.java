package com.qiaochu.aceai.service;

import com.qiaochu.aceai.model.dto.memory.ConversationMemoryQueryRequest;
import com.qiaochu.aceai.model.entity.AiChatMemory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Zhuanz
* @description 针对表【ai_chat_memory】的数据库操作Service
* @createDate 2025-08-02 17:14:47
*/
public interface AiChatMemoryService extends IService<AiChatMemory> {

    List<AiChatMemory> getMemoryByConversationId(ConversationMemoryQueryRequest conversationMemoryQueryRequest);

}
