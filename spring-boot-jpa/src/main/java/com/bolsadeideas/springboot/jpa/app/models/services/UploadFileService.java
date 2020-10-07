package com.bolsadeideas.springboot.jpa.app.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bolsadeideas.springboot.jpa.app.controllers.ClienteController;

@Service
public class UploadFileService implements IUploadFileService {

	private final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	private static final String UPLOADS = "uploads";

	@Override
	public Resource loadFile(String pFileName) throws MalformedURLException {

		Path rutaFoto = getAbsolutePath(pFileName);
		logger.info("rutaFoto: " + rutaFoto);
		Resource recursoFoto = null;

		recursoFoto = new UrlResource(rutaFoto.toUri());

		if (!recursoFoto.exists() || !recursoFoto.isReadable()) {
			throw new RuntimeException("Error, no se pudo cargar la imagen: " + rutaFoto.toString());
		}

		return recursoFoto;
	}

	@Override
	public String copyFile(MultipartFile pFile) throws IOException {
		// Path directorioArchivos = Paths.get("src//main//resources//static//uploads");
		// String rutaAbsolutaArchivos = directorioArchivos.toFile().getAbsolutePath();
		// String rutaAbsolutaArchivos = "H://SpringBootCourse//uploads";

		String uniqueFileName = UUID.randomUUID().toString() + "_" + pFile.getOriginalFilename();
		Path directorioArchivos = getAbsolutePath(uniqueFileName);

		logger.info("directorioArchivos: " + directorioArchivos);

		Files.copy(pFile.getInputStream(), directorioArchivos);

		return uniqueFileName;

	}

	@Override
	public boolean deleteFile(String pFileName) {
		Path rutaArchivo = getAbsolutePath(pFileName);
		File archivo = rutaArchivo.toFile();
		if (archivo.exists() && archivo.canRead()) {
			archivo.delete();
		}
		return false;
	}

	private Path getPath() {
		return Paths.get(UPLOADS);
	}

	private Path getAbsolutePath(String pFileName) {
		return getPath().resolve(pFileName).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(getPath().toFile());

	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(getPath());

	}

}
