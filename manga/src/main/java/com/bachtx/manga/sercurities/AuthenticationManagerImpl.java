package com.bachtx.manga.sercurities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerImpl implements AuthenticationManager {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthenticationManagerImpl(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        if(!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
            throw new BadCredentialsException("Password doesn't match");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
