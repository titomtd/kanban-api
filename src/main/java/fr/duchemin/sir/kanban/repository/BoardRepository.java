package fr.duchemin.sir.kanban.repository;

import fr.duchemin.sir.kanban.entity.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
}
