package com.mityanin.app.services;

import com.mityanin.app.domain.Node;
import com.mityanin.app.repository.NodeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.configuration.PropertyConverter.toURL;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepo repo;

    @Override
    public Iterable<Node> getNodeWithURL(String url) {
        return repo.findByUrl(toURL(url));
    }
}
