package com.khoinguyen.amela.model.dto.position;

import com.khoinguyen.amela.util.StringUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPositionDtoRequest {
    Long id;

    @Length(min = 4, message = "Length greater than 5")
    String name;

    String description;

    public String getName() {
        return StringUtil.formatString(name, true);
    }

    public Long getId() {
        return id == null ? 0 : id;
    }
}
