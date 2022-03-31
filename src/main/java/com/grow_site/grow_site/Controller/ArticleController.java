package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.DTO.article.ArticleDTO;
import com.grow_site.grow_site.DTO.article.ArticleModifyForm;
import com.grow_site.grow_site.DTO.article.ArticleSaveForm;
import com.grow_site.grow_site.domain.Article;
import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.domain.File;
import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.ArticleService;
import com.grow_site.grow_site.service.BoardService;
import com.grow_site.grow_site.service.FileService;
import com.grow_site.grow_site.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final MemberService memberService;
    private final FileService fileService;



    @GetMapping("/boards/articles/add/{id}")
    public String showAddArticle(@PathVariable(name="id")Long id, Model model){

        Board board=boardService.getBoard(id);
        String boardName=board.getName();
        Long boardId=board.getId();

        model.addAttribute("boardName",boardName);
        model.addAttribute("boardId",boardId);
        model.addAttribute("ArticleSaveForm",new ArticleSaveForm());

        return "user/article/add";
    }

    @PostMapping("/boards/articles/add/{id}")
    public String doAddArticle(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, @PathVariable(name="id")Long id, Model model, Principal principal,@RequestParam("file") MultipartFile multipartFile) throws IOException {


        Board board=boardService.getBoard(id);
        List<File> fileList=new ArrayList<>();
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if(bindingResult.hasErrors()){
            model.addAttribute("boardName",board.getName());
            model.addAttribute("boardId",board.getId());

            return "user/article/add";
        }

        try{


            File file=new File();
            file.setName(fileName);
            file.setContent(multipartFile.getBytes());
            file.setSize(multipartFile.getSize());
            file.setUploadTime(new Date());
            fileService.save(file);

            fileList.add(file);



            Member member=memberService.getMember(principal.getName());
            articleService.save(articleSaveForm,member,board,fileList);




        }
        catch(IllegalStateException e){
            model.addAttribute("error_msg",e.getMessage());
            return "user/article/add";
        }
        return "redirect:/boards/"+id;
    }

    @GetMapping("/articles/{id}")
    public String showArticleDetail(@PathVariable(name="id")Long id,Model model)throws IllegalStateException, IOException{




        List<File> listFile=fileService.getFileListByArticleId(id);
        ArticleDTO articleDTO= articleService.getArticleById(id);

        String body=articleDTO.getBody();

        model.addAttribute("articleDTO",articleDTO);
        model.addAttribute("body",body);

        model.addAttribute("listFile",listFile);

        return "user/article/detail";

    }

   @GetMapping("/download/{id}")
   public void downLoadFile(@PathVariable(name="id")Long id, Model model, HttpServletResponse response) throws Exception {
       Optional<File> result=fileService.findById(id);

       if(!result.isPresent()){
           throw new Exception("Could not find file with id: "+id);
       }

       File file=result.get();


       response.setContentType("application/octet-stream;charset=UTF-8");
       String headerKey="Content-Disposition";
       String headerValue="attachment; filename=" + URLEncoder.encode(file.getName(),"UTF-8");

       response.setHeader(headerKey,headerValue);


       ServletOutputStream outputStream=response.getOutputStream();


       outputStream.write(file.getContent());
       outputStream.close();

   }

   @GetMapping("/articles/modify/{id}")
   public String showModify(@PathVariable(name="id")Long id,Model model){

        List<File> fileList=fileService.getFileListByArticleId(id);

        Article findArticle=articleService.getById(id);
       ArticleModifyForm articleModifyForm=new ArticleModifyForm(findArticle);
       Long Id= findArticle.getId();;

       model.addAttribute("fileList",fileList);
       model.addAttribute("id",Id);
       model.addAttribute("articleModifyForm",articleModifyForm);



        return "user/article/modify";
   }

   @PostMapping("/articles/modify/{id}")
    public String doModify(@Validated ArticleModifyForm articleModifyForm,BindingResult bindingResult,@PathVariable(name="id")Long id,Model model,@RequestParam("file") MultipartFile multipartFile){

        List<File> listFile=fileService.getFileListByArticleId(id);
        Article findArticle=articleService.getById(id);
        if(bindingResult.hasErrors()){
            return "user/article/modify";
        }
        try{
            String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());


            File file=new File();
            file.setName(fileName);
            file.setContent(multipartFile.getBytes());
            file.setSize(multipartFile.getSize());
            file.setUploadTime(new Date());
            fileService.save(file);

            listFile.add(file);

            articleService.modifyArticle(articleModifyForm,id,listFile);

        }
        catch(Exception e){
            model.addAttribute("e_msg",e.getMessage());
            model.addAttribute("articleModifyForm",new ArticleModifyForm(findArticle));
        }

        return "redirect:/articles/"+id;
   }

   @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable(name="id")Long id){

       ArticleDTO articleDTO= articleService.getArticleById(id);
        try{


            articleService.deleteArticle(id);
        }
        catch (Exception e){
            return "redirect:/";
        }

        return "redirect:/boards/"+articleDTO.getBoardId();




   }











}
