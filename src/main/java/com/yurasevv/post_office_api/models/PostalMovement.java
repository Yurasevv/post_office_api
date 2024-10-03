package com.yurasevv.post_office_api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class PostalMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private PostalItem postalItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "postalCode")
    @JsonIgnore
    private PostOffice postOffice;

    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

}
