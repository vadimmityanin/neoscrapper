package com.mityanin.app.repository;

import com.mityanin.app.domain.Node;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.net.URL;

public interface NodeRepo extends Neo4jRepository<Node,Long> {

    @Depth(3)
    Iterable<Node> findByUrl(URL url);

    Node existsByUrl(URL url);
}
