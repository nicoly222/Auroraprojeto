package com.ifsp.Auroraprojeto;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static List<Usuario> usuarios = new ArrayList<>();

   
    // CADASTRO
    public static boolean cadastrar(Usuario usuario) {
        // evita emails duplicados
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(usuario.getEmail())) {
                return false; // email já cadastrado
            }
        }
        usuarios.add(usuario);
        return true; // cadastro feito
    }
    
    // LOGIN
    
    public static Usuario login(String email, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u;
            }
        }
        return null;
    }
}
