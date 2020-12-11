package com.infotrans.osk.usefullthings.domain;

import lombok.AllArgsConstructor;
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

    public WorkStation() {
    }

    public WorkStation(String name) {
        this.name = name;
    }
}
