package com.khoinguyen.amela.model.dto.group;

import com.khoinguyen.amela.util.StringUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDtoRequest {
    Long id;

    @Length(min = 4, message = "Length greater than 5")
    String name;

    String description;

    @Length(min = 9, message = "Length greater than 10")
    String listMail;

    public String getName() {
        return StringUtil.formatString(name, true);
    }
}
