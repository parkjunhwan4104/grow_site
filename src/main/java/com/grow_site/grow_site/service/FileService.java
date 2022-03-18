package com.grow_site.grow_site.service;

import com.grow_site.grow_site.Dao.FileRepository;
import com.grow_site.grow_site.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;



    @Transactional
    public void save(File file) {
        fileRepository.save(file);
    }


    public List<File> findByAll(){

        List<File> fileList=fileRepository.findAll();

        return fileList;
    }

    public Optional<File> findById(Long id){

        return fileRepository.findById(id);

    }
}
