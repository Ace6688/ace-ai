package com.qiaochu.aceai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiaochu.aceai.common.ErrorCode;
import com.qiaochu.aceai.exception.BusinessException;
import com.qiaochu.aceai.model.dto.history.HistoryMessageQueryRequest;
import com.qiaochu.aceai.model.entity.HistoryMessage;
import com.qiaochu.aceai.service.HistoryMessageService;
import com.qiaochu.aceai.mapper.HistoryMessageMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Zhuanz
* @description 针对表【history_message】的数据库操作Service实现
* @createDate 2025-08-03 10:25:51
*/
@Service
public class HistoryMessageServiceImpl extends ServiceImpl<HistoryMessageMapper, HistoryMessage>
    implements HistoryMessageService{

    @Override
    public List<HistoryMessage> getHistoryMessageByConversationId(HistoryMessageQueryRequest historyMessageQueryRequest) {
        if (StringUtils.isAnyBlank(historyMessageQueryRequest.getId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        QueryWrapper<HistoryMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("conversationId", historyMessageQueryRequest.getId());
        return this.list(wrapper);

    }

    @Override
    public boolean addHistoryMessage(String text, String role, String conversationId) {
        HistoryMessage historyMessage = new HistoryMessage();
        historyMessage.setContent(text);
        historyMessage.setType(role);
        historyMessage.setConversationId(conversationId);
        historyMessage.setTimestamp(new Date());
        return this.save(historyMessage);
    }
}




