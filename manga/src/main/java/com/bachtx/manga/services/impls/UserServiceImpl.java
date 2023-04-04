package com.bachtx.manga.services.impls;

import com.bachtx.manga.dto.request.LoginRequest;
import com.bachtx.manga.dto.request.RegisterRequest;
import com.bachtx.manga.dto.response.UserResponse;
import com.bachtx.manga.exceptions.AlreadyExistException;
import com.bachtx.manga.mapper.UserMapper;
import com.bachtx.manga.models.User;
import com.bachtx.manga.models.UserDetailsImpl;
import com.bachtx.manga.repositories.UserRepository;
import com.bachtx.manga.services.UserService;
import com.bachtx.manga.utils.JwtUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    public boolean usernameIsAlreadyExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserResponse register(@Valid RegisterRequest registerRequest) {
        if (!usernameIsAlreadyExists(registerRequest.getUsername())) {
            registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            User user = userMapper.MAPPER.registerRequestToUser(registerRequest);
            User userRegistered = userRepository.save(user);
            entityManager.refresh(userRegistered);
            return userMapper.MAPPER.userToUserResponse(userRegistered);
        } else {
            throw new AlreadyExistException("Username already exists!");
        }
    }

    @Override
    public UserResponse login(@Valid LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            token.setDetails(request);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            User userLogged = userDetails.getUser();
            UserResponse userResponse = userMapper.MAPPER.userToUserResponse(userLogged);
            userResponse.setToken(jwt);
            return userResponse;
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new RuntimeException(e);
        }
    }
}
