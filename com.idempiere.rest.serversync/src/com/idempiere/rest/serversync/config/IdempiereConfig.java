package com.idempiere.rest.serversync.config;

/**
 * Configuration holder for Idempiere REST access.
 * Default protocol is http, default port is 8080, default apiVersion is v1.
 */
public class IdempiereConfig {

    private boolean isHTTPS = true; // default to HTTPS
    private String host;
    private int port = 8080;
    private final String apiVersion = "v1"; // static, no setter

    public IdempiereConfig() {}

    public IdempiereConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public IdempiereConfig(String host) {
        this.host = host;
    }

    // getters & setters
    public boolean isHTTPS() {
        return isHTTPS;
    }

    public void setHTTPS(boolean isHTTPS) {
        this.isHTTPS = isHTTPS;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    // Dynamically returns http or https based on isHTTPS flag
    public String getProtocol() {
        return isHTTPS ? "https" : "http";
    }

    public String getBaseUrl() {
        return getProtocol() + "://" + host + ":" + port + "/api/" + apiVersion;
    }

    public String getEndpoint(String path) {
        if (path == null) return getBaseUrl();
        if (path.startsWith("/")) {
            return getBaseUrl() + path;
        } else {
            return getBaseUrl() + "/" + path;
        }
    }
}
