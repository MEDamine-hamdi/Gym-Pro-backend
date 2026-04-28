package com.gympro.gymprobackend.repository;

import com.gympro.gymprobackend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  RoomRepository extends JpaRepository<Room,Long> {
}
