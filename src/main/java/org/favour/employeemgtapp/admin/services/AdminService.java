package org.favour.employeemgtapp.admin.services;


import org.favour.employeemgtapp.admin.dto.AdminDto;
import org.favour.employeemgtapp.admin.vo.AdminVO;

public interface AdminService {
    AdminVO login (String email, String password);
    AdminVO logout (String email, String password);
    AdminVO addAdmin (AdminDto adminDto);

    AdminVO signup(AdminDto adminDto);
}
