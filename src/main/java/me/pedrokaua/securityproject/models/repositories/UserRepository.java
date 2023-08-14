package me.pedrokaua.securityproject.models.repositories;

import me.pedrokaua.securityproject.models.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    public Optional<UserModel> findByUsername(String username);
    public Optional<UserModel> deleteByUsername(String username);
}
