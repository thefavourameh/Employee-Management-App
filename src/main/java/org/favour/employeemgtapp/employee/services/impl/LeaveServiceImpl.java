package org.favour.employeemgtapp.employee.services.impl;

import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.employee.dto.LeaveDto;
import org.favour.employeemgtapp.employee.model.Employee;
import org.favour.employeemgtapp.employee.model.Leave;
import org.favour.employeemgtapp.employee.services.LeaveService;
import org.springframework.stereotype.Service;
import org.favour.employeemgtapp.employee.repository.EmployeeRepository;
import org.favour.employeemgtapp.employee.repository.LeaveRepository;
import org.favour.employeemgtapp.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;
    @Override
    public Leave applyLeave(LeaveDto leaveDto) {
        System.out.println(leaveDto);
        Optional<Employee> employeeOptional = employeeRepository.findById(leaveDto.getEmpId());
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Leave newLeave = Leave.builder()
                    .leaveType(leaveDto.getLeaveType())
                    .endDate(leaveDto.getEndDate())
                    .startDate(leaveDto.getStartDate())
                    .createdAt(LocalDateTime.now())
                    .employee(employee)
                    .status("NOT_APPROVED")
                    .build();

            Leave savedLeave =  leaveRepository.save(newLeave);
            List<Leave> leaves = employee.getLeaves();
            leaves.add(savedLeave);
            employee.setLeaves(leaves);
            employeeRepository.save(employee);
            return savedLeave;
        }else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    @Override
    public List<Leave> getAllLeaves(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            return leaveRepository.findAllByEmployee(employee);
        }else {
            throw new UserNotFoundException("Employee not found");
        }
    }

    @Override
    public void approveLeave(long empId, long leaveId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Optional<Leave> leaveOptional = leaveRepository.findById(leaveId);
            if (leaveOptional.isPresent()){
                Leave leave = leaveOptional.get();
                leave.setStatus("APPROVED");
                leaveRepository.save(leave);
            }
        }else {
            throw new UserNotFoundException("Employee not found");
        }
    }
}
