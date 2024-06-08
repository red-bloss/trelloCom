package kz.bitlab.trelloCom.repositories;

import jakarta.transaction.Transactional;
import kz.bitlab.trelloCom.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CommentRep extends JpaRepository<Comments, Long> {
}
