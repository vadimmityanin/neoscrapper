package com.mityanin.app.scraping;

import com.mityanin.app.domain.Node;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
@Builder
public class Site {

    private Step startingStep;

    private URL startingURL;

    private Node root;

    public Node getRoot() {
        if (root == null) {
            root = new Node();
            root.setUrl(startingURL);
        }
        return root;
    }
}
