package org.fearless.recordstore.artist;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintViolationException;
import org.fearless.recordstore.album.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ArtistTests {

  @Autowired private TestEntityManager entityManager;

  private Artist artist;

  @BeforeEach
  public void setup() {
    artist = new Artist("Nirvana", "Aberdeen, WA", 1987);
  }

  @Test
  public void test_getId() {
    entityManager.persistAndFlush(artist);
    assertNotNull(artist.getId());
  }

  @Test
  public void test_nameCannotBeBlank() {
    artist.setName("");
    assertThrows(
        ConstraintViolationException.class,
        () -> {
          entityManager.persist(artist);
        });
  }

  @Test
  public void test_toString() {
    entityManager.persistAndFlush(artist);
    String artistString = artist.toString();
    assertTrue(artistString.contains("id="));
    assertTrue(artistString.contains("name=Nirvana"));
    assertTrue(artistString.contains("location=Aberdeen, WA"));
    assertTrue(artistString.contains("year=1987"));
  }

  @Test
  public void test_getAlbums() {
    entityManager.persistAndFlush(artist);
    assertTrue(artist.getAlbums().size() == 0);

    Album bleach = new Album();
    bleach.setArtist(artist);
    bleach.setTitle("Bleach");
    bleach.setYear(1989);
    entityManager.persistAndFlush(bleach);

    Album unplugged = new Album();
    unplugged.setArtist(artist);
    unplugged.setTitle("Unplugged in New York");
    unplugged.setYear(1994);
    entityManager.persistAndFlush(unplugged);

    entityManager.refresh(artist);
    assertTrue(artist.getAlbums().size() == 2);
    assertTrue(artist.getAlbums().contains(bleach));
    assertTrue(artist.getAlbums().contains(unplugged));
  }
}
