package com.example.lovelypet.service;

import com.example.lovelypet.entity.Verify;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.VerifyException;
import com.example.lovelypet.repository.VerifyRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerifyService {

    private final VerifyRepository repository;

    private final PasswordEncoder passwordEncoder;


    public VerifyService(VerifyRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;

        this.passwordEncoder = passwordEncoder;
    }

    public void create() throws BaseException {

        String userName = "godOfSystem";
        String passWord = "godOfSystem";
        String email = "godOfSystem@gmail.com";

        //validate
        if (repository.existsByUserName(userName)) {
            //throw error email duplicated
            throw VerifyException.createUserNameDuplicated();
        }

        //save
        else {

            Verify entity = new Verify();
            entity.setUserName(userName);
            entity.setPassWord(passwordEncoder.encode(passWord));
            entity.setEmail(email);
            repository.save(entity);
        }
    }

    public Optional<Verify> findById(int id) throws BaseException {
        return repository.findById(id);
    }

    public Optional<Verify> findLog(String userName) {
        return repository.findByUserName(userName);

    }

    public boolean matchPassword(String requestPass, String dataPass) {
        return passwordEncoder.matches(requestPass, dataPass);
    }
}
