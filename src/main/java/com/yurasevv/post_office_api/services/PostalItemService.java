package com.yurasevv.post_office_api.services;

import com.yurasevv.post_office_api.enums.PostalItemStatus;
import com.yurasevv.post_office_api.exceptions.PostalItemException;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import com.yurasevv.post_office_api.repos.PostOfficeRepo;
import com.yurasevv.post_office_api.repos.PostalItemRepo;
import com.yurasevv.post_office_api.repos.PostalMovementRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostalItemService {

    private final PostalItemRepo postalItemRepo;
    private final PostalMovementRepo postalMovementRepo;
    private final PostOfficeRepo postOfficeRepo;

    public PostalItemService(PostalItemRepo postalItemRepo, PostalMovementRepo postalMovementRepo, PostOfficeRepo postOfficeRepo) {
        this.postalItemRepo = postalItemRepo;
        this.postalMovementRepo = postalMovementRepo;
        this.postOfficeRepo = postOfficeRepo;
    }

    @Transactional
    public PostalItem registerPostalItem(PostalItem postalItem) {
        postalItem.setStatus(PostalItemStatus.REGISTERED);
        postalItemRepo.save(postalItem);
        return postalItem;
    }

    @Transactional
    public void recordArrival(Integer postalItemId, PostalMovement movement) {
        PostalItem postalItem = postalItemRepo.findById(postalItemId)
                .orElseThrow(() -> new PostalItemException("Postal item not found"));
        postOfficeRepo.findById(movement.getPostOffice().getPostalCode())
                .orElseThrow(() -> new PostalItemException("Post Office not found"));
        enrichArrivalMovement(movement);
        movement.setPostalItem(postalItem);
        postalItem.setStatus(PostalItemStatus.ARRIVED_AT_POST_OFFICE);
        postalMovementRepo.save(movement);
    }

    @Transactional
    public void recordDeparture(Integer postalItemId, PostalMovement movement) {
        PostalItem postalItem = postalItemRepo.findById(postalItemId)
                .orElseThrow(() -> new PostalItemException("Postal item not found"));
        postOfficeRepo.findById(movement.getPostOffice().getPostalCode())
                .orElseThrow(() -> new PostalItemException("Post Office not found"));
        enrichDepartureMovement(movement);
        movement.setPostalItem(postalItem);
        postalItem.setStatus(PostalItemStatus.IN_TRANSIT);
        postalMovementRepo.save(movement);
    }

    @Transactional
    public PostalItem deliverPostalItem(Integer postalItemId) {
        PostalItem postalItem = postalItemRepo.findById(postalItemId)
                .orElseThrow(() -> new PostalItemException("Postal item not found"));
        postalItem.setStatus(PostalItemStatus.DELIVERED);
        postalItemRepo.save(postalItem);
        return postalItem;
    }

    public void deleteById(Integer postalItemId) {
        PostalItem postalItem = postalItemRepo.findById(postalItemId)
                .orElseThrow(() -> new PostalItemException("Postal item not found"));
        postalItemRepo.delete(postalItem);
    }

    public List<PostalItem> getPostalItems() {
        return postalItemRepo.findAll();
    }

    public List<PostalMovement> getPostalItemHistory(Integer postalItemId) {

        PostalItem postalItem = postalItemRepo.findById(postalItemId)
                .orElseThrow(() -> new PostalItemException("Postal item not found"));
            return postalItem.getMovements();
    }

    public PostalItem getPostalItemStatus(Integer postalItemId) {
        return postalItemRepo.findById(postalItemId)
                .orElseThrow(() -> new PostalItemException("Postal item not found"));
    }

    public void enrichArrivalMovement(PostalMovement postalMovement) {
        postalMovement.setArrivalTime(LocalDateTime.now());
    }

    public void enrichDepartureMovement(PostalMovement postalMovement) {
        postalMovement.setDepartureTime(LocalDateTime.now());
    }

}
