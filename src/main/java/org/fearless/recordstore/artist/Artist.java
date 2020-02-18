package org.fearless.recordstore.artist;

// import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.persistence.GenerationType;
// import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @Table is an unnecessary annotation, since the class is named after the table.
// @Table(name = "artist")
@Entity
@ToString
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

    public Artist() {}

    public Artist(String name) {
        this(name, null, null);
    }

    public Artist(String name, String location, Integer year) {
        this.name = name;
        this.location = location;
        this.year = year;
    }
}