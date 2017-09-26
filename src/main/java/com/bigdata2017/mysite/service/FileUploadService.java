package com.bigdata2017.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	private static String SAVE_PATH = "/uploads";
	private static String PREFIX_URL = "uploads/";

	public String restore(MultipartFile multipartFile) {

		String saveFileUrl = "";
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			Long size = multipartFile.getSize();
			String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
			String saveFileName = genSaveFileName(extName);

			System.out.println("[originalFilename]" + originalFilename);
			System.out.println("[size]" + size);
			System.out.println("[extName]" + extName);
			System.out.println("[saveFileName]" + saveFileName);

			writeFile(multipartFile, saveFileName);
			saveFileUrl = PREFIX_URL + saveFileName;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return saveFileUrl;
	}

	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {

		byte[] fileData = multipartFile.getBytes();
		String filePath = SAVE_PATH + "/" + saveFileName;
		
		FileOutputStream fos = new FileOutputStream(filePath);
		fos.write(fileData);
		fos.close();
	}

	private String genSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;

		return fileName;
	}
}
