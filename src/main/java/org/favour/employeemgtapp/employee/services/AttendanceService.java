package org.favour.employeemgtapp.employee.services;

import org.favour.employeemgtapp.employee.dto.CheckInData;
import org.favour.employeemgtapp.employee.vo.AttendanceVO;

import java.util.List;

public interface AttendanceService {
    AttendanceVO signAttendance(CheckInData checkData);

    AttendanceVO getAttendanceByEmployeeId(long empId);

    List<AttendanceVO> getAllAttendances(long id);

    void approveAttendance(long empId, long id);
}
