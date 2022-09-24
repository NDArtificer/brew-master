package com.artificer.storage.local;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.artificer.services.FotoStorageService;
import com.artificer.storage.StorageProperties;

import groovy.util.logging.Log4j2;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Log4j2
public class FotoStorageLocal implements FotoStorageService {

	private Path path;
	private Path tempPath;
	private StorageProperties properties;

	public FotoStorageLocal(StorageProperties properties) {
		this.properties = properties;
		this.path = properties.getLocal().getDiretorio();
		createFolders();
	}

	public void createFolders() {

		try {
			Files.createDirectories(this.path);
			this.tempPath = FileSystems.getDefault().getPath(this.path.toString(), "temp");
			Files.createDirectories(this.tempPath);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar a pasta para criar os aquivos de imagens!", e);
		}
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {

		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile file = files[0];
			novoNome = renomearArquivo(file.getOriginalFilename());
			try {
				file.transferTo(new File(this.tempPath.toAbsolutePath().toString()
						+ FileSystems.getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("Erro ao salvar o arquivo na pasta temporaria!", e);
			}

		}
		return novoNome;

	}

	private String renomearArquivo(String originalFilename) {
		var novoNome = String.format("%s_%s", UUID.randomUUID().toString(), originalFilename);
		return novoNome;
	}

}
