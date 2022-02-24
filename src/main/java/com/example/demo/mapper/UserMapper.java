package com.example.demo.mapper;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.mapper.mixin.AddressMixin;
import com.example.demo.mapper.mixin.NameMixin;
import com.example.demo.mapper.mixin.PhoneNumberMixin;
import com.example.demo.model.Landlord;
import com.example.demo.model.Tenant;
import com.example.demo.model.misc.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class UserMapper implements NameMixin, PhoneNumberMixin, AddressMixin {

    //@Autowired
    //private DictionaryService dictionaryService;

    @Mapping(source = "landlord.user.phoneNumber", target = "phoneNumber.number")
    @Mapping(source = "landlord.user.dialCode", target = "phoneNumber.dialCode")
    @Mapping(source = "landlord.user", target = "name", qualifiedByName = "mapUserName")
    @Mapping(source = "landlord.user.email", target = "email")
    //@Mapping(source = "landlord.user.language", target = "language") TODO switch language
    @Mapping(source = "role", target = "role")
    public abstract ProfileResponse fromLandlordToProfile(Landlord landlord, Role role);

    @Mapping(source = "tenant.user.phoneNumber", target = "phoneNumber.number")
    @Mapping(source = "tenant.user.dialCode", target = "phoneNumber.dialCode")
    @Mapping(source = "tenant.user", target = "name", qualifiedByName = "mapUserName")
    @Mapping(source = "tenant.user.email", target = "email")
    //@Mapping(source = "tenant.user.language", target = "language") TODO switch language
    @Mapping(source = "role", target = "role")
    public abstract ProfileResponse fromTenantToProfile(Tenant tenant, Role role);


}
