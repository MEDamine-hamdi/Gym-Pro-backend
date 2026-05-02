package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.entity.Room;
import com.gympro.gymprobackend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room>getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    public Room createRoom(Room room){
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id , Room room){
        Room roomExisting =roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        roomExisting.setName(room.getName());
        roomExisting.setCapacity(room.getCapacity());
        roomExisting.setEquipment(room.getEquipment());
        roomExisting.setActive(room.getActive() != null ? room.getActive() : true);
        return roomRepository.save(roomExisting);
    }

    public void deleteRoom(Long id){
        roomRepository.deleteById(id);
    }


}
