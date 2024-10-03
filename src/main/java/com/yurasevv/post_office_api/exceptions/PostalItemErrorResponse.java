package com.yurasevv.post_office_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostalItemErrorResponse {
    private String message;
    private long timestamp;

}
