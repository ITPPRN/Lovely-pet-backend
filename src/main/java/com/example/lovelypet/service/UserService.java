package com.example.lovelypet.service;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public User create(String userName, String passWord, String name, String email, String phoneNumber) throws BaseException {

        //validate
        if (Objects.isNull(email)) {
            //throw error email null
            throw UserException.createEmailNull();
        }

        if (Objects.isNull(passWord)) {
            //throw error password null
            throw UserException.createPasswordNull();
        }

        if (Objects.isNull(name)) {
            //throw error name null
            throw UserException.createNameNull();
        }

        if (Objects.isNull(userName)) {
            throw UserException.createUserNameNull();
        }

        if (Objects.isNull(phoneNumber)) {
            throw UserException.createPhoneNumberNull();
        }

        //verify
        if (repository.existsByEmail(email)) {
            //throw error email duplicated
            throw UserException.createEmailDuplicated();
        }
        if (repository.existsByUserName(userName)) {
            //throw error email duplicated
            throw UserException.createUserNameDuplicated();
        }

        //save
        else {
            User entity = new User();
            entity.setUserName(userName);
            entity.setPassWord(passWord);
            entity.setName(name);
            entity.setEmail(email);
            entity.setPhoneNumber(phoneNumber);
            return repository.save(entity);
        }
    }

    public Optional<User> findLog(String userName) throws BaseException {
        Optional<User> user = repository.findByUserName(userName);
        return user;
    }

    public boolean matchPassword(String requestPass, String dataPass) {
        return requestPass.matches(dataPass);
    }
}
