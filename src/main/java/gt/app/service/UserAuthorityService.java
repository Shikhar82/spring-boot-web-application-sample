package gt.app.service;

import gt.app.config.security.UserDetails;
import gt.app.domain.Note;
import gt.app.domain.User;
import gt.app.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("appSecurity")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserAuthorityService {

    private final NoteRepository noteRepository;

    public boolean hasAccess(UserDetails curUser, Long id, String entity) {

        if (curUser.isSystemAdmin()) {
            return true;
        }

        if (User.class.getSimpleName().equalsIgnoreCase(entity)) {
            return id.equals(curUser.getId());
        }


        if (Note.class.getSimpleName().equalsIgnoreCase(entity)) {

            Long createdById = noteRepository.findCreatedByUserIdById(id);

            return createdById.equals(curUser.getId());
        }


        /*
        add more rules
         */

        return false;
    }

}