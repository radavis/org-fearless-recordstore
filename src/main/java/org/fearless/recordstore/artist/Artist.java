package org.fearless.recordstore.artist;

import org.fearless.recordstore.album.Album;

import java.util.ArrayList;
import java.util.List;

// import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.persistence.GenerationType;
// import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @Table is an unnecessary annotation, since the class is named after the table.
// @Table(name = "artist")
@Entity
@ToString
@NoArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter @Setter
    @NotBlank
    private String name;

    // @Column is an unnecessary annotation, since the class attribute and 
    //   column name are identical.
    // @Column(name = "location")
    @Getter @Setter
    private String location;

    @Getter @Setter
    private Integer year;

    @OneToMany(mappedBy = "artist")
    @Getter
    private List<Album> albums;

    public Artist(String name) {
        this(name, null, null);
    }

    public Artist(String name, String location, Integer year) {
        this.name = name;
        this.location = location;
        this.year = year;
        this.albums = new ArrayList<Album>();
    }
}