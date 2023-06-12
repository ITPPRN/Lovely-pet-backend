package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Verify;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerifyRepository extends CrudRepository<Verify, String> {
    boolean existsByUserName(String userName);

    Optional<Verify> findById(int id);

    Optional<Verify> findByUserName(String userName);
}