package comidev.giffinity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comidev.giffinity.dto.LastSearchDTO;
import comidev.giffinity.model.Search;
import comidev.giffinity.model.User;
import comidev.giffinity.repo.SearchRepo;

@Service
public class SearchService {

    @Autowired
    private SearchRepo repo;

    public void addSearch(LastSearchDTO lastSearch, Long idUser) {
        Search search = new Search(lastSearch.getLastSearch(), new User(idUser));
        repo.save(search);
    }

}
