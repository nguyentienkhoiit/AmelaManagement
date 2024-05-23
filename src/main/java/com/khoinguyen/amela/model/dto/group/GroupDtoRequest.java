package com.khoinguyen.amela.model.dto.group;

import com.khoinguyen.amela.util.StringUtil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDtoRequest {
    Long id;

    @Size(min = 4, max = 30, message = "{validation.length.between}")
    String name;

    String description;

    @NotEmpty(message = "{validation.required}")
    List<Long> usersIds;

    public String getName() {
        return StringUtil.formatString(name, true);
    }

    public Long getId() {
        return id == null ? 0 : id;
    }
}
