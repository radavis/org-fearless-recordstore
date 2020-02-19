package org.fearless.recordstore.album;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.fearless.recordstore.artist.Artist;

@Entity
@ToString
@NoArgsConstructor
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  @Getter
  @Setter
  private Artist artist;

  @NotBlank @Getter @Setter private String title;

  @Getter @Setter private Integer year;
}
