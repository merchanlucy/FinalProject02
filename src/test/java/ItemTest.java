import com.library.domain.Book;
import com.library.domain.ItemStatus;
import com.library.domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    @Test
    @DisplayName("A new book starts with status in store")
    public void inStoreTest1() {
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");

        assertEquals(ItemStatus.IN_STORE, book.getStatus());
    }

    @Test
    @DisplayName("A new book is available when it is in store")
    public void isAvailableTest1() {
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");

        assertTrue(book.isItemAvailable());
    }

    @Test
    @DisplayName("borrowing a book changes the item status to borrowed")
    public void borrowBookTest1() {
        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");

        student.borrowItem(book);

        Assertions.assertEquals(ItemStatus.BORROWED, book.getStatus());
    }

    @Test
    @DisplayName("A borrowed book is not available")
    public void borrowedBookTest2() {
        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");

        student.borrowItem(book);

        Assertions.assertFalse(book.isItemAvailable());
    }

    @Test
    @DisplayName("Returning a borrowed book changes the item status to in store.")
    public void returnBookTest1() {
        Student student = new Student("Lucy");
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        student.borrowItem(book);
        student.returnItem(book);

        Assertions.assertEquals(ItemStatus.IN_STORE, book.getStatus());
    }

    @Test
    @DisplayName("marking a book as lost changes the item status to lost")
    public void markBookAsLostTest1() {
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        book.markAsLost();

        Assertions.assertEquals(ItemStatus.LOST, book.getStatus());
    }
}