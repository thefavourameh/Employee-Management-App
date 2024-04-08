package org.favour.employeemgtapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryDto {
    private long empId;
    private double grossAmount;
    private double netAmount;
    private double taxPercentage;
    private String month;
}
