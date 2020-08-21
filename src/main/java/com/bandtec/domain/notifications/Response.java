package com.bandtec.domain.notifications;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Response {
    private boolean  isFailure;
    private boolean isSuccess;
    private List<String> messages;

    public Response() {
        messages = new ArrayList<>();
        isFailure = false;
        isSuccess = true;
    }

    public Response fail(String message) {
        messages.add(message);
        setFailure();
        return this;
    }

    private void setFailure() {
        if (!messages.isEmpty()){
            isFailure = true;
            isSuccess = false;
        }
    }
}

