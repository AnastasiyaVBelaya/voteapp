package by.it_academy.jd2.storage;

import by.it_academy.jd2.dto.VoteDTO;
import by.it_academy.jd2.storage.api.IVoteStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteStorage implements IVoteStorage {

    private final static VoteStorage instance = new VoteStorage();

    private Map<String, Integer> artist = new HashMap<>();
    private Map<String, Integer> genre = new HashMap<>();
    private List<String> abouts = new ArrayList<>();

    private VoteStorage() {
    }

    @Override
    public void create(VoteDTO vote) {
        incrementVoteCount(artist, vote.getArtist());

        for (String genre : vote.getGenre()) {
            incrementVoteCount(this.genre, genre);
        }
        abouts.add(vote.getAbout());
    }

    @Override
    public Map<String, Integer> getArtist() {
        return new HashMap<>(artist);
    }

    @Override
    public Map<String, Integer> getGenre() {
        return new HashMap<>(genre);
    }

    @Override
    public List<String> getAbouts() {
        return new ArrayList<>(abouts);
    }

    private void incrementVoteCount(Map<String, Integer> data, String key) {
        data.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
    }

    public static VoteStorage getInstance() {
        return instance;
    }
}
