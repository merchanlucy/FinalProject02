import com.library.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LibraryTest {
    @Test
    @DisplayName("Borrowing an item adds it to the user's borrowed items.")
    public void borrowItemAddsToUserTest1() {
        Library library = new Library();

        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        library.addUser(student);
        library.addItem(book);
        library.borrowItem(student, book);

        Assertions.assertTrue(student.getBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("Borrowing an item changes its status to borrowed.")
    public void borrowItemChangesStatusTest1() {
        Library library = new Library();
        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        library.addUser(student);
        library.addItem(book);

        library.borrowItem(student, book);

        Assertions.assertEquals(ItemStatus.BORROWED, book.getStatus());
    }

    @Test
    @DisplayName("Returning an item removes it from the user's borrowed items.")
    public void returnItemRemovesFromUserTest1() {
        Library library = new Library();
        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        library.addUser(student);
        library.addItem(book);

        library.borrowItem(student, book);
        library.returnItem(student, book);

        Assertions.assertFalse(student.getBorrowedItems().contains(book));
    }

    @Test
    @DisplayName("Returning an item changes its status back in store")
    public void returnItemChangesStatusTest1() {
        Library library = new Library();

        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        library.addUser(student);
        library.addItem(book);
        library.borrowItem(student, book);
        library.returnItem(student, book);

        Assertions.assertEquals(ItemStatus.IN_STORE, book.getStatus());
    }

    @Test
    @DisplayName("Recursive title search finds a book by title.")
    public void searchByTitleRecursiveFindsBookTest1() {
        Library library = new Library();
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        library.addItem(book);
        List<Item> results = library.searchByTitleRecursive("frankenstein");

        Assertions.assertTrue(results.contains(book));
    }

    @Test
    @DisplayName("stream search finds a book by author.")
    public void searchByKeywordStreamFindsAuthorTest1() {
        Library library = new Library();
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        library.addItem(book);

        List<Item> results = library.searchByAuthorStream("mary");

        Assertions.assertTrue(results.contains(book));
    }
}
