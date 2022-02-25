package com.backend.demo.mapper.mixin;

import com.backend.demo.model.User;
import org.mapstruct.Named;

public interface NameMixin {

    String USER_NAME_MAPPING_PATTERN = "%s %s";

    @Named("mapName")
    default String mapName(String firstName, String lastName) {
        return String.format(USER_NAME_MAPPING_PATTERN, firstName, lastName);
    }

    @Named("mapUserName")
    default String mapUserName(User user) {
        return String.format(USER_NAME_MAPPING_PATTERN, user.getFirstName(), user.getLastName());
    }

    @Named("mapUserInitials")
    default String mapUserInitials(User user) {
        return String.format("%c%c", user.getFirstName().charAt(0), user.getLastName().charAt(0));
    }

    @Named("mapInitials")
    default String mapInitials(String firstName, String lastName) {
        return String.format("%c%c", firstName.charAt(0), lastName.charAt(0));
    }

    @Named("getFirstNameFromString")
    default String getFirstNameFromString(String name) {
        return name.substring(0, name.indexOf(' '));
    }

    @Named("getLastNameFromString")
    default String getLastNameFromString(String name) {
        return name.substring(name.indexOf(' ') + 1);
    }
}
