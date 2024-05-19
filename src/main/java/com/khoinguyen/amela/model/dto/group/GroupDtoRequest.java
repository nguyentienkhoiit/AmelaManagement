package com.khoinguyen.amela.model.dto.group;

import com.khoinguyen.amela.util.StringUtil;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDtoRequest {
    Long id;

    @Size(min = 4, max = 30, message = "Length must be between {min} and {max} characters")
    String name;

    String description;

    @Size(min = 10, message = "Length must be greater than or equal to {min}")
    String listMail;

    public String getName() {
        return StringUtil.formatString(name, true);
    }

    public Long getId() {
        return id == null ? 0 : id;
    }
}
