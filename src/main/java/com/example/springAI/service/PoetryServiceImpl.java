package com.example.springAI.service;

import com.example.springAI.model.dtos.PoetryDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PoetryServiceImpl implements PoetryService {

    public static final String WRITE_ME_HAIKU_ABOUT_CAT = """
            Write me a Haiku about a cat,
            haiku should start with the word cat obligatory""";

    private final ChatClient chatClient;

    public PoetryServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @Override
    public String getCatHaiku() {
        return this.chatClient.
                prompt(WRITE_ME_HAIKU_ABOUT_CAT)
                .call()
                .content();
    }

    String promptString = """
            Write me {genre} poetry about {theme}""";

    PromptTemplate promptTemplate = new PromptTemplate(promptString);

    @Override
    public PoetryDto getPoetryByGenreAndTheme(String genre, String theme) {
        BeanOutputConverter<PoetryDto> poetryDtoBeanOutputConverter = new BeanOutputConverter<>(PoetryDto.class);
        Prompt prompt = promptTemplate.create(Map.of("genre", genre, "theme", theme, "format", poetryDtoBeanOutputConverter.getFormat()));

        return this.chatClient
                .prompt(prompt)
                .call()
                .entity(poetryDtoBeanOutputConverter);
    }
}
