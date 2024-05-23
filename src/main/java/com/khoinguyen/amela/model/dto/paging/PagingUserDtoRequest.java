package com.khoinguyen.amela.model.dto.paging;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PagingUserDtoRequest extends PagingDtoRequest {
    Long departmentId;
    Long positionId;
    Long groupId;
}
