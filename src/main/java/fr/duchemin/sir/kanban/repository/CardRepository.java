package fr.duchemin.sir.kanban.repository;

import fr.duchemin.sir.kanban.entity.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    @Query(value = "SELECT c FROM Card c WHERE c.section.id = :sectionId")
    Iterable<Card> findAllBySectionId(@Param("sectionId") Long sectionId);
}
