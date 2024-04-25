package com.khoinguyen.amela.model.dto.role;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDtoResponse {
    Long id;
    String name;
    String description;
    boolean status;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;
}
