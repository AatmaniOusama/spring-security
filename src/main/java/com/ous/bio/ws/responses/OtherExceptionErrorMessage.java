package com.ous.bio.ws.responses;

import java.util.Date;

public class OtherExceptionErrorMessage {

    private Date timestamp;
    private String message;

    private String url;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OtherExceptionErrorMessage(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
