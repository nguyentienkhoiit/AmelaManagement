package com.khoinguyen.amela.model.dto.position;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPositionDtoResponse {
    Long id;
    String name;
    String description;
    boolean status;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;
}
