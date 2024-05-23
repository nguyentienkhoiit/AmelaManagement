package com.khoinguyen.amela.model.dto.group;

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
public class GroupDtoResponse {
    Long id;
    String name;
    String description;
    List<Long> usersIds;
    String createdAt;
    String updateAt;
    boolean status;
    Long member;
}
