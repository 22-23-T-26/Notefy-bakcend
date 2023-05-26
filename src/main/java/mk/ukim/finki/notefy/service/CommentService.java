package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.dto.CommentDto;
import mk.ukim.finki.notefy.model.entities.Comment;
import mk.ukim.finki.notefy.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        comment.setDiscussion(discussionService.getDiscussionById(discussionId));
        if (parentCommentId != null) {
            comment.setParentComment(getCommentById(parentCommentId));
        }

        return CommentService.mapToDto(commentRepo.save(comment));
    }

    public Page<Comment> getAllComments(Long discussionId, Pageable pageable) {
        return commentRepo.findAllByDiscussionId(discussionId, pageable);
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
