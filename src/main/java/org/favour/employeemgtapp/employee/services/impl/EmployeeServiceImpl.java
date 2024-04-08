package org.favour.employeemgtapp.employee.services.impl;


import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.employee.dto.EmployeeDto;
import org.favour.employeemgtapp.employee.enums.Gender;
import org.favour.employeemgtapp.employee.model.Employee;
import org.favour.employeemgtapp.employee.services.EmployeeService;
import org.favour.employeemgtapp.employee.vo.EmployeeVO;
import org.springframework.stereotype.Service;
import org.favour.employeemgtapp.employee.repository.EmployeeRepository;
import org.favour.employeemgtapp.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeVO login(String email, String password) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getPassword().equals(password)) {
                return EmployeeVO.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .email(employee.getEmail())
                        .dob(employee.getDob())
                        .phoneNumber(employee.getPhoneNumber())
                        .department(employee.getDepartment())
                        .createdAt(LocalDateTime.now().toString())
                        .updatedAt(LocalDateTime.now().toString())
                        .build();
            }
            throw new ValidationException("Invalid password");
        }
        else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    @Override
    public EmployeeVO logout(String email, String password) {
        return null;
    }

    @Override
    public EmployeeVO signup(EmployeeDto employeeDto) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employeeDto.getEmail());
        if (employeeOptional.isPresent()) {
            throw new ValidationException("Employee with email " + employeeDto.getEmail() + " already exists");
        }
        Employee newEmployee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .password(employeeDto.getPassword())
                .dob(employeeDto.getDob())
                .phoneNumber(employeeDto.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Employee savedEmployee = employeeRepository.save(newEmployee);
        return EmployeeVO.builder()
                .id(savedEmployee.getId())
                .firstName(savedEmployee.getFirstName())
                .lastName(savedEmployee.getLastName())
                .email(savedEmployee.getEmail())
                .dob(savedEmployee.getDob())
                .phoneNumber(savedEmployee.getPhoneNumber())
                .department(savedEmployee.getDepartment())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
    @Override
    public EmployeeVO findById(long empId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return EmployeeVO.builder()
                    .id(employee.getId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .dob(employee.getDob())
                    .phoneNumber(employee.getPhoneNumber())
                    .department(employee.getDepartment())
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .build();
        }
        else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    @Override
    public List<EmployeeVO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employee -> EmployeeVO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .phoneNumber(employee.getPhoneNumber())
                .department(employee.getDepartment())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build()).toList();
    }

    @Override
    public EmployeeVO getEmployeeById(long id) {
        return employeeRepository.findById(id).stream().map(employee -> EmployeeVO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .phoneNumber(employee.getPhoneNumber())
                .department(employee.getDepartment())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build()).toList().getFirst();
    }

    @Override
    public EmployeeVO editEmployeeDetails(long id, EmployeeVO employeeVO) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Employee employeeToSave = getEmployee(employee, employeeVO);
            Employee savedEmployee = employeeRepository.save(employeeToSave);
            return EmployeeVO.builder()
                    .id(savedEmployee.getId())
                    .firstName(savedEmployee.getFirstName())
                    .lastName(savedEmployee.getLastName())
                    .email(savedEmployee.getEmail())
                    .dob(savedEmployee.getDob())
                    .phoneNumber(savedEmployee.getPhoneNumber())
                    .department(savedEmployee.getDepartment())
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .build();

        }
        else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    private static Employee getEmployee(Employee employee, EmployeeVO employeeVO) {
        if(employeeVO.getFirstName() != null){
            employee.setFirstName(employeeVO.getFirstName());
        }
        if(employeeVO.getLastName() != null){
            employee.setLastName(employeeVO.getLastName());
        }
        if(employeeVO.getStatus() != null){
            employee.setStatus(employeeVO.getStatus());
        }
        if(employeeVO.getGender() != null){
            employee.setGender(Gender.valueOf(employeeVO.getGender().toUpperCase()));
        }
        if(employeeVO.getPhoneNumber() != null){
            employee.setPhoneNumber(employeeVO.getPhoneNumber());
        }
        if(employeeVO.getDob() != null){
            employee.setDob(employeeVO.getDob());
        }
        if(employeeVO.getEmail() != null){
            employee.setEmail(employeeVO.getEmail());
        }
        if(employeeVO.getDepartment() != null){
            employee.setDepartment(employeeVO.getDepartment());
        }
        return employee;

    }

    @Override
    public void delete(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employeeRepository.delete(employee);
        }
        else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    @Override
    public void suspendEmployee(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setStatus("SUSPENDED");
            employeeRepository.save(employee);
        }
        else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    @Override
    public void terminateEmployee(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setStatus("TERMINATED");
            employeeRepository.save(employee);
        }
        else {
            throw new UserNotFoundException("Employee not found");
        }
    }
}
