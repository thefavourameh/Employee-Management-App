package org.favour.employeemgtapp.employee.services;

import org.favour.employeemgtapp.employee.dto.SalaryDto;
import org.favour.employeemgtapp.employee.vo.SalaryVO;

import java.util.List;

public interface SalaryService {

    List<SalaryVO> getAllSalaries(long id);

    void paySalary(SalaryDto salaryDto);
}
