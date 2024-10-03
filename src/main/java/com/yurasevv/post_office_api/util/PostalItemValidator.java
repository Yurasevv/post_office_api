package com.yurasevv.post_office_api.util;

import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import com.yurasevv.post_office_api.services.PostalItemService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostalItemValidator implements Validator {
    private final PostalItemService postalItemService;

    public PostalItemValidator(PostalItemService postalItemService) {
        this.postalItemService = postalItemService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PostalItem.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostalMovement postalMovement = (PostalMovement) target;

        // Проверка на наличие отправлений с таким же id почтового офиса. Значит отправление уже на почте.
        boolean isItemInOffice = postalItemService.getPostalItemHistory(postalMovement.getPostalItem().getId()).stream()
                .anyMatch(m -> m.getPostOffice().getPostalCode().equals(postalMovement.getPostOffice().getPostalCode()));

        if (isItemInOffice) {
            errors.rejectValue("postal_office", "", "Postal item is already in office");
        }
    }
}
