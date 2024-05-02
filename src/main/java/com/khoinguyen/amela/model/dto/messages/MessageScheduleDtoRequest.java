package com.khoinguyen.amela.model.dto.messages;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageScheduleDtoRequest {
    String subject;
    String message;
    String senderName;
    LocalDateTime sendTime;
}
