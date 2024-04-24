package com.khoinguyen.amela.model.dto.position;

import lombok.*;

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
}
