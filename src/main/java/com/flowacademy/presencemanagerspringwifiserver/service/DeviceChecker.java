package com.flowacademy.presencemanagerspringwifiserver.service;

import com.flowacademy.presencemanagerspringwifiserver.model.Log;
import com.flowacademy.presencemanagerspringwifiserver.model.MacLog;
import com.flowacademy.presencemanagerspringwifiserver.repository.LogsRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DeviceChecker {

    public void checkIPs(LogsRepository logsRepository) throws IOException, InterruptedException {
        String[] cmd = { "fping", "-c", "1", "-g", "192.168.5.0/24" };
        Pattern ip = Pattern.compile("192.168.5.[0-9]+");

        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line = reader.readLine();

        while (line != null) {
            Matcher m = ip.matcher(line);

            if (m.find()) {
                getMac(m.group(0), logsRepository);
            }

            line = reader.readLine();
        }

    }

    private void getMac(String ip, LogsRepository logsRepository) throws IOException, InterruptedException {

        String[] cmd = { "arp", ip };
        Pattern macAddress = Pattern.compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+");

        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        String line = reader.readLine();

        while (line != null) {
            Matcher m = macAddress.matcher(line);

            if (m.find()) {
                String mac = m.group(0);
                getRecordByMAC(mac, logsRepository);
            }

            line = reader.readLine();
        }

    }

    private void getRecordByMAC(String mac, LogsRepository logsRepository) {
        Log log = logsRepository.findByMacAddress(mac);

        if (log != null) {
            if (isSameDay(log)) {
                checkInSameDay(logsRepository, log);
            } else {
                checkInNewDay(logsRepository, log);
            }
        } else {
            createNewRecord(mac, logsRepository);
        }
    }

    public Log checkInNewDay(LogsRepository logsRepository, Log log) {
        log.getLogs().add(new MacLog(new ObjectId().toString(), LocalDate.now().toString(), LocalTime.now().toString(), null));
        logsRepository.save(log);
        return log;
    }

    public Log checkInSameDay(LogsRepository logsRepository, Log log) {
        log.getLogs().get(log.getLogs().size() - 1).setLastCheckIn(LocalTime.now().toString());
        logsRepository.save(log);
        return log;
    }

    private void createNewRecord(String mac, LogsRepository logsRepository) {
        ArrayList<MacLog> newMacLog = new ArrayList<>();
        newMacLog.add(new MacLog(new ObjectId().toString(), LocalDate.now().toString(), LocalTime.now().toString(), null));
        Log newLog = new Log(mac, newMacLog);
        logsRepository.save(newLog);
    }

    public boolean isSameDay(Log log) {
        ArrayList<MacLog> macLogArrayList = log.getLogs();
        MacLog macLog = macLogArrayList.get(macLogArrayList.size() - 1);
        return macLog.getSubjectDate().equals(LocalDate.now().toString());
    }

}
