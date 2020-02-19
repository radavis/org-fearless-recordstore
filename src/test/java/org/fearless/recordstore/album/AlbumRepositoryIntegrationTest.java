package org.fearless.recordstore.album;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;
import org.fearless.recordstore.artist.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class AlbumRepositoryIntegrationTest {

  @Autowired private AlbumRepository albumRepository;

  private Artist guidedByVoices() {
    Artist result = new Artist();
    result.setName("Guided By Voices");
    result.setLocation("Dayton, OH");
    result.setYear(1985);
    return result;
  }

  @Test
  public void whenFindByTitle_thenReturnAlbum() {
    Album beeThousand = new Album();
    // beeThousand.setArtist("Guided by Voices");
    beeThousand.setTitle("Bee Thousand");
    beeThousand.setYear(1994);

    albumRepository.save(beeThousand);

    Album result = albumRepository.findByTitle("Bee Thousand");

    assertEquals(result.getTitle(), beeThousand.getTitle());
  }
}
