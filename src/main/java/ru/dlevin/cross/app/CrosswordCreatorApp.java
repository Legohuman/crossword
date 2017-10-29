package ru.dlevin.cross.app;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.word.ReadOnlyWordDictionary;
import ru.dlevin.cross.api.word.WordDictionary;
import ru.dlevin.cross.impl.CrosswordCreationContextImpl;
import ru.dlevin.cross.impl.CrosswordCreatorImpl;
import ru.dlevin.cross.impl.EmptyCrosswordCreationListener;
import ru.dlevin.cross.impl.board.CrosswordBoardBuilderImpl;
import ru.dlevin.cross.impl.word.WordImpl;
import ru.dlevin.cross.impl.word.dict.WordDictionaryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CrosswordCreatorApp {

    private static final Logger log = LoggerFactory.getLogger(CrosswordCreatorApp.class);

    public static void main(String[] args) {
        CrosswordCreatorImpl creator = new CrosswordCreatorImpl();
        CrosswordBoard board = new CrosswordBoardBuilderImpl()
                .addHorizontalContainer(0, 0, 6)
                .addHorizontalContainer(5, 0, 6)
                .addVerticalContainer(0, 0, 6)
                .addVerticalContainer(0, 5, 6)
                .build();

        CrosswordCreationContextImpl context = new CrosswordCreationContextImpl(board, readDictWithIndexedSearch());
        creator.create(context, new EmptyCrosswordCreationListener());
    }

    @NotNull
    private static ReadOnlyWordDictionary readDictWithIndexedSearch() {
        log.info("Starting");
        long s = System.currentTimeMillis();
        WordDictionary dictionary = new WordDictionaryImpl();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(DictIndexerApp.class.getClassLoader().getResourceAsStream("dictionary.txt"), Charset.forName("UTF-8")))) {
            String word;
            int wordsRead = 0;
            while ((word = reader.readLine()) != null) {
                try {
                    dictionary.addWord(new WordImpl(word));
                    wordsRead++;
                } catch (IllegalArgumentException e) {
                    //ignore
                }
            }
            long f = System.currentTimeMillis();
            log.info("Finished, words read: " + wordsRead + ", time: " + (f - s) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
