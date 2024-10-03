package com.yurasevv.post_office_api.util;

import com.yurasevv.post_office_api.models.PostOffice;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import com.yurasevv.post_office_api.services.PostalItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PostalItemValidatorTest {

    @Mock
    private PostalItemService postalItemService;

    @Mock
    private Errors errors;

    @InjectMocks
    private PostalItemValidator postalItemValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateWhenItemAlreadyInOffice() {
        // Arrange
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);

        PostOffice postOffice = new PostOffice();
        postOffice.setPostalCode(123456);

        PostalMovement postalMovement = new PostalMovement();
        postalMovement.setPostalItem(postalItem);
        postalMovement.setPostOffice(postOffice);

        List<PostalMovement> existingMovements = new ArrayList<>();
        PostalMovement existingMovement = new PostalMovement();
        existingMovement.setPostOffice(postOffice);
        existingMovements.add(existingMovement);

        when(postalItemService.getPostalItemHistory(1)).thenReturn(existingMovements);

        postalItemValidator.validate(postalMovement, errors);

        verify(errors, times(1)).rejectValue("postal_office", "", "Postal item is already in office");
    }

    @Test
    void testValidateWhenItemNotInOffice() {
        // Arrange
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);

        PostOffice postOffice = new PostOffice();
        postOffice.setPostalCode(123456);

        PostalMovement postalMovement = new PostalMovement();
        postalMovement.setPostalItem(postalItem);
        postalMovement.setPostOffice(postOffice);

        List<PostalMovement> existingMovements = new ArrayList<>();
        PostalMovement otherMovement = new PostalMovement();
        PostOffice otherOffice = new PostOffice();
        otherOffice.setPostalCode(654321);
        otherMovement.setPostOffice(otherOffice);
        existingMovements.add(otherMovement);

        when(postalItemService.getPostalItemHistory(1)).thenReturn(existingMovements);

        postalItemValidator.validate(postalMovement, errors);

        verify(errors, never()).rejectValue("postal_office", "", "Postal item is already in office");
    }
}
