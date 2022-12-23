package com.artificer.storage.s3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.artificer.services.FotoStorageService;
import com.artificer.storage.StorageProperties;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
public class S3FotoStorage implements FotoStorageService {

	@Autowired
	private AmazonS3 amazonS3;

	private StorageProperties storageProperties;

	public S3FotoStorage(StorageProperties storageProperties) {
		this.storageProperties = storageProperties;
	}

	@Override
	public String salvar(MultipartFile[] file) {
		String novoNome = null;
		if (file != null && file.length > 0) {
			MultipartFile archive = file[0];
			novoNome = renomearArquivo(archive.getOriginalFilename());

			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentType(archive.getContentType());
			metaData.setContentLength(archive.getSize());
			AccessControlList controlList = new AccessControlList();
			controlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
			try {
				amazonS3.putObject(new PutObjectRequest(storageProperties.getS3().getBucket(), novoNome,
						archive.getInputStream(), metaData).withAccessControlList(controlList));

				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				Thumbnails.of(archive.getInputStream()).size(40, 68).toOutputStream(byteArray);
				byte[] bytes = byteArray.toByteArray();

				InputStream input = new ByteArrayInputStream(bytes);

				var thumbMetaData = new ObjectMetadata();
				metaData.setContentType(archive.getContentType());
				metaData.setContentLength(bytes.length);

				amazonS3.putObject(new PutObjectRequest(storageProperties.getS3().getBucket(),
						String.format("%s%s", THUMBNAIL_PREFIX, novoNome), input, thumbMetaData)
						.withAccessControlList(controlList));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar o arquivo no bucket da S3!", e);
			}
		}
		return null;
	}

	@Override
	public byte[] recuperar(String nome) {
		var content = amazonS3.getObject(storageProperties.getS3().getBucket(), nome).getObjectContent();

		try {
			return IOUtils.toByteArray(content);
		} catch (IOException e) {
			log.error("Falha ao recuperar foto do Bucket do S3!", e);
		}
		return null;
	}

	@Override
	public void excluir(String foto) {
		amazonS3.deleteObjects(new DeleteObjectsRequest(storageProperties.getS3().getBucket()).withKeys(foto,
				String.format("%s%s", THUMBNAIL_PREFIX, foto)));

	}

	@Override
	public String getUrl(String nomeFoto) {
		String url = null;
		if (StringUtils.hasText(nomeFoto)) {

			String filePath = getFilePath(nomeFoto);
			url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath).toString();

		}
		return url;
	}

	private String getFilePath(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), nomeArquivo);
	}

}
