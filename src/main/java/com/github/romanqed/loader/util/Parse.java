package com.github.romanqed.loader.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Parse {
    public static Map<String, String> parseSchedules(Document source) {
        Map<String, String> ret = new HashMap<>();
        Elements found = source.select("div.panel div.row div.btn-group a");
        for (Element element : found) {
            ret.put(element.text().toLowerCase(Locale.ROOT), element.attr("href"));
        }
        return ret;
    }
}
