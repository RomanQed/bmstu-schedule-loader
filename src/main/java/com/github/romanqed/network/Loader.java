package com.github.romanqed.network;

import java.io.IOException;
import java.net.URL;

public interface Loader {
    String load(URL url) throws IOException;
}
