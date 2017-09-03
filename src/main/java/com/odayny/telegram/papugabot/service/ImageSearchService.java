package com.odayny.telegram.papugabot.service;

import java.io.IOException;
import java.util.List;

public interface ImageSearchService {
    List<String> search(String query) throws IOException;
}
