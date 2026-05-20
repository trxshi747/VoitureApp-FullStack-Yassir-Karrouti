package org.cours.springdatarest.service;

import org.cours.springdatarest.modele.Voiture;
import org.cours.springdatarest.modele.VoitureRepo;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class OllamaAiService {

    private final OllamaChatModel chatModel;
    private final VoitureRepo voitureRepo;
    private final String modelName;

    public OllamaAiService(
        OllamaChatModel chatModel,
        VoitureRepo voitureRepo,
        @Value("${spring.ai.ollama.chat.options.model:llama2}") String modelName
    ) {
        this.chatModel = chatModel;
        this.voitureRepo = voitureRepo;
        this.modelName = modelName;
    }

    public String generateResult(String promptMessage) {
        return runPrompt(promptMessage, 0.4d);
    }

    public String summarizeVoiture(Long id) {
        Voiture voiture = getVoitureOrThrow(id);
        String prompt = """
            Tu es un assistant automobile pedagogique.
            Fais un resume clair et utile de cette voiture en francais simple.
            Mentionne son positionnement, ses points d'attention et le type d'usage conseille.

            Marque : %s
            Modele : %s
            Couleur : %s
            Immatricule : %s
            Annee : %d
            Prix : %d
            """.formatted(
            voiture.getMarque(),
            voiture.getModele(),
            voiture.getCouleur(),
            voiture.getImmatricule(),
            voiture.getAnnee(),
            voiture.getPrix()
        );
        return runPrompt(prompt, 0.3d);
    }

    public String askVoitureQuestion(Long id, String question) {
        Voiture voiture = getVoitureOrThrow(id);
        String prompt = """
            Tu es un assistant automobile.
            Reponds de maniere claire, concise et utile en t'appuyant uniquement sur cette fiche vehicule.

            Marque : %s
            Modele : %s
            Couleur : %s
            Immatricule : %s
            Annee : %d
            Prix : %d

            Question : %s
            """.formatted(
            voiture.getMarque(),
            voiture.getModele(),
            voiture.getCouleur(),
            voiture.getImmatricule(),
            voiture.getAnnee(),
            voiture.getPrix(),
            question
        );
        return runPrompt(prompt, 0.4d);
    }

    public String generateQuizForVoiture(Long id) {
        Voiture voiture = getVoitureOrThrow(id);
        String prompt = """
            Cree un petit quiz en francais base sur cette voiture.

            Marque : %s
            Modele : %s
            Couleur : %s
            Immatricule : %s
            Annee : %d
            Prix : %d

            Contraintes :
            - Donne 3 questions a choix multiples
            - Chaque question doit avoir 3 options
            - Indique clairement la bonne reponse
            - Reste fidele aux informations connues
            """.formatted(
            voiture.getMarque(),
            voiture.getModele(),
            voiture.getCouleur(),
            voiture.getImmatricule(),
            voiture.getAnnee(),
            voiture.getPrix()
        );
        return runPrompt(prompt, 0.5d);
    }

    public String generateQuizWithDifficulty(Long id, String difficulty) {
        Voiture voiture = getVoitureOrThrow(id);
        String prompt = """
            Cree un quiz de difficulte %s en francais base sur cette voiture.

            Marque : %s
            Modele : %s
            Couleur : %s
            Immatricule : %s
            Annee : %d
            Prix : %d

            Contraintes :
            - Donne 3 questions a choix multiples
            - Chaque question doit avoir 3 options
            - Indique clairement la bonne reponse
            - Adapte la complexite des questions au niveau demande
            """.formatted(
            difficulty,
            voiture.getMarque(),
            voiture.getModele(),
            voiture.getCouleur(),
            voiture.getImmatricule(),
            voiture.getAnnee(),
            voiture.getPrix()
        );
        return runPrompt(prompt, 0.5d);
    }

    private Voiture getVoitureOrThrow(Long id) {
        return voitureRepo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Voiture non trouvee"));
    }

    private String runPrompt(String prompt, double temperature) {
        ChatResponse response = chatModel.call(
            new Prompt(
                prompt,
                OllamaOptions.builder()
                    .model(modelName)
                    .temperature(temperature)
                    .build()
            )
        );
        return response.getResult().getOutput().getText();
    }
}
