package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
