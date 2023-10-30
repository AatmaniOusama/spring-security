package com.ous.bio.ws.responses;

import java.util.Date;

public class ErrorMessage {

    private Date timestamp;
    private String messgae;

    public ErrorMessage(Date timestamp, String messgae) {
        this.timestamp = timestamp;
        this.messgae = messgae;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
}
