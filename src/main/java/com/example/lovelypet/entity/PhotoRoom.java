package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "photo_room")
public class PhotoRoom extends BaseEntity {

    @Column(nullable = true, length = 254)
    private String photoRoomFile;

    // Fk

    //entity hotel
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room roomId;
}
