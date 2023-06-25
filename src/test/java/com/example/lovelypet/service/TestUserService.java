package com.example.lovelypet.service;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.service.PetService;
import com.example.lovelypet.service.PetTypeService;
import com.example.lovelypet.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService pService;

    @Autowired
    private PetTypeService petTypeService;

    @Order(1)
    @Test
    void testCreate() throws BaseException {
        User user = userService.create(
                TestCreateData.userName,
                TestCreateData.passWord,
                TestCreateData.name,
                TestCreateData.email,
                TestCreateData.phoneNumber
        );

        //check not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());

        //check equals
        Assertions.assertEquals(TestCreateData.email, user.getEmail());
        Assertions.assertEquals(TestCreateData.userName, user.getUserName());

        boolean isMatched = userService.matchPassword(TestCreateData.passWord, user.getPassWord());
        Assertions.assertTrue(isMatched);

        Assertions.assertEquals(TestCreateData.name, user.getName());
        Assertions.assertEquals(TestCreateData.phoneNumber, user.getPhoneNumber());

    }

    @Order(2)
    @Test
    void testUpdate() throws BaseException {
        Optional<User> opt = userService.findLog(TestCreateData.userName);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        User updateedUser = userService.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updateedUser);
        Assertions.assertEquals(TestUpdateData.name, updateedUser.getName());
    }

    @Order(3)
    @Test
    void testCretePet() throws BaseException {
        Optional<User> opt = userService.findLog(TestCreateData.userName);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        List<Pet> p = user.getPet();
        Assertions.assertTrue(p.isEmpty());

        Optional<PetType> obj = petTypeService.findByName(TestPetCreateData.type);
        if(obj.isEmpty()){
            PetType pt = petTypeService.create(TestPetCreateData.type);
            Assertions.assertNotNull(pt);
            Assertions.assertEquals(TestPetCreateData.type, pt.getName());
            obj = petTypeService.findByName(TestPetCreateData.type);
        }
        Assertions.assertTrue(obj.isPresent());

        PetType petType = obj.get();

        List<Pet> p1 = petType.getPet();
        Assertions.assertTrue(p1.isEmpty());

        for (int i = 0; i <= 2; i++) {
            Pet pet = pService.create(user, TestPetCreateData.petName, TestPetCreateData.petPhoto,petType);

            Assertions.assertNotNull(pet);
            Assertions.assertEquals(TestPetCreateData.petName, pet.getPetName());
            Assertions.assertEquals(TestPetCreateData.petPhoto, pet.getPetPhoto());
        }

        Optional<PetType> obj1 = petTypeService.findByName(TestPetCreateData.type1);
        if(obj1.isEmpty()){
            PetType pt1 = petTypeService.create(TestPetCreateData.type1);
            Assertions.assertNotNull(pt1);
            Assertions.assertEquals(TestPetCreateData.type1, pt1.getName());
            obj1 = petTypeService.findByName(TestPetCreateData.type1);
        }
        Assertions.assertTrue(obj1.isPresent());

        PetType petType1 = obj1.get();

        List<Pet> p11 = petType1.getPet();
        Assertions.assertTrue(p11.isEmpty());

        for (int i = 0; i <= 2; i++) {
            Pet pet1 = pService.create(user, TestPetCreateData.petName, TestPetCreateData.petPhoto,petType1);

            Assertions.assertNotNull(pet1);
            Assertions.assertEquals(TestPetCreateData.petName, pet1.getPetName());
            Assertions.assertEquals(TestPetCreateData.petPhoto, pet1.getPetPhoto());
        }
    }

//    @Order(9)
//    @Test
//    void testDelete() throws BaseException {
//        Optional<User> opt = userService.findLog(TestCreateData.userName);
//        Assertions.assertTrue(opt.isPresent());
//
//        User user = opt.get();
//        userService.deleteByIdU(String.valueOf(user.getId()));
//
//        Optional<User> optDelete = userService.findLog(TestCreateData.userName);
//        Assertions.assertTrue(optDelete.isEmpty());
//
//    }


    interface TestPetCreateData {
        String petName = "Panda";
        String petPhoto = "";

        String type = "Dog";

        String type1 = "Cat";
    }


    interface TestCreateData {

        String email = "test@gmail.com";
        String passWord = "test";
        String name = "test1";
        String userName = "test1";
        String phoneNumber = "0876543210";
    }

    interface TestUpdateData {

        String email = "1111@gmail.com";
        String passWord = "1111";
        String name = "1111";
        String userName = "11111";
        String phoneNumber = "111111";
    }


}
