package fr.duchemin.sir.kanban.repository;

import fr.duchemin.sir.kanban.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}
