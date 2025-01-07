package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String getMethodName() {
        return "hola";
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> map = new HashMap<>();

        map.put("username", username);

        User user = userService.changePassword(username, changePasswordDto);

        if (user != null) {

            map.put("message", "La contraseña se ha modificado correctamente");
        } else {
            map.put("message", "No se ha podido modificar la contraseña");
        }
        return ResponseEntity.ok().body(map);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/secure")
    public String secureUserMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var authority = authentication.getAuthorities();

        System.out.println(authority);

        return "tienees rol ADMIN";
    }

    @PutMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestBody ChangeRoleDto ChangeRoleDto) {

        UserDto userDto = null;

        Map<String, Object> map = new HashMap<>();

        userDto = userService.changeRole(ChangeRoleDto);

        map.put("user", userDto);

        return ResponseEntity.ok().body(map);
    }

}
