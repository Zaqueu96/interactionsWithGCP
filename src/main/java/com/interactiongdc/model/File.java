package com.interactiongdc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotBlank(message = "the Key is required")
	String keyBucket;

	@NotBlank(message = "the FileName is required")
	String fileName;

	@NotBlank(message = "the Extension is required")
	String extension;
	
	// @NotBlank(message = "the ContentType is required")
	String contentType;
	

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension)  {
		this.extension = extension;
	}

	public String getKeyBucket() {
		return keyBucket;
	}

	public void setKeyBucket(String key) {
		this.keyBucket = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getId() {
		return id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	// public File getInstance(){
	// 	return new this();
	// }
}
