package com.grow_site.grow_site.domain;




import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(length=512)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="article_id")
    private Article article;

    private Long size;

    @Column(name="upload_time")
    private Date uploadTime;

    @Column(columnDefinition="LONGBLOB")
    private byte[] content;

    public File(Long id, String name, Long size){
        super();
        this.id=id;
        this.name=name;
        this.size=size;
    }




    public void setArticle(Article article){
        this.article=article;

    }

}
