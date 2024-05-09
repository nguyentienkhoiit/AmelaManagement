package com.khoinguyen.amela.model.dto.messages;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageScheduleDtoResponse {
    Long id;
    String subject;
    String message;
    String senderName;
    Long viewers;
    boolean status;
    String publishAt;
    String createdAt;
    Long createdBy;
    String updateAt;
    Long updateBy;
    boolean choice;
    boolean isPublished;
}
