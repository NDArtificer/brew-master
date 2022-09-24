package com.artificer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

//	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	private String upload(@RequestParam MultipartFile files) {
//		
//		FotoCervejaInput fotoCerveja = new FotoCervejaInput(files.getOriginalFilename(), files.getContentType());
//		
//		
//		return "OK";
//
//	}
//	

}
