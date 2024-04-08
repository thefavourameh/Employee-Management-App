package org.favour.employeemgtapp.employee.services;


import org.favour.employeemgtapp.employee.dto.EmployeeDto;
import org.favour.employeemgtapp.employee.vo.EmployeeVO;

import java.util.List;

public interface EmployeeService {
    EmployeeVO login (String email, String password);
    EmployeeVO logout (String email, String password);

    EmployeeVO signup(EmployeeDto employeeDto);
    EmployeeVO findById(long empId);

    List<EmployeeVO> getAllEmployees();

    EmployeeVO getEmployeeById(long id);

    EmployeeVO editEmployeeDetails(long id, EmployeeVO employeeVO);

    void delete(long id);

    void suspendEmployee(long id);

    void terminateEmployee(long id);
}
