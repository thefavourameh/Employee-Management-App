package org.favour.employeemgtapp.admin.model;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.favour.employeemgtapp.admin.enums.Role;
import org.favour.employeemgtapp.common.BaseUser;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Admin extends BaseUser {
    private Role role;
}
