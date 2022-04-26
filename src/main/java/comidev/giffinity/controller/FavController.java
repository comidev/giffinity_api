package comidev.giffinity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comidev.giffinity.dto.FavDTO;
import comidev.giffinity.jwt.JwtTokenService;
import comidev.giffinity.service.FavService;
import comidev.giffinity.service.UserService;

@RestController
@RequestMapping("/fav")
public class FavController {

    @Autowired
    private FavService favService;
    @Autowired
    private JwtTokenService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addFav(@RequestHeader("Authorization") String token, @RequestBody FavDTO fav) {
        String username = jwtService.getUsernameByTokenBearer(token);
        Long id = userService.getIdByUsername(username);
        boolean added = favService.addFav(fav, id);
        HttpStatus response = added ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<HttpStatus>(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteFav(@RequestBody FavDTO fav) {
        boolean deleted = favService.deleteFav(fav.getGifId());
        HttpStatus response = deleted ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<HttpStatus>(response);
    }
}
