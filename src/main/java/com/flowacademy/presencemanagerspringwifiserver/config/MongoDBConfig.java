package com.flowacademy.presencemanagerspringwifiserver.config;

import com.flowacademy.presencemanagerspringwifiserver.repository.LogsRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = LogsRepository.class)
@Configuration
public class MongoDBConfig {

}
