package org.favour.employeemgtapp.employee.services;

import org.favour.employeemgtapp.employee.model.Leave;
import org.favour.employeemgtapp.employee.dto.LeaveDto;

import java.util.List;

public interface LeaveService {
    Leave applyLeave(LeaveDto leaveDto);

    List<Leave> getAllLeaves(long id);

    void approveLeave(long empId, long leaveId);
}
