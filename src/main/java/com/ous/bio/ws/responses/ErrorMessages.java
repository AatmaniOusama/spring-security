package com.ous.bio.ws.reponses;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field."),
    RECORD_ALREADY_EXIST("Record already exist."),
    INTERNAL_SERVER_ERROR("Internal OUS server error."),
    NO_RECORD_FOUND("Record with provided id is not found.");

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}