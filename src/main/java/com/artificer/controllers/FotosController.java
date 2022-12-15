package com.artificer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.artificer.input.FotoCervejaInput;
import com.artificer.services.FotoStorageService;
import com.artificer.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

	@Autowired
	private FotoStorageService storageService;

	@PostMapping
	private DeferredResult<FotoCervejaInput> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoCervejaInput> result = new DeferredResult<>();

		Thread thread = new Thread(new FotoStorageRunnable(files, result, storageService));
		thread.start();
		return result;

	}

	@GetMapping("/{nome:.*}")
	public byte[] recuperarFoto(@PathVariable String nome) {
		return storageService.recuperar(nome);
	}

}
