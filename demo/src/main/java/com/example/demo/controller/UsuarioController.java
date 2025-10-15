package com.example.demo.controller;

import com.example.demo.model.mysql.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @GetMapping("/perfil")
    public String perfilUsuario(Model model) {
        Usuario usuario = new Usuario();
        usuario.setUsuario("demo");
        usuario.setCorreo("demo@sistemadegestionsrp.com.co");
        usuario.setRol("ROLE_USER");
        model.addAttribute("usuario", usuario);
        return "perfil/perfil";
    }

    @GetMapping("/home")
    public String perfilAdmin(Model model) {
        Usuario admin = new Usuario();
        admin.setUsuario("admin");
        admin.setCorreo("admin@sistemadegestionsrp.com.co");
        admin.setRol("ROLE_ADMIN");
        model.addAttribute("admin", admin);
        return "home/home";
    }
}
