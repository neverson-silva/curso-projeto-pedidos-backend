package com.neversonsilva.cursomc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredenciaisDto implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String email;
	private String senha;

	
}
