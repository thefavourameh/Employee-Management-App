package org.favour.employeemgtapp.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String createdAt;
    private String updatedAt;
}
