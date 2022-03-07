package com.demo.backend.service;

import com.demo.backend.exception.ResourceNotFoundException;
import com.demo.backend.model.Student;
import com.demo.backend.model.User;
import com.demo.backend.repository.StudentRepository;
import com.demo.backend.service.api.StudentService;
import com.demo.backend.service.api.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public Student getById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
    }


    @Override
    public Student getCurrentUserTenant() {
        return studentRepository.findById(userService.getCurrentUserContextId())
                .filter(tenant -> {
                    User currentUser = userService.getCurrentUser();
                    return tenant.getUser().getId().equals(currentUser.getId());
                }).orElseThrow(() -> new ResourceNotFoundException("Can not find tenant"));
    }

    @Override
    public Optional<Student> getByUser(User user) {
        return studentRepository.findByUser(user);
    }

    @Override
    public Student save(Student tenant) {
        User tenantUser = tenant.getUser();
        tenant.setUser(tenantUser);
        return studentRepository.save(tenant);
    }

}

