package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.exception.BadRequest;
import mk.ukim.finki.notefy.model.dto.DiscussionDto;
import mk.ukim.finki.notefy.model.entities.AppUser;
import mk.ukim.finki.notefy.model.entities.Discussion;
import mk.ukim.finki.notefy.model.entities.Like;
import mk.ukim.finki.notefy.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionRepository discussionRepo;

    @Autowired
    private UserService userService;

    public DiscussionDto createDiscussion(String title, String description) {
        Discussion discussion = new Discussion();
        discussion.setTitle(title);
        discussion.setDescription(description);
        discussion.setCreatedUser(userService.getCurrentUser());

        return this.convertToDto(discussionRepo.save(discussion), List.of(), false);
    }

    public Page<DiscussionDto> getAllDiscussions(String title, Pageable pageable) {
        AppUser currentUser = userService.getCurrentUser();
        List<Long> likeIds = currentUser.getCreatedLikes().stream().map(Like::getId).collect(Collectors.toList());
        return discussionRepo.findByTitleContaining(title, pageable).map((discussion) -> convertToDto(discussion, likeIds, false));
    }

    private DiscussionDto convertToDto(Discussion discussion, List<Long> likeIds, Boolean loadComments) {



        // Instantiate and return a new DiscussionDto based on the given Discussion entity.
        // You'll have to fill the details of this method based on the specific fields
        // present in your Discussion and DiscussionDto classes.
        DiscussionDto dto = new DiscussionDto();

        dto.setId(discussion.getId());
        dto.setTitle(discussion.getTitle());
        dto.setDescription(discussion.getDescription());
        dto.setCreatedTime(discussion.getCreatedTime());

        dto.setCreatedBy(discussion.getCreatedUser().getFullName());
        if (loadComments) {
            dto.setComments(discussion.getComments());
        }
        dto.setNumberOfComments((long) discussion.getComments().size());
        dto.setNumberOfLikes((long) discussion.getLikes().size());
        dto.setIsLiked(discussion.getLikes()
                .stream().anyMatch(like -> likeIds.contains(like.getId())));
        return dto;
    }


    public Discussion getDiscussionById(Long id) {
        return discussionRepo.findById(id).orElseThrow(() -> new BadRequest("Discussion with id: " + id + " wasn't found."));
    }

    public DiscussionDto getDiscussionByIdDto(Long id) {
        return this.convertToDto(discussionRepo.findById(id).orElseThrow(() -> new BadRequest("Discussion with id: " + id + " wasn't found.")), List.of(), true);
    }
}