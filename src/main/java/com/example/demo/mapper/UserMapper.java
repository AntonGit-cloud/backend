package com.example.demo.mapper;

import com.example.demo.dto.UserResponse;
import com.example.demo.mapper.mixin.AddressMixin;
import com.example.demo.mapper.mixin.NameMixin;
import com.example.demo.mapper.mixin.PhoneNumberMixin;
import com.example.demo.model.User;
import com.example.demo.model.misc.Role;
import com.example.demo.service.api.DictionaryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper
public abstract class UserMapper implements NameMixin, PhoneNumberMixin, AddressMixin {

    @Autowired
    private DictionaryService dictionaryService;

    @Mapping(source = "user.dialCode", target = "phone.dialCode")
    @Mapping(source = "user.phoneNumber", target = "phone.number")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "contextId", target = "id")
    public abstract UserResponse fromUserToUserResponse(User user, Role role, String contextId);


}
