package com.khoinguyen.amela.model.dto.messages;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageScheduleUpdateResponse {
    Long id;
    String subject;

    String message;

    String senderName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime publishAt;
    boolean isPublished = Boolean.FALSE;
    String listMail;
    Long groupId;
    boolean choice;
}
