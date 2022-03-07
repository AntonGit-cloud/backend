package com.demo.backend.service.api;

import com.demo.backend.dto.PasswordSetRequest;
import com.demo.backend.dto.ProfileRequest;
import com.demo.backend.dto.ProfileResponse;
import com.demo.backend.model.User;
import com.demo.backend.model.misc.Role;

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