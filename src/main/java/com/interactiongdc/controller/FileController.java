package com.interactiongdc.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.interactiongdc.model.File;
import com.interactiongdc.service.FileService;
import com.interactiongdc.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    @Autowired
    private FileService service;
    @Autowired
    private StorageService storageService;

    @GetMapping
    public ResponseEntity<List<File>> getFiles() {
        try {
            final List<File> files = this.service.getAll();
            return ResponseEntity.ok(files);
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public HttpEntity<String> getFile(@PathVariable final Long id, final HttpServletResponse response) {
        try {
            final File file = this.service.getOne(id);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
            final String contentFile = this.storageService.getFileContent(file);
            return new HttpEntity<String>(contentFile, headers);

        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<File> createFile(@RequestParam("file") final MultipartFile f) {
        try {
            final File file = this.service.create(this.storageService.upload(f));
            return ResponseEntity.ok(file);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete File
     * 
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public ResponseEntity<File> deleteFile(@PathVariable final Long id) {
        try {
            final File f = this.service.getOne(id);
            if (this.storageService.deleteFile(f)) {
                this.service.delete(f);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(400).build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
