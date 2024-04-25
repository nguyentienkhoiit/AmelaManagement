package com.khoinguyen.amela.model.dto.department;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDtoResponse {
    Long id;
    String name;
    String description;
    boolean status;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;
}
