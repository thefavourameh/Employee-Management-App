package org.favour.employeemgtapp.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.favour.employeemgtapp.common.BaseModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "employee_leave")
public class Leave extends BaseModel {
    private String status;
    private String leaveType;
    private String startDate;
    private String endDate;
    @ManyToOne
    private Employee employee;
}
