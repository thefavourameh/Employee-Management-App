package org.favour.employeemgtapp.employee.repository;

import org.favour.employeemgtapp.employee.enums.Months;
import org.favour.employeemgtapp.employee.model.Employee;
import org.favour.employeemgtapp.employee.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<Salary> findByEmployee(Employee employee);
    Optional<Salary> findByEmployeeAndMonth(Employee employee, Months month);

}
