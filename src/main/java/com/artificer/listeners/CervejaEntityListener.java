package com.artificer.listeners;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.artificer.model.Cerveja;
import com.artificer.services.FotoStorageService;

public class CervejaEntityListener {

	@Autowired
	private FotoStorageService storageService;

	@PostLoad
	private void postLoad(final Cerveja cerveja) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		cerveja.setUrlFoto(storageService.getUrl(cerveja.getFotoOrMock()));
		String thumbnailFoto = String.format("%s%s", FotoStorageService.THUMBNAIL_PREFIX, cerveja.getFotoOrMock());
		cerveja.setUrlThumbnailFoto(storageService.getUrl(thumbnailFoto));
	}

}
