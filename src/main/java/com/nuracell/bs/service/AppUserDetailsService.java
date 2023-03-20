package com.nuracell.bs.service;

import com.nuracell.bs.entity.AppUser;
import com.nuracell.bs.exception.AppUserNotFoundException;
import com.nuracell.bs.repository.AppUserRepository;
import com.nuracell.bs.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public void addAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new AppUserNotFoundException(username));

        System.out.println("[APPUSER]: " + user);

        return new AppUserDetails(user);
    }
}
