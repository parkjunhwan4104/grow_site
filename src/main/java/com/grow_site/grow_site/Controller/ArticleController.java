package com.grow_site.grow_site.Controller;


import com.grow_site.grow_site.DTO.article.ArticleDTO;
import com.grow_site.grow_site.DTO.article.ArticleSaveForm;
import com.grow_site.grow_site.domain.Board;
import com.grow_site.grow_site.domain.File;
import com.grow_site.grow_site.domain.Member;
import com.grow_site.grow_site.service.ArticleService;
import com.grow_site.grow_site.service.BoardService;
import com.grow_site.grow_site.service.FileService;
import com.grow_site.grow_site.service.MemberService;
import lombok.RequiredArgsConstructor;
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
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;
    private final MemberService memberService;
    private final FileService fileService;



    @GetMapping("/boards/{id}/articles/add")
    public String showAddArticle(@PathVariable(name="id")Long id, Model model){

        Board board=boardService.getBoard(id);
        String boardName=board.getName();
        Long boardId=board.getId();

        model.addAttribute("boardName",boardName);
        model.addAttribute("boardId",boardId);
        model.addAttribute("ArticleSaveForm",new ArticleSaveForm());

        return "user/article/add";
    }

    @PostMapping("/boards/{id}/articles/add")
    public String doAddArticle(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, @PathVariable(name="id")Long id, Model model, Principal principal,@RequestParam("file") MultipartFile multipartFile) throws IOException {


        Board board=boardService.getBoard(id);
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

            Member member=memberService.getMember(principal.getName());
            articleService.save(articleSaveForm,member,board);

        }
        catch(IllegalStateException e){
            model.addAttribute("error_msg",e.getMessage());
            return "user/article/add";
        }
        return "redirect:/boards/"+id;
    }

    @GetMapping("/articles/{id}")
    public String showArticleDetail(@PathVariable(name="id")Long id,Model model)throws IllegalStateException, IOException{

        List<File> listFile=fileService.findByAll();
        ArticleDTO articleDTO= articleService.getArticleById(id);
        model.addAttribute("articleDTO",articleDTO);
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
       response.setContentType("application/octet-stream");
       String headerKey="Content-Disposition";
       String headerValue="attachment; filename=" +file.getName();

       response.setHeader(headerKey,headerValue);

       ServletOutputStream outputStream=response.getOutputStream();
       outputStream.write(file.getContent());
       outputStream.close();

   }











}
