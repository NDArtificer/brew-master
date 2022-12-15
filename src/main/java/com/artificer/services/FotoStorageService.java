package com.artificer.services;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorageService {

	public final String THUMBNAIL_PREFIX = "thumbnail.";
	
	String salvar(MultipartFile[] file);

	byte[] recuperar(String nome);

	void excluir(String foto);

	String getUrl(String nomeFoto);
}
