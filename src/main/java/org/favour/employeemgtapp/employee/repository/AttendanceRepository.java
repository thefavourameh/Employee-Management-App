package org.favour.employeemgtapp.employee.repository;

import org.favour.employeemgtapp.employee.model.Attendance;
import org.favour.employeemgtapp.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    Optional<Attendance> findByEmployeeAndDate(Employee employee, String date);

    List<Attendance> findAllByEmployee(Employee employee);
}
