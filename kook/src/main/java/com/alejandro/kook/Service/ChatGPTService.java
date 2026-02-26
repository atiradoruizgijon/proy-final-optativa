package com.alejandro.kook.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import com.alejandro.kook.Dto.ChatRequestDTO;
import com.alejandro.kook.Dto.ChatResponseDTO;

@Service
public class ChatGPTService {

    private final ChatClient chatClient;

    public ChatGPTService(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    // Pregunta a ChatGPT sobre recetas
    public ChatResponseDTO askForRecipe(ChatRequestDTO request) {
        String systemPrompt = """
            Eres un asistente de cocina experto. Cuando el usuario te pida una receta,
            proporciona una respuesta clara y detallada con:
            - Nombre de la receta
            - Descripción breve
            - Instrucciones paso a paso

            Formatea tu respuesta para que sea fácil de leer y guardar. Y no aceptes otra
            petición que no sea la de una receta de cocina.
            """;

        String response = chatClient.prompt()
            .system(systemPrompt)
            .user(request.getMensaje())
            .call()
            .content();

        ChatResponseDTO result = new ChatResponseDTO();
        result.setRespuesta(response);
        return result;
    }

    // Chat general con ChatGPT
    public ChatResponseDTO chat(ChatRequestDTO request) {
        String response = chatClient.prompt()
            .user(request.getMensaje())
            .call()
            .content();

        ChatResponseDTO result = new ChatResponseDTO();
        result.setRespuesta(response);
        return result;
    }
}
