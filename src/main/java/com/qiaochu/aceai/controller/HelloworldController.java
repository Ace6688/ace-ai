package com.qiaochu.aceai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.memory.jdbc.MysqlChatMemoryRepository;
import com.qiaochu.aceai.common.ErrorCode;
import com.qiaochu.aceai.exception.BusinessException;
import com.qiaochu.aceai.service.HistoryMessageService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/helloworld")
public class HelloworldController {
  private static final String DEFAULT_PROMPT = "你是一个博学的智能聊天助手，请根据用户提问回答！";

  private final ChatClient dashScopeChatClient;
  @Resource
  private HistoryMessageService historyMessageService;

  public HelloworldController(JdbcTemplate jdbcTemplate, ChatClient.Builder chatClientBuilder) {
    //基于mysql的聊天上下文
    ChatMemoryRepository chatMemoryRepository = MysqlChatMemoryRepository.mysqlBuilder()
            .jdbcTemplate(jdbcTemplate)
            .build();
    ChatMemory chatMemory = MessageWindowChatMemory.builder()
            .chatMemoryRepository(chatMemoryRepository)
            .maxMessages(20)
            .build();

    this.dashScopeChatClient = chatClientBuilder
        .defaultSystem(DEFAULT_PROMPT)
        // 实现 Logger 的 Advisor
        .defaultAdvisors(
            new SimpleLoggerAdvisor()
        )
        // 注册Advisor
        .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
        // 设置 ChatClient 中 ChatModel 的 Options 参数
        .defaultOptions(
            DashScopeChatOptions.builder()
                .withTopP(0.7)
                .build()
        )
        .build();
  }

  /**
  * ChatClient 简单调用
  */
//  @GetMapping("/simple/chat")
//  public String simpleChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？")String query) {
//
//    return dashScopeChatClient.prompt(query).call().content();
//  }
  @GetMapping("/simple/chat")
  public String simpleChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？")String query,
                           @RequestParam(value = "conversationId", defaultValue = "1") String conversationId) {

    boolean save = historyMessageService.addHistoryMessage(query, "user", conversationId);
    if (!save) {
      throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加历史记录失败，数据库错误");
    }
    String response = dashScopeChatClient.prompt(query)
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
            .call().content();
    save = historyMessageService.addHistoryMessage(response, "assistant", conversationId);
    if (!save) {
      throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加历史记录失败，数据库错误");
    }
    return response;
  }
  /**
   * ChatClient 流式调用
   */
  @GetMapping("/stream/chat")
  public Flux<String> streamChat(@RequestParam(value = "query", defaultValue = "你好，很高兴认识你，能简单介绍一下自己吗？")String query, HttpServletResponse response) {

    response.setCharacterEncoding("UTF-8");
    return dashScopeChatClient.prompt(query).stream().content();
  }
}