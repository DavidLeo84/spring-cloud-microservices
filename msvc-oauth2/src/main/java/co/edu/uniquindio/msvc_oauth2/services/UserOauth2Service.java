package co.edu.uniquindio.msvc_oauth2.services;

import co.edu.uniquindio.msvc_oauth2.entities.UserEntityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserOauth2Service implements UserDetailsService {

    private final WebClient.Builder client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        try {
            UserEntityDTO userEntityDTO = client.build().get().uri("/username/{username}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(UserEntityDTO.class)
                    .block();

            assert userEntityDTO != null;
            List<GrantedAuthority> roles = userEntityDTO.roles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new User(userEntityDTO.username(),
                    userEntityDTO.password(),
                    true,
                    true,
                    true,
                    true,
                    roles);

        } catch (WebClientResponseException e) {
            throw new UsernameNotFoundException("user no found or not exist in system");
        }

    }
}
