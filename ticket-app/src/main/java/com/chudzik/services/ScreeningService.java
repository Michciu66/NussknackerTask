package com.chudzik.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chudzik.comparators.ScreeningComparator;
import com.chudzik.dtos.*;
import com.chudzik.exceptions.ScreeningNotFoundException;
import com.chudzik.mappers.ScreeningMapper;
import com.chudzik.models.Screening;
import com.chudzik.repositories.ScreeningRepository;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepo;

    public ScreeningService(ScreeningRepository screeningRepo) {
        this.screeningRepo = screeningRepo;
    }

    public List<ScreeningDto> listScreenings() {
        return screeningRepo.findAll().stream()
                .map(ScreeningMapper::convertToLimited)
                .sorted(new ScreeningComparator())
                .toList();
    }

    public ScreeningDto findScreeningById(Long id) {
        Screening screening = screeningRepo.findById(id).orElseThrow(() -> new ScreeningNotFoundException(id));
        return ScreeningMapper.convertToDto(screening);
    }

    public List<ScreeningDto> listScreeningsBetweenDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(dateTime, formatter);
        if (startDateTime.isBefore(LocalDateTime.now().plusMinutes(15))) {
            startDateTime = LocalDateTime.now().plusMinutes(15);
        }
        LocalDateTime endDateTime = startDateTime.toLocalDate().atTime(23, 59, 59);
        return screeningRepo.findAllByTimeBetween(startDateTime, endDateTime).stream()
                .map(ScreeningMapper::convertToLimited)
                .sorted(new ScreeningComparator())
                .toList();
    }

}
