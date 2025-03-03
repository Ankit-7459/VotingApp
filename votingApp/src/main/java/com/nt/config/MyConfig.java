package com.nt.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class MyConfig {
	
	@Autowired
	private AuthenticationSuccessHandler customSuccessHandler;
	
	@Autowired 
	private AdminDetailsServiceImpl adminDSImpl;
	
	@Autowired
	private UserDetailsServiceImpl userDSImpl;
	
	@Bean
	public UserDetailsService getAdminDetailsServicie()
	{
		
		
		return adminDSImpl;

	
	}
	
	@Bean
	public UserDetailsService getUserDetailsServicie()
	{
		
		
		return userDSImpl;

	
	}


	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsServicie());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
		
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider2()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getAdminDetailsServicie());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
		
	}

	
	///configure method...
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(authenticationProvider());
		auth.authenticationProvider(authenticationProvider2());
		
	}

	@Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/user/**").hasRole("USER")
		.requestMatchers("/**").permitAll()
		.and().formLogin().loginPage("/signin")
		.loginProcessingUrl("/dologin")
		.successHandler(customSuccessHandler)
		.and().csrf().disable();
		
		  return http.build();
	
	}




}

