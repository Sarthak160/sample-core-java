package demo.keploy.service;

import demo.keploy.Util;
import demo.keploy.db.DbUtil;
import demo.keploy.pojo.UrlShortenerRequest;
import demo.keploy.pojo.UrlShortenerResponse;
import io.keploy.servlet.KeployMiddleware;

// as this is a sample app....not gonna add much validations
public class UrlShortenerService {

    public UrlShortenerResponse shortenUrl(UrlShortenerRequest request) throws Exception {
        String shortUrl = Util.getAlphaNumericString(5);
        DbUtil.addShortenUrl(request.getUrl(), shortUrl);
        return new UrlShortenerResponse(shortUrl);
    }

    public String getUrl(String shortUrl) throws Exception {
        return DbUtil.getUrl(shortUrl);
    }
}
