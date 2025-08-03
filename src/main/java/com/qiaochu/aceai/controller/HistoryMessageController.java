package com.qiaochu.aceai.controller;

import com.qiaochu.aceai.common.BaseResponse;
import com.qiaochu.aceai.common.ResultUtils;
import com.qiaochu.aceai.model.dto.history.HistoryMessageQueryRequest;
import com.qiaochu.aceai.model.entity.HistoryMessage;
import com.qiaochu.aceai.service.HistoryMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@Slf4j
public class HistoryMessageController {

    @Resource
    private HistoryMessageService historyMessageService;
    @PostMapping("/")
    public BaseResponse<List<HistoryMessage>> getMemoryByConversationId(@RequestBody HistoryMessageQueryRequest historyMessageQueryRequest){

        List<HistoryMessage> aiChatMemoryList=historyMessageService.getHistoryMessageByConversationId(historyMessageQueryRequest);
        return ResultUtils.success(aiChatMemoryList);
    }
}
