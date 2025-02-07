package com.cts.medisync360.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.csrf().disable()
	        .authorizeHttpRequests(auth -> auth
	        		.requestMatchers("/images/**").permitAll()
	            .requestMatchers("/api/patients/register", "/api/doctor/register", "/auth**","/auth/register","/home","/contact","/about").permitAll()
	            .requestMatchers("/api/patients/**").hasRole("PATIENT")
	            .requestMatchers("/api/doctors/**").hasRole("DOCTOR")
	            .requestMatchers("/api/medicalrecords/create", "/api/medicalrecords/update").hasRole("DOCTOR")
	            .requestMatchers("/api/appointments/**").hasAnyRole("DOCTOR", "PATIENT")
	            .anyRequest().authenticated())
	        .formLogin(login -> login
	            .usernameParameter("userName")
	            .passwordParameter("password")
	            .loginPage("/auth/login")
	            .successHandler(successHandler)
	            .permitAll())
	        .logout(logout -> logout
	            .logoutUrl("/auth/logout")
	            .clearAuthentication(true)
	            .invalidateHttpSession(true)
	            .logoutSuccessUrl("/home")
	            .permitAll())
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
	        .authenticationProvider(authProvider(userDetailsService()))
	        .build();
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authProvider(UserDetailsService userDetailsService){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
