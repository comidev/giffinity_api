package comidev.giffinity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comidev.giffinity.model.Fav;

@Repository
public interface FavRepo extends JpaRepository<Fav, Long> {
    public Fav getFavByGifId(String gifId);
}
