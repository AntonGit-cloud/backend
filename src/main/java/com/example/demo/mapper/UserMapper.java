package com.example.demo.mapper;

import com.example.demo.dto.ProfileResponse;
import com.example.demo.mapper.mixin.NameMixin;
import com.example.demo.mapper.mixin.PhoneNumberMixin;
import com.example.demo.model.Lecturer;
import com.example.demo.model.Student;
import com.example.demo.model.misc.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class UserMapper implements NameMixin, PhoneNumberMixin {

    //@Autowired
    //private DictionaryService dictionaryService;

    @Mapping(source = "lecturer.user.phoneNumber", target = "phoneNumber.number")
    @Mapping(source = "lecturer.user.dialCode", target = "phoneNumber.dialCode")
    @Mapping(source = "lecturer.user", target = "name", qualifiedByName = "mapUserName")
    @Mapping(source = "lecturer.user.email", target = "email")
    //@Mapping(source = "lecturer.user.language", target = "language") TODO switch language
    @Mapping(source = "role", target = "role")
    public abstract ProfileResponse fromLecturerToProfile(Lecturer lecturer, Role role);

    @Mapping(source = "student.user.phoneNumber", target = "phoneNumber.number")
    @Mapping(source = "student.user.dialCode", target = "phoneNumber.dialCode")
    @Mapping(source = "student.user", target = "name", qualifiedByName = "mapUserName")
    @Mapping(source = "student.user.email", target = "email")
    //@Mapping(source = "tenant.user.language", target = "language") TODO switch language
    @Mapping(source = "role", target = "role")
    public abstract ProfileResponse fromStudentToProfile(Student student, Role role);


}
