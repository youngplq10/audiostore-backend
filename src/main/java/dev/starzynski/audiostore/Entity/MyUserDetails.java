package dev.starzynski.audiostore.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class MyUserDetails implements UserDetails {

    private Optional<User> user;

    public MyUserDetails(Optional<User> user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        if(user.isPresent()){
            return user.get().getPassword();
        }else{
            return "";
        }
    }

    @Override
    public String getUsername() {
        if(user.isPresent()){
            return user.get().getUsername();
        }else{
            return "";
        }
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
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
