package com.example.lovelypet.model;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoRoom;
import com.example.lovelypet.entity.RoomType;
import lombok.Data;

import java.util.List;

@Data
public class RoomResponseList {

    private int id;
    private int roomNumber;
    private String roomDetails;
    private float roomPrice;
    private String status;
    private String type;
}
