package comidev.giffinity.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Fav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String gifId;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    @JoinColumn(name = "user_id")
    private User user;

    public Fav() {
    }

    public Fav(String title, String gifId, String url, User user) {
        this.title = title;
        this.gifId = gifId;
        this.url = url;
        this.user = user;
    }

    public Fav(Long id, String title, String gifId, String url, User user) {
        this.id = id;
        this.title = title;
        this.gifId = gifId;
        this.user = user;
    }
}
