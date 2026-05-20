package org.cours.springdatarest;

import org.cours.springdatarest.web.AIController;
import org.cours.springdatarest.web.VoitureController;
import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringDataRestApplicationTests {

    @MockitoBean
    private OllamaChatModel chatModel;

    @Autowired
    private VoitureController voitureController;

    @Autowired
    private AIController aiController;

    @Test
    void contextLoads() {
        assertThat(voitureController).isNotNull();
        assertThat(aiController).isNotNull();
    }

}
