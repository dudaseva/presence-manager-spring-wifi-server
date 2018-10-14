package com.flowacademy.presencemanagerspringwifiserver.service;

import com.flowacademy.presencemanagerspringwifiserver.model.Log;
import com.flowacademy.presencemanagerspringwifiserver.model.MacLog;
import com.flowacademy.presencemanagerspringwifiserver.model.MacLogPatchBody;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service
public class LogService {

    public MacLog findMacLogBySubjectDate(ArrayList<MacLog> macLogArrayList, String subjectDate) {
        for (MacLog maclog: macLogArrayList) {
            if(maclog.getSubjectDate().equals(subjectDate)) {
                return maclog;
            }
        }
        return null;
    }

    public Log patchMacLog(@RequestBody MacLogPatchBody body, Log log, ArrayList<MacLog> macLogArrayList, MacLog macLog) {
        int index = macLogArrayList.indexOf(macLog);

        if (body.getFirstCheckIn() != null) {
            macLog.setFirstCheckIn(body.getFirstCheckIn());
        }

        if(body.getLastCheckIn() != null) {
            macLog.setLastCheckIn(body.getLastCheckIn());
        }

        macLogArrayList.set(index, macLog);
        log.setLogs(macLogArrayList);
        return log;
    }
}
