package com.grow_site.grow_site.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length=512,nullable = false,unique=true)
    private String name;


    private Long size;

    @Column(name="upload_time")
    private Date uploadTime;

    @Column(columnDefinition="BLOB")
    private byte[] content;

    public File(Long id, String name, Long size){
        super();
        this.id=id;
        this.name=name;
        this.size=size;
    }

}
