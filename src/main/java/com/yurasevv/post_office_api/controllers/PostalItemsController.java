package com.yurasevv.post_office_api.controllers;

import com.yurasevv.post_office_api.dto.PostalItemDto;
import com.yurasevv.post_office_api.dto.PostalMovementDto;
import com.yurasevv.post_office_api.exceptions.PostalItemErrorResponse;
import com.yurasevv.post_office_api.exceptions.PostalItemException;
import com.yurasevv.post_office_api.exceptions.PostalMovementErrorResponse;
import com.yurasevv.post_office_api.exceptions.PostalMovementException;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import com.yurasevv.post_office_api.services.PostalItemService;
import com.yurasevv.post_office_api.util.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yurasevv.post_office_api.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/postal_items")
public class PostalItemsController {

    private final PostalItemService postalItemService;
    private final DtoMapper dtoMapper;

    public PostalItemsController(PostalItemService postalItemService, DtoMapper dtoMapper) {
        this.postalItemService = postalItemService;
        this.dtoMapper = dtoMapper;
    }

    @Operation(summary = "Получить все почтовые отправления")
    @GetMapping
    public ResponseEntity<List<PostalItemDto>> getPostalItem() {
        List<PostalItemDto> postalItems = postalItemService.getPostalItems()
                .stream().map(dtoMapper::convertToDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(postalItems);
    }


    @Operation(summary = "Зарегистрировать почтовое отправление", description = "Обязательно указать существующую почту")
    @PostMapping("/register")
    public ResponseEntity<PostalItem> registerPostalItem(@RequestBody @Valid PostalItemDto postalItemDto,
                                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult, postalItemDto);
        }
        PostalItem postalItem = dtoMapper.convertToEntity(postalItemDto);

        PostalItem registeredItem = postalItemService.registerPostalItem(postalItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredItem);
    }


    @Operation(summary = "Зарегистрировать прибытие почтового отправления", description = "Обязательно указать существующую почту")
    @PatchMapping("/{id}/movement/arrival")
    public ResponseEntity<Void> recordArrival(@PathVariable Integer id,
                                               @RequestBody @Valid PostalMovementDto movementDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult, movementDto);
        }

        PostalMovement movement = dtoMapper.convertToEntity(movementDto);

        postalItemService.recordArrival(id, movement);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Зарегистрировать убытие почтового отправления", description = "Обязательно указать существующую почту")
    @PatchMapping("/{id}/movement/departure")
    public ResponseEntity<Void> recordDeparture(@PathVariable Integer id,
                                              @RequestBody @Valid PostalMovementDto movementDto,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult, movementDto);
        }

        PostalMovement movement = dtoMapper.convertToEntity(movementDto);

        postalItemService.recordDeparture(id, movement);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(summary = "Зарегистрировать доставку почтового отправления")
    @PostMapping("/{id}/deliver")
    public ResponseEntity<PostalItemDto> deliverPostalItem(@PathVariable Integer id) {

        PostalItemDto deliveredItemDto = dtoMapper.convertToDto(postalItemService.deliverPostalItem(id));

        return ResponseEntity.ok(deliveredItemDto);
    }


    @Operation(summary = "Получить историю почтового отправления")
    @GetMapping("/{id}/history")
    public ResponseEntity<List<PostalMovementDto>> getPostalItemHistory(@PathVariable Integer id) {

        List<PostalMovement> rawHistory = postalItemService.getPostalItemHistory(id);
        List<PostalMovementDto> history = rawHistory
                .stream()
                .map(dtoMapper::convertToDto)
                .toList();

        return ResponseEntity.ok(history);
    }


    @Operation(summary = "Получить статус почтового отправления")
    @GetMapping("/{id}/status")
    public ResponseEntity<PostalItemDto> getPostalItemStatus(@PathVariable Integer id) {

        PostalItemDto postalItemDto = dtoMapper.convertToDto(postalItemService.getPostalItemStatus(id));

        return ResponseEntity.ok(postalItemDto);
    }

    @Operation(summary = "Удалить почтовое отправление")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deletePostalItem(@PathVariable Integer id) {
        postalItemService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @ExceptionHandler
    private ResponseEntity<PostalItemErrorResponse> handleException(PostalItemException e) {
        PostalItemErrorResponse response = new PostalItemErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PostalMovementErrorResponse> handleException(PostalMovementException e) {
        PostalMovementErrorResponse response = new PostalMovementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
