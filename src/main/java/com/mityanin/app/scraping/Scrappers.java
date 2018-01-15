package com.mityanin.app.scraping;

import com.mityanin.app.domain.Node;
import com.mityanin.app.util.GoogleSearcher;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.String.format;
import static org.apache.commons.configuration.PropertyConverter.toURL;

public final class Scrappers {

    private static final String DELIMITER = ":";
    private static final String TEXT = "text";
    private static final String GOOGLE_RESULT = "GoogleSearchResult_";

    public static Function<Element, Node> defaultScrapper(String urlAttribute, boolean scrapText, String... attributes) {
        return element -> {
            URL nodeURL = null;
            if (urlAttribute != null) {
                URL url = toURL(element.baseUri());
                nodeURL = toURL(format("%s://%s%s", url.getProtocol(), url.getAuthority(), element.attr(urlAttribute)));
            }
            Node node = Node.newWithUrl(nodeURL);
            Arrays.stream(attributes).forEach(attribute -> node.getData().add(attribute + DELIMITER + element.attr(attribute)));
            if (scrapText) {
                node.getData().add(TEXT + DELIMITER + element.text());
            }
            return node;
        };
    }

    public static Function<Node, Node> attachGoogleSearchResult(int nuberOfFirstResults, String dataFieldOfNode) {
        return node -> {
            Optional<String> term = node.getData().stream()
                    .filter(data -> data.startsWith(dataFieldOfNode))
                    .findFirst()
                    .map(str -> str.split(DELIMITER, 2)[1]);

            if (term.isPresent()) {
                List<String> search = GoogleSearcher.search(term.get(), nuberOfFirstResults);
                for (int i = 0; i < search.size(); i++) {
                    node.getData().add(GOOGLE_RESULT + (i + 1) + DELIMITER + search.get(i));
                }
            }
            return node;
        };
    }
}