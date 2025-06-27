package com.example.back_end.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class NoticeE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private LocalDate date;

    @Lob
    private byte[] noticeImage;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getNoticeImage() {
        return noticeImage;
    }

    public void setNoticeImage(byte[] noticeImage) {
        this.noticeImage = noticeImage;
    }

}
