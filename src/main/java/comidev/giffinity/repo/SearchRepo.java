package comidev.giffinity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.giffinity.model.Search;

@Repository
public interface SearchRepo extends JpaRepository<Search, Long> {

}
