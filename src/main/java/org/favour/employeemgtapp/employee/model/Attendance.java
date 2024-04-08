package org.favour.employeemgtapp.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class Attendance extends BaseModel {
    private String date;
    private String inTime;
    private String outTime;
    private String status;
    @ManyToOne
    private Employee employee;
}
