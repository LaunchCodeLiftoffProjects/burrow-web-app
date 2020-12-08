package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PropertyRepository extends CrudRepository<Property, Integer>
{
}
