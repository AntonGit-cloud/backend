package com.example.demo.service;

import com.example.demo.dto.PasswordSetRequest;
import com.example.demo.dto.ProfileRequest;
import com.example.demo.dto.ProfileResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Lecturer;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.model.misc.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUser;
import com.example.demo.service.api.LecturerService;
import com.example.demo.service.api.StudentService;
import com.example.demo.service.api.UserService;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {


    @Lazy
    @Autowired
    private StudentService studentService;

    @Lazy
    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${garentii.lecturer.frontend.url}")
    private String studentFrontendUrl;

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public User getByEmailOrPhone(String emailOrPhone) {
        return userRepository.findByEmailOrPhone(Pattern.quote(emailOrPhone).trim(), emailOrPhone.trim())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Can not find user by '%s'", emailOrPhone)));
    }

    @Override
    public Optional<User> getByEmailOrPhone(String email, String dialCode, String phoneNumber) {
        return userRepository.findByEmailIgnoreCaseOrDialCodeAndPhoneNumber(email, dialCode, phoneNumber);
    }

    @Override
    public Optional<User> getByPhone(String dialCode, String phoneNumber) {
        return userRepository.findByDialCodeAndPhoneNumber(dialCode, phoneNumber);
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find user by '%s'", id)));
    }

    @Override
    public ProfileResponse getCurrentUserProfile() {
        Role currentUserContextRole = getCurrentUserContextRole();
        switch (currentUserContextRole) {
            case ROLE_STUDENT:
                Student currentUserStudent = studentService.getCurrentUserTenant();
                return userMapper.fromStudentToProfile(currentUserStudent, currentUserContextRole);
            case ROLE_LECTURER:
                Lecturer currentUserLecturer = lecturerService.getCurrentUserLecturer();
                return userMapper.fromLecturerToProfile(currentUserLecturer, currentUserContextRole);
            case ROLE_ACCOUNT_ADMINISTRATOR:
                //Assistant currentUserAssistant = assistantService.getCurrentUserAssistant();
                //return userMapper.fromAssistantToProfile(currentUserAssistant, currentUserContextRole);
            default:
                throw new IllegalArgumentException(String.format("Unknown role '%s'", currentUserContextRole));
        }
    }

    @Override
    public ProfileResponse updateCurrentUserProfile(ProfileRequest profileRequest) {
        Role currentUserContextRole = getCurrentUserContextRole();
        User currentUser = getCurrentUser();

       /* Set<LocalizedExceptionCode> exceptions = new HashSet<>();
        if (!currentUser.getEmail().equals(profileRequest.getEmail())) {
            if (userRepository.findByEmailIgnoreCase(profileRequest.getEmail()).isPresent()) {
                exceptions.add(LocalizedExceptionCode.USER_WITH_EMAIL_ALREADY_EXISTS);
            } else {
                currentUser.setEmail(profileRequest.getEmail());
            }
        }

        PhoneNumberInfo phone = profileRequest.getPhoneNumber();
        if (!currentUser.getDialCode().equals(phone.getDialCode()) || !currentUser.getPhoneNumber().equals(phone.getNumber())) {
            if (userRepository.findByDialCodeAndPhoneNumber(phone.getDialCode(), phone.getNumber()).isPresent()) {
                exceptions.add(LocalizedExceptionCode.USER_WITH_PHONE_ALREADY_EXISTS);
            } else {
                currentUser.setDialCode(phone.getDialCode());
                currentUser.setPhoneNumber(phone.getNumber());
            }
        }

        if (!PhoneNumberUtils.isValidPhoneNumber(profileRequest.getPhoneNumber())) {
            exceptions.add(LocalizedExceptionCode.INVALID_PHONE_NUMBER);
        }

        if (!exceptions.isEmpty()) {
            throw new LocalizedInputException(exceptions);
        }

        currentUser.setLanguage(profileRequest.getLanguage());

        User savedUser = this.save(currentUser);
        int extId = erpUserSyncService.sync(savedUser);
        saveExtId(savedUser, extId);

        if (Role.ROLE_LECTURER.equals(currentUserContextRole)) {
            this.changeLandlordBankAccount(profileRequest.getBankAccount());
        }*/

        return getCurrentUserProfile();
    }

    @Override
    public void changePassword(PasswordSetRequest passwordSetRequest) {
        User currentUser = getCurrentUser();

        currentUser.setPassword(passwordEncoder.encode(passwordSetRequest.getPassword()));

        //createChangedPasswordNotification(currentUser);

        this.save(currentUser);
    }

    /*private void createChangedPasswordNotification(User user) {
        org.bson.Document context = new org.bson.Document();
        context.put(NotificationService.ID_KEY, user.getId());

        Tenant tenant = tenantService.getByUser(user)
                .orElse(null);
        Lecturer landlord = landlordService.getByUser(user)
                .orElse(null);

        notificationService.createNotification(NotificationType.PASSWORD_CHANGED, context, tenant, landlord);
    }*/

  /* @Override
    public User confirmAccount(PasswordSetRequest passwordSetRequest) {
        User user = userRepository.findByAccountConfirmationToken_Token(passwordSetRequest.getToken())
                .orElseThrow(() -> new LocalizedException(LocalizedExceptionCode.ACCOUNT_CONFIRMATION_TOKEN_IS_NOT_RELEVANT));
        VerificationToken accountConfirmationToken = user.getAccountConfirmationToken();

        if (accountConfirmationToken == null || LocalDateTime.now(clock).compareTo(accountConfirmationToken.getExpiryDate()) > 0) {
            throw new LocalizedException(LocalizedExceptionCode.ACCOUNT_CONFIRMATION_TOKEN_EXPIRED);
        }

        user.setActive(true);
        user.setAccountConfirmationToken(null);
        user.setPassword(passwordEncoder.encode(passwordSetRequest.getPassword()));

        assistantService.confirmInvitation(user); // in case user was invited from user management

        return this.save(user);
    }

    @Override
    public void resetPassword(String email) {
        User user = this.getByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can not find user by email '%s'", email)));
        user.setPasswordResetToken(new VerificationToken());

        User savedUser = this.save(user);
        String baseUrl = request.getServerName();

        emailService.sendResetPassword(savedUser, getRoleByBaseUrl(baseUrl));
    }*/

    @Override
    public User updateUserRoles(User user, Set<Role> rolesToAdd, Set<Role> rolesToRemove) {
        Set<Role> userRoles = user.getRoles();

        userRoles.removeAll(rolesToRemove);
        userRoles.addAll(rolesToAdd);

        return userRepository.save(user);
    }

    /*@Override
    public void confirmPasswordReset(PasswordSetRequest passwordSetRequest) {
        User user = userRepository.findByPasswordResetToken_Token(passwordSetRequest.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Can not find user"));
        VerificationToken passwordResetToken = user.getPasswordResetToken();

        if (passwordResetToken == null || LocalDateTime.now(clock).compareTo(passwordResetToken.getExpiryDate()) > 0) {
            throw new AccessDeniedException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(passwordSetRequest.getPassword()));
        user.setPasswordResetToken(null);
        user.setActive(true);
        User savedUser = this.save(user);

        String baseUrl = request.getServerName();
        emailService.sendPasswordResetConfirmation(savedUser, getRoleByBaseUrl(baseUrl));
        createChangedPasswordNotification(savedUser);
    }*/

    @Override
    public User saveOrUpdateExisting(User user, Role role, boolean sendActivationEmail, boolean overwrite) {
        final Optional<User> optionalUser;
        if (StringUtils.isBlank(user.getDialCode()) || StringUtils.isBlank(user.getPhoneNumber())) {
            optionalUser = getByEmail(user.getEmail());
        } else {
            optionalUser = getByEmailOrPhone(user.getEmail(), user.getDialCode(), user.getPhoneNumber());
        }
        User userToSave = optionalUser
                .map(oldUser -> {
                    if (overwrite) {
                        oldUser.setFirstName(user.getFirstName());
                        oldUser.setLastName(user.getLastName());
                        oldUser.setDialCode(user.getDialCode());
                        oldUser.setPhoneNumber(user.getPhoneNumber());
                        return oldUser;
                    }
                    return oldUser;
                }).orElse(user);

        if (role != null) {
            Set<Role> userRoles = userToSave.getRoles();
            userRoles.add(role);
        }

        if (!userToSave.isActive() && sendActivationEmail) {
            VerificationToken verificationToken = new VerificationToken();
            userToSave.setAccountConfirmationToken(verificationToken);

            //emailService.sendAccountConfirmation(userToSave, role);
        }

        return this.save(userToSave);
    }

    @Override
    public User getCurrentUser() {
        return getCurrentUserDetails()
                .map(jwtUser -> getById(jwtUser.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Can not find current user"));
    }

    @Override
    public Role getCurrentUserContextRole() {
        JwtUser jwtUser = getCurrentUserDetails()
                .orElseThrow(() -> new AccessDeniedException("You need to authorize to access this source"));
        return jwtUser.getAuthorities().stream()
                .findFirst()
                .map(authority -> Role.valueOf(authority.getAuthority()))
                .orElseThrow(() -> new AccessDeniedException("At least single role must be provided"));
    }

    @Override
    public String getCurrentUserContextId() {
        JwtUser jwtUser = getCurrentUserDetails()
                .orElseThrow(() -> new AccessDeniedException("You need to authorize to access this source"));
        return jwtUser.getContextId();
    }

    private User save(User user) {
        return userRepository.save(cleanupUserPhone(user));
    }

    private User cleanupUserPhone(User user) { // workaround to prevent having incomplete phones in the db
        if (StringUtils.isBlank(user.getDialCode()) || StringUtils.isBlank(user.getPhoneNumber())) {
            user.setDialCode(null);
            user.setPhoneNumber(null);
        }
        return user;
    }

    private Optional<JwtUser> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return Optional.ofNullable((JwtUser) authentication.getPrincipal());
    }

    private Role getRoleByBaseUrl(String baseUrl) {
        if (studentFrontendUrl.contains(baseUrl)) {
            return Role.ROLE_LECTURER;
        } else {
            return Role.ROLE_STUDENT;
        }
    }

}