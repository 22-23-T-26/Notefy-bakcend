package mk.ukim.finki.notefy.controller;

import mk.ukim.finki.notefy.model.dto.CommentDto;
import mk.ukim.finki.notefy.model.dto.DiscussionDto;
import mk.ukim.finki.notefy.model.entities.Discussion;
import mk.ukim.finki.notefy.service.CommentService;
import mk.ukim.finki.notefy.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<DiscussionDto> createDiscussion(@RequestBody Discussion discussion) {
        DiscussionDto createdDiscussion = discussionService.createDiscussion(discussion.getTitle(), discussion.getDescription());
        return new ResponseEntity<>(createdDiscussion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllDiscussions(@RequestParam(required = false)  Long discussionId, @RequestParam(required = false, defaultValue = "") String title, Pageable pageable) {
        if (discussionId != null) {
            return new ResponseEntity<>(discussionService.getDiscussionByIdDto(discussionId), HttpStatus.OK);
        }
        return new ResponseEntity<>(discussionService.getAllDiscussions(title, pageable), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @RequestParam(required = false) Long parenCommentId, @RequestParam Long discussionId ) {
        return new ResponseEntity<>(commentService.createComment(discussionId, commentDto.getContent(), parenCommentId), HttpStatus.OK);
    }

}
