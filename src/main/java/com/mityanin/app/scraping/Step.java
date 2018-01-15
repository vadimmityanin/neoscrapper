package com.mityanin.app.scraping;

import com.mityanin.app.domain.Node;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Data
@Builder
@Slf4j
public class Step {
    private Step nextStep;
    private Function<Document, Elements> elementsPicker;
    private Function<Element, Node> scrapper;

    public List<Element> getElements(Node node) {
        Document document;
        Elements elements;
        try {
            log.info("Scrapping " + node.getUrl());
            document = Jsoup.parse(node.getUrl(), 10000);
            elements = elementsPicker.apply(document);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return elements == null ? Collections.emptyList() : elements;
    }

    public Node scrapeInfo(Element element) {
        return scrapper.apply(element);
    }

}
