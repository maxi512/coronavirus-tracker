package com.example.tracker.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CSVGetter {
    public static String getCSV() throws IOException, InterruptedException {
        String CSV_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CSV_URL))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
