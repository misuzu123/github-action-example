package com.training.controller;

import com.training.common.constant.Constant;
import com.training.common.constant.Errors;
import com.training.common.utils.JwtUtils;
import com.training.dto.ResponseJson;
import com.training.dto.user.LoginRequest;
import com.training.dto.user.UserLogin;
import com.training.entity.User;
import com.training.exception.BadRequestException;
import com.training.exception.ErrorParam;
import com.training.exception.SysError;
import com.training.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseJson<UserLogin>> signIn(@RequestBody LoginRequest request) {
        User loginUser = userService.findUserByUserName(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), loginUser.getPassword())) {
            throw new BadRequestException(new SysError(Errors.ERROR_PASSWORD_NOT_MATCH, new ErrorParam(Errors.PASSWORD)));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateJwtToken(authentication);
        UserLogin userLogin = new UserLogin(loginUser.getId(), loginUser.getUsername(), token);

        return ResponseEntity.ok().body(new ResponseJson<>(userLogin, HttpStatus.OK, Constant.SUCCESS));
    }

}
