package org.favour.employeemgtapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDto {
    private long empId;
    private String leaveType;
    private String startDate;
    private String endDate;
}
