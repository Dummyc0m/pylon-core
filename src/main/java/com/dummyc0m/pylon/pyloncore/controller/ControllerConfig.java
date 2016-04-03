package com.dummyc0m.pylon.pyloncore.controller;

import java.util.List;

/**
 * Created by Dummyc0m on 3/23/16.
 */
public class ControllerConfig {
    private String identifier;
    private String name;
    private String type;
    private String url;
    private String username;
    private String password;
    private String hostname;
    private int[] availablePorts;

    public ControllerConfig(String identifier, String name, String type, String url, String username, String password, String hostname, int[] availablePorts) {
        this.identifier = identifier;
        this.name = name;
        this.type = type;
        this.url = url;
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.availablePorts = availablePorts;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHostname() {
        return hostname;
    }

    public int[] getAvailablePorts() {
        return availablePorts;
    }
}
