package com.ifsp.Auroraprojeto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TelaDisciplinasController {

    @GetMapping("/tela-disciplinas")
     public String telaDisciplinas(){
        return "TelaDisciplinas.html";
     }
    
}
