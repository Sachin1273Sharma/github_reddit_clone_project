package io.mountblue.reddit.controller;

import io.mountblue.reddit.model.SubReddit;
import io.mountblue.reddit.service.PostService;
import io.mountblue.reddit.model.Post;
import io.mountblue.reddit.service.SubRedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final SubRedditService subRedditService;

    @Autowired
    public PostController(PostService postService,SubRedditService subRedditService) {
        this.postService = postService;
        this.subRedditService = subRedditService;
    }

    @GetMapping("/new")
    public String createNewPost(Model model) {
        List<String> subRedditList = subRedditService.getAllSubRedditsByName();
        model.addAttribute("subRedditList",subRedditList);
        return "create-post";
    }

    @PostMapping("/createFirstPart")
    public String savePost(@RequestParam("title") String title,
                           @RequestParam("body") String body,
                           @RequestParam("subRedditName") String subRedditName,
                           @RequestParam("image") MultipartFile imageFile,
                           Model model) throws IOException {
        System.out.println(subRedditName);
        Post post = new Post();
        post.setTitle(title);
        SubReddit subReddit = subRedditService.getSubReddit(subRedditName);
        System.out.println(subReddit);
        post.setSubReddit(subReddit);
        System.out.println(post.getSubReddit());
        post.setBody(body);

        if (!imageFile.isEmpty()) {
            post.setImage(imageFile.getBytes());
        }

        post.setCreatedAt(LocalDateTime.now());
        postService.saveCreatePost(post);
        return "redirect:/sub";
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getPostImage(@PathVariable Long id) {
        Post post = postService.getPostById(id);

        if (post != null && post.getImage() != null) {
            ByteArrayResource resource = new ByteArrayResource(post.getImage());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Default for binary data
            headers.setContentLength(post.getImage().length);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}