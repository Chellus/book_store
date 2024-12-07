package com.marcelo.book_store_jsf.util;

import com.marcelo.book_store_jsf.book.Book;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIterNodeList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookAPI {
    public static Book getBook(String isbn) throws IOException, ParseException, java.text.ParseException {
        Book book = new Book();

        String apiUrl = "https://openlibrary.org/isbn/" + isbn +".json";
        URL url = new URL(apiUrl);
        System.out.println("URL: " + apiUrl);

        // Configuración de la conexión
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        // Leer la respuesta
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(reader);

        String title = (String) jsonResponse.get("title");

        JSONArray authorsArray = (JSONArray) jsonResponse.get("authors");
        JSONObject author = (JSONObject) authorsArray.get(0);
        String authorKey = (String) author.get("key");
        String authorName = getAuthorName(authorKey);

        JSONArray publisherArray = (JSONArray) jsonResponse.get("publishers");
        String publisher = (String) publisherArray.get(0);
        long pages = (Long) jsonResponse.get("number_of_pages");
        //Date releaseDate = jsonResponse.get("publish_date") != null ? new SimpleDateFormat("MMMM dd, yyyy").parse(jsonResponse.get("publish_date").toString()) : null;

        String imageUrl = "https://covers.openlibrary.org/b/isbn/" + isbn + "-L.jpg";

        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(authorName);
        book.setPublisher(publisher);
        book.setPages((int) pages);
        //book.setReleaseDate(releaseDate);
        book.setImageUrl(imageUrl);

        return book;
    }

    public static String getAuthorName(String author) throws IOException, ParseException {
        String apiUrl = "https://openlibrary.org/"+ author + ".json";
        URL url = new URL(apiUrl);
        System.out.println("URL: " + apiUrl);

        // Configuración de la conexión
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.connect();
        // Leer la respuesta
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(reader);

        return (String) jsonResponse.get("personal_name");

    }
}
