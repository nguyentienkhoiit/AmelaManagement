package com.khoinguyen.amela.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Entity
@Builder
@Table(name = "message_schedule_tbl")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String subject;

    @Column(columnDefinition = "text")
    String message;

    String senderName;
    boolean status;
    Long viewers = 0L;
    LocalDateTime publishAt;
    LocalDateTime createdAt;
    Long createdBy;
    LocalDateTime updateAt;
    Long updateBy;

    @OneToMany(mappedBy = "messageSchedule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<UserMessageSchedule> userMessageSchedules;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    Group group;
}
