package comidev.giffinity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comidev.giffinity.model.Role;
import comidev.giffinity.repo.RoleRepo;

@Service
public class RoleService {

    @Autowired
    private RoleRepo repo;

    public Role getRoleByName(String name) {
        return repo.findByName(name).get();
    }
}
