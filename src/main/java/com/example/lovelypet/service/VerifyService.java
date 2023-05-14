package com.example.lovelypet.service;

import com.example.lovelypet.entity.Category;
import com.example.lovelypet.entity.Verify;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.repository.CategoryRepository;
import com.example.lovelypet.repository.VerifyRepository;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    private final VerifyRepository verifyRepository;

    private final CategoryRepository categoryRepository;

    public VerifyService(VerifyRepository verifyRepository, CategoryRepository categoryRepository) {
        this.verifyRepository = verifyRepository;
        this.categoryRepository = categoryRepository;
    }

    public Verify create() throws BaseException {

        String userName = "godOfSystem";
        String passWord = "godOfSystem";
        String email = "godOfSystem@gmail.com";

        String[] name = {"serve", "hotel", "heal"};

        //validate
        if (verifyRepository.existsByUserName(userName)) {
            //throw error email duplicated
            throw UserException.createUserNameDuplicated();
        }

        //save
        else {
            for (int i = 0; i < 3; i++) {
                Category entity1 = new Category();
                entity1.setName(name[i]);
                categoryRepository.save(entity1);
            }
            Verify entity = new Verify();
            entity.setUserName(userName);
            entity.setPassWord(passWord);
            entity.setEmail(email);
            return verifyRepository.save(entity);
        }
    }
}
