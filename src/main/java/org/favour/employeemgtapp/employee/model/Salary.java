package org.favour.employeemgtapp.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.favour.employeemgtapp.common.BaseModel;
import org.favour.employeemgtapp.employee.enums.Months;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Salary extends BaseModel{
   private double grossAmount;
   private double netAmount;
   private double taxPercentage;
   private Months month;
   private boolean paidFully;
   private String status;
   @ManyToOne
   private Employee employee;
}
