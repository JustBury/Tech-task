package com.ifortex.bookservice.dao;

import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;

import java.util.List;
import java.util.Map;

public interface BookDAO {

    Map<String, Long> getBooks();

    List<Book> getAllByCriteria(SearchCriteria searchCriteria);
}
