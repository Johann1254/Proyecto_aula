package com.example.demo.service;

import java.util.List;
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

    // 🔹 Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // 🔹 Guardar o actualizar un usuario
    public void guardarUsuario(Usuario usuario) {
        // Si es nuevo usuario o está cambiando la contraseña, la encriptamos
        if (usuario.getId() == null) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        } else {
            // Si ya existe, conservar la contraseña anterior si no se envió una nueva
            Usuario existente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (existente != null) {
                if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
                    usuario.setContrasena(existente.getContrasena());
                } else {
                    usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
                }
            }
        }
        usuarioRepository.save(usuario);
    }

    // 🔹 Obtener usuario por ID
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // 🔹 Eliminar usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // 🔹 Crear usuario directamente (para inicialización)
    public Usuario createUsuario(String usuario, String contrasena, String correo, String rol) {
        Usuario nuevo = new Usuario();
        nuevo.setUsuario(usuario);
        nuevo.setContrasena(passwordEncoder.encode(contrasena));
        nuevo.setCorreo(correo);
        nuevo.setRol(rol);
        return usuarioRepository.save(nuevo);
    }

    // 🔹 Buscar usuario por nombre
    public Optional<Usuario> findByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    // 🔹 Verificar si existe un usuario
    public boolean existsByUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario).isPresent();
    }

    // 🔹 Crear usuarios iniciales al iniciar la aplicación
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
