package com.example.lovelypet.repository;

import com.example.lovelypet.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUserName(String userName);
    Optional<User> findById(int idU);



    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);


}