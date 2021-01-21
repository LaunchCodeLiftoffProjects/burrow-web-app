package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Integer>
{

}
