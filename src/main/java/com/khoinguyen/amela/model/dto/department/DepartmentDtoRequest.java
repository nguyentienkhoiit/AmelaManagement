package com.khoinguyen.amela.model.dto.department;

import com.khoinguyen.amela.util.StringUtil;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentDtoRequest {
    Long id;

    @Size(min = 4, max = 30, message = "{validation.length.between}")
    String name;

    String description;

    public String getName() {
        return StringUtil.formatString(name, true);
    }

    public Long getId() {
        return id == null ? 0 : id;
    }
}
