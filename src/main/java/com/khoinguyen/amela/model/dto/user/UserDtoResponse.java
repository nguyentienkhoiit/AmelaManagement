package com.khoinguyen.amela.model.dto.user;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {
    Long id;
    String code;
    String email;
    String firstname;
    String lastname;
    int gender;
    String username;
    String phone;
    String address;
    boolean status;
    String dateOfBirth;
    DepartmentDtoResponse department;
    RoleDtoResponse role;
    JobPositionDtoResponse position;
    boolean enabled;
    boolean activated;
}
