package br.com.luishmalafaia.repositories;

import br.com.luishmalafaia.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
