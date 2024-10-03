package com.yurasevv.post_office_api.dto;

import com.yurasevv.post_office_api.enums.PostalItemStatus;
import com.yurasevv.post_office_api.enums.PostalItemType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostalItemDto {

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

    @NotNull
    private PostalItemStatus status;
}
