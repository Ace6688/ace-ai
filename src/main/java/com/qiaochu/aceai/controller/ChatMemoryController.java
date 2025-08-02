package com.qiaochu.aceai.controller;


import com.qiaochu.aceai.common.BaseResponse;
import com.qiaochu.aceai.common.ResultUtils;
import com.qiaochu.aceai.model.dto.memory.ConversationMemoryQueryRequest;
import com.qiaochu.aceai.model.entity.AiChatMemory;
import com.qiaochu.aceai.service.AiChatMemoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/memory")
@Slf4j
public class ChatMemoryController {

    @Resource
    private AiChatMemoryService aiChatMemoryService;
    @PostMapping("/")
    public BaseResponse<List<AiChatMemory>> getMemoryByConversationId(@RequestBody ConversationMemoryQueryRequest conversationMemoryQueryRequest){

        List<AiChatMemory> aiChatMemoryList=aiChatMemoryService.getMemoryByConversationId(conversationMemoryQueryRequest);
        return ResultUtils.success(aiChatMemoryList);
    }

}
