package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer>
{
}
