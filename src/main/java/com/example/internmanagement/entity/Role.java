package com.example.internmanagement.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name="tbl_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column

    private String name;
    public Role() {

    }

    public Role(String name) {
        super();
        this.name = name;
    }

}
