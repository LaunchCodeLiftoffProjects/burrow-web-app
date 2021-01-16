package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByName(String name);
}
