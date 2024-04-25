package com.khoinguyen.amela.model.dto.department;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDtoRequest {
    String name;
    String description;
}
