package org.favour.employeemgtapp.admin.services;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.favour.employeemgtapp.admin.model.Admin;
import org.favour.employeemgtapp.admin.vo.AdminVO;
import org.springframework.stereotype.Service;
import org.favour.employeemgtapp.admin.dto.AdminDto;
import org.favour.employeemgtapp.admin.repository.AdminRepository;
import org.favour.employeemgtapp.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
   private final AdminRepository adminRepository;
    @Override
    public AdminVO login(String email, String password) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.getPassword().equals(password)) {
                return AdminVO.builder()
                        .id(Math.toIntExact(admin.getId()))
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .build();
            }
            throw new ValidationException("Invalid password");
        }
        else {
            throw new UserNotFoundException("Admin not found");
        }

        }

    @Override
    public AdminVO logout(String email, String password) {
        return null;
    }

    @Override
    public AdminVO addAdmin(AdminDto adminDto) {
        return null;
    }

    @Override
    public AdminVO signup(AdminDto adminDto) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(adminDto.getEmail());
        if (adminOptional.isPresent()) {
            throw new ValidationException("Admin with email " + adminDto.getEmail() + " already exists");
        }
        Admin newAdmin = Admin.builder()
                .firstName(adminDto.getFirstName())
                .lastName(adminDto.getLastName())
                .email(adminDto.getEmail())
                .password(adminDto.getPassword())
                .build();
        Admin savedAdmin = adminRepository.save(newAdmin);
        return AdminVO.builder()
                .id(Math.toIntExact(savedAdmin.getId()))
                .firstName(savedAdmin.getFirstName())
                .lastName(savedAdmin.getLastName())
                .email(savedAdmin.getEmail())
                .build();
    }
}
