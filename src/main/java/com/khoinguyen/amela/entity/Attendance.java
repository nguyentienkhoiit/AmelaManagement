package com.khoinguyen.amela.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@Table(name = "attendance_tbl")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //    @Temporal(TemporalType.TIME)
    LocalDateTime checkInTime;
    LocalDate checkDay;

    //    @Temporal(TemporalType.TIME)
    LocalDateTime checkOutTime;
    String note;
    boolean status;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
}
