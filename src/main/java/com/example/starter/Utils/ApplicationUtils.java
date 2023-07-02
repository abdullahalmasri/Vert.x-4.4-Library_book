package com.example.starter.Utils;

import lombok.Data;

@Data
public class ApplicationUtils {
    private Integer serverPort;

    public ApplicationUtils(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public ApplicationUtils() {
    }


    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public static int numberOfAvailableCores() {
        // I divide this in half to save some resources while developing
        return Runtime.getRuntime().availableProcessors() / 2;
    }
}
