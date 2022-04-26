package comidev.giffinity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comidev.giffinity.dto.LastSearchDTO;
import comidev.giffinity.jwt.JwtTokenService;
import comidev.giffinity.service.SearchService;
import comidev.giffinity.service.UserService;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService service;

    @Autowired
    private JwtTokenService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<HttpStatus> addSearch(@RequestHeader("Authorization") String token,
            @RequestBody() LastSearchDTO search) {
        try {
            String username = jwtService.getUsernameByTokenBearer(token);
            Long idUser = userService.getIdByUsername(username);
            service.addSearch(search, idUser);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            return ResponseEntity.status(406).build();
        }
    }
}
