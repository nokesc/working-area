package org.nebula.example;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.loader.FileLocator;

public class JinjavaTemplateService {

    private final File templatesDir;

    private final Jinjava jinjava = new Jinjava();

    public JinjavaTemplateService(File workingDir) {
        super();
        this.templatesDir = new File(workingDir, "templates/jinja");
        FileLocator fileLocator = Utils.Jinjava.fileLocator(templatesDir);
        jinjava.setResourceLocator(fileLocator);
    }

    public String process(FileGen fileGen) {
        Map<String, Object> model = fileGen.getModel();
        String template = Utils.readString(new File(templatesDir, fileGen.getTemplate() + ".sh").toPath());
        return jinjava.render(template, model);
    }

    public void process(FileGen fileGen, Writer out) {
        try {
            out.write(process(fileGen));
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static void main(String[] args) {
        File workingDir = Utils.WORKING_DIR;
        JinjavaTemplateService templateService = new JinjavaTemplateService(workingDir);
        File model = new File(workingDir, "project-gen.yml");
        ProjectGen projectGen = Utils.YAML_MAPPER.readValue(model, ProjectGen.class);
        System.out.println(Utils.YAML_MAPPER.writeValueAsString(projectGen));
        projectGen.getFileGen().forEach(fileGen -> System.out.println(templateService.process(fileGen)));
    }
}
