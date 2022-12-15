package com.artificer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.artificer.services.FotoStorageService;
import com.artificer.storage.StorageProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Getter
@Setter
@Slf4j
public class FotoStorageLocal implements FotoStorageService {

	private Path path;
	private StorageProperties properties;

	public FotoStorageLocal(StorageProperties properties) {
		this.properties = properties;
		this.path = properties.getLocal().getDiretorio();
		createFolders();
	}

	public void createFolders() {

		try {
			Files.createDirectories(this.path);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao criar a pasta para criar os aquivos de imagens!", e);
		}
	}

	@Override
	public String salvar(MultipartFile[] files) {

		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile file = files[0];
			novoNome = renomearArquivo(file.getOriginalFilename());
			try {
				file.transferTo(new File(this.path.toAbsolutePath().toString()
						+ FileSystems.getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("Erro ao salvar o arquivo no diretório!", e);
			}

		}
		
		try {
			Thumbnails.of(this.path.resolve(novoNome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Falha ao gerar a thumbnail!", e);
		}
		
		return novoNome;

	}

	private String renomearArquivo(String originalFilename) {
		var novoNome = String.format("%s_%s", UUID.randomUUID().toString(), originalFilename);
		return novoNome;
	}



	@Override
	public byte[] recuperar(String nome) {
		try {
			return Files.readAllBytes(this.path.resolve(nome));
		} catch (Exception e) {
			throw new RuntimeException("Falha ao mover a foto do diretorio!", e);
		}
	}

	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.path.resolve(foto));
			Files.deleteIfExists(this.path.resolve("thumbnail." + foto));
		} catch (IOException e) {
			log.warn(String.format("Falha ao deletar a foto %s", foto));
		}
	}

	@Override
	public String getUrl(String nomeFoto) {
		String url = String.format("http://localhost:8080/fotos/%s", nomeFoto);
		return url;
	}

}
