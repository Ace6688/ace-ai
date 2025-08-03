package com.qiaochu.aceai.service;

import com.qiaochu.aceai.model.dto.history.HistoryMessageQueryRequest;
import com.qiaochu.aceai.model.entity.HistoryMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Zhuanz
* @description 针对表【history_message】的数据库操作Service
* @createDate 2025-08-03 10:25:51
*/
public interface HistoryMessageService extends IService<HistoryMessage> {

    List<HistoryMessage> getHistoryMessageByConversationId(HistoryMessageQueryRequest historyMessageQueryRequest);

    boolean addHistoryMessage(String text, String role, String conversationId);
}
