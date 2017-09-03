package com.odayny.telegram.papugabot.service;

import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;
import com.odayny.telegram.papugabot.model.GoogleApiKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageSearchServiceImpl implements ImageSearchService {
    private Customsearch customsearch;
    private GoogleApiKeys googleApiKeys;

    @Autowired
    public ImageSearchServiceImpl(Customsearch customsearch, GoogleApiKeys googleApiKeys) {
        this.customsearch = customsearch;
        this.googleApiKeys = googleApiKeys;
    }

    @Override
    public List<String> search(String query) throws IOException {
        Customsearch.Cse.List list = customsearch.cse().list(query);
        list.setKey(googleApiKeys.getKey());
        list.setCx(googleApiKeys.getCx());
        list.setSearchType("image");
        Search result = list.execute();
        return result.getItems().stream().map(Result::getLink).collect(Collectors.toList());
    }
}
