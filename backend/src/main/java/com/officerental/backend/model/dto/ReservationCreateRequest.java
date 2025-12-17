package com.officerental.backend.model.dto;

public class ReservationCreateRequest {
    private Long officeId;
    private String startDate; // "2025-12-12"
    private String endDate;

    public Long getOfficeId() { return officeId; }
    public void setOfficeId(Long officeId) { this.officeId = officeId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
