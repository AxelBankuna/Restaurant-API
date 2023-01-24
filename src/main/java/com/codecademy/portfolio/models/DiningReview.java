package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="DINING_REVIEWS")
public class DiningReview {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "diningReview_id_generator"
    )
    @SequenceGenerator(
            name = "diningReview_id_generator",
            sequenceName = "diningReview_id_sequence_name",
            allocationSize = 1,
            initialValue = 10
    )
    private Long id;
    @Column(updatable=false, nullable = false)
    private String username;
    @Column(name = "RESTAURANTID", nullable = false)
    private Long restaurantId;
    @Column(name = "PEANUTSCORE")
    private Float peanutScore;
    @Column(name = "EGGSCORE")
    private Float eggScore;
    @Column(name = "DAIRYSCORE")
    private Float dairyScore;
    @Column(nullable = false)
    private String commentary;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
