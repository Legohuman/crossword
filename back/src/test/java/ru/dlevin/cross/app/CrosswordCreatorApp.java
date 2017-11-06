package ru.dlevin.cross.app;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.engine.api.board.CrosswordBoard;
import ru.dlevin.cross.engine.api.word.dict.ReadOnlyWordDictionary;
import ru.dlevin.cross.engine.api.word.dict.WordDictionary;
import ru.dlevin.cross.engine.impl.CrosswordCreationContextImpl;
import ru.dlevin.cross.engine.impl.CrosswordCreatorImpl;
import ru.dlevin.cross.engine.impl.EmptyCrosswordCreationListener;
import ru.dlevin.cross.engine.impl.board.CrosswordBoardBuilderImpl;
import ru.dlevin.cross.engine.impl.word.dict.ResourceWordDictionaryFactory;
import ru.dlevin.cross.utils.StreamUtils;

import java.nio.charset.Charset;

public class CrosswordCreatorApp {

    private static final Logger log = LoggerFactory.getLogger(CrosswordCreatorApp.class);

    public static void main(String[] args) {
        CrosswordCreatorImpl creator = new CrosswordCreatorImpl();
        CrosswordBoard board = new CrosswordBoardBuilderImpl()
                .addHorizontalContainer(0, 0, 6)
                .addHorizontalContainer(0, 2, 6)
                .addHorizontalContainer(0, 5, 6)
                .addVerticalContainer(0, 0, 6)
                .addVerticalContainer(4, 0, 6)
                .build();

        CrosswordCreationContextImpl context = new CrosswordCreationContextImpl(board, readDictWithIndexedSearch());
        creator.create(context, new EmptyCrosswordCreationListener());
    }

    @NotNull
    private static ReadOnlyWordDictionary readDictWithIndexedSearch() {
        log.info("Starting");
        long s = System.currentTimeMillis();
        ResourceWordDictionaryFactory factory = new ResourceWordDictionaryFactory(() -> StreamUtils.getResourceStream("dictionary.txt"), Charset.forName("UTF-8"));
        WordDictionary dictionary = factory.create();
        long f = System.currentTimeMillis();
        log.info("Finished, time: " + (f - s) + "ms");

        return dictionary;
    }
}
