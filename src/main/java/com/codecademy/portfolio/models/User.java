package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_generator"
    )
    @SequenceGenerator(
            name = "user_id_generator",
            sequenceName = "user_id_sequence_name",
            allocationSize = 1,
            initialValue = 10
    )
    private Long id;
    @Column(unique = true, updatable = false, nullable = false)
    private String username;
    private String city;
    @Column(name="STATE")
    private String state;
    private Integer zipcode;
    @Column(name="PEANUTINTEREST")
    private Boolean peanutInterest;
    @Column(name="EGGINTEREST")
    private Boolean eggInterest;
    @Column(name="DAIRYINTEREST")
    private Boolean dairyInterest;
}
