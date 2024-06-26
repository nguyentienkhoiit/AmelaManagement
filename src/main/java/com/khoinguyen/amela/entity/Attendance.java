package com.khoinguyen.amela.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @Temporal(TemporalType.TIME)
    LocalTime checkInTime;

    LocalDate checkDay;

    @Temporal(TemporalType.TIME)
    LocalTime checkOutTime;

    String note;
    boolean status;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;
}
