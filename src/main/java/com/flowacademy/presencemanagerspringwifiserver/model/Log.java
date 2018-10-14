package com.flowacademy.presencemanagerspringwifiserver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Log {

    @Id
    private String _id;
    private String macAddress;
    private ArrayList<MacLog> logs;

    public Log(String macAddress, ArrayList<MacLog> logs) {
        this.macAddress = macAddress;
        this.logs = logs;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public ArrayList<MacLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<MacLog> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "Log{" +
                "_id='" + _id + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", logs=" + logs +
                '}';
    }
}
