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
    @Column(updatable=false)
    private String username;
    @Column(name = "RESTAURANTID")
    private Long restaurantId;
    @Column(name = "PEANUTSCORE")
    private Float peanutScore;
    @Column(name = "EGGSCORE")
    private Float eggScore;
    @Column(name = "DAIRYSCORE")
    private Float dairyScore;
    @Column
    private String commentary;
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
}
