/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ucuenca.cepraXI.testPost;

import ec.edu.ucuenca.cepraXI.utilities.utilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
/**
 *
 * @author ucuenca
 */
public class testTFIDF {
   



	// http://localhost:8080/RESTfulExample/json/product/post
	public static void main(String[] args) {

		try {

			URL url = new URL(
					"http://localhost:8080/Text2Numeric/rest/service/frequency");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{"
                                + "\"text\":["
                                + "\"Texto de prueba ingenieria relacionado con las materias de ingenieria informatica de la UC\""
                                + ","
                                + "\"Segundo documento test relacionado con las asignaturas de la escuela de informatica\""
                                + "]}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

//			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ conn.getResponseCode());
//			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}

			conn.disconnect();
                        
                        textSplitandStopWords("Prueba de texto para dividir y stopwords");

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

    public static void textSplitandStopWords(String text)
    {
        utilities util = new utilities();
        List<String> result = util.splitText(text);
        for (String word:result)
        {
            System.out.println(" - " + word);
        }
    }
}
