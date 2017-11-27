/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ucuenca.cepraXI.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ucuenca
 */
public class utilities {

    public final List<String> stopwords = new ArrayList<String>();

    /**
     * Constructor para leer el archivo de stopwords y cargarlas a memoria en
     * una lista.
     */
    public utilities() {
        try {
            URLConnection conn = new URL("http://172.17.0.2:8000/stopwords.txt").openConnection();
            InputStream in = conn.getInputStream();
            readStream(in);
        } catch (IOException ex) {
            Logger.getLogger(utilities.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readStream(InputStream in) throws IOException {
        BufferedReader r = null;
        r = new BufferedReader(new InputStreamReader(in));
        String stopword;
        while ((stopword = r.readLine()) != null) {
            stopwords.add(stopword);
        }
        if (r != null) {
            r.close();
        }
        in.close();
    }

    /*
    Esta funcion elimina los encabezados (palabras)
    @return: Matrix numerica en formato JSON
     */
    public JSONObject matrixToJson(List<List<String>> corpusmatrix) {
        JSONObject json = new JSONObject();
        int doc = 0;

        for (List<String> documentselement : corpusmatrix) {
            if (doc != 0) {//se omite la primera fila de corpusmatrix (encabezados -> palabras )
                JSONArray array = new JSONArray();
                for (String term : documentselement) {
                    array.put(term);
                }
                json.put(String.valueOf(doc), array);
            }
            doc++;
        }
        return json;
    }

    public void printCorpusMatrix(List<List<String>> corpusmatrix) {
        for (List<String> documentselement : corpusmatrix) {
            for (String element : documentselement) {
                System.out.print(element);
                System.out.print(" - ");
            }
            System.out.println("\n");
        }
    }

    public List<String> splitText(String text) {
        List<String> document = new ArrayList<String>();
        String[] words = text.split(" ");
        for (String word : words) {
            if (!stopWord(word)) {
                document.add(word);
            }
        }
        return document;
    }

    public boolean stopWord(String word) {
        for (String stopword : stopwords) {
            if (stopword.compareTo(word) == 0) {
                return true;
            }
        }
        return false;
    }
}
