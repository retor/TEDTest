package ted.loader;

/**
 * Created by retor on 05.05.2015.
 */
public class TedNews {
    String header;
    String videoURL;
    String description;
    String name;
    String date;

    public TedNews() {
    }

    public TedNews(String date, String description, String header, String name, String videoURL) {
        this.date = date;
        this.description = description;
        this.header = header;
        this.name = name;
        this.videoURL = videoURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
