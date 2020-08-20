package com.app.manager.service.implementClass;

import com.app.manager.entity.Role;
import com.app.manager.repository.RoleRepository;
import com.app.manager.repository.UserRepository;
import com.app.manager.service.interfaceClass.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    RoleRepository roleRepository;
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Optional<Role> find(String id) {
        var role = roleRepository.findRoleById(id);
        if(role != null) return Optional.of(role);
        return Optional.empty();
    }
    @Override
    public Optional<Role> findBasicRole() {
        try {
            var roleName = userRepository.count() < 3 ?
                    Role.RoleList.ADMIN.name() :
                    Role.RoleList.USER.name();
            var role = roleRepository.findFirstByName(roleName);
            if (role != null) return Optional.of(role);
            System.out.println(roleName + " not found, create new role ...");
            var newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
            return Optional.of(newRole);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void generateRoles() {
        for(Role.RoleList roleName : Role.RoleList.values()){
            try {
                var role = roleRepository.findFirstByName(roleName.getName());
                if (role == null) {
                    var newRole = new Role();
                    newRole.setName(roleName.getName());
                    roleRepository.save(newRole);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Can create role: " + roleName.getName());
                System.out.println("Reason: " + e.getMessage());
                System.out.println("Cause by: " + e.getCause().toString());
            }
        }
    }
}
