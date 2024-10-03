package com.yurasevv.post_office_api.services;

import com.yurasevv.post_office_api.enums.PostalItemStatus;
import com.yurasevv.post_office_api.models.PostOffice;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import com.yurasevv.post_office_api.repos.PostOfficeRepo;
import com.yurasevv.post_office_api.repos.PostalItemRepo;
import com.yurasevv.post_office_api.repos.PostalMovementRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostalItemServiceTest {
    @Mock
    private PostalItemRepo postalItemRepo;

    @Mock
    private PostalMovementRepo postalMovementRepo;

    @Mock
    private PostOfficeRepo postOfficeRepo;

    @InjectMocks
    private PostalItemService postalItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterPostalItem() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);
        PostalItem savedPostalItem = new PostalItem();
        savedPostalItem.setId(1);
        savedPostalItem.setStatus(PostalItemStatus.REGISTERED);

        when(postalItemRepo.save(any(PostalItem.class))).thenReturn(savedPostalItem);

        PostalItem result = postalItemService.registerPostalItem(postalItem);

        assertEquals(PostalItemStatus.REGISTERED, result.getStatus());
        verify(postalItemRepo, times(1)).save(postalItem);
    }

    @Test
    void testRecordArrival() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);
        postalItem.setStatus(PostalItemStatus.IN_TRANSIT);

        PostOffice postOffice = new PostOffice();
        postOffice.setPostalCode(12345);

        PostalMovement movement = new PostalMovement();
        movement.setPostOffice(postOffice);

        when(postalItemRepo.findById(1)).thenReturn(Optional.of(postalItem));
        when(postOfficeRepo.findById(12345)).thenReturn(Optional.of(postOffice));

        postalItemService.recordArrival(1, movement);

        assertEquals(PostalItemStatus.ARRIVED_AT_POST_OFFICE, postalItem.getStatus());
        verify(postalMovementRepo, times(1)).save(movement);
    }

    @Test
    void testRecordDeparture() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);
        postalItem.setStatus(PostalItemStatus.ARRIVED_AT_POST_OFFICE);

        PostOffice postOffice = new PostOffice();
        postOffice.setPostalCode(123456);

        PostalMovement movement = new PostalMovement();
        movement.setPostOffice(postOffice);

        when(postalItemRepo.findById(1)).thenReturn(Optional.of(postalItem));
        when(postOfficeRepo.findById(123456)).thenReturn(Optional.of(postOffice));

        postalItemService.recordDeparture(1, movement);

        assertEquals(PostalItemStatus.IN_TRANSIT, postalItem.getStatus());
        verify(postalMovementRepo, times(1)).save(movement);
    }

    @Test
    void testDeliverPostalItem() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);
        postalItem.setStatus(PostalItemStatus.IN_TRANSIT);

        when(postalItemRepo.findById(1)).thenReturn(Optional.of(postalItem));

        PostalItem result = postalItemService.deliverPostalItem(1);

        assertEquals(PostalItemStatus.DELIVERED, result.getStatus());
        verify(postalItemRepo, times(1)).save(postalItem);
    }

    @Test
    void testDeleteById() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);

        when(postalItemRepo.findById(1)).thenReturn(Optional.of(postalItem));

        postalItemService.deleteById(1);

        verify(postalItemRepo, times(1)).delete(postalItem);
    }

    @Test
    void testGetPostalItems() {
        List<PostalItem> postalItems = new ArrayList<>();
        postalItems.add(new PostalItem());

        when(postalItemRepo.findAll()).thenReturn(postalItems);

        List<PostalItem> result = postalItemService.getPostalItems();

        assertEquals(1, result.size());
        verify(postalItemRepo, times(1)).findAll();
    }

    @Test
    void testGetPostalItemStatus() {
        PostalItem postalItem = new PostalItem();
        postalItem.setId(1);
        postalItem.setStatus(PostalItemStatus.REGISTERED);

        when(postalItemRepo.findById(1)).thenReturn(Optional.of(postalItem));

        PostalItem result = postalItemService.getPostalItemStatus(1);

        assertEquals(PostalItemStatus.REGISTERED, result.getStatus());
    }
}
