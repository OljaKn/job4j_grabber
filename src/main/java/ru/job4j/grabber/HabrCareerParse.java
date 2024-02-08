package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";
    private final DateTimeParser dateTimeParser;


    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public static void main(String[] args) throws IOException {
        HabrCareerParse habrCareerParse = new HabrCareerParse(new HabrCareerDateTimeParser());
        System.out.println(habrCareerParse.list(String.format("%s/vacancies?page=", SOURCE_LINK)));
    }

    private String retrieveDescription(String link) throws IOException {
            Document document = Jsoup.connect(link).get();
            Element descriptionElement = document.select(".vacancy-description__text").first();
            return descriptionElement.text();
        }

        private Post createPosts(Element element) throws IOException {
            Element titleElement = element.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            Element dataElement = element.select(".vacancy-card__date").first();
            String title = titleElement.text();
            String linkPost = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
            String description = retrieveDescription(linkPost);
            LocalDateTime created = dateTimeParser.parse(dataElement.attr("datetime"));
            return new Post(title, linkPost, description, created);
        }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Document document = Jsoup.connect(String.format("%s%s", SOURCE_LINK, PREFIX + i)).get();
            Elements rows = document.select(".vacancy-card__inner");
            for (Element row : rows) {
                Post post = createPosts(row);
                posts.add(post);
            }
        }
        return posts;
    }
}