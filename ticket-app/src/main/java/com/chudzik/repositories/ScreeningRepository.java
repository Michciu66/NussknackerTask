package com.chudzik.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chudzik.models.Screening;

public interface ScreeningRepository extends JpaRepository<Screening,Long>{
    
    List<Screening> findAllByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
