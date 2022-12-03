package com.artificer.services;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorageService {

	String salvarTemporariamente(MultipartFile[] file);

	byte[] recuperarFoto(String nome) ;

	void salvar(String foto);
	
	byte[] recuperar(String nome);

	void excluir(String foto);
}
