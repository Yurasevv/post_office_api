package com.yurasevv.post_office_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class PostOffice {

    @Id
    private Integer postalCode;

    private String name;
    private String address;

}
