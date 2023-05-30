package com.Travel.Travel.infraestructure.services;

import com.Travel.Travel.domain.entities.documents.AppUserDocument;
import com.Travel.Travel.domain.repositories.mongo.AppUserRepository;
import com.Travel.Travel.infraestructure.abstract_services.ModifyUserService;
import com.Travel.Travel.util.exceptions.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class AppUserService implements ModifyUserService, UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Map<String, Boolean> enabled(String username) {
        var user = appUserRepository.findByUsername(username).orElseThrow(()-> new IdNotFoundException("app_user"));
        user.setEnabled(!user.isEnabled());
        var userSaved = appUserRepository.save(user);
        return Collections.singletonMap(userSaved.getUsername(),userSaved.isEnabled());
    }

    @Override
    public Map<String, Set<String>> addRole(String username, String role) {
        var user = appUserRepository.findByUsername(username).orElseThrow(()-> new IdNotFoundException("app_user"));
        user.getRole().getGrantedAuthorities().add(role);
        var userSaved = appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSaved.getUsername(),authorities);
    }

    @Override
    public Map<String, Set<String>> removeRole(String username, String role) {
        var user = appUserRepository.findByUsername(username).orElseThrow(()-> new IdNotFoundException("app_user"));
        user.getRole().getGrantedAuthorities().remove(role);
        var userSaved = appUserRepository.save(user);
        var authorities = userSaved.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSaved.getUsername(),authorities);
    }


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = appUserRepository.findByUsername(username).orElseThrow(()-> new IdNotFoundException("app_user"));
        return mapUserToUserDetails(user);
    }

    private static UserDetails mapUserToUserDetails(AppUserDocument userDocument){
        Set<GrantedAuthority> authorities = userDocument.getRole().getGrantedAuthorities()
                                                        .stream().map(SimpleGrantedAuthority::new)
                                                        .collect(Collectors.toSet());
        return new User(
                userDocument.getUsername(),
                userDocument.getPassword(),
                userDocument.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
