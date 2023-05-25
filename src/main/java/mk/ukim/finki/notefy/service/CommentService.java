package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.entities.Comment;
import mk.ukim.finki.notefy.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private UserService userService;

    public Comment createComment(Long discussionId, String content, Long parentCommentId) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedUser(userService.getCurrentUser());
        comment.setDiscussion(discussionService.getDiscussionById(discussionId));
        comment.setParentComment(getCommentById(parentCommentId));

        return commentRepo.save(comment);
    }

    public Page<Comment> getAllComments(Long discussionId, Pageable pageable) {
        return commentRepo.findAllByDiscussionId(discussionId, pageable);
    }

    public Comment getCommentById(Long id) {
        return commentRepo.findById(id).orElseThrow(() -> new BadRequest("Comment with id: " + id + " wasn't found."));
    }
}
