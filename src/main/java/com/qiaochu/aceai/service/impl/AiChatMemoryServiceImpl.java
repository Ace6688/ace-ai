package com.qiaochu.aceai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiaochu.aceai.common.ErrorCode;
import com.qiaochu.aceai.exception.BusinessException;
import com.qiaochu.aceai.model.dto.memory.ConversationMemoryQueryRequest;
import com.qiaochu.aceai.model.entity.AiChatMemory;
import com.qiaochu.aceai.service.AiChatMemoryService;
import com.qiaochu.aceai.mapper.AiChatMemoryMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Zhuanz
* @description 针对表【ai_chat_memory】的数据库操作Service实现
* @createDate 2025-08-02 17:14:47
*/
@Service
public class AiChatMemoryServiceImpl extends ServiceImpl<AiChatMemoryMapper, AiChatMemory>
    implements AiChatMemoryService{

    @Override
    public List<AiChatMemory> getMemoryByConversationId(ConversationMemoryQueryRequest conversationMemoryQueryRequest) {
        if (StringUtils.isAnyBlank(conversationMemoryQueryRequest.getId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        QueryWrapper<AiChatMemory> wrapper = new QueryWrapper<>();
        wrapper.eq("conversation_id", conversationMemoryQueryRequest.getId());
        return this.list(wrapper);
    }
}




