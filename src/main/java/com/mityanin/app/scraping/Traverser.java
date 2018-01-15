package com.mityanin.app.scraping;


import com.mityanin.app.domain.Node;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface Traverser {

    default List<Node> traverse(Step step, List<Node> nodeList) {
        return nodeList.parallelStream().flatMap(node -> traverse(step, node).stream()).collect(toList());
    }

    List<Node> traverse(Step step, Node parentNode);

    Node buildGraph(Site site);

}
