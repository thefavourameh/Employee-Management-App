package org.favour.employeemgtapp.employee.services.impl;

import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.employee.dto.CheckInData;
import org.favour.employeemgtapp.employee.model.Attendance;
import org.favour.employeemgtapp.employee.model.Employee;
import org.favour.employeemgtapp.employee.repository.AttendanceRepository;
import org.favour.employeemgtapp.employee.services.AttendanceService;
import org.favour.employeemgtapp.employee.vo.AttendanceVO;
import org.springframework.stereotype.Service;
import org.favour.employeemgtapp.employee.repository.EmployeeRepository;
import org.favour.employeemgtapp.exceptions.InvalidOperationException;
import org.favour.employeemgtapp.exceptions.MultipleClockInException;
import org.favour.employeemgtapp.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AttendanceVO signAttendance(CheckInData checkData) {
        Optional<Employee> employeeOptional = employeeRepository.findById(checkData.getEmpId());
        if(employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Optional<Attendance> attendanceOptional = attendanceRepository.findByEmployeeAndDate(employee, checkData.getDate());
            if (attendanceOptional.isPresent()){
                Attendance attendance = attendanceOptional.get();
                if (checkData.getAction() != null) {
                    if(checkData.getAction().equalsIgnoreCase("CLOCK_IN") && attendance.getInTime() != null){
                        throw new MultipleClockInException("You can only sign in once per day");
                    } else if (checkData.getAction().equalsIgnoreCase("CLOCK_OUT") && attendance.getOutTime() != null) {
                        throw new MultipleClockInException("You can only sign out once per day");
                    }else if (checkData.getAction().equalsIgnoreCase("CLOCK_OUT") && attendance.getOutTime() == null) {
                        attendance.setOutTime(LocalTime.now().toString().substring(0,5));
                        attendanceRepository.save(attendance);
                    }else {
                        attendance.setInTime(LocalTime.now().toString().substring(0,5));
                        attendanceRepository.save(attendance);
                    }
                }else {
                    throw new InvalidOperationException("Invalid operation");
                }
            }else {
                if (checkData.getAction() != null) {
                    Attendance newAttendance = new Attendance();
                    if(checkData.getAction().equalsIgnoreCase("CLOCK_IN")){
                        newAttendance.setDate(checkData.getDate());
                        newAttendance.setInTime(LocalTime.now().toString().substring(0,5));
                        newAttendance.setStatus("UNVERIFIED");
                        newAttendance.setEmployee(employee);
                    } else if (checkData.getAction().equalsIgnoreCase("CLOCK_OUT")) {
                        newAttendance.setDate(checkData.getDate());
                        newAttendance.setOutTime(LocalTime.now().toString().substring(0,5));
                        newAttendance.setStatus("UNVERIFIED");
                        newAttendance.setEmployee(employee);
                    }
                    newAttendance.setCreatedAt(LocalDateTime.now());
                    newAttendance.setUpdatedAt(LocalDateTime.now());
                    Attendance savedAttendance = attendanceRepository.save(newAttendance);
                    List<Attendance> attendanceList = employee.getAttendance();
                    attendanceList.add(newAttendance);
                    employee.setAttendance(attendanceList);
                    employeeRepository.save(employee);
                    return AttendanceVO.builder()
                            .id(savedAttendance.getId())
                            .inTime(savedAttendance.getInTime())
                            .outTime(savedAttendance.getOutTime())
                            .date(savedAttendance.getDate())
                            .status(savedAttendance.getStatus())
                            .createdAt(savedAttendance.getCreatedAt().toString().split("T")[0])
                            .updatedAt(savedAttendance.getUpdatedAt().toString().split("T")[0])
                            .status(savedAttendance.getStatus())
                            .build();
                }else {
                    throw new InvalidOperationException("Invalid operation");
                }
            }
        }else {
            throw new UserNotFoundException("Employee not found");
        }
        return null;
    }

    @Override
    public AttendanceVO getAttendanceByEmployeeId(long empId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Optional<Attendance> attendanceOptional = employee.getAttendance().stream().findFirst();
            if (attendanceOptional.isPresent()){
                Attendance savedAttendance = attendanceOptional.get();
                return AttendanceVO.builder()
                        .id(savedAttendance.getId())
                        .inTime(savedAttendance.getInTime())
                        .outTime(savedAttendance.getOutTime())
                        .date(savedAttendance.getDate())
                        .createdAt(savedAttendance.getCreatedAt().toString().split("T")[0])
                        .updatedAt(savedAttendance.getUpdatedAt().toString().split("T")[0])
                        .status(savedAttendance.getStatus())
                        .build();
            }
        }
        return null;
    }

    @Override
    public List<AttendanceVO> getAllAttendances(long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            List<Attendance> attendances = attendanceRepository.findAllByEmployee(employee);
            if (!attendances.isEmpty()){
                return attendances.stream().map(attendance -> AttendanceVO.builder()
                        .id(attendance.getId())
                        .inTime(attendance.getInTime())
                        .outTime(attendance.getOutTime())
                        .date(attendance.getDate())
                        .createdAt(attendance.getCreatedAt().toString().split("T")[0])
                        .updatedAt(attendance.getUpdatedAt().toString().split("T")[0])
                        .status(attendance.getStatus())
                        .build()).toList();

            }
        }
        return null;
    }

    @Override
    public void approveAttendance(long empId, long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
            if (attendanceOptional.isPresent()){
                Attendance attendance = attendanceOptional.get();
                attendance.setStatus("VERIFIED");
                attendanceRepository.save(attendance);
            }
        }else {
            throw new UserNotFoundException("Employee not found");
        }
    }
}
