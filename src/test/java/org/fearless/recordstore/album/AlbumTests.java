package org.fearless.recordstore.album;

import org.fearless.recordstore.artist.Artist;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AlbumTests {

    @Autowired
    private TestEntityManager entityManager;

    private Album album;

    private Artist artist;

    @BeforeEach
    public void setup() {
        artist = new Artist("The Flaming Lips", "Oklahoma City, OK", 1983);
        entityManager.persistAndFlush(artist);

        album = new Album();
        album.setArtist(artist);
        album.setTitle("The Soft Bulletin");
        album.setYear(1999);
    }

    @Test
    public void getId() {
        entityManager.persistAndFlush(album);
        assertNotNull(album.getId());
    }

    @Test
    public void getArtist() {
        assertTrue(album.getArtist().equals(artist));
    }

    @Test
    public void getTitle() {
        assertTrue(album.getTitle().equals("The Soft Bulletin"));
    }

    @Test
    public void test_titleCannotBeBlank() {
        album.setTitle("");
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(album);
        });
    }

    @Test
    public void getYear() {
        assertEquals(album.getYear(), 1999);
    }

    @Test
    public void test_toString() {
        entityManager.persistAndFlush(album);
        String albumString = album.toString();
        String expectedString = "The Flaming Lips - The Soft Bulletin (1999)";
        assertTrue(albumString.equals(expectedString));
    }
}