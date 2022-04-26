package comidev.giffinity.dto;

import lombok.Getter;

@Getter
public class FavDTO {
    private String title;
    private String gifId;
    private String url;

    public FavDTO() {

    }

    public FavDTO(String gifId) {
        this.gifId = gifId;
    }

    public FavDTO(String title, String gifId, String url) {
        this.title = title;
        this.gifId = gifId;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Title: " + title + " | GifID: " + gifId + " | Url: " + url;
    }

}
