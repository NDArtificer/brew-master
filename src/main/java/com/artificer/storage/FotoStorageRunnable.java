package com.artificer.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.artificer.input.FotoCervejaInput;
import com.artificer.services.FotoStorageService;

public class FotoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<FotoCervejaInput> result;

	private FotoStorageService storageService;

	public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoCervejaInput> result,
			FotoStorageService storageService) {
		this.files = files;
		this.result = result;
		this.storageService = storageService;
	}

	@Override
	public void run() {

		System.out.println(files[0].getSize());
		System.out.println(files[0].getOriginalFilename());
		System.out.println(files[0].getContentType());

		String nomeFoto = this.storageService.salvar(files);
		String contentType = files[0].getContentType();
		result.setResult(new FotoCervejaInput(nomeFoto, contentType, storageService.getUrl(nomeFoto)));

	}

}
