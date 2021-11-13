package org.nebula.example;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileGen {
    private String template;
    private boolean overwrite;
    private Map<String, Object> model;

    public String sayHi() {
        return "Hi from " + template;
    }

    public String getTemplate() {
        return template;
    }
}
