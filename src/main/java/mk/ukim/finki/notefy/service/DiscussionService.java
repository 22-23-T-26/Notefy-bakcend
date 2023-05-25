package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.entities.Discussion;
import mk.ukim.finki.notefy.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionRepository discussionRepo;

    @Autowired
    private UserService userService;

    public Discussion createDiscussion(String title, String description) {
        Discussion discussion = new Discussion();
        discussion.setTitle(title);
        discussion.setDescription(description);
        discussion.setCreatedUser(userService.getCurrentUser());

        return discussionRepo.save(discussion);
    }

    public Page<Discussion> getAllDiscussions(String title, Pageable pageable) {
        return discussionRepo.findByTitleContaining(title, pageable);
    }

    public Discussion getDiscussionById(Long id) {
        return discussionRepo.findById(id).orElseThrow(() -> new BadRequest("Discussion with id: " + id + " wasn't found."));
    }
}