package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "room_type")
public class RoomType extends BaseEntity {

    @Column(nullable = false, length = 60,unique = true)
    private String name;

    //FK
    @OneToMany(mappedBy = "roomTypeId", fetch = EAGER, orphanRemoval = true)
    private List<Room> rooms;

}
