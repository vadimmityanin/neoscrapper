package com.mityanin.app.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleSearcher {
    private static String ERROR_MESSAGE = "Error occurred during googling: ";

    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    public static List<String> search(String term, int number) {

        String searchURL = GOOGLE_SEARCH_URL + "?q=" + term + "&num=" + number;
        Document doc;
        try {
            log.info("Googling: " + searchURL);
            doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        } catch (IOException e) {
            log.error(e.getMessage());
            return Collections.singletonList(ERROR_MESSAGE + searchURL);
        }

        return doc.select("h3.r > a").stream().map(res -> {
            String link = res.attr("href");
            return "Text:" + res.text() + ", URL:" + link.substring(7, link.indexOf('&'));
        }).collect(toList());
    }
}

