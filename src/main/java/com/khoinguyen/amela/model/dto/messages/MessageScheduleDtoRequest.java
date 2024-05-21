package com.khoinguyen.amela.model.dto.messages;

import jakarta.validation.constraints.NotBlank;
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

    @Size(min = 10, max = 100, message = "{validation.length.between}")
    String subject;

    @NotBlank(message = "{validation.required}")
    String message;

    @Size(max = 50, message = "{validation.length.max}")
    String senderName;

    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime publishAt;

    String listMail;
    Long groupId;
    boolean choice;
}
