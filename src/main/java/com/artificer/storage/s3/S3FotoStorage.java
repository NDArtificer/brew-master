package com.artificer.storage.s3;

import org.springframework.web.multipart.MultipartFile;

import com.artificer.services.FotoStorageService;
import com.artificer.storage.StorageProperties;

public class S3FotoStorage implements FotoStorageService {

	private StorageProperties storageProperties;

	public S3FotoStorage(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;
	}

	@Override
	public String salvar(MultipartFile[] file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] recuperar(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(String foto) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUrl(String nomeFoto) {
		// TODO Auto-generated method stub
		return null;
	}

}
