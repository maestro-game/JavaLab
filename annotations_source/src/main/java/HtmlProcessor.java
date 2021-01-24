import com.google.auto.service.AutoService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        for (Element element : annotatedElements) {
            Map<String, Object> root = new HashMap<>();
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
            try {
                cfg.setDirectoryForTemplateLoading(Paths.get(path).toFile());
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
            path = path + element.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);
            HtmlForm annotation = element.getAnnotation(HtmlForm.class);
            root.put("action", annotation.action());
            root.put("method", annotation.method());
            List<AnnotationDTO> fields = new LinkedList<>();
            for (Element field : element.getEnclosedElements()) {
                HtmlInput input;
                if ((input = field.getAnnotation(HtmlInput.class)) != null){
                    fields.add(new AnnotationDTO(input));
                }
            }
            root.put("fields", fields);
            try {
                Files.deleteIfExists(out);
                cfg.getTemplate("form.ftlh").process(root, new BufferedWriter(new FileWriter(out.toFile())));
            } catch (TemplateException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
        return true;
    }
}
