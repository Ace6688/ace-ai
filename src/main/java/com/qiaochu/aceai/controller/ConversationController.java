package com.qiaochu.aceai.controller;

import com.qiaochu.aceai.annotation.AuthCheck;
import com.qiaochu.aceai.common.BaseResponse;
import com.qiaochu.aceai.common.DeleteRequest;
import com.qiaochu.aceai.common.ErrorCode;
import com.qiaochu.aceai.common.ResultUtils;
import com.qiaochu.aceai.constant.UserConstant;
import com.qiaochu.aceai.exception.BusinessException;
import com.qiaochu.aceai.model.dto.conversation.ConversationAddRequest;
import com.qiaochu.aceai.model.dto.conversation.ConversationDeleteRequest;
import com.qiaochu.aceai.model.dto.user.UserRegisterRequest;
import com.qiaochu.aceai.model.entity.Conversation;
import com.qiaochu.aceai.model.entity.User;
import com.qiaochu.aceai.service.ConversationService;
import com.qiaochu.aceai.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conversation")
@Slf4j
public class ConversationController {


    @Resource
    private ConversationService conversationService;
    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<String> addConversation(@RequestBody ConversationAddRequest conversationAddRequest, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        String conversationId=conversationService.addConversation(conversationAddRequest, loginUser.getId());
        return ResultUtils.success(conversationId);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody ConversationDeleteRequest deleteRequest ) {
        boolean b = conversationService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }
    @PostMapping("/get")
    public BaseResponse<List<Conversation>> getConversationByUser(HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        List<Conversation> conversations = conversationService.getByUserId(loginUser.getId());
        return ResultUtils.success(conversations);
    }


}
