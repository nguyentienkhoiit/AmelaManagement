package com.khoinguyen.amela.model.dto.messages;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

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
    List<Long> usersIds;
    Long groupId;
    boolean choice;
}
