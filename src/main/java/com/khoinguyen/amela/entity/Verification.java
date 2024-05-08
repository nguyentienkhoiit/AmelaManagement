package com.khoinguyen.amela.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@Table(name = "verify_token_tbl")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String token;
    LocalDateTime createdAt;
    LocalDateTime expireAt;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    User user;
}
