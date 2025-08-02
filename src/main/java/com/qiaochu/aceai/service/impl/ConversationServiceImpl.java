package com.qiaochu.aceai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiaochu.aceai.model.entity.Conversation;
import com.qiaochu.aceai.service.ConversationService;
import com.qiaochu.aceai.mapper.ConversationMapper;
import org.springframework.stereotype.Service;


@Service
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation>
    implements ConversationService{

}




