package com.chudzik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chudzik.models.Room;

public interface RoomRepository extends JpaRepository<Room,Long>{
    
}
