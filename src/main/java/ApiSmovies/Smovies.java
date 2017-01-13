package ApiSmovies;

import Config.ConfigService;
import crawl.JsonReader;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by super on 1/13/2017.
 */
public class Smovies {
    private static Smovies ourInstance = new Smovies();

    public static Smovies getInstance() {
        return ourInstance;
    }

    private Smovies() {
    }

    public boolean createEpisode(String movieName,String season ,String episode,String googleid) throws URISyntaxException, IOException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(ConfigService.getInstance().get("apiURL"))
                .setPath("/api/auto/upload/episode")
                .setParameter("movie_name", movieName)
                .setParameter("season", season)
                .setParameter("episode",episode)
                .setParameter("googleid", googleid)
                .build();
        HttpGet httpget = new HttpGet(uri);
        new JsonReader().readJsonFromUrl(httpget.getURI().toString());
        return true;
    }

    public boolean createMovie(String movieName,String year,String googleid,String quality) throws URISyntaxException, IOException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(ConfigService.getInstance().get("apiURL"))
                .setPath("/api/auto/upload/movie")
                .setParameter("movie_name", movieName)
                .setParameter("year", year)
                .setParameter("googleid", googleid)
                .setParameter("quality", quality)
                .build();
        HttpGet httpget = new HttpGet(uri);
        System.out.println(httpget);
        System.out.println(new JsonReader().readJsonFromUrl(httpget.getURI().toString()));
        return true;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        boolean movie = Smovies.getInstance().createMovie("passengers", "2016", "dfsdfdsfds","HD");
        System.out.println(movie);
    }
}
