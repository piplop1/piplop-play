package com.piplop.play.domain.services;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface PiplopPlayAiService {
    @SystemMessage("""
            Eres un experto en cine que recomienda películas personalizadas según el gusto del usuario.
            Debes recomendar máximo 3 películas.
            No incluyas películas que estén por fuera de la plataforma PiplopPlay.
            """)
    String generateMoviesSuggestion(@UserMessage String userMessage);
}
