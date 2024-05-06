package com.khoinguyen.amela.model.dto.messages;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageScheduleDtoRequest {
    Long id;
    @Length(min = 3, message = "Length greater than 4")
    String subject;

    @Length(min = 10, message = "Length greater than 9")
    String message;

    String senderName;

    @NotNull(message = "Send time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime publishAt;
    String listMail;
    Long groupId;
    boolean choice;
}
