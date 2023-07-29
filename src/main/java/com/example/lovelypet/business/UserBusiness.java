package com.example.lovelypet.business;

import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.FileException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.mapper.UserMapper;
import com.example.lovelypet.model.*;
import com.example.lovelypet.service.TokenService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import jakarta.mail.MessagingException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserBusiness {

    private final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private final EmailBusiness emailBusiness;

    private final String part = "src/main/resources/imageUpload/imageUserProfileUpload";

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.emailBusiness = emailBusiness;
    }

    public UserRegisterResponse register(UserRegisterRequest reqUser) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(
                reqUser.getUserName(),
                reqUser.getPassWord(),
                reqUser.getName(),
                reqUser.getEmail(),
                reqUser.getPhoneNumber(),
                token,
                nextXMinute(30)
        );
        UserRegisterResponse ures = userMapper.toUserRegisterResponse(user);
        sendEmail(user);
        return ures;
    }

    public UserProfileResponse getMyUserProfile() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(Integer.parseInt(userId));
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        return userMapper.toUserProfileResponse(optUser.get());
    }

    private void sendEmail(User user) {


        // TODO: generate token
        String token = user.getToken();

        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String updateNormalData(UserUpdateRequest updateRequest) throws BaseException {

        User updatedUser = userService.updateNormalData(
                updateRequest.getId(),
                updateRequest.getName(),
                updateRequest.getPhoneNumber()
        );
        return "";
    }

    public ActivateResponse activate(ActivateRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.activateNoToken();
        }
        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.activateFail();
        }

        User user = opt.get();
        if (user.isActivated()) {
            throw UserException.activateAlready();
        }

        Date now = new Date();
        Date expireDate = user.getTokenExpire();
        if (now.after(expireDate)) {
            //TODO: re-mail
            throw UserException.activateTokenExpire();
        }
        user.setActivated(true);
        userService.update(user);

        ActivateResponse response = new ActivateResponse();
        response.setSuccess(true);
        return response;
    }

    public void resendActivationEmail(ResendActivateEmailRequest request) throws BaseException {
        String token = request.getToken();
        if (StringUtil.isNullOrEmpty(token)) {
            throw UserException.resendActivationEmailNoToken();
        }

        Optional<User> opt = userService.findByToken(token);
        if (opt.isEmpty()) {
            throw UserException.resendActivationTokenNotFound();
        }

        User user = opt.get();

        if (user.isActivated()) {
            throw UserException.activateAlready();
        }
        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextXMinute(30));
        userService.update(user);
        sendEmail(user);
    }

    private Date nextXMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }


    public String resetPassword(UserUpdateRequest updateRequest) throws BaseException {

        String newPassword = updateRequest.getNewPassWord();

        if (Objects.isNull(newPassword)) {
            throw UserException.createPasswordNull();
        }
        Optional<User> opt = userService.findById(updateRequest.getId());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(updateRequest.getOldPassword(), user.getPassWord())) {
            throw UserException.passwordIncorrect();

        }
        User updatedUser = userService.resetPassword(
                updateRequest.getId(),
                updateRequest.getNewPassWord()
        );
        return "";
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(Integer.parseInt(userId));
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public String login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.createUserNameNull();
        }

        Optional<User> opt = userService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        //verify password
        if (!userService.matchPassword(loginRequest.getPassWord(), user.getPassWord())) {
            throw UserException.loginFailPasswordIncorrect();

        }
        if (!user.isActivated()) {
            throw UserException.loginFailUserUnactivated();

        } else {
            return tokenService.tokenize(user);
        }
    }

    public User uploadImage(MultipartFile file, int id) throws IOException, BaseException {

        //validate request
        if (file == null) {
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 5) {
            throw FileException.fileMaxSize();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.unsupported();
        }

        List<String> supportedType = Arrays.asList("image/jpeg", "image/png");
        if (!supportedType.contains(contentType)) {
            throw FileException.unsupported();
        }

        if (Objects.isNull(id)) {
            throw UserException.createUserIdNull();
        }

        // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        String filePath = part + File.separator + fileName;
        //File filePath = new File(uploadDir, fileName);


        // สร้างไดเร็กทอรีถ้ายังไม่มี
        File directory = new File(part);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save the image file
        //file.transferTo(filePath);
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save the image information in the database
        Optional<User> optIdUser = userService.findById(id);
        User user = optIdUser.get();
        user.setUserPhoto(fileName);
        User response = userService.update(user);

        return response;
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    public Optional<User> findById(int id) throws BaseException {
        return userService.findById(id);
    }

    public ResponseEntity<InputStreamResource> getImageById(int id) throws BaseException {
        Optional<User> opt = userService.findById(id);
        if (opt.isPresent()) {
            String filename = opt.get().getUserPhoto();
            File imageFile = new File(part + File.separator + filename);

            try {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + filename)
                        .contentType(MediaType.IMAGE_JPEG)
                        .contentLength(imageFile.length())
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public String getImageUrl(int id) throws BaseException {
        Optional<User> opt = userService.findById(id); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        String filePhoto = opt.get().getUserPhoto();
        String imageUrl = part + File.separator + filePhoto;
        return imageUrl;
    }


    //////////////////////////////////////////


    //////////////////////////////////////////////////////////


}


/* example mapper
public UserRegisterResponse login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.createUserNameNull();
        }

        Optional<User> opt = userService.findLog(loginRequest.getUserName());
        if (opt.isEmpty()) {
            throw UserException.loginFailUserNameNotFound();
        }
        User user = opt.get();
        if (!userService.matchPassword(loginRequest.getPassWord(), user.getPassWord())) {
            throw UserException.loginFailPasswordIncorrect();

        } else {
            UserRegisterResponse ures = userMapper.toUserRegisterResponse(user);
            return ures;
        }
    }*/
