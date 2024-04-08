package org.favour.employeemgtapp.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.favour.employeemgtapp.admin.dto.AdminDto;
import org.favour.employeemgtapp.admin.vo.AdminVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.favour.employeemgtapp.admin.services.AdminService;
import org.favour.employeemgtapp.employee.dto.LeaveDto;
import org.favour.employeemgtapp.employee.dto.SalaryDto;
import org.favour.employeemgtapp.employee.model.Leave;
import org.favour.employeemgtapp.employee.services.AttendanceService;
import org.favour.employeemgtapp.employee.services.EmployeeService;
import org.favour.employeemgtapp.employee.services.LeaveService;
import org.favour.employeemgtapp.employee.services.SalaryService;
import org.favour.employeemgtapp.employee.vo.AttendanceVO;
import org.favour.employeemgtapp.employee.vo.EmployeeVO;
import org.favour.employeemgtapp.employee.vo.SalaryVO;

import java.util.List;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
    private final LeaveService leaveService;
    private final SalaryService salaryService;
    @PostMapping("/signup")
    public String signup(@ModelAttribute("admin") AdminDto adminDto, Model model) {
        log.info("signup admin");
        log.info(adminDto.toString());
        AdminVO adminVO = adminService.signup(adminDto);
        model.addAttribute("admin", new AdminDto());
        return "admin-login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("admin") AdminDto adminDto,Model model) {
        AdminVO adminVO = adminService.login(adminDto.getEmail(), adminDto.getPassword());
        List<EmployeeVO> employeeVOList = employeeService.getAllEmployees();
        model.addAttribute("admin", adminVO);
        model.addAttribute("employees", employeeVOList);
        return "redirect:/admins/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<EmployeeVO> employeeVOList = employeeService.getAllEmployees();
        model.addAttribute("employees", employeeVOList);
        model.addAttribute("employeeLeave", new LeaveDto());
        model.addAttribute("salaryDto", new SalaryDto());
        return "admin-dashboard";
    }

    @GetMapping("/view-employee/{id}")
    public String viewEmployee(@PathVariable("id") long id, Model model) {
        EmployeeVO employeeVO = employeeService.getEmployeeById(id);
        model.addAttribute("employeeVO", employeeVO);
        return "manage-employee";
    }

    @GetMapping("/manage-employee/{id}")
    public String manageEmployee(@PathVariable("id") long id, @ModelAttribute("employeeVO") EmployeeVO employeeVO, Model model) {
        EmployeeVO employee = employeeService.editEmployeeDetails(id, employeeVO);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("employeeLeave", new LeaveDto());
        model.addAttribute("salaryDto", new SalaryDto());
        List<AttendanceVO> attendanceVOS = attendanceService.getAllAttendances(id);
        List<Leave> leaveList = leaveService.getAllLeaves(id);
        List<SalaryVO> salaryList = salaryService.getAllSalaries(id);
        model.addAttribute("leaves", leaveList);
        model.addAttribute("attendances", attendanceVOS);
        model.addAttribute("salaries", salaryList);
        return "manage-employee";
    }

    @GetMapping("/approve-attendance/{empId}/{id}")
    public String approveAttendance(@PathVariable("empId") String empId, @PathVariable("id") String id){
        log.info("Approving attendance with EmpID {} and Id {}",empId,id);
        attendanceService.approveAttendance(Long.parseLong(empId), Long.parseLong(id));
        return "redirect:/admins/edit-employee/"+empId;
    }

    @GetMapping("/approve-leave/{empId}/{id}")
    public String approveLeave(@PathVariable("empId") String empId, @PathVariable("id") String id){
        log.info("Approving leave with EmpID {} and Id {}",empId,id);
        leaveService.approveLeave(Long.parseLong(empId), Long.parseLong(id));
        return "redirect:/admins/manage-employee/"+empId;
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable("id") long id, Model model) {
        employeeService.delete(id);
        return "redirect:/admins/dashboard";
    }
    @GetMapping("/suspend-employee/{id}")
    public String suspendEmployee(@PathVariable("id") long id, Model model) {
        employeeService.suspendEmployee(id);
        return "redirect:/admins/manage-employee/"+id;
    }
    @GetMapping("/terminate-employee/{id}")
    public String terminateEmployee(@PathVariable("id") long id, Model model) {
        employeeService.terminateEmployee(id);
        return "redirect:/admins/manage-employee/"+id;
    }

    @PostMapping("/pay-salary")
    public String approveAttendance(@ModelAttribute("salaryDto") SalaryDto salaryDto){
        log.info(salaryDto.toString());
        salaryService.paySalary(salaryDto);
        return "redirect:/admins/manage-employee/"+salaryDto.getEmpId();
    }
}
