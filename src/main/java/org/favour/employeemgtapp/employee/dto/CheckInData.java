package org.favour.employeemgtapp.employee.dto;

import lombok.Data;

@Data
public class CheckInData {
    private long empId;
    private String action;
    private String date;
}
