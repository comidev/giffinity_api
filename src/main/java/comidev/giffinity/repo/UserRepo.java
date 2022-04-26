package comidev.giffinity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.giffinity.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public User getUserByUsername(String username);

    public Optional<User> findByUsername(String username);

    public Boolean existsByUsername(String username);
}
