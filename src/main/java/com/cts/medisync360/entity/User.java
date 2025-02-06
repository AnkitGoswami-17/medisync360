package com.cts.medisync360.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_details")
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
	@SequenceGenerator(name="user_seq",allocationSize = 1, sequenceName = "user_seq")
	private long id;
	private String userName;
	private String userPassword;
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userType.name()));
	}
	@Override
	public String getPassword() {
		return userPassword;
	}
	@Override
	public String getUsername() {
		return userName;
	}
	
	@Override
    public boolean isAccountNonExpired() {
        return true; // Or your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Or your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Or your logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Or your logic
    }
	
	
}
