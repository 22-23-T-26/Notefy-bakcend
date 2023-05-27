package mk.ukim.finki.notefy.service;

import mk.ukim.finki.notefy.model.entities.Like;
import mk.ukim.finki.notefy.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussionService discussionService;


    public Like save(Long discussionId) {
        Like like = Like.builder()
                .user(userService.getCurrentUser())
                .discussion(discussionService.getDiscussionById(discussionId))
                .build();
        return likeRepository.save(like);
    }

    @Transactional
    public void remove(Long discussionId) {
        likeRepository.deleteByDiscussion_IdAndUser_Id(discussionId, userService.getCurrentUser().getId());
    }
}
