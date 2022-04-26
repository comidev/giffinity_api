package comidev.giffinity.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import comidev.giffinity.dto.FavDTO;
import comidev.giffinity.dto.UserDTO;
import comidev.giffinity.model.Fav;
import comidev.giffinity.model.Role;
import comidev.giffinity.model.Search;
import comidev.giffinity.model.User;
import comidev.giffinity.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFound = userRepo.findByUsername(username).get();
        if (userFound != null) {
            List<? extends GrantedAuthority> roles = userFound.getRoles().stream()
                    .map(role -> {
                        String roleName = role.getName();
                        return new SimpleGrantedAuthority(roleName);
                    })
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    userFound.getUsername(),
                    userFound.getPassword(),
                    roles);
        }
        return null;
    }

    public boolean register(UserDTO register) {
        boolean exists = userRepo.existsByUsername(register.getUsername());
        if (!exists) {
            Role role = roleService.getRoleByName("ROLE_USER");
            User userNew = new User(register, Collections.singleton(role));
            userRepo.save(userNew);
            return true;
        }
        return false;
    }

    public Long getIdByUsername(String username) {
        return userRepo.getUserByUsername(username).getId();
    }

    public String getLastSearch(String username) {
        User userFound = userRepo.getUserByUsername(username);
        List<Search> searchs = userFound.getSearchs();
        if (searchs != null) {
            if (searchs.size() > 0) {
                int size = searchs.size();
                return searchs.get(size - 1).getSearch();
            }
        }
        return null;
    }

    public List<FavDTO> getFavs(String username) {
        User userFound = userRepo.getUserByUsername(username);
        List<Fav> favs = userFound.getFavs();

        if (favs != null) {
            if (favs.size() > 0) {
                List<FavDTO> favsExport = favs.stream()
                        .map(fav -> new FavDTO(fav.getTitle(), fav.getGifId(), fav.getUrl()))
                        .collect(Collectors.toList());
                return favsExport;
            }
        }
        return null;
    }

}
