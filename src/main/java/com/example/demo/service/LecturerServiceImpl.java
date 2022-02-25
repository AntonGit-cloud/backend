package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.mixin.NameMixin;
import com.example.demo.model.Lecturer;
import com.example.demo.model.User;
import com.example.demo.model.misc.Role;
import com.example.demo.repository.LecturerRepository;
import com.example.demo.service.api.LecturerService;
import com.example.demo.service.api.UserService;
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