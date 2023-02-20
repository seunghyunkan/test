package com.example.test.controller;

import com.example.test.dto.UserDto;
import com.example.test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    //전체조회
    @GetMapping({"/", ""})
    public Page<UserDto.Info> findUser(Pageable pageable) {
        return userService.findUser(pageable);
    }

    //개별조회
    @GetMapping("/{userId}")
    public UserDto.Info findOneUser(@PathVariable String userId) {
        return null;
    }

    //저장
    @PostMapping(value = {"/", ""})
    public UserDto.Info saveUser(@ModelAttribute @Valid UserDto.Save param) {
        return null;
    }

    //수정
    @PostMapping("/update")
    public UserDto.Info updateUser(@ModelAttribute @Valid UserDto.Update param) {
        return null;
    }

    //삭제
    @PostMapping("/delete")
    public UserDto.Info deleteUser(@RequestBody UserDto.Delete param) {
        return null;
    }
}
