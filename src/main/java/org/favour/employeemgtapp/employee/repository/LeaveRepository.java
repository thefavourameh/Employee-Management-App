package org.favour.employeemgtapp.employee.repository;

import org.favour.employeemgtapp.employee.model.Employee;
import org.favour.employeemgtapp.employee.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findAllByEmployee(Employee employee);
}
