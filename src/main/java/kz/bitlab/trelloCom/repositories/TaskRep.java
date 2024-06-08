package kz.bitlab.trelloCom.repositories;

import jakarta.transaction.Transactional;
import kz.bitlab.trelloCom.entities.Folders;
import kz.bitlab.trelloCom.entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaskRep extends JpaRepository<Tasks, Long> {
    List<Tasks> findByFolder(Folders folders);
}
