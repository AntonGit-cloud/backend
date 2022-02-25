package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.api.StudentService;
import com.example.demo.service.api.UserService;
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

