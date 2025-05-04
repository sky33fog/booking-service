package com.example.booking_service.web.controller;

import com.example.booking_service.mapper.UserMapper;
import com.example.booking_service.model.RoleType;
import com.example.booking_service.model.User;
import com.example.booking_service.model.kafka.CreateUserEvent;
import com.example.booking_service.service.UserService;
import com.example.booking_service.web.model.ResponseList;
import com.example.booking_service.web.model.UserResponse;
import com.example.booking_service.web.model.UserUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final KafkaTemplate<String, CreateUserEvent> kafkaTemplateCreateUser;

    @Value("${app.kafka.kafkaUserTopic}")
    private String kafkaUserTopic;

//    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<ResponseList> findAll() {
//        return ResponseEntity.ok(userMapper.userListToResponseList(userService.findAll()));
//    }
//
//    @GetMapping("/{name}")
//    public ResponseEntity<UserResponse> findByName(@PathVariable String name) {
//        return ResponseEntity.ok(userMapper.userToResponse(userService.findByName(name)));
//    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserUpsertRequest request,
                                               @RequestParam RoleType roleType) {

        User user = userService.save(userMapper.requestToUser(request), roleType);

        kafkaTemplateCreateUser.send(kafkaUserTopic, new CreateUserEvent(user.getName()));

        return ResponseEntity.ok(userMapper.userToResponse(user));
    }

//    @PutMapping
//    public ResponseEntity<UserResponse> update(@RequestBody UserUpsertRequest request) {
//        return ResponseEntity.ok(userMapper.userToResponse(userService.update(userMapper.requestToUser(request))));
//    }
//
//    @DeleteMapping("/{name}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<Void> delete(@PathVariable String name) {
//        userService.deleteByName(name);
//        return ResponseEntity.noContent().build();
//    }
}
