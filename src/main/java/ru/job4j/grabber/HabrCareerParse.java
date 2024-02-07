package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";
    public static final String PREFIX = "/vacancies?page=";
    public static final String SUFFIX = "&q=Java%20developer&type=all";

    public static void main(String[] args) throws IOException {
        for (int pageNumber = 1; pageNumber <= 5; pageNumber++) {
            String fullLink = "%s%s%d%s".formatted(SOURCE_LINK, PREFIX, pageNumber, SUFFIX);
            Connection connection = Jsoup.connect(fullLink);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                Element dataElement = row.select(".vacancy-card__date").first();
                String vacancyName = titleElement.text();
                String dataVacancy = dataElement.text();
                String link = String.format("%s%s %s", SOURCE_LINK, linkElement.attr("href"), dataElement.attr("datetime"));
                System.out.printf("%s %s %s%n", vacancyName, link, dataVacancy);
            });
        }
        /*HabrCareerParse h = new HabrCareerParse();
        String str = h.retrieveDescription("https://career.habr.com/vacancies/1000137127");
        System.out.println(str);*/
    }

    private String retrieveDescription(String link) throws IOException {
            Document document = Jsoup.connect(link).get();
            Element descriptionElement = document.select(".vacancy-description__text").first();
            return descriptionElement.text();
        }
    }