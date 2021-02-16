package com.infotrans.osk.usefullthings.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class WorkStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public WorkStation() {
    }

    public WorkStation(String name, User author) {
        this.name = name;
        this.author = author;
    }

    public String getAuthorName(){
        return author!=null? author.getUsername() : "none";
    }
}
