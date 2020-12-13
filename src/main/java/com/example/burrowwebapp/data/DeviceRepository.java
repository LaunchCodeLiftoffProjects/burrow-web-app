package com.example.burrowwebapp.data;

import com.example.burrowwebapp.models.Device;
import org.springframework.data.repository.CrudRepository;
<<<<<<< HEAD

=======
import org.springframework.stereotype.Repository;

@Repository
>>>>>>> device-controller
public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
