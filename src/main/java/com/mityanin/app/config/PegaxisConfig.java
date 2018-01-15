package com.mityanin.app.config;

import com.mityanin.app.scraping.Site;
import com.mityanin.app.scraping.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mityanin.app.scraping.ElementsPickers.applySelector;
import static com.mityanin.app.scraping.ElementsPickers.byElementClass;
import static com.mityanin.app.scraping.Scrappers.attachGoogleSearchResult;
import static com.mityanin.app.scraping.Scrappers.defaultScrapper;
import static org.apache.commons.configuration.PropertyConverter.toURL;

@Configuration
public class PegaxisConfig {
    @Bean
    public Site pegaxisSite() {
        Step step = Step.builder()
                .elementsPicker(byElementClass("service_list_title"))
                .scrapper(defaultScrapper("href", false, "title")
                ).build();
        Step step2 = Step.builder()
                .elementsPicker(byElementClass("company-name").andThen(applySelector(">a")))
                .scrapper(defaultScrapper(null, true)
                        .andThen(attachGoogleSearchResult(2, "text"))
                ).build();

        step.setNextStep(step2);
        return Site.builder().startingStep(step).startingURL(toURL("https://www.pegaxis.com/services")).build();
    }
}
