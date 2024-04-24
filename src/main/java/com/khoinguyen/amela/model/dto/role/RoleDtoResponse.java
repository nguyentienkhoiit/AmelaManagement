package com.khoinguyen.amela.model.dto.role;

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
}
