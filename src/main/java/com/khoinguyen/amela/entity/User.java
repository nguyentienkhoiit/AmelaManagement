package com.khoinguyen.amela.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@Table(name = "user_tbl")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String code;

    @Column(unique = true, nullable = false)
    String email;

    String avatar;

    @Column(unique = true, nullable = false)
    String username;

    String firstname;

    String lastname;

    LocalDate dateOfBirth;

    String address;

    String password;

    boolean enabled = false;

    boolean activated = false;

    int gender;

    String phone;

    LocalDateTime createdAt;

    Long createdBy;

    LocalDateTime updateAt;

    Long updateBy;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    Role role;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    Department department;

    @OneToMany(mappedBy = "user")
    List<Verification> verifications;

    @OneToMany(mappedBy = "user")
    List<Attendance> attendances;

    @OneToMany(mappedBy = "user")
    List<UserMessageSchedule> userMessageSchedules;

    @ManyToOne
    @JoinColumn(name = "job_position_id")
    JobPosition jobPosition;

    @OneToMany(mappedBy = "user")
    List<UserGroup> userGroups;
}
