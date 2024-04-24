package com.khoinguyen.amela.model.dto.department;

import lombok.*;

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
}
