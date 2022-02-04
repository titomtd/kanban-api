package fr.duchemin.sir.kanban.repository;

import fr.duchemin.sir.kanban.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
