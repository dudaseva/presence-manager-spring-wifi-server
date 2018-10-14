package com.flowacademy.presencemanagerspringwifiserver.model;


public class MacLog {

    private String _id;
    private String subjectDate;
    private String firstCheckIn;
    private String lastCheckIn;

    public MacLog(String _id, String subjectDate, String firstCheckIn, String lastCheckIn) {
        this._id = _id;
        this.subjectDate = subjectDate;
        this.firstCheckIn = firstCheckIn;
        this.lastCheckIn = lastCheckIn;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSubjectDate() {
        return subjectDate;
    }

    public void setSubjectDate(String subjectDate) {
        this.subjectDate = subjectDate;
    }

    public String getFirstCheckIn() {
        return firstCheckIn;
    }

    public void setFirstCheckIn(String firstCheckIn) {
        this.firstCheckIn = firstCheckIn;
    }

    public String getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(String lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    @Override
    public String toString() {
        return "MacLog{" +
                "_id='" + _id + '\'' +
                ", subjectDate='" + subjectDate + '\'' +
                ", firstCheckIn='" + firstCheckIn + '\'' +
                ", lastCheckIn='" + lastCheckIn + '\'' +
                '}';
    }
}
