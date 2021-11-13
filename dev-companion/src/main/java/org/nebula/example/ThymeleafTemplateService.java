package org.nebula.example;

import java.io.File;
import java.io.Writer;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.util.FastStringWriter;

public class ThymeleafTemplateService {

    private final File templatesDir;
    private final TemplateEngine templateEngine;
    private final String encoding = "UTF8";

    public ThymeleafTemplateService(File workingDir) {
        super();
        this.templatesDir = new File(workingDir, "templates/thymeleaf");
        FileTemplateResolver bashTemplateResolver = new FileTemplateResolver();
        bashTemplateResolver.setPrefix(templatesDir.getAbsolutePath() + File.separatorChar);
        bashTemplateResolver.setCharacterEncoding(encoding);
        bashTemplateResolver.setTemplateMode(TemplateMode.TEXT);
        bashTemplateResolver.setSuffix(".sh");
        bashTemplateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        bashTemplateResolver.setCacheable(false); // TODO Configure for dev mode

        this.templateEngine = new TemplateEngine();
        this.templateEngine.addTemplateResolver(bashTemplateResolver);
        // this.templateEngine.clearTemplateCache();
    }

    public void process(FileGen fileGen, Writer writer) {
        Context context = new Context();
        Map<String, Object> model = fileGen.getModel();
        context.setVariables(model);
        templateEngine.process(fileGen.getTemplate(), context, writer);
    }

    public String process(FileGen fileGen) {
        FastStringWriter writer = new FastStringWriter(128);
        process(fileGen, writer);
        return writer.toString();
    }

    public static void main(String[] args) {
        File workingDir = Utils.WORKING_DIR;
        ThymeleafTemplateService templateService = new ThymeleafTemplateService(workingDir);
        ProjectGen projectGen = Utils.YAML_MAPPER.readValue(new File(workingDir, "project-gen.yml"), ProjectGen.class);
        projectGen.getFileGen().forEach(fileGen -> System.out.println(templateService.process(fileGen)));
    }
}
