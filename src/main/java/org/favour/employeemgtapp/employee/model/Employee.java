package org.favour.employeemgtapp.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.favour.employeemgtapp.common.BaseUser;
import org.favour.employeemgtapp.employee.enums.Gender;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Employee extends BaseUser {
    private String status;
    private String dob;
    private String phoneNumber;
    private Gender gender;
    private String department;
    @OneToMany
    private List<Attendance> attendance;
    @OneToMany
    private Set<Salary> salary;

    @OneToMany
    private List<Leave> leaves;
}
