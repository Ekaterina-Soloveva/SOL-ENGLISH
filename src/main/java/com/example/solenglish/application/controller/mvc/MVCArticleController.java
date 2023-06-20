package com.example.solenglish.application.controller.mvc;

import com.example.solenglish.application.dto.ArticleDTO;
import com.example.solenglish.application.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import com.example.solenglish.application.exception.MyDeleteException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/articles")
public class MVCArticleController {
    private final ArticleService articleService;

    public MVCArticleController(ArticleService articleService) {
        this. articleService = articleService;
    }

    @GetMapping
    public String viewAllArticles(Model model) {
        List<ArticleDTO> articles = articleService.listAll();
        model.addAttribute("articles", articles);
        return "/articles/viewAllArticles";
    }


    @GetMapping("/add")
    public String create() {
        return "articles/addArticle";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("articleForm") ArticleDTO newArticle) {
        log.info(newArticle.toString());
        articleService.create(newArticle);
        return "redirect:/articles";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model) {
        model.addAttribute("article", articleService.getOne(id));
        return "articles/viewArticle";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        articleService.deleteSoft(id);
        return "redirect:/articles";
    }

    @PostMapping("/search")
    public String searchArticles(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int pageSize,
                                @ModelAttribute("articleSearchForm") ArticleDTO articleDTO,
                                Model model) {
        if (StringUtils.hasText(articleDTO.getKeyWords()) || StringUtils.hasLength(articleDTO.getKeyWords())) {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "title"));
            model.addAttribute("articles", articleService.searchArticles(articleDTO.getKeyWords().trim(), pageRequest));
            return "articles/viewAllArticles";
        }
        else {
            return "redirect:/articles";
        }
    }





}
