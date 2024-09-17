package io.mountblue.reddit.controller;

import io.mountblue.reddit.model.Post;
import io.mountblue.reddit.model.SubReddit;
import io.mountblue.reddit.service.PostService;
import io.mountblue.reddit.service.SubRedditService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SubRedditController {
    private final SubRedditService subRedditService;
    private final PostService postService;

    public SubRedditController(SubRedditService subRedditService, PostService postService){
        this.subRedditService = subRedditService;
        this.postService = postService;
    }

    @GetMapping("/sub")
    public String subRedditPageView(Model model){
        model.addAttribute("subReddit",new SubReddit());
        List<Post> posts= postService.getAllPosts();
        model.addAttribute("posts",posts);
        model.addAttribute("subReddits",subRedditService.getAllSubReddits());
        System.out.println(subRedditService.getAllSubReddits());
        return "view-sub";
    }

    @PostMapping("/sub/new")
    public String subRedditCreate(@ModelAttribute SubReddit subReddit, Model model){
        subReddit.setCreatedAt(LocalDateTime.now());
        subRedditService.createSubReddit(subReddit);
        return "redirect:/sub";
    }
}

