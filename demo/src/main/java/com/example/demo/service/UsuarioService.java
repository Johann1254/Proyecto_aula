package com.example.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.mysql.Usuario;
import com.example.demo.repos.mysql.UsuarioRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createUsuario(String usuario, String contrasena, String correo, String rol) {
        Usuario nuevo = new Usuario();
        nuevo.setUsuario(usuario);
        nuevo.setContrasena(passwordEncoder.encode(contrasena));
        nuevo.setCorreo(correo);
        nuevo.setRol(rol);
        return usuarioRepository.save(nuevo);
    }

    public Optional<Usuario> findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public boolean existsByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario).isPresent();
    }

    // 🧩 Este método se ejecutará automáticamente al iniciar el proyecto
    @PostConstruct
    public void inicializarUsuariosPorDefecto() {
        if (!existsByUsuario("admin")) {
            createUsuario("admin", "admin123", "admin@sistemadegestionsrp.com.co", "ROLE_ADMIN");
            System.out.println("✅ Administrador creado: admin / admin123");
        }
        if (!existsByUsuario("user")) {
            createUsuario("user", "user123", "user@sistemadegestionsrp.com.co", "ROLE_USER");
            System.out.println("✅ Usuario normal creado: user / user123");
        }
    }
}
