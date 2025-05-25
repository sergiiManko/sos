package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.Book;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByAuthor(String author);

    List<Book> findByCategory(String category);

    List<Book> findByStatus(String status);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
