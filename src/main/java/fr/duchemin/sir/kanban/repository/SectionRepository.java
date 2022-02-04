package fr.duchemin.sir.kanban.repository;

import fr.duchemin.sir.kanban.entity.Section;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {

    @Query(value = "SELECT s FROM Section s WHERE s.board.id = :boardId ORDER BY s.position ASC")
    Iterable<Section> findAllByBoardId(@Param("boardId") Long boardId);
}
