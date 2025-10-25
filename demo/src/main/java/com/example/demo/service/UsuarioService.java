package com.example.demo.service;

import com.example.demo.model.mysql.Usuario;
import com.example.demo.repos.mysql.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Listar todos
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Guardar nuevo
    public Usuario guardarUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    // Actualizar existente
    public Usuario actualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio");
        }
        // Si la contraseña viene vacía, conservar la anterior
        Usuario existente = usuarioRepository.findById(usuario.getId()).orElseThrow();
        if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
            usuario.setContrasena(existente.getContrasena());
        } else {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        return usuarioRepository.save(usuario);
    }

    // Obtener por ID
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Eliminar
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
