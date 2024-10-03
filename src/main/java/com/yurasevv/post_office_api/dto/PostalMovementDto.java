package com.yurasevv.post_office_api.dto;

import com.yurasevv.post_office_api.models.PostOffice;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostalMovementDto {

    private PostOffice postOffice;

}
