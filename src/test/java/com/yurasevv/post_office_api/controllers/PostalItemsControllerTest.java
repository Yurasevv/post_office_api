package com.yurasevv.post_office_api.controllers;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yurasevv.post_office_api.dto.PostalItemDto;
import com.yurasevv.post_office_api.dto.PostalMovementDto;
import com.yurasevv.post_office_api.enums.PostalItemStatus;
import com.yurasevv.post_office_api.enums.PostalItemType;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import com.yurasevv.post_office_api.services.PostalItemService;
import com.yurasevv.post_office_api.util.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

class PostalItemsControllerTest {

    @Mock
    private PostalItemService postalItemService;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private PostalItemsController postalItemsController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postalItemsController).build();
    }


    @Test
    void testGetPostalItem() throws Exception {
        PostalItemDto postalItemDto = new PostalItemDto();
        when(postalItemService.getPostalItems()).thenReturn(Collections.singletonList(new PostalItem()));
        when(dtoMapper.convertToDto(any(PostalItem.class))).thenReturn(postalItemDto);

        mockMvc.perform(get("/api/postal_items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testRegisterPostalItem() throws Exception {
        PostalItemDto postalItemDto = new PostalItemDto(
                "name", PostalItemType.LETTER, 123456, "address", PostalItemStatus.REGISTERED
        );
        PostalItem postalItem = new PostalItem();
        when(dtoMapper.convertToEntity(any(PostalItemDto.class))).thenReturn(postalItem);
        when(postalItemService.registerPostalItem(any(PostalItem.class))).thenReturn(postalItem);

        mockMvc.perform(post("/api/postal_items/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postalItemDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testRecordArrival() throws Exception {
        PostalMovementDto movementDto = new PostalMovementDto();
        PostalMovement movement = new PostalMovement();
        when(dtoMapper.convertToEntity(any(PostalMovementDto.class))).thenReturn(movement);

        mockMvc.perform(patch("/api/postal_items/1/movement/arrival")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movementDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testRecordDeparture() throws Exception {
        PostalMovementDto movementDto = new PostalMovementDto();
        PostalMovement movement = new PostalMovement();
        when(dtoMapper.convertToEntity(any(PostalMovementDto.class))).thenReturn(movement);

        mockMvc.perform(patch("/api/postal_items/1/movement/departure")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movementDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeliverPostalItem() throws Exception {
        PostalItemDto postalItemDto = new PostalItemDto();
        when(postalItemService.deliverPostalItem(anyInt())).thenReturn(new PostalItem());
        when(dtoMapper.convertToDto(any(PostalItem.class))).thenReturn(postalItemDto);

        mockMvc.perform(post("/api/postal_items/1/deliver"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPostalItemHistory() throws Exception {
        PostalMovementDto movementDto = new PostalMovementDto();
        when(postalItemService.getPostalItemHistory(anyInt())).thenReturn(Collections.singletonList(new PostalMovement()));
        when(dtoMapper.convertToDto(any(PostalMovement.class))).thenReturn(movementDto);

        mockMvc.perform(get("/api/postal_items/1/history"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPostalItemStatus() throws Exception {
        PostalItemDto postalItemDto = new PostalItemDto();
        when(postalItemService.getPostalItemStatus(anyInt())).thenReturn(new PostalItem());
        when(dtoMapper.convertToDto(any(PostalItem.class))).thenReturn(postalItemDto);

        mockMvc.perform(get("/api/postal_items/1/status"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePostalItem() throws Exception {
        mockMvc.perform(delete("/api/postal_items/1/delete"))
                .andExpect(status().isNoContent());
    }
}
