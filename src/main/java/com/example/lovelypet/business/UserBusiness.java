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
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;
    private final String path = "src/main/resources/imageUpload/imageUserProfileUpload";

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper, EmailBusiness emailBusiness, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.emailBusiness = emailBusiness;
        this.passwordEncoder = passwordEncoder;    }

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
        if (optUser.isEmpty()) {
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

    public UserRegisterResponse updateNormalData(UserUpdateRequest updateRequest) throws BaseException {
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt1.get();

        Optional<User> opt = userService.findById(Integer.parseInt(userId));
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        User user = opt.get();
        if (!Objects.isNull(updateRequest.getName())) {
            user.setName(updateRequest.getName());
        }
        if (!Objects.isNull(updateRequest.getPhoneNumber())) {
            user.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        User update = userService.update(user);
        return userMapper.toUserRegisterResponse(update);
    }

    public ActivateResponse activate(String request) throws BaseException {

        if (StringUtil.isNullOrEmpty(request)) {
            throw UserException.activateNoToken();
        }
        Optional<User> opt = userService.findByToken(request);
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
            throw UserException.resetPasswordIsNullNewPassword();
        }
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt1.get();
        Optional<User> opt = userService.findById(Integer.parseInt(userId));
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        User user = opt.get();

        if (!userService.matchPassword(updateRequest.getOldPassword(), user.getPassWord())) {
            throw UserException.passwordIncorrect();

        }
        User optEmail = userService.resetPassword(user, newPassword);
        sendEmailResetPassword(optEmail);
        return "Successful password reset";
    }

    private void sendEmailResetPassword(User user) {

        // generate token
        String token = user.getToken();

        try {
            emailBusiness.sendResetPasswordEmail(user.getEmail(), user.getName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public String notResetPassword(UserUpdateRequest request) throws BaseException {

        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt1.get();
        if (Objects.isNull(request.getOldPassword())) {
            throw UserException.resetPasswordIsNullOldPassword();
        }
        Optional<User> opt = userService.findById(Integer.parseInt(userId));
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        User user = opt.get();
        user.setPassWord(passwordEncoder.encode(request.getOldPassword()));
        userService.update(user);
        return "Successful password recovery";
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(Integer.parseInt(userId));
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public String login(LoginRequest loginRequest) throws BaseException {
        String op = loginRequest.getPassWord();
        String ou = loginRequest.getUserName();
        if (Objects.isNull(op)) {
            throw UserException.loginPasswordNull();
        }
        if (Objects.isNull(ou)) {
            throw UserException.loginUserNameNull();
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
        }
        if (Objects.nonNull(user.getDateDeleteAccount())) {
            user.setDateDeleteAccount(null);
            User response = userService.update(user);
            return tokenService.tokenize(response);
        } else {
            return tokenService.tokenize(user);
        }
    }

    public String uploadImage(MultipartFile file) throws IOException, BaseException {

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

        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt1.get();

        Optional<User> optIdUser = userService.findById(Integer.parseInt(userId));
        if (optIdUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optIdUser.get();
        //จะมีได้แค่ 1 รูป
        if (Objects.nonNull(user.getUserPhoto())) {
            throw UserException.imageAlreadyExists();
        }


        // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        String filePath = path + File.separator + fileName;
        //File filePath = new File(uploadDir, fileName);


        // สร้างไดเร็กทอรีถ้ายังไม่มี
        File directory = new File(path);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            // ตรวจสอบผลลัพธ์
            if (!success) {
                throw FileException.failedToCreateDirectory();
            }
        }

        // Save the image file
        //file.transferTo(filePath);
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save the image information in the database
        user.setUserPhoto(fileName);
        User response = userService.update(user);

        return "Image" + response.getUserPhoto() + "has been successfully uploaded.";
    }

    // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    public Optional<User> findById(int id) throws BaseException {
        return userService.findById(id);
    }

    public ResponseEntity<InputStreamResource> getImageById() throws BaseException {
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt1.get();
        Optional<User> opt = userService.findById(Integer.parseInt(userId));
        if (opt.isPresent()) {
            String filename = opt.get().getUserPhoto();
            File imageFile = new File(path + File.separator + filename);

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

    public String getImageUrl() throws BaseException {
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt1.get();
        Optional<User> opt = userService.findById(Integer.parseInt(userId)); // ดึงข้อมูลรูปภาพทั้งหมดจากฐานข้อมูล
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        return opt.get().getUserPhoto();

    }

    public String deleteRequest() throws BaseException {
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt1.get();
        Optional<User> optUser = findById(Integer.parseInt(userId));
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        calendar.add(Calendar.DAY_OF_MONTH, 90);
        Date dateDelete = calendar.getTime();
        user.setDateDeleteAccount(dateDelete);
        User response = userService.update(user);
        return response.getName() + "account will be deleted when" + response.getDateDeleteAccount() + "if not logged in.";
    }

    public void deleteAccount(int id) throws BaseException {
        Optional<User> opt = userService.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        Date date = calendar.getTime();
        User user = opt.get();
        if (!user.getDateDeleteAccount().equals(date)) {
            return;
        }
        String fileName = user.getUserPhoto();
        String filePath = path + File.separator + fileName;

        // สร้างอ็อบเจ็กต์ File จาก path ของไฟล์
        File imageFile = new File(filePath);

        // ตรวจสอบว่าไฟล์มีอยู่จริงหรือไม่ และลบไฟล์ออกจากเครื่อง server
        if (imageFile.exists()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                throw FileException.deleteImageFailed();
            }
        } else {
            throw FileException.deleteNoFile();
        }
        userService.deleteByIdU(user.getId());
    }

    public String deleteImage() throws BaseException {
        Optional<String> opt1 = SecurityUtil.getCurrentUserId();
        if (opt1.isEmpty()) {
            throw UserException.unauthorized();
        }
        String userId = opt1.get();
        Optional<User> optUser = findById(Integer.parseInt(userId));
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();
        String fileName = user.getUserPhoto();
        String filePath = path + File.separator + fileName;

        // สร้างอ็อบเจ็กต์ File จาก path ของไฟล์
        File imageFile = new File(filePath);

        // ตรวจสอบว่าไฟล์มีอยู่จริงหรือไม่ และลบไฟล์ออกจากเครื่อง server
        if (imageFile.exists()) {
            boolean deleted = imageFile.delete();
            if (!deleted) {
                throw FileException.deleteImageFailed();
            }
        } else {
            throw FileException.deleteNoFile();
        }

        user.setUserPhoto(null);
        userService.update(user);

        return "Successfully deleted the" + fileName + "image.";
    }

    //////////////////////////////////////////

    public String uploadImageForRegister(MultipartFile file,int id) throws IOException, BaseException {

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

        Optional<User> optIdUser = userService.findById(id);
        if (optIdUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optIdUser.get();
        //จะมีได้แค่ 1 รูป
        if (Objects.nonNull(user.getUserPhoto())) {
            throw UserException.imageAlreadyExists();
        }


        // สร้างชื่อไฟล์ที่ไม่ซ้ำกัน
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        String filePath = path + File.separator + fileName;
        //File filePath = new File(uploadDir, fileName);


        // สร้างไดเร็กทอรีถ้ายังไม่มี
        File directory = new File(path);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            // ตรวจสอบผลลัพธ์
            if (!success) {
                throw FileException.failedToCreateDirectory();
            }
        }

        // Save the image file
        //file.transferTo(filePath);
        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        // Save the image information in the database
        user.setUserPhoto(fileName);
        User response = userService.update(user);

        return "Image" + response.getUserPhoto() + "has been successfully uploaded.";
    }

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
