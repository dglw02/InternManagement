package com.example.internmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String projectName;

    @Column
    private String projectDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;



}
