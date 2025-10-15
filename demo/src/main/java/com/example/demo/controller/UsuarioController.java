package com.example.demo.controller;

import com.example.demo.model.mysql.Usuario;
import com.example.demo.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // =====================================================
    // 🔹 VISTA: Lista de usuarios (solo admin)
    // =====================================================
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/usuarios"; // templates/usuarios/usuarios.html
    }

    // =====================================================
    // 🔹 VISTA: Formulario de nuevo usuario
    // =====================================================
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/usuario-form";
    }

    // =====================================================
    // 🔹 GUARDAR: Crear o actualizar usuario
    // =====================================================
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // =====================================================
    // 🔹 VISTA: Editar usuario existente
    // =====================================================
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/usuario-form";
    }

    // =====================================================
    // 🔹 ELIMINAR: (por AJAX)
    // =====================================================
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "Usuario eliminado correctamente";
    }

    // =====================================================
    // 🔹 API: Retorna lista JSON (para DataTables o JS)
    // =====================================================
    @GetMapping("/api/lista")
    @ResponseBody
    public List<Usuario> apiUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // =====================================================
    // 🔹 VISTA: Perfil del usuario normal
    // =====================================================
    @GetMapping("/perfil")
    public String perfilUsuario(Model model) {
        Usuario usuario = new Usuario();
        usuario.setUsuario("demo");
        usuario.setCorreo("demo@sistemadegestionsrp.com.co");
        usuario.setRol("ROLE_USER");
        model.addAttribute("usuario", usuario);
        return "perfil/perfil";
    }

    // =====================================================
    // 🔹 VISTA: Dashboard administrador
    // =====================================================
    @GetMapping("/home")
    public String homeAdmin(Model model) {
        Usuario admin = new Usuario();
        admin.setUsuario("admin");
        admin.setCorreo("admin@sistemadegestionsrp.com.co");
        admin.setRol("ROLE_ADMIN");
        model.addAttribute("admin", admin);
        return "home/home";
    }
}
