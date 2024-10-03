package com.yurasevv.post_office_api.util;

import com.yurasevv.post_office_api.dto.PostalItemDto;
import com.yurasevv.post_office_api.dto.PostalMovementDto;
import com.yurasevv.post_office_api.models.PostalItem;
import com.yurasevv.post_office_api.models.PostalMovement;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    private final ModelMapper modelMapper;

    public DtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PostalItemDto convertToDto(PostalItem postalItem) {
        return modelMapper.map(postalItem, PostalItemDto.class);
    }

    public PostalItem convertToEntity(PostalItemDto postalItemDto) {
        return modelMapper.map(postalItemDto, PostalItem.class);
    }

    public PostalMovementDto convertToDto(PostalMovement postalMovement) {
        return modelMapper.map(postalMovement, PostalMovementDto.class);
    }

    public PostalMovement convertToEntity(PostalMovementDto postalMovementDto) {
        return modelMapper.map(postalMovementDto, PostalMovement.class);
    }
}


