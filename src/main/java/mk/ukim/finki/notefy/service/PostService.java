package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.model.entities.Category;
import mk.ukim.finki.notefy.model.entities.Post;
import mk.ukim.finki.notefy.model.entities.Subject;
import mk.ukim.finki.notefy.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post) {
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
        existingPost.setFile(updatedPost.getFile());
        existingPost.setUrl(updatedPost.getUrl());
        existingPost.setPicture(updatedPost.getPicture());
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

    //Adding user to the Post,
    // I mainly did this because we want our posts to be rated only by users who have viewed the post
    public void assignUserToPost(AppUser appUser,Post post){

        Post existingPost = this.postRepository.findById(post.getId()).orElse(null);
        if (existingPost != null) {
            if (existingPost.getAssignedUsers() != null) {
                existingPost.getAssignedUsers().add(appUser);
            }
            else{
                List<AppUser> assignedUsersList = new ArrayList<>();
                assignedUsersList.add(appUser);
                existingPost.setAssignedUsers(assignedUsersList);
            }
        }
    }

    // Rating functionality for the post
    public void ratingPostByAssignedUser(AppUser appUser,Post post, Integer rating){

        Post postById = this.postRepository.findById(post.getId()).orElse(null);
        if(postById != null){
            if(rating>0 && rating<=5 && postById.getAssignedUsers().contains(appUser)) {
                if(postById.getRatingsByUser()==null){
                    HashMap<Long,Integer> ratings= new HashMap<>();
                    ratings.put(appUser.getId(),rating);
                    postById.setRatingsByUser(ratings);
                }
                else {
                    postById.getRatingsByUser().put(appUser.getId(),rating);
                }
            }
        }
    }

    // Getting the post rating
    public double getPostRating(Post post){
        Post postById = this.postRepository.findById(post.getId()).orElse(null);
        if (postById != null){
            int sumOfRatings = postById.getRatingsByUser()
                    .values()
                    .stream()
                    .mapToInt(i->i)
                    .sum();

            if (postById.getAssignedUsers() != null) {
                double rating= (double) sumOfRatings /(postById.getAssignedUsers().size());
                postById.setFinalRating(rating);
                return rating;
            }
        }
        return 0;

    }

    public List<Post> findPostsByAuthor(AppUser appUser){
        return this.postRepository.findAllByCreatedBy(appUser);
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