package org.favour.employeemgtapp.employee.services.impl;

import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.employee.dto.SalaryDto;
import org.favour.employeemgtapp.employee.enums.Months;
import org.favour.employeemgtapp.employee.model.Employee;
import org.favour.employeemgtapp.employee.model.Salary;
import org.favour.employeemgtapp.employee.repository.SalaryRepository;
import org.favour.employeemgtapp.employee.services.SalaryService;
import org.favour.employeemgtapp.employee.vo.SalaryVO;
import org.springframework.stereotype.Service;
import org.favour.employeemgtapp.employee.repository.EmployeeRepository;
import org.favour.employeemgtapp.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public List<SalaryVO> getAllSalaries(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            List<Salary> previousSalaries = salaryRepository.findByEmployee(employee);
            if(previousSalaries != null && !previousSalaries.isEmpty()){
                return previousSalaries.stream().map(salary -> SalaryVO.builder()
                        .id(salary.getId())
                        .grossAmount(salary.getGrossAmount())
                        .netAmount(salary.getNetAmount())
                        .taxPercentage(salary.getTaxPercentage())
                        .paidFully(salary.isPaidFully())
                        .status(salary.getStatus())
                        .month(salary.getMonth().name())
                        .createdAt(salary.getCreatedAt().toLocalDate().toString())
                        .updatedAt(salary.getUpdatedAt().toLocalDate().toString())
                        .build()).toList();
            }
        }else{
            throw new UserNotFoundException("Employee not found");
        }
        return new ArrayList<>();
    }

    @Override
    public void paySalary(SalaryDto salaryDto) {
        Optional<Employee> employeeOptional = employeeRepository.findById(salaryDto.getEmpId());
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Optional<Salary> salaryOptional = salaryRepository.findByEmployeeAndMonth(employee, Months.valueOf(salaryDto.getMonth().toUpperCase()));
            if (salaryOptional.isEmpty()){
                Salary newSalary = Salary.builder()
                        .employee(employee)
                        .grossAmount(salaryDto.getGrossAmount())
                        .netAmount(salaryDto.getNetAmount())
                        .taxPercentage(salaryDto.getTaxPercentage())
                        .month(Months.valueOf(salaryDto.getMonth().toUpperCase()))
                        .paidFully(true)
                        .status("PAID")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                Salary savedSalary = salaryRepository.save(newSalary);
                Set<Salary> salaryList = employee.getSalary();
                salaryList.add(savedSalary);
                employee.setSalary(salaryList);
                employeeRepository.save(employee);
            }
        }else{
            throw new UserNotFoundException("Employee not found");
        }
    }
}
