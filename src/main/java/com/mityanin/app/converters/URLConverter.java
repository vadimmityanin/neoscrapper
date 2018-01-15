package com.mityanin.app.converters;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.net.URL;

import static org.apache.commons.configuration.PropertyConverter.toURL;

public class URLConverter implements AttributeConverter<URL, String> {
    @Override
    public String toGraphProperty(URL value) {
        return value == null ? null : value.toString();
    }

    @Override
    public URL toEntityAttribute(String value) {
        return value == null ? null : toURL(value);
    }
}
