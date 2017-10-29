package ru.dlevin.cross.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.api.word.WordDictionary;
import ru.dlevin.cross.impl.word.WordImpl;
import ru.dlevin.cross.impl.word.WordPatternImpl;
import ru.dlevin.cross.impl.word.dict.WordDictionaryImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Legohuman
 * Date: 18/10/15
 */
public class DictIndexerApp {

    private static final Logger log = LoggerFactory.getLogger(DictIndexerApp.class);

    public static void main(String[] args) throws IOException {
        readDictWithDirectSearch();
//        readDictWithIndexedSearch();
    }

    private static void readDictWithIndexedSearch() throws IOException {
        log.info("Starting");
        long s = System.currentTimeMillis();
        WordDictionary dictionary = new WordDictionaryImpl();
        BufferedReader reader = new BufferedReader(new InputStreamReader(DictIndexerApp.class.getClassLoader().getResourceAsStream("dictionary.txt"), Charset.forName("UTF-8")));
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


        String pattern = "моло**";
        search(dictionary, pattern);

        s = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            search(dictionary, pattern);
        }
        f = System.currentTimeMillis();
        log.info("Finished, search avg in " + (f - s) / 100 + "ms");
    }

    private static void search(WordDictionary dictionary, String pattern) {
        Iterator<ru.dlevin.cross.api.word.Word> iterator = dictionary.search(new WordPatternImpl(pattern));
//        System.out.println("Result for search " + pattern);
        while (iterator.hasNext()) {
            iterator.next();
//            System.out.println(iterator.next().getText());
        }
    }

    private static void readDictWithDirectSearch() throws IOException {
        log.info("Starting");
        long s = System.currentTimeMillis();
        List<String> dictionary = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(DictIndexerApp.class.getClassLoader().getResourceAsStream("dictionary.txt"), Charset.forName("UTF-8")));

        String word;
        int wordsRead = 0;
        while ((word = reader.readLine()) != null) {
            try {
                dictionary.add(word);
                wordsRead++;
            } catch (IllegalArgumentException e) {
                //ignore
            }
        }
        long f = System.currentTimeMillis();
        log.info("Finished, words read: " + wordsRead + ", time: " + (f - s) + "ms");


        directSearch(dictionary);

        s = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            directSearch(dictionary);
        }
        f = System.currentTimeMillis();
        log.info("Finished, search avg in " + (f - s) / 100 + "ms");
    }

    private static void directSearch(List<String> dictionary) {
        for (String word : dictionary) {
            String currentWord = null;
            if (word.length() == 6 && word.charAt(0) == 'м' && word.charAt(1) == 'о' && word.charAt(2) == 'л' && word.charAt(1) == 'о') {
                currentWord = word;
            }
        }
    }
}
