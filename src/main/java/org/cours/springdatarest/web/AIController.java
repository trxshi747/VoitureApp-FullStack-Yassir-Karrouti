package org.cours.springdatarest.web;

import org.cours.springdatarest.service.OllamaAiService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping
public class AIController {

    private final OllamaAiService ollamaAiService;

    public AIController(OllamaAiService ollamaAiService) {
        this.ollamaAiService = ollamaAiService;
    }

    @GetMapping("/api/v1/generate")
    public String generate(@RequestParam String promptMessage) {
        return ollamaAiService.generateResult(promptMessage);
    }

    @GetMapping("/voitures/{id}/summary")
    public String summarizeVoiture(@PathVariable Long id) {
        return ollamaAiService.summarizeVoiture(id);
    }

    @GetMapping("/voitures/{id}/quiz")
    public String quizVoiture(@PathVariable Long id) {
        return ollamaAiService.generateQuizForVoiture(id);
    }

    @GetMapping("/voitures/{id}/quizD")
    public String quizVoitureWithDifficulty(
        @PathVariable Long id,
        @RequestParam(defaultValue = "Facile") String difficulte
    ) {
        return ollamaAiService.generateQuizWithDifficulty(id, difficulte);
    }

    @PostMapping(value = "/voitures/{id}/ask", consumes = "text/plain")
    public String askVoitureQuestion(@PathVariable Long id, @RequestBody String question) {
        return ollamaAiService.askVoitureQuestion(id, question);
    }
}
