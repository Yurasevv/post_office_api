package com.yurasevv.post_office_api.util;

import com.yurasevv.post_office_api.dto.PostalItemDto;
import com.yurasevv.post_office_api.dto.PostalMovementDto;
import com.yurasevv.post_office_api.exceptions.PostalItemException;
import com.yurasevv.post_office_api.exceptions.PostalMovementException;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static void returnErrorsToClient(BindingResult bindingResult, PostalItemDto item) {

        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" -- ").append(error.getDefaultMessage())
                    .append(";");
        }

        throw new PostalItemException(errorMsg.toString());
    }

    public static void returnErrorsToClient(BindingResult bindingResult, PostalMovementDto movement) {

        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();

        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" -- ").append(error.getDefaultMessage())
                    .append(";");
        }

        throw new PostalMovementException(errorMsg.toString());
    }
}
