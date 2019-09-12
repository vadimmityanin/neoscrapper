package com.mityanin.app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mityanin.app.converters.URLConverter;
import com.mityanin.app.scraping.RelTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NodeEntity
@EqualsAndHashCode(of = {"url", "data"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {

    public static Node newWithUrl(URL url) {
        Node node = new Node();
        node.setUrl(url);
        return node;
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Convert(URLConverter.class)
    private URL url;

    private Set<String> data = new HashSet<>();


    @JsonBackReference
    @Relationship(type = RelTypes.LEAD_TO, direction = Relationship.INCOMING)
    private Node ancestor;

    @JsonManagedReference
    @Relationship(type = RelTypes.LEAD_TO)
    private List<Node> descendants;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + "\"" + id + "\"" +
                ", \"url\":" + "\"" + url + "\"" +
                ", \"data\":" + "\"" + data + "\"" +
                ", \"ancestor\":" + "\"" + (ancestor == null ? "null" : ancestor.id) + "\"" +
                ", \"descendants\":" + "\"" + descendants + "\"" +
                '}';
    }
}
