package com.yurasevv.post_office_api.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yurasevv.post_office_api.enums.PostalItemStatus;
import com.yurasevv.post_office_api.enums.PostalItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PostalItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Empty name")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String recipientName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PostalItemType type;

    @Min(6)
    @NotNull
    private Integer recipientPostalCode;

    @NotEmpty
    @Size(min = 2, max = 50, message = "Address should be between 2 and 50 characters")
    private String recipientAddress;

    @Enumerated(EnumType.STRING)
    private PostalItemStatus status;

    @OneToMany(mappedBy = "postalItem")
    private List<PostalMovement> movements = new ArrayList<>();

}
