package com.yurasevv.post_office_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostalMovementErrorResponse {
    private String message;
    private long timestamp;
}
