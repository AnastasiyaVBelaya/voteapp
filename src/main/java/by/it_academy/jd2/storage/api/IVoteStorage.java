package by.it_academy.jd2.storage.api;

import by.it_academy.jd2.dto.VoteDTO;

import java.util.List;
import java.util.Map;

public interface IVoteStorage {
    void create(VoteDTO vote);
    Map<String, Integer> getArtist();
    Map<String, Integer> getGenre();
    List<String> getAbouts();
}
