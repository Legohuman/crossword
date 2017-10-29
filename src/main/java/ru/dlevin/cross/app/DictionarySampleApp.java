package ru.dlevin.cross.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.impl.word.WordImpl;
import ru.dlevin.cross.impl.word.WordPatternImpl;
import ru.dlevin.cross.impl.word.dict.WordDictionaryImpl;

import java.util.Iterator;

public class DictionarySampleApp {
    private static final Logger log = LoggerFactory.getLogger(CrosswordCreatorApp.class);

    public static void main(String[] args) {
        WordDictionaryImpl dictionary = new WordDictionaryImpl();

        dictionary.addWord(new WordImpl("батон"));
        dictionary.addWord(new WordImpl("загон"));

        dictionary.addWord(new WordImpl("молоко"));
        dictionary.addWord(new WordImpl("погоня"));
        dictionary.addWord(new WordImpl("решето"));

        Iterator<Word> iterator = dictionary.search(new WordPatternImpl("*а***"));
        while (iterator.hasNext()) {
            log.info(iterator.next().getText());
        }
    }
}
