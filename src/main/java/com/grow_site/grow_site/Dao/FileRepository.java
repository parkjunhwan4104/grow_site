package com.grow_site.grow_site.Dao;

import com.grow_site.grow_site.domain.File;
import groovy.transform.ASTTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long> {



}
