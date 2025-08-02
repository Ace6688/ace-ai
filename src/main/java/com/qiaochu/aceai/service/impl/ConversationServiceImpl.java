package com.qiaochu.aceai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiaochu.aceai.common.ErrorCode;
import com.qiaochu.aceai.exception.BusinessException;
import com.qiaochu.aceai.model.dto.conversation.ConversationAddRequest;
import com.qiaochu.aceai.model.entity.Conversation;
import com.qiaochu.aceai.model.entity.User;
import com.qiaochu.aceai.service.ConversationService;
import com.qiaochu.aceai.mapper.ConversationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
    implements ConversationService{

    @Override
    public String addConversation(ConversationAddRequest conversationAddRequest, Long userId) {
        if (StringUtils.isAnyBlank(conversationAddRequest.getConversationName(),conversationAddRequest.getId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 会话id不能重复
        QueryWrapper<Conversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", conversationAddRequest.getId());
        long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "会话id重复");
        }
        Conversation conversation = new Conversation();
        conversation.setId(conversationAddRequest.getId());
        conversation.setConversationName(conversationAddRequest.getConversationName());
        conversation.setUserId(userId);
        boolean save = this.save(conversation);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建会话失败，数据库错误");
        }
        return conversation.getId();
    }

    @Override
    public List<Conversation> getByUserId(Long id) {
        QueryWrapper<Conversation> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", id);
        return this.list(wrapper);
    }

}




