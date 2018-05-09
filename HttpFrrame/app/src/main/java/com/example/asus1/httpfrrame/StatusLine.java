package com.example.asus1.httpfrrame;

public class StatusLine {

    private int mResponseCode;
    private ProtocolVersion mVersion;
    private String mResponseMessage;

    public StatusLine(ProtocolVersion mVersion,int mResponseCode,  String mResponseMessage) {
        this.mResponseCode = mResponseCode;
        this.mVersion = mVersion;
        this.mResponseMessage = mResponseMessage;
    }
}
