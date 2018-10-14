package com.flowacademy.presencemanagerspringwifiserver.repository;

import com.flowacademy.presencemanagerspringwifiserver.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogsRepository extends MongoRepository<Log, String> {

    Log findByMacAddress(String macAddress);
}
