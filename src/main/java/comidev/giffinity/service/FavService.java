package comidev.giffinity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comidev.giffinity.dto.FavDTO;
import comidev.giffinity.model.Fav;
import comidev.giffinity.model.User;
import comidev.giffinity.repo.FavRepo;

@Service
public class FavService {
    @Autowired
    private FavRepo repo;

    public boolean addFav(FavDTO fav, Long id) {
        Fav favFound = repo.getFavByGifId(fav.getGifId());
        if (favFound == null) {
            Fav newFav = new Fav(fav.getTitle(), fav.getGifId(), fav.getUrl(), new User(id));
            repo.save(newFav);
            return true;
        }
        return false;
    }

    public boolean deleteFav(String gifId) {
        Fav favFound = repo.getFavByGifId(gifId);
        if (favFound != null) {
            repo.delete(favFound);
            return true;
        }
        return false;
    }
}
