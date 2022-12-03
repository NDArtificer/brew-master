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

	@Override
	public byte[] recuperarFoto(String nome) {

		try {
			return Files.readAllBytes(this.tempPath.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("falha ao ler o arquivo !", e);
		}
	}

	private String renomearArquivo(String originalFilename) {
		var novoNome = String.format("%s_%s", UUID.randomUUID().toString(), originalFilename);
		return novoNome;
	}

	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.tempPath.resolve(foto), this.path.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Falha ao mover a foto do diretorio temporário!", e);
		}

		try {
			Thumbnails.of(this.path.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Falha ao gerar a thumbnail!", e);
		}
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

}
