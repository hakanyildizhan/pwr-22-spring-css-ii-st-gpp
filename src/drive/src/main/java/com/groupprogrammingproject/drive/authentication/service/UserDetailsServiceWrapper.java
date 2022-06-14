package com.groupprogrammingproject.drive.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.groupprogrammingproject.drive.domain.security.AuthorizationData;
import com.groupprogrammingproject.drive.domain.security.AuthorizationDataRepository;

import static com.groupprogrammingproject.drive.domain.security.AccountStatus.ACTIVE;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceWrapper implements UserDetailsService {

    private final AuthorizationDataRepository authorizationDataRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserDetails() {
            AuthorizationData user = authorizationDataRepository.findByEmail(email).get();

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return user.getId();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return user.getStatus().equals(ACTIVE.name());
            }
        };
    }
}
