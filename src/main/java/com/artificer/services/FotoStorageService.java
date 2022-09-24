package com.artificer.services;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorageService {

	String salvarTemporariamente(MultipartFile[] file);

}
