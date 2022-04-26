package comidev.giffinity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.giffinity.model.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    public Optional<Role> findByName(String name);

}
