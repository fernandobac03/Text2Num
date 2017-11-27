/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ucuenca.cepraXI.application;

import ec.edu.ucuenca.cepraXI.utilities.utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ucuenca
 */
public class text2Numeric {

    utilities util = new utilities();

    public String getNumericMatrix(String jsondocuments, String method) {
        JSONObject json = new JSONObject(jsondocuments);
        JSONArray jsonArray = json.getJSONArray("text");

        List<List<String>> documentsAsList = new ArrayList<List<String>>();
        for (int i = 0; i < jsonArray.length(); i++) {
            documentsAsList.add(util.splitText(jsonArray.get(i).toString()));
        }

        return text2NumericMain(documentsAsList, method);
    }

    public String text2NumericMain(List<List<String>> documents, String method) {
        List<List<String>> corpusmatrix = new ArrayList<List<String>>();
        //agregando primera palabra del primer documento al corpus
        for (int i = 0; i < documents.size() + 1; i++) {
            corpusmatrix.add(new ArrayList<String>());
        }

        for (List<String> document : documents) {
            for (String word : document) {
                boolean wordexistincorpusmatrix = false;
                for (String corpusword : corpusmatrix.get(0)) {
                    if (word.equalsIgnoreCase(corpusword)) {
                        wordexistincorpusmatrix = true;
                        break;
                    }
                }
                //agregando la palabra al corpus matriz si aun no existe
                if (!wordexistincorpusmatrix) {
                    corpusmatrix.get(0).add(word);
                }
            }
        }

        int indexdocument = 1;
        for (List<String> document : documents) {
            for (int cw = 0; cw < corpusmatrix.get(0).size(); cw++) {
                corpusmatrix.get(indexdocument).add("0");
            }
            indexdocument++;
        }

        util.printCorpusMatrix(corpusmatrix);

        indexdocument = 1;
        for (List<String> document : documents) {
            for (String word : document) {
                for (int cw = 0; cw < corpusmatrix.get(0).size(); cw++) {
                    String corpusword = corpusmatrix.get(0).get(cw);

                    //Lo siguiente crea la matriz con valores TF-IDF
                    if (word.equalsIgnoreCase(corpusword)) {
                        if (method.compareTo("tfidf") == 0) {
                            tfidf calculator = new tfidf();
                            corpusmatrix.get(indexdocument).set(cw, String.valueOf(calculator.tfIdf(document, documents, word)));
                            break;
                        }
                        if (method.compareTo("binary") == 0) {
                            corpusmatrix.get(indexdocument).set(cw, "1");
                            break;
                        }
                        if (method.compareTo("frequency") == 0) {
                            int frequency = Integer.parseInt(corpusmatrix.get(indexdocument).get(cw)) + 1;
                            corpusmatrix.get(indexdocument).set(cw, String.valueOf(frequency));
                            break;
                        }
                    }
                }
            }
            indexdocument++;
        }
        util.printCorpusMatrix(corpusmatrix);
        return util.matrixToJson(corpusmatrix).toString();
    }

  

}
