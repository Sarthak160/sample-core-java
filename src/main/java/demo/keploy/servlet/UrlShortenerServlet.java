package demo.keploy.servlet;

import demo.keploy.Util;
import demo.keploy.pojo.UrlShortenerRequest;
import demo.keploy.pojo.UrlShortenerResponse;
import demo.keploy.service.UrlShortenerService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class UrlShortenerServlet extends HttpServlet {

    private final UrlShortenerService urlShortenerService = new UrlShortenerService();

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
//            .addInterceptor(new io.keploy.httpClients.OkHttpInterceptor_Kotlin())
            .build();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String shortUrl = req.getRequestURI().split("/")[2];


        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        Request request = new Request.Builder()
                .url("https://reqres.in/api/users/2")
                .header("User-Agent", "A Baeldung Reader")
                .build();
//
        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//        } catch (IOException e) {
//            System.out.println("ERROR: From Get Handler");
//            throw new RuntimeException(e);
//        }
//        System.out.println("Response from okhttp(Kotlin) client " + response);
//
        resp.addHeader("core", "java");
        try {
            resp.sendRedirect(urlShortenerService.getUrl(shortUrl));
        } catch (Exception e) {
            resp.sendError(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String strResp = "";
        try {
            UrlShortenerRequest urlShortenerRequest = Util.inputStreamToObject(req.getInputStream(), UrlShortenerRequest.class);
            UrlShortenerResponse response = urlShortenerService.shortenUrl(urlShortenerRequest);
            strResp = Util.toJsonString(response);

        } catch (Exception e) {
            strResp = e.getMessage();
            resp.sendError(400);
        }
        try (PrintWriter p = new PrintWriter(resp.getOutputStream())) {
            p.println(strResp);
        }
    }
}
