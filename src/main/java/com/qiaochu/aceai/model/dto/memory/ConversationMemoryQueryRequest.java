package com.qiaochu.aceai.model.dto.memory;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ConversationMemoryQueryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2002111181241259122L;
    /**
     * 会话id
     */
    private String id;

}
