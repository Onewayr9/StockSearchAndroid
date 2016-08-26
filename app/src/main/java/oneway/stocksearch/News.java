package oneway.stocksearch;

/**
 * Created by Oneway on 16/4/10.
 */
public class News {
    private String title;
    private String url;
    private String description;
    private String publisher;
    private String date;

    public News(String title,String url,String description,String publisher,String date){
        this.title = title;
        this.url = url;
        this.description = description;
        this.publisher = publisher;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
