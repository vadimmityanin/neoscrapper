package com.mityanin.app.scraping;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.function.Function;

public final class ElementsPickers {
    public static Function<Document, Elements> byElementClass(String elementClass) {
        return doc -> doc.getElementsByClass(elementClass);
    }

    public static Function<Document, Elements> byAttributeValue(String attribute, String value) {
        return doc -> doc.getElementsByAttributeValue(attribute, value);
    }

    public static Function<Elements, Elements> applySelector(String selector) {
        return elements -> elements.select(selector);
    }
}
