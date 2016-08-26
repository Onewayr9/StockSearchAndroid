package oneway.stocksearch;

/**
 * Created by Oneway on 16/4/8.
 */
public class DetailItems {
    private String title;
    private String description;

    public DetailItems(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
