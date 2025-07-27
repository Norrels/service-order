package com.bytes.service.order.contexts.kitchen.utils;

import com.bytes.service.order.contexts.kitchen.adapters.inbound.dtos.UserRequest;
import com.bytes.service.order.contexts.kitchen.adapters.outbound.persistence.entities.UserEntity;
import com.bytes.service.order.contexts.kitchen.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    User toUser(UserRequest userRequest);
}
