package com.neversonsilva.cursomc.security;

import com.neversonsilva.cursomc.domains.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserSS implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private Integer id;
	
	private String email;
	
	private String senha;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfils) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfils.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao()))
					.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
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

	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}



}
