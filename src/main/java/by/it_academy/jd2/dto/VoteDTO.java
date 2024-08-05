package by.it_academy.jd2.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class VoteDTO {
    private String artist;
    private String[] genre;
    private String about;
    private LocalDateTime timestamp;

    public VoteDTO() {
    }

    public VoteDTO(String artist, String[] genre, String about) {
        this.artist = artist;
        this.genre = genre;
        this.about = about;
        this.timestamp = LocalDateTime.now();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String[] getGenre() {
        return genre;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public String getAbout() {
        return about;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteDTO voteDTO = (VoteDTO) o;
        return Objects.equals(artist, voteDTO.artist) &&
                Arrays.equals(genre, voteDTO.genre) &&
                Objects.equals(about, voteDTO.about) &&
                Objects.equals(timestamp, voteDTO.timestamp);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(artist, about, timestamp);
        result = 31 * result + Arrays.hashCode(genre);
        return result;
    }

    @Override
    public String toString() {
        return "VoteDTO{" +
                "artist='" + artist + '\'' +
                ", genre=" + Arrays.toString(genre) +
                ", about='" + about + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
