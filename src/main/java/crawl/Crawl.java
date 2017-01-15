/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawl;

import Config.ConfigService;
import controller.DBController;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Viet Bac
 */
public class Crawl extends Thread {

    @Override
    public void run() {
        try {
            Crawl();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CrawlUI cUI;

    public void Crawl() throws ClassNotFoundException, SQLException {
        try {
            DBController contr = new DBController();
            Document get = Jsoup.connect("http://123movies.is/movies/library").userAgent("Chrome").get();
            Element element = get.getElementsByAttributeValueMatching("class", "movies-letter").get(0);
//            System.out.println(element);
            Elements lstABC = element.getElementsByTag("a");
            for (Element element1 : lstABC) {
//                System.out.println(element1);
                String attr = element1.attr("href");
                Document get1 = Jsoup.connect(attr).userAgent("Chrome").get();
                Element getLastPage = get1.getElementById("pagination");
                Elements lstA = getLastPage.getElementsByTag("a");
                if (!lstA.isEmpty()) {
                    Element lastPage = lstA.get(lstA.size() - 1);
                    int numOfLast = Integer.parseInt(lastPage.attr("data-ci-pagination-page"));
                    String href = lastPage.attr("href");
                    for (int i = 1; i <= numOfLast; i++) {
                        href = href.substring(0, href.lastIndexOf("/") + 1);
                        href += i;
                        System.out.println("Dang crawl: " + href);
                        Document get2 = Jsoup.connect(href).userAgent("Chrome").get();
                        Elements lstMlNew = get2.getElementsByClass("mlnew");
                        for (Element element2 : lstMlNew) {
                            String link = element2.getElementsByTag("td").get(1).getElementsByTag("a").get(0).attr("href");
                            String name = element2.getElementsByTag("td").get(1).getElementsByTag("a").get(0).attr("title").trim();
                            String year = element2.getElementsByTag("td").get(3).html().trim();
                            String status = element2.getElementsByTag("td").get(4).html().trim();
                            String movieOrSerie = movieOrSerie(status);
                            if (movieOrSerie.equals("movie")) {
                                boolean existMovies = existMovies(name, year);
                                if (!existMovies) {
                                    cUI.appendText(name + " chua co" + "\n");
                                    contr.insertMovies(name, year, link, System.currentTimeMillis() + "", status,1);
                                } else {
                                    cUI.appendText(name + " da co" + "\n");
                                }
                            } else {
                                String title;
                                String season;
                                if (name.lastIndexOf(" - Season") != -1) {
                                    title = name.substring(0, name.lastIndexOf(" - Season"));
                                    season = name.substring(name.lastIndexOf(" - Season") + " - Season ".length(), name.length());
                                } else {
                                    title = name;
                                    season = "1";
                                }
                                if (!existSerie(title, year, season)) {
                                    cUI.appendText(name + " chua co - So tap: " + getQuanEp(status) + "\n");
                                } else {
                                    cUI.appendText(name + " da co - So tap: " + getQuanEp(status) + "\n");
                                }
                                contr.insertMovies(name, year, link, System.currentTimeMillis() + "", "serie",Integer.parseInt(season));
                                int quanEp = getQuanEp(status);
                                int seriId = contr.getMovieId(name, year);
                                for (int h = 1; h < quanEp; h++) {
                                    if (!existEps(title, year, season, h)) {
                                        cUI.appendText("Tap " + h + " chua co\n");
                                        contr.insertEps(seriId, h, link, System.currentTimeMillis() + "");
                                    } else {
                                        cUI.appendText("Tap " + h + " da co\n");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Elements lstMlNew = get1.getElementsByClass("mlnew");
                    for (Element element2 : lstMlNew) {
                        String link = element2.getElementsByTag("td").get(1).getElementsByTag("a").get(0).attr("href");
                        String title = element2.getElementsByTag("td").get(1).getElementsByTag("a").get(0).attr("title");
                        String year = element2.getElementsByTag("td").get(3).html();
                        String status = element2.getElementsByTag("td").get(4).html().trim();

                    }
                }
            }
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Crawl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String movieOrSerie(String s) {
        if (s.contains("Eps")) {
            return "serie";
        } else {
            return "movie";
        }
    }

    public int getQuanEp(String s) {
        String[] split = s.split(" ");
        String[] split1 = split[1].split("/");
        return Integer.parseInt(split1[0]);
    }

    public boolean existMovies(String title, String year) throws IOException, URISyntaxException {
        JsonReader reader = new JsonReader();
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(ConfigService.getInstance().get("apiURL"))
                .setPath("/api/auto/movie/" + title + "/" + year)
                .build();
        HttpGet httpget = new HttpGet(uri);
        String json = reader.readJsonFromUrl(httpget.getURI().toString());
        return (json.length() != 0);
    }

    public boolean existSerie(String title, String year, String season) throws IOException, URISyntaxException {
        JsonReader reader = new JsonReader();
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(ConfigService.getInstance().get("apiURL"))
                .setPath("/api/auto/movie/" + title + "/" + year + "/" + season)
                .build();
        HttpGet httpget = new HttpGet(uri);
        String json = reader.readJsonFromUrl(httpget.getURI().toString());
        return (json.length() != 0);
    }

    public boolean existEps(String title, String year, String season, int ep) throws URISyntaxException, IOException {
        JsonReader reader = new JsonReader();
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost(ConfigService.getInstance().get("apiURL"))
                .setPath("/api/auto/movie/" + title + "/" + year + "/" + season + "/" + ep)
                .build();
        HttpGet httpget = new HttpGet(uri);
        String json = reader.readJsonFromUrl(httpget.getURI().toString());
        return json.equals("1");
    }
}
