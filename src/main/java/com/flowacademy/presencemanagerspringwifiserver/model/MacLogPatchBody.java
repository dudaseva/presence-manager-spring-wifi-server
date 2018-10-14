package com.flowacademy.presencemanagerspringwifiserver.model;

public class MacLogPatchBody {
    private String macAddress;
    private String subjectDate;
    private String firstCheckIn;
    private String lastCheckIn;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
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
}
