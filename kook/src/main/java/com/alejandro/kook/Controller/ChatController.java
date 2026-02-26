package com.alejandro.kook.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.kook.Dto.ChatRequestDTO;
import com.alejandro.kook.Dto.ChatResponseDTO;
import com.alejandro.kook.Service.ChatGPTService;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

    private final ChatGPTService chatGPTService;

    public ChatController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @PostMapping("/receta")
    public ChatResponseDTO askForRecipe(@RequestBody ChatRequestDTO request) {
        System.out.println("Pregunta sobre receta: " + request.getMensaje());
        return chatGPTService.askForRecipe(request);
    }

    @PostMapping("/general")
    public ChatResponseDTO chat(@RequestBody ChatRequestDTO request) {
        System.out.println("Chat general: " + request.getMensaje());
        return chatGPTService.chat(request);
    }
}
