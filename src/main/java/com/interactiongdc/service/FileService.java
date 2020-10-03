package com.interactiongdc.service;

import com.interactiongdc.repository.FileRepository;

import java.util.List;

import com.interactiongdc.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    /**
     * Get All File Storaded
     * 
     * @return List<Files> list files registred
     */
    public List<File> getAll() {
        return this.fileRepository.findAll();
    }

    /**
     * Get One File storededue
     * 
     * @param id identification uniq
     * @return File object
     */
    public File getOne(Long id) {
        return this.fileRepository.getOne(id);
    }

    /**
     * Create new File Register
     * 
     * @param f File Object
     * @return File register
     */
    public File create(File f) {
        return this.fileRepository.saveAndFlush(f);
    }

    /**
     * Delete register File
     * 
     * @param f @param f File Object
     */
    public void delete(File f) {
        this.fileRepository.delete(f);
    }

    /**
     *  Update Registers
     * @param f File
     * @return File object
     */
    public File update(File f) {
        return this.fileRepository.save(f);
    }

}
