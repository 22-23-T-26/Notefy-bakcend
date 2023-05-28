package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.dto.CreatePostDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.model.entities.Category;
import mk.ukim.finki.notefy.model.entities.Post;
import mk.ukim.finki.notefy.model.entities.Subject;
import mk.ukim.finki.notefy.repository.PostRepository;
import mk.ukim.finki.notefy.repository.SubjectRepository;
import mk.ukim.finki.notefy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;


    @PostConstruct
    public void init() {
        subjectRepository.save(
                Subject.builder()
                        .id(1L)
                        .subjectName("General")
                        .build()
        );

        subjectRepository.save(
                Subject.builder()
                        .id(2L)
                        .subjectName("Math")
                        .build()
        );

        subjectRepository.save(
                Subject.builder()
                        .id(3L)
                        .subjectName("Programming")
                        .build()
        );


    }

    public PostService(PostRepository postRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(CreatePostDto postDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        AppUser currentuser = userRepository.getByUsernameOrEmail(username, username)
                .orElseThrow(() -> new BadRequest("You are not authenticated"));
        Post post = new Post();
        post.setDescription(postDto.getDescription());
        post.setDescription(postDto.getTitle());
        post.setUrl(postDto.getUrl());
        post.setPrice(postDto.getPrice());
        post.setPaymentFlag(postDto.isPaymentFlag());
        post.setCategory(Category.valueOf(postDto.getCategory()));
        post.setSubject(subjectRepository.findById(postDto.getSubject()).orElse(null));
        post.setCreatedBy(currentuser);

        return postRepository.save(post);
    }

    public Post updatePost(Post updatedPost) {
        Post existingPost = postRepository.findById(updatedPost.getId()).orElse(null);
        if (existingPost == null) {
            return null;
        }
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setDescription(updatedPost.getDescription());
        existingPost.setPrice(updatedPost.getPrice());
        existingPost.setUrl(updatedPost.getUrl());
        existingPost.setSubject(updatedPost.getSubject());
        existingPost.setCreatedBy(updatedPost.getCreatedBy());
        existingPost.setPaymentFlag(updatedPost.isPaymentFlag());
        existingPost.setCategory(updatedPost.getCategory());
        return postRepository.save(existingPost);

    }

    public boolean deletePostById(Long id) {
        Post existingPost = postRepository.findById(id).orElse(null);
        if (existingPost == null) {
            return false;
        }
        postRepository.delete(existingPost);
        return true;
    }

    // Getting the post rating
    public double getPostRating(Post post){
        Post postById = this.postRepository.findById(post.getId()).orElse(null);
        return postById.getFinalRating();

    }

    public List<Post> findPostsByAuthor(String username) throws IOException {
        AppUser user = userRepository.findByUsername(username).orElseThrow(IOException::new);
        return this.postRepository.findAllByCreatedBy(user);
    }

    public List<Post> findPostsBySubject(Subject subject){
        return this.postRepository.findAllBySubject(subject);
    }

    public List<Post> findPostsByCategory(Category category){
        return this.postRepository.findAllByCategory(category);
    }

    public List<Post> findPostsByDate(Date date){
        return this.postRepository.findAllByDateOfCreation(date);
    }

    public List<Post> findPostsByRating(Integer rating){
        return this.postRepository.findAllByFinalRating( (double) rating );
    }
    public List<Post> findPostsByRatingBefore(Integer rating){
        return this.postRepository.findAllByFinalRatingBefore( (double) rating );
    }
    public List<Post> findPostsByRatingAfter(Integer rating){
        return this.postRepository.findAllByFinalRatingAfter( (double) rating );
    }

}
