package org.favour.employeemgtapp.employee.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeVO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String dob;
    private String department;
    private String phoneNumber;
    private String gender;
    private String createdAt;
    private String updatedAt;
}
