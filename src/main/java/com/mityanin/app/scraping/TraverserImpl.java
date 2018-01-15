package com.mityanin.app.scraping;

import com.mityanin.app.domain.Node;
import com.mityanin.app.repository.NodeRepo;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Component
public class TraverserImpl implements Traverser {

    @Autowired
    private List<Site> configs;
    @Autowired
    private NodeRepo repo;


    @Override
    public List<Node> traverse(Step step, Node parentNode) {
        List<Element> elements = step.getElements(parentNode);
        List<Node> descendants = elements.stream().map(step::scrapeInfo).collect(toList());

        descendants.forEach(descendant -> descendant.setAncestor(parentNode));
        parentNode.setDescendants(descendants);
        return descendants;
    }

    @Override
    public Node buildGraph(Site site) {
        Step step = site.getStartingStep();
        Node root = site.getRoot();
        for (List<Node> currentNodes = newArrayList(root); step != null; step = step.getNextStep()) {
            currentNodes = traverse(step, currentNodes);
        }
        return repo.save(root);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void buildGraph() {
        configs.stream().filter(cfg -> repo.existsByUrl(cfg.getStartingURL()) == null).forEach(this::buildGraph);
    }
}
