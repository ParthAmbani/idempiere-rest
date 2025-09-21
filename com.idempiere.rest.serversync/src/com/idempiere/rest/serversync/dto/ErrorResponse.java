package com.idempiere.rest.serversync.dto;

/**
 * Error response structure from Idempiere REST.
 */
public class ErrorResponse {
    private String title;
    private int status;
    private String detail;

    public ErrorResponse() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}
