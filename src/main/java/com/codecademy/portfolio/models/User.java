package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    private String username;
    @Column
    private String City;
    @Column
    private String state;
    @Column
    private Integer zipcode;
    @Column
    private Boolean peanutInterest;
    @Column
    private Boolean eggInterest;
    @Column
    private Boolean dairyInterest;
}
