package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.MessageSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageScheduleRepository extends JpaRepository<MessageSchedule, Long> {

    @Query("SELECT ms FROM MessageSchedule ms where ms.id != ?2 ORDER BY ms.publishAt DESC limit ?1")
    List<MessageSchedule> findTopBy(Long topElement, Long id);
}
