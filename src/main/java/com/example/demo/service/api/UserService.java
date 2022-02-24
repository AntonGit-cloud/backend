package com.example.demo.service.api;

import com.example.demo.dto.PasswordSetRequest;
import com.example.demo.dto.ProfileRequest;
import com.example.demo.dto.ProfileResponse;
import com.example.demo.model.User;
import com.example.demo.model.misc.Role;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> getByEmail(String email);

    User getByEmailOrPhone(String emailOrPhone);

    Optional<User> getByEmailOrPhone(String email, String dialCode, String phoneNumber);

    Optional<User> getByPhone(String dialCode, String phoneNumber);

    User getById(String id);

    ProfileResponse getCurrentUserProfile();

    ProfileResponse updateCurrentUserProfile(ProfileRequest profileRequest);

    void changePassword(PasswordSetRequest passwordSetRequest);

    //User confirmAccount(PasswordSetRequest passwordSetRequest);

    //void resetPassword(String email);

    User updateUserRoles(User user, Set<Role> rolesToAdd, Set<Role> rolesToRemove);

    //void confirmPasswordReset(PasswordSetRequest passwordSetRequest);

    User saveOrUpdateExisting(User user, Role role, boolean sendActivationEmail, boolean overwrite);

    //User saveExtId(User user, Integer extId);

    User getCurrentUser();

    Role getCurrentUserContextRole();

    String getCurrentUserContextId();
}