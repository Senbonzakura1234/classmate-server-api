package com.app.manager.service.interfaceClass;

import com.app.manager.entity.Role;
import com.app.manager.model.returnResult.DatabaseQueryResult;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAll();
    Optional<Role> find(String id);
    Optional<Role> findBasicRole();
    void generateRoles();
}
