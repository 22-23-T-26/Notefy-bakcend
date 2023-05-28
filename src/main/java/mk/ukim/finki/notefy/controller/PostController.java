package mk.ukim.finki.notefy.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.notefy.model.dto.CreatePostDto;
import mk.ukim.finki.notefy.model.entities.Post;
import mk.ukim.finki.notefy.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getAllPosts(){

        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){

        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/add")
    public ResponseEntity<Post> addPost(@RequestBody CreatePostDto postDto){

        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @GetMapping("/all-by-author")
    public List<Post> findPostsByAuthor(@RequestParam String authorUsername) throws IOException {

        return postService.findPostsByAuthor(authorUsername);
    }
}