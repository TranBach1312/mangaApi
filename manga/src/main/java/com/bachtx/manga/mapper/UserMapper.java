package com.bachtx.manga.mapper;

import com.bachtx.manga.dto.request.LoginRequest;
import com.bachtx.manga.dto.request.RegisterRequest;
import com.bachtx.manga.dto.response.UserResponse;
import com.bachtx.manga.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    User loginRequestToUser(LoginRequest loginRequest);
    User registerRequestToUser(RegisterRequest registerRequest);
    @Mappings({
            @Mapping(target = "mangas", ignore = true)
    })
    UserResponse userToUserResponse(User user);
}
