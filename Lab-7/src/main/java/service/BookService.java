package com.porio.Lab7.service;

import com.porio.Lab7.dto.NewBookDto;
import com.porio.Lab7.entity.Author;
import com.porio.Lab7.entity.Book;
import com.porio.Lab7.repository.AuthorRepository;
import com.porio.Lab7.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @PostConstruct
    public void init() {
        List<Author> authors = List.of(
                new Author(null, "J.R.R. Tolkien", new ArrayList<>()),
                new Author(null, "Sir Arthur Conan Doyle", new ArrayList<>()),
                new Author(null, "Jules Verne", new ArrayList<>())
        );

        List<Author> savedAuthors = authorRepository.saveAll(authors);

        List<Book> books = List.of(
                new Book(null, "Lord of the Rings", 1000, savedAuthors.get(0)),
                new Book(null, "The Adventures of Sherlock Holmes", 500, savedAuthors.get(1))
        );

        bookRepository.saveAll(books);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found."));
    }

    public Book addBook(NewBookDto dto) {
        Author author = authorRepository.findById(dto.authId())
                .orElseThrow(() -> new RuntimeException("Author not found."));

        Book newBook = new Book(
                null,
                dto.title(),
                dto.pages(),
                author
        );

        return bookRepository.save(newBook);
    }

    public void updateBook(Long bookId, NewBookDto updatedDto) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found."));

        Author author = authorRepository.findById(updatedDto.authId())
                .orElseThrow(() -> new RuntimeException("Author not found."));

        existingBook.setTitle(updatedDto.title());
        existingBook.setPages(updatedDto.pages());
        existingBook.setAuthor(author);

        bookRepository.save(existingBook);
    }

    public boolean deleteBookById(Long id) {
        bookRepository.deleteById(id);
        return bookRepository.findById(id).isEmpty();
    }
}
