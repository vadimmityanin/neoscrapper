package com.mityanin.app.controllers;

import com.mityanin.app.domain.Node;
import com.mityanin.app.services.NodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DataController {

    @Autowired
    private NodeService service;

    @RequestMapping("/data")
    public Iterable<Node> getData(@RequestParam String url){
        Iterable<Node> nodeWithURL = service.getNodeWithURL(url);
        log.debug("Response:" + nodeWithURL);
        return nodeWithURL;
    }
}
