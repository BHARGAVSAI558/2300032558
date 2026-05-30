package com.vehicle.service.impl;

import com.vehicle.model.dto.RegistrationRequest;
import com.vehicle.model.dto.RegistrationResponse;
import com.vehicle.model.entity.Student;
import com.vehicle.repository.StudentRepository;
import com.vehicle.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final StudentRepository studentRepository;

    public RegistrationServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            Student existing = studentRepository.findByEmail(request.getEmail()).get();
            log.info("Student already registered: {}", request.getEmail());
            return new RegistrationResponse(existing.getClientId(), existing.getClientSecret(),
                    existing.getAccessToken(), "Already registered");
        }

        String clientId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String clientSecret = UUID.randomUUID().toString().replace("-", "");
        String accessToken = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");

        Student student = new Student();
        student.setEmail(request.getEmail());
        student.setName(request.getName());
        student.setMobileNo(request.getMobileNo());
        student.setGithubUsername(request.getGithubUsername());
        student.setRollNo(request.getRollNo());
        student.setClientId(clientId);
        student.setClientSecret(clientSecret);
        student.setAccessToken(accessToken);

        studentRepository.save(student);
        log.info("Student registered successfully: {}", request.getEmail());

        return new RegistrationResponse(clientId, clientSecret, accessToken, "Registration successful");
    }
}
