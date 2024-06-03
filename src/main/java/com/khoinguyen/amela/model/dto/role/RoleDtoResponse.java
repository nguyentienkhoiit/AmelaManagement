package com.khoinguyen.amela.model.dto.role;

import java.time.LocalDateTime;

import lombok.*;

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
