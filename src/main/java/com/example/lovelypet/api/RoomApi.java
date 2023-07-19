package com.example.lovelypet.api;
import com.example.lovelypet.business.PhotoRoomBusiness;
import com.example.lovelypet.business.RoomBusiness;
import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.model.RoomRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomApi {

    private static final String IMAGES_PATH = "src/main/resources/imageUpload/"; // เปลี่ยนเป็นเส้นทางจริงของรูปภาพที่อยู่ในเครื่อง Host
    private final RoomBusiness roomBusiness;
    private final PhotoRoomBusiness photoRoomBusiness;

    public RoomApi(RoomBusiness roomBusiness, PhotoRoomBusiness photoRoomBusiness) {
        this.roomBusiness = roomBusiness;
        this.photoRoomBusiness = photoRoomBusiness;
    }

    @PostMapping("/add-room")
    public ResponseEntity<String> addRoom(@RequestBody RoomRequest request) throws BaseException {
        String response = roomBusiness.addRoom(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-room")
    public ResponseEntity<String> updateRoom(@RequestBody RoomRequest request) throws BaseException {
        String response = roomBusiness.updateRoom(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<PhotoRoom> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam int id) throws BaseException, IOException {
        PhotoRoom response = photoRoomBusiness.uploadImage(file, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/imageNames")
    @ResponseBody
    public List<String> getImageNames() {
        return photoRoomBusiness.getAllImageNames();
    }

}
/* ดึงรูปภาพ จาก database
 @GetMapping("/images")
    public ResponseEntity<InputStreamResource> getImageById(@RequestParam int id) {
        Optional<PhotoRoom> imageEntity = photoRoomBusiness.findById(id);
        if (imageEntity.isPresent()) {
            String filename = imageEntity.get().getPhotoRoomPartFile();
            File imageFile = new File("src/main/resources/imageUpload/" + filename);

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
 */