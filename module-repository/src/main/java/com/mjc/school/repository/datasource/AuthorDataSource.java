package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.Author;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AuthorDataSource {
    private List<String> authorList;
    private List<Author> authors;
    public AuthorDataSource() {
        authorList = new ArrayList<String>();
        authors = new ArrayList<>();
        loadAuthors();
        authorsCreateList();
    }
    public List<Author> getAuthors() {
        return authors;
    }
    public List<String> getAuthorList() {
        return authorList;
    }
    private void loadAuthors() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("author.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                authorList.add(line);
            }
        } catch (IOException e) {
            System.err.println("AuthorDataSource read error" + e.getMessage());
        }
    }
    private void authorsCreateList() {
        long count = 0;
        for (String author : authorList) {
            authors.add(new Author(count++, author));
        }
    }
}
