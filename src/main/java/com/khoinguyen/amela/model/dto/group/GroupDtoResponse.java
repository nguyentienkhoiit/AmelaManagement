package com.khoinguyen.amela.model.dto.group;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDtoResponse {
    Long id;
    String name;
    String description;
    String listMail;
    String createdAt;
    boolean status;
}
