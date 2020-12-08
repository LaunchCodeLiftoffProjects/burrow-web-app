package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer>
{
}
