package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="DINING_REVIEW")
public class DiningReview {
    @Id
    private Long id;
    @Column(name="USERNAME", updatable=false, unique = true)
    private String username;
    @Column
    private Long restaurantId;
    @Column
    private Float peanutScore;
    @Column
    private Float eggScore;
    @Column
    private Float dairyScore;
    @Column
    private String commentary;
    @Column
    private Status status;
}
