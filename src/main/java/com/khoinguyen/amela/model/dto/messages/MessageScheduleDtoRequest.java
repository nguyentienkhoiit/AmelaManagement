package com.khoinguyen.amela.model.dto.messages;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
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

    @Size(min = 10, max = 100, message = "Length must be between {min} and {max} characters")
    String subject;

    @Size(min = 10, message = "Length must be greater than or equal to {min}")
    String message;

    @Size(max = 50, message = "Length must be smaller than or equal to {max}")
    String senderName;

    @NotNull(message = "Publish at is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime publishAt;

    String listMail;
    Long groupId;
    boolean choice;
}
