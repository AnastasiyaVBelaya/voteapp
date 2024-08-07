package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.VoteDTO;
import by.it_academy.jd2.service.api.IVoteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class VoteService implements IVoteService {

    private final static VoteService instance = new VoteService();
    private Map<String, Integer> artist = new HashMap<>();
    private Map<String, Integer> genre = new HashMap<>();
    private Map<String, List<VoteDTO>> about = new HashMap<>();

    private VoteService() {
    }
//to-do инициализировать значения artist и genre нулями!

    @Override
    public void create(VoteDTO vote) {
        artist.compute(vote.getArtist(), (k, v) -> (v == null) ? 1 : v + 1);

        for (String genreItem : vote.getGenre()) {
            genre.compute(genreItem, (k, v) -> (v == null) ? 1 : v + 1);
        }
        about.computeIfAbsent(vote.getAbout(), k -> new ArrayList<>()).add(vote);
    }

    private Map<String, Integer> sortMapByValueDescending(Map<String, Integer> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private List<VoteDTO> sortVotesByTimestampDescending(List<VoteDTO> votes) {
        return votes.stream()
                .sorted((v1, v2) -> v2.getTimestamp().compareTo(v1.getTimestamp()))

                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();

        results.put("artists", sortMapByValueDescending(artist));
        results.put("genres", sortMapByValueDescending(genre));

        Map<String, List<VoteDTO>> sortedAboutVotes = new HashMap<>();
        for (Map.Entry<String, List<VoteDTO>> entry : about.entrySet()) {
            List<VoteDTO> sortedVotes = sortVotesByTimestampDescending(entry.getValue());
            sortedAboutVotes.put(entry.getKey(), sortedVotes);
        }

        results.put("aboutVotes", sortedAboutVotes);

        return results;
    }

    public static VoteService getInstance() {
        return instance;
    }
}
