package com.demo.backend.service;

import com.demo.backend.service.api.LecturerService;
import com.demo.backend.service.api.UserService;
import com.demo.backend.exception.ResourceNotFoundException;
import com.demo.backend.mapper.mixin.NameMixin;
import com.demo.backend.model.Lecturer;
import com.demo.backend.model.User;
import com.demo.backend.model.misc.Role;
import com.demo.backend.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LecturerServiceImpl implements LecturerService, NameMixin {

    @Autowired
    private UserService userService;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public Lecturer getById(String id) {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));
    }

    @Override
    public Lecturer getCurrentUserLecturer() {
        Role currentUserContextRole = userService.getCurrentUserContextRole();

        if (Set.of(Role.ROLE_ACCOUNT_ADMINISTRATOR, Role.ROLE_ACCOUNT_MANAGER).contains(currentUserContextRole)) {
            //Assistant currentUserAssistant = assistantService.getCurrentUserAssistant();
            //return currentUserAssistant.getLecturer();
        }

        return lecturerRepository.findById(userService.getCurrentUserContextId())
                .filter(lecturer -> {
                    User currentUser = userService.getCurrentUser();
                    return lecturer.getUser().getId().equals(currentUser.getId());
                }).orElseThrow(() -> new ResourceNotFoundException("Can not find lecturer"));
    }


    @Override
    public Optional<Lecturer> getByUser(User user) {
        return lecturerRepository.findByUser(user);
    }

    @Override
    public Lecturer save(Lecturer lecturer) {
        User lecturerUser = lecturer.getUser();
        lecturer.setUser(lecturerUser);
        return lecturerRepository.save(lecturer);
    }


}