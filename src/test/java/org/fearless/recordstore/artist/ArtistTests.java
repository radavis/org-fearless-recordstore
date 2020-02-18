package org.fearless.recordstore.artist;

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


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ArtistTests {

    @Autowired
    private TestEntityManager entityManager;

    private Artist artist;

    @BeforeEach
    public void setup() {
        artist = new Artist("Nirvana", "Aberdeen, WA", 1987);
    }

    @Test
    public void testGetId() {
        entityManager.persistAndFlush(artist);
        assertNotNull(artist.getId());
    }

    @Test
    public void testNameCannotBeBlank() {
        artist.setName("");
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(artist);
        });
    }

    @Test
    public void testToString() {
        entityManager.persistAndFlush(artist);
        String artistString = artist.toString();
        assertTrue(artistString.contains("id="));
        assertTrue(artistString.contains("name=Nirvana"));
        assertTrue(artistString.contains("location=Aberdeen, WA"));
        assertTrue(artistString.contains("year=1987"));
    }
}