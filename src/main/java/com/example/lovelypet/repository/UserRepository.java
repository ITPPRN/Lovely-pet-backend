package com.example.lovelypet.repository;

import com.example.lovelypet.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByToken(String token);

    Optional<User> findById(int idU);

    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);


}