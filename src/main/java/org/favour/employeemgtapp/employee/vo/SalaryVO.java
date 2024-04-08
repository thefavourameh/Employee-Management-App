package org.favour.employeemgtapp.employee.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryVO {
    private long id;
    private double grossAmount;
    private double netAmount;
    private double taxPercentage;
    private String month;
    private boolean paidFully;
    private String status;
    private String createdAt;
    private String updatedAt;
}
