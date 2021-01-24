public class AnnotationDTO {
    private String type;
    private String name;
    private String placeholder;

    public AnnotationDTO(HtmlInput htmlInput) {
        type = htmlInput.type();
        name = htmlInput.name();
        placeholder = htmlInput.placeholder();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getPlaceholder() {
        return placeholder;
    }
}
