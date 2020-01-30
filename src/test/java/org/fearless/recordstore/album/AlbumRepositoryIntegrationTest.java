package org.fearless.recordstore.album;

import org.fearless.recordstore.album.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AlbumRepositoryIntegrationTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    public void whenFindByTitle_thenReturnAlbum() {
        Album beeThousand = new Album();
        beeThousand.setArtist("Guided by Voices");
        beeThousand.setTitle("Bee Thousand");
        beeThousand.setYear(1994);

        albumRepository.save(beeThousand);

        Album result = albumRepository.findByTitle("Bee Thousand");

        assertEquals(result.getTitle(), beeThousand.getTitle());
    }
}
