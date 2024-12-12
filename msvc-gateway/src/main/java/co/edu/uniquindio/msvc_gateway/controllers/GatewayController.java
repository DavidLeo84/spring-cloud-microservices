package co.edu.uniquindio.msvc_gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GatewayController {

    @GetMapping("/authorized")
    public Map<String, String> authorizedMap(@RequestParam String code) {

        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        return map;
    }

    @PostMapping("/logout")
    public Map<String, String> logoutMap() {

        return Collections.singletonMap("logout", "Ok");
    }

}
