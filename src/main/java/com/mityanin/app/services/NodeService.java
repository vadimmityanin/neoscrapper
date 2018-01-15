package com.mityanin.app.services;

import com.mityanin.app.domain.Node;

public interface NodeService {
    Iterable<Node> getNodeWithURL(String url);
}
