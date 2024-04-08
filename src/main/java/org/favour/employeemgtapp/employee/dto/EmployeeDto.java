package org.favour.employeemgtapp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    public String firstName;
    private String lastName;
    private String email;
   private String password;
   private String dob;
   private String phoneNumber;
}
