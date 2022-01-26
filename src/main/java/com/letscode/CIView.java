package com.letscode;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface CIView {

    void startAPP();
    void joinAlliance();
    void generateReport() throws FileNotFoundException, UnsupportedEncodingException;
}
