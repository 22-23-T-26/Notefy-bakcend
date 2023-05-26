package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.dto.CommentDto;
import mk.ukim.finki.notefy.model.entities.Comment;
import mk.ukim.finki.notefy.model.entities.Discussion;
import mk.ukim.finki.notefy.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private UserService userService;

    public CommentDto createComment(Long discussionId, String content, Long parentCommentId) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedUser(userService.getCurrentUser());
        if (parentCommentId != null) {
            Comment parentComment = getCommentById(parentCommentId);
            parentComment.addComment(comment); // Add the reply to the parent comment's replies list
            parentComment = commentRepo.save(parentComment);
            return CommentService.mapToDto(parentComment.getLast());
        } else {
            Discussion discussion = discussionService.getDiscussionById(discussionId);
            discussion.addComment(comment);
            discussion = discussionService.save(discussion);

            return CommentService.mapToDto(discussion.getLastComment());
        }

    }


    public static CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .createdBy(comment.getCreatedUser().getFullName())
                .content(comment.getContent())
                .createdTime(comment.getCreatedTime())
                .replies(comment.getReplies().stream().map(CommentService::mapToDto).collect(Collectors.toList()))
                .build();
    }

    public Comment getCommentById(Long id) {
        return commentRepo.findById(id).orElseThrow(() -> new BadRequest("Comment with id: " + id + " wasn't found."));
    }
}
