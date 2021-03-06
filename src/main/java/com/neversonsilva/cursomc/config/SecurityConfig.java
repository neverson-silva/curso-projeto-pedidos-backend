package com.neversonsilva.cursomc.config;

import com.neversonsilva.cursomc.security.JWTAuthenticationFilter;
import com.neversonsilva.cursomc.security.JWTAuthorizationFilter;
import com.neversonsilva.cursomc.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**", "/login/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**", "/categorias/**", "/estados/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = { 
		"/clientes", 
		"/auth/forgot/**"
	};



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
        http.cors().and().csrf().disable()
	        .authorizeRequests()
	        .antMatchers(PUBLIC_MATCHERS).permitAll()
	        .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
	        .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
	        .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService))
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		corsConfiguration.setAllowedMethods(
				Arrays.asList("POST", "PUT", "OPTIONS", "DELETE", "GET")
		);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	
}
