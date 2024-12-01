package com.ifortex.bookservice.dao.impl;

import com.ifortex.bookservice.dao.BookDAO;
import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDAOImpl implements BookDAO {

    private final String SELECT_GENRE_BY_COUNT_OF_BOOKS = "SELECT genre, COUNT(*) FROM (SELECT unnest(genre) AS genre FROM books) AS genres GROUP BY genre ORDER BY COUNT(*) DESC";
    private final String SELECT_BY_FILTER = "SELECT * FROM books WHERE title ILIKE :titlePattern AND author ILIKE :authorPattern AND description ILIKE :descriptionPattern";
    private final String TITLE_PATTERN = "titlePattern";
    private final String AUTHOR_PATTERN = "authorPattern";
    private final String DESCRIPTION_PATTERN = "descriptionPattern";
    private final String GENRE_PATTERN = "genrePattern";
    private final String YEAR_PATTERN = "yearPattern";
    private final String ADDING_GENRE = " AND :genrePattern = ANY(genre)";
    private final String ADDING_YEAR = " AND EXTRACT(YEAR FROM publication_date) = :yearPattern";
    private final String PERCENT_SYMBOL = "%";


    EntityManager entityManager;

    public BookDAOImpl(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Map<String, Long> getBooks() {
        List<Object[]> listGenreAndCountOfBooks = entityManager.createNativeQuery(SELECT_GENRE_BY_COUNT_OF_BOOKS).getResultList();
        Map<String, Long> linkedHashMap = new LinkedHashMap<>();
        for (Object[] genre : listGenreAndCountOfBooks) {
            linkedHashMap.put((String) genre[0], (Long) genre[1]);
        }
        return linkedHashMap;
    }

    @Override
    public List<Book> getAllByCriteria(SearchCriteria searchCriteria) {
        StringBuilder stringBuilder = new StringBuilder(SELECT_BY_FILTER);
        boolean isGenrePresent = false;
        boolean isYearPresent = false;

        if (searchCriteria.getGenre() != null && !searchCriteria.getGenre().isEmpty()) {
            stringBuilder.append(ADDING_GENRE);
            isGenrePresent = true;

        }
        if (searchCriteria.getYear() != null && searchCriteria.getYear() < 2024 && searchCriteria.getYear() > 0) {
            stringBuilder.append(ADDING_YEAR);
            isYearPresent = true;
        }
        Query query = entityManager.createNativeQuery(stringBuilder.toString())
                .setParameter(TITLE_PATTERN, (searchCriteria.getTitle() == null ? PERCENT_SYMBOL : PERCENT_SYMBOL + searchCriteria.getTitle() + PERCENT_SYMBOL))
                .setParameter(AUTHOR_PATTERN, (searchCriteria.getAuthor() == null ? PERCENT_SYMBOL : PERCENT_SYMBOL + searchCriteria.getAuthor() + PERCENT_SYMBOL))
                .setParameter(DESCRIPTION_PATTERN, (searchCriteria.getDescription() == null ? PERCENT_SYMBOL : PERCENT_SYMBOL + searchCriteria.getDescription() + PERCENT_SYMBOL));

        if (isGenrePresent) query.setParameter(GENRE_PATTERN, searchCriteria.getGenre());
        if (isYearPresent) query.setParameter(YEAR_PATTERN, searchCriteria.getYear());

        return (List<Book>) query.getResultList();
    }
}
