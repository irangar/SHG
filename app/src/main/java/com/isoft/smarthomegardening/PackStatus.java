package com.isoft.smarthomegardening;

public class PackStatus {
    String status;

    public PackStatus() {
    }

    public PackStatus(String status) {
        this.status = status;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) { this.status = status; }
}
