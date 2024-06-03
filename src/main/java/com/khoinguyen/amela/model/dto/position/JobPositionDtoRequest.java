package com.khoinguyen.amela.model.dto.position;

import jakarta.validation.constraints.Size;

import com.khoinguyen.amela.util.StringUtil;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPositionDtoRequest {
    Long id;

    @Size(min = 2, max = 30, message = "{validation.length.between}")
    String name;

    String description;

    public String getName() {
        return StringUtil.formatString(name, true);
    }

    public Long getId() {
        return id == null ? 0 : id;
    }
}
