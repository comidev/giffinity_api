package comidev.giffinity.dto;

import java.util.List;

public class FavsDTO {
    private List<FavDTO> favs;

    public FavsDTO() {

    }

    public FavsDTO(List<FavDTO> favs) {
        this.favs = favs;
    }

    public List<FavDTO> getFavs() {
        return favs;
    }
}
