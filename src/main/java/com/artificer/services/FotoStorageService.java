package com.artificer.services;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorageService {

	public final String THUMBNAIL_PREFIX = "thumbnail.";

	String salvar(MultipartFile[] file);

	byte[] recuperar(String nome);

	void excluir(String foto);

	String getUrl(String nomeFoto);

	default String renomearArquivo(String originalFilename) {
		return String.format("%s_%s", UUID.randomUUID().toString(), originalFilename);

	}
}
