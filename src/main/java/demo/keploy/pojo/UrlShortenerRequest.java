package demo.keploy.pojo;

public class UrlShortenerRequest {
    private String url;

    public UrlShortenerRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
