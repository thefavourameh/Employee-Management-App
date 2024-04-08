package org.favour.employeemgtapp.employee.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceVO {
    private long id;
    private String date;
    private String inTime;
    private String outTime;
    private String status;
    private String createdAt;
    private String updatedAt;
}
