package com.example.reactiveclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

@Controller
public class ViewController {
    @Autowired
    UnsplashService unsplashService;

    @Autowired
    PexelsService pexelsService;

    @GetMapping("/")
    public String displayIndex(Model model) {
        model.addAttribute("searchKeyword", new SearchKeyword());
        return "index";
    }

    @PostMapping("/")
    public String performSearch(@ModelAttribute("searchKeyword") SearchKeyword searchKeyword, Model model) {
        if(searchKeyword.getSource().equals("unsplash")) {
            ReactiveDataDriverContextVariable reactiveData = new ReactiveDataDriverContextVariable(unsplashService.getPhotos(searchKeyword.getText(),
                            searchKeyword.getOrientation()), 1);
            model.addAttribute("photos", reactiveData);
        }else if(searchKeyword.getSource().equals("pexels")){
            ReactiveDataDriverContextVariable reactiveData2 = new ReactiveDataDriverContextVariable(pexelsService
                    .getPhotos(searchKeyword.getText()), 1);
            model.addAttribute("photos", reactiveData2);
        }else {
            ReactiveDataDriverContextVariable reactiveData3 = new ReactiveDataDriverContextVariable(Flux.merge(unsplashService.getPhotos(searchKeyword.getText(),searchKeyword.getOrientation()),
                    pexelsService.getPhotos(searchKeyword.getText())), 1);
            model.addAttribute("photos", reactiveData3);
        }
        model.addAttribute("searchKeyword", searchKeyword);
        if(searchKeyword == null){
            model.addAttribute("noPhotos", true);
        }
        return "index";
    }
}
