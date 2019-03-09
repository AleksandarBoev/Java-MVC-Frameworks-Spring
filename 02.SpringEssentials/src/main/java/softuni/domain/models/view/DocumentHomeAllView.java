package softuni.domain.models.view;

public class DocumentHomeAllView {
    private String id;
    private String trimmedTitle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrimmedTitle() {
        return this.trimmedTitle;
    }

    public void setTrimmedTitle(String title) {
        if (title.length() > 12)
            title = title.substring(0, 13) + "...";

        this.trimmedTitle = title;
    }
}
