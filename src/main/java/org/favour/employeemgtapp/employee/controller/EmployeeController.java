package org.favour.employeemgtapp.employee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.favour.employeemgtapp.employee.dto.CheckInData;
import org.favour.employeemgtapp.employee.dto.EmployeeDto;
import org.favour.employeemgtapp.employee.dto.LeaveDto;
import org.favour.employeemgtapp.employee.dto.LoginDto;
import org.favour.employeemgtapp.employee.model.Leave;
import org.favour.employeemgtapp.employee.services.AttendanceService;
import org.favour.employeemgtapp.employee.services.EmployeeService;
import org.favour.employeemgtapp.employee.services.LeaveService;
import org.favour.employeemgtapp.employee.services.SalaryService;
import org.favour.employeemgtapp.employee.vo.AttendanceVO;
import org.favour.employeemgtapp.employee.vo.EmployeeVO;
import org.favour.employeemgtapp.employee.vo.SalaryVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final SalaryService salaryService;
    private final LeaveService leaveService;
    @PostMapping("/signup")
    public String signup(@ModelAttribute("employee") EmployeeDto employeeDto, Model model) {
        EmployeeVO employeeVO = employeeService.signup(employeeDto);
        model.addAttribute("employee", employeeVO);
        model.addAttribute("loginDto", new LoginDto());
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDto loginDto, Model model, HttpServletRequest request) {
        EmployeeVO employeeVO = employeeService.login(loginDto.getEmail(), loginDto.getPassword());
        model.addAttribute("employee", employeeVO);
        model.addAttribute("employeeId", employeeVO.getId());
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("employee", employeeVO);
        httpSession.setAttribute("employeeId", employeeVO.getId());
        return "redirect:/employees/dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        EmployeeVO employeeVO = (EmployeeVO) httpSession.getAttribute("employee");
        log.info("Session Employee "+employeeVO);
        model.addAttribute("employee",employeeVO);
        if(request.getAttribute("employeeId") != null){
            long empId = (long) request.getAttribute("employeeId");
            model.addAttribute("employeeAttendance", attendanceService.getAttendanceByEmployeeId(empId));
        }

        List<SalaryVO> salaryVO = salaryService.getAllSalaries(employeeVO.getId());
        List<Leave> leaveList = leaveService.getAllLeaves(employeeVO.getId());
        List<AttendanceVO> attendanceList = attendanceService.getAllAttendances(employeeVO.getId());
        model.addAttribute("employeeId", 1);
        model.addAttribute("checkData", new CheckInData());
        model.addAttribute("salaryData", salaryVO);
        model.addAttribute("leaves", leaveList);
        model.addAttribute("salaryList", salaryVO);
        model.addAttribute("employeeLeave", new LeaveDto());
        model.addAttribute("attendances", attendanceList);

        return "employee-dashboard";
    }

    @PostMapping("/sign-attendance")
    public String clockIn(@ModelAttribute("checkData") CheckInData checkData, Model model) throws IOException {
        log.info(checkData.toString());
        AttendanceVO attendance = attendanceService.signAttendance(checkData);
        model.addAttribute("employeeAttendance", attendance);
        return "redirect:/employees/dashboard";
    }

    @PostMapping("/apply-leave")
    public String applyLeave(@ModelAttribute("employeeLeave") LeaveDto leaveDto, Model model){
        log.info("id is "+leaveDto);
        Leave leave = leaveService.applyLeave(leaveDto);
        return "redirect:employees/dashboard";
    }

}
