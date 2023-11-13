package com.chudzik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chudzik.models.Reservation;


public interface ReservationRepository extends JpaRepository<Reservation,Long>{

    Reservation findByClientName(String clientName);
}
