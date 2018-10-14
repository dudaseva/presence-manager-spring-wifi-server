package com.flowacademy.presencemanagerspringwifiserver.controller;

import com.flowacademy.presencemanagerspringwifiserver.model.Log;
import com.flowacademy.presencemanagerspringwifiserver.model.MacLog;
import com.flowacademy.presencemanagerspringwifiserver.model.MacLogPatchBody;
import com.flowacademy.presencemanagerspringwifiserver.repository.LogsRepository;
import com.flowacademy.presencemanagerspringwifiserver.service.DeviceChecker;
import com.flowacademy.presencemanagerspringwifiserver.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("logs")
@RestController
public class LogController {

    private LogsRepository logsRepository;
    private DeviceChecker deviceChecker;
    private LogService logService;

    public LogController(LogsRepository logsRepository, DeviceChecker deviceChecker, LogService logService) {
        this.logsRepository = logsRepository;
        this.deviceChecker = deviceChecker;
        this.logService = logService;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void runIt() throws IOException, InterruptedException {
        deviceChecker.checkIPs(logsRepository);
    }

    @GetMapping("/")
    public ResponseEntity getAllLog() {
        List<Log> logs = logsRepository.findAll();

        if (logs == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(logs);
        }
    }

    @PatchMapping(value = "/")
    public ResponseEntity patchLog(@RequestBody MacLogPatchBody body) {
        Log log = logsRepository.findByMacAddress(body.getMacAddress());

        if (log == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            ArrayList<MacLog> macLogArrayList = log.getLogs();
            MacLog macLog = logService.findMacLogBySubjectDate(macLogArrayList, body.getSubjectDate());
            if (macLog == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            log = logService.patchMacLog(body, log, macLogArrayList, macLog);
            log = logsRepository.save(log);
            return ResponseEntity.ok(log);
        }
    }

    @PostMapping("/")
    public ResponseEntity postLog(@RequestBody MacLogPatchBody body) {
        Log log = logsRepository.findByMacAddress(body.getMacAddress());

        if (log == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (deviceChecker.isSameDay(log)) {
            log = deviceChecker.checkInSameDay(logsRepository, log);
            if (log == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(log);
        } else {
            log = deviceChecker.checkInNewDay(logsRepository, log);
            if (log == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(log);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity isInToday(@PathVariable("id") String id) {
        Log log = logsRepository.findByMacAddress(id);

        if (log == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (deviceChecker.isSameDay(log)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

}
