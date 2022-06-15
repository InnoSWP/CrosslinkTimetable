package com.timetable.outlook;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:personal.properties")
public class MailingListManager {
    private ExchangeService service;
    private ExchangeCredentials credentials;

    public MailingListManager(
            @Value("${personal.email}") String personalEmail,
            @Value("${personal.password}") String personalPassword) {
        service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(personalEmail, personalPassword);
        service.setCredentials(credentials);
        try {
            service.autodiscoverUrl(
                    personalEmail, new MailingListManager.RedirectionUrlCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
        public boolean autodiscoverRedirectionUrlValidationCallback(
                String redirectionUrl) {
            return redirectionUrl.toLowerCase().startsWith("https://");
        }
    }
}
