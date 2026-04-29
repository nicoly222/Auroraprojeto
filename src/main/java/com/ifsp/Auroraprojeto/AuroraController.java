package com.ifsp.Auroraprojeto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuroraController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConteudoRepository conteudoRepository;


    // ================= LOGIN =================

    @GetMapping("/login")
    public String telaLogin(HttpSession session) {

        if (usuarioLogado(session)) {
            return "redirect:/inicio";
        }

        return "Login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session) {

        Usuario usuario = usuarioService.login(email, senha);

        if (usuario != null) {

            session.setAttribute("usuario", usuario);

            return "redirect:/inicio";
        }

        return "redirect:/login?erro=true";
    }



    // ================= CADASTRO =================

    @GetMapping("/cadastro")
    public String telaCadastro(HttpSession session) {

        if (usuarioLogado(session)) {
            return "redirect:/inicio";
        }

        return "Cadastro";
    }


    @PostMapping("/cadastro")
    public String cadastro(@ModelAttribute Usuario usuario,
                           HttpSession session) {

        boolean sucesso = usuarioService.cadastrar(usuario);

        if (sucesso) {

            session.setAttribute("usuario", usuario);

            return "redirect:/inicio";
        }

        return "redirect:/cadastro?erro=email";
    }



    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }



    // ================= DASHBOARD =================

    @GetMapping("/inicio")
public String inicio(HttpSession session,
                     Model model) {

    if (!usuarioLogado(session)) {
        return "redirect:/login";
    }

    Usuario usuario =
            (Usuario) session.getAttribute("usuario");

    model.addAttribute("usuario", usuario);

    return "TelaInicio";
}


    // ================= DISCIPLINAS =================

    @GetMapping("/disciplinas")
    public String disciplinas(HttpSession session, Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }
         Usuario usuario = (Usuario) session.getAttribute("usuario");

         model.addAttribute("usuario", usuario);

        return "TelaDisciplinas";
    }



    // ================= EXERCÍCIOS =================

    @GetMapping("/exercicios")
    public String exercicios(HttpSession session, Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");

         model.addAttribute("usuario", usuario);

        return "TelaExercicios";
    }



    // ================= PROVAS =================

    @GetMapping("/provas")
    public String provas(HttpSession session,
                         Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }
         Usuario usuario = (Usuario) session.getAttribute("usuario");

         model.addAttribute("usuario", usuario);


        List<Conteudo> provas =
                conteudoRepository.findByTipo(TipoConteudo.PROVA);

        model.addAttribute("provas", provas);

        return "TelaProvas";
    }



    // ================= MATERIAL EXTRA =================

    @GetMapping("/material")
    public String material(HttpSession session, Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }
         Usuario usuario = (Usuario) session.getAttribute("usuario");

         model.addAttribute("usuario", usuario);


        return "TelaMaterialExtra";
    }



    // ================= PERFIL =================

    @GetMapping("/perfil")
    public String perfil(HttpSession session,
                         Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        Usuario usuario =
                (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuario);

        return "PerfilAluno";
    }



   // ================= SALVAR PERFIL =================

@PostMapping("/salvarPerfil")
public String salvarPerfil(@ModelAttribute Usuario usuarioAtualizado,
                           HttpSession session) {

    if (!usuarioLogado(session)) {
        return "redirect:/login";
    }

    Usuario usuario = (Usuario) session.getAttribute("usuario");

    usuario.setNome(usuarioAtualizado.getNome());
    usuario.setEmail(usuarioAtualizado.getEmail());
    usuario.setCurso(usuarioAtualizado.getCurso());
    usuario.setCidade(usuarioAtualizado.getCidade());
    usuario.setTelefone(usuarioAtualizado.getTelefone());

    usuarioService.salvar(usuario);

    session.setAttribute("usuario", usuario);

    return "redirect:/perfil";
}


    // ================= ADMIN =================

    @GetMapping("/admin")
    public String admin(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "admin-dashboard";
    }



    // ================= GERENCIAR AULAS =================

    @GetMapping("/admin/conteudo")
    public String gerenciarAulas(HttpSession session,
                                 Model model) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> aulas =
                conteudoRepository.findByTipo(TipoConteudo.VIDEO);

        model.addAttribute("aulas", aulas);

        return "gerenciar-aulas";
    }



    // ================= SALVAR CONTEÚDO =================

    @PostMapping("/salvarconteudo")
    public String salvarConteudo(@ModelAttribute Conteudo conteudo,
                                 HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        conteudoRepository.save(conteudo);

        return "redirect:/admin/conteudo";
    }



    // ================= NOVO CONTEÚDO =================

    @GetMapping("/admin/conteudo/novo")
    public String novoConteudo(HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        return "admin-dashboard";
    }



    // ================= EXCLUIR AULA =================

    @GetMapping("/admin/excluir/{id}")
    public String excluirAula(@PathVariable Long id,
                              HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        conteudoRepository.deleteById(id);

        return "redirect:/admin/conteudo";
    }



    // ================= MATEMÁTICA BÁSICO =================

    @GetMapping("/matematica-basico")
    public String matematicaBasico(Model model,
                                   HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> aulas =
                conteudoRepository.findByDisciplinaAndNivel(
                        Disciplina.MATEMATICA,
                        "Básico"
                );

        model.addAttribute("aulas", aulas);

        return "TelaAulas";
    }



    // ================= MATEMÁTICA VESTIBULAR =================

    @GetMapping("/matematica-vestibular")
    public String matematicaVestibular(Model model,
                                       HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> aulas =
                conteudoRepository.findByDisciplinaAndNivel(
                        Disciplina.MATEMATICA,
                        "Vestibular"
                );

        model.addAttribute("aulas", aulas);

        return "TelaAulas";
    }



    // ================= PORTUGUÊS BÁSICO =================

    @GetMapping("/portugues-basico")
    public String portuguesBasico(Model model,
                                  HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> aulas =
                conteudoRepository.findByDisciplinaAndNivel(
                        Disciplina.PORTUGUES,
                        "Básico"
                );

        model.addAttribute("aulas", aulas);

        return "TelaAulas";
    }



    // ================= PORTUGUÊS VESTIBULAR =================

    @GetMapping("/portugues-vestibular")
    public String portuguesVestibular(Model model,
                                      HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> aulas =
                conteudoRepository.findByDisciplinaAndNivel(
                        Disciplina.PORTUGUES,
                        "Vestibular"
                );

        model.addAttribute("aulas", aulas);

        return "TelaAulas";
    }



    // ================= PORTUGUÊS ENSINO MÉDIO =================

    @GetMapping("/portugues-ensino-medio")
    public String portuguesEnsinoMedio(Model model,
                                       HttpSession session) {

        if (!usuarioLogado(session)) {
            return "redirect:/login";
        }

        List<Conteudo> aulas =
                conteudoRepository.findByDisciplinaAndNivel(
                        Disciplina.PORTUGUES,
                        "Ensino Médio"
                );

        model.addAttribute("aulas", aulas);

        return "TelaAulas";
    }

    //LOGIN ADMIN
    
    @GetMapping("/login-admin")
public String loginAdmin() {
    return "login-admin"; // Retorna o arquivo login-admin.html
}




    // ================= VERIFICAR SESSÃO =================

    private boolean usuarioLogado(HttpSession session) {

        return session.getAttribute("usuario") != null;
    }

}