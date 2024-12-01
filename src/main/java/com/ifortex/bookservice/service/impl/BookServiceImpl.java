package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.dao.BookDAO;
import com.ifortex.bookservice.dto.SearchCriteria;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Primary
public class BookServiceImpl implements BookService {


    BookDAO bookDAO;

    public BookServiceImpl(@Autowired BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }


    @Override
    @Transactional
    public Map<String, Long> getBooks() {
        return bookDAO.getBooks();
    }

    @Override
    @Transactional
    public List<Book> getAllByCriteria(SearchCriteria searchCriteria) {
        return bookDAO.getAllByCriteria(searchCriteria);
    }
}
