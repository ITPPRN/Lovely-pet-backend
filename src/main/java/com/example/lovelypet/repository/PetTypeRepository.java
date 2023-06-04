package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Verify;
import org.springframework.data.repository.CrudRepository;

public interface VerifyRepository extends CrudRepository<Verify, String> {
    boolean existsByUserName(String userName);

}