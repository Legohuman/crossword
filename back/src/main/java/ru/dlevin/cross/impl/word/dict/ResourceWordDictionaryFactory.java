package ru.dlevin.cross.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.api.word.dict.WordDictionary;
import ru.dlevin.cross.api.word.dict.WordDictionaryFactory;
import ru.dlevin.cross.impl.word.WordImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Supplier;

public class ResourceWordDictionaryFactory implements WordDictionaryFactory {
    private static final Logger log = LoggerFactory.getLogger(ResourceWordDictionaryFactory.class);
    @NotNull
    private final Supplier<InputStream> inSupplier;
    @NotNull
    private final Charset charset;

    public ResourceWordDictionaryFactory(@NotNull Supplier<InputStream> inSupplier, @NotNull Charset charset) {
        this.inSupplier = inSupplier;
        this.charset = charset;
    }

    @NotNull
    @Override
    public WordDictionary create() {
        WordDictionary dictionary = new WordDictionaryImpl();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inSupplier.get(), charset))) {
            String word;
            while ((word = reader.readLine()) != null) {
                try {
                    dictionary.addWord(new WordImpl(word));
                } catch (IllegalArgumentException e) {
                    log.debug("Word " + word + " was not added to dictionary", e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to create dictionary", e);
        }
        return dictionary;
    }
}
