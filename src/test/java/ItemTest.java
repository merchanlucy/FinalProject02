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
    @DisplayName("marking a book as lost changes the item status to lost")
    public void markBookAsLostTest1() {
        Book book = new Book("Frankenstein", "1234567891234", "Mary Shelley", "Science Fiction");
        book.markAsLost();

        Assertions.assertEquals(ItemStatus.LOST, book.getStatus());
    }
}