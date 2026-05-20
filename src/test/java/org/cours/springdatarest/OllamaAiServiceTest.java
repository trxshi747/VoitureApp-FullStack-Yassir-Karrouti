package org.cours.springdatarest;

import org.cours.springdatarest.modele.Proprietaire;
import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.cours.springdatarest.service.OllamaAiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OllamaAiServiceTest {

    @Mock
    private OllamaChatModel chatModel;

    @Mock
    private VoitureRepo voitureRepo;

    @Test
    void summarizeVoitureReturnsModelText() {
        ChatResponse response = mock(ChatResponse.class, RETURNS_DEEP_STUBS);
        when(response.getResult().getOutput().getText()).thenReturn("Resume IA");
        when(chatModel.call(ArgumentMatchers.any(Prompt.class))).thenReturn(response);
        when(voitureRepo.findById(1L)).thenReturn(Optional.of(sampleVoiture()));

        OllamaAiService service = new OllamaAiService(chatModel, voitureRepo, "llama2");

        String result = service.summarizeVoiture(1L);

        assertThat(result).isEqualTo("Resume IA");
    }

    @Test
    void askVoitureQuestionThrowsWhenVoitureDoesNotExist() {
        when(voitureRepo.findById(99L)).thenReturn(Optional.empty());

        OllamaAiService service = new OllamaAiService(chatModel, voitureRepo, "llama2");

        assertThatThrownBy(() -> service.askVoitureQuestion(99L, "Question"))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("404 NOT_FOUND");
    }

    private Voiture sampleVoiture() {
        Proprietaire proprietaire = new Proprietaire("Ali", "Hassan");
        Voiture voiture = new Voiture("Toyota", "Corolla", "Grise", "A-1-9090", 2018, 95000, proprietaire);
        voiture.setId(1L);
        return voiture;
    }
}
