package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.VoteDTO;
import by.it_academy.jd2.service.api.IVoteService;
import by.it_academy.jd2.storage.VoteStorage;
import by.it_academy.jd2.storage.api.IVoteStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;


public class VoteService implements IVoteService {

    private final static VoteService instance = new VoteService();
    private final static IVoteStorage voteStorage = VoteStorage.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final List<String> DEFAULT_ARTISTS = List.of("KISS", "QUEEN", "ACDC", "Arch Enemy");
    private static final List<String> DEFAULT_GENRES =
            List.of("Rock", "Jazz", "Metal", "Pop", "Folk", "Punk", "Electro", "Rap", "Classic", "Blues");
    private Map<String, LocalDateTime> voteTimestamps = new HashMap<>();

    private VoteService() {
    }

    @Override
    public void create(VoteDTO vote) {
        validateVote(vote);
        LocalDateTime voteDateTime = LocalDateTime.now();
        voteStorage.create(vote);
        voteTimestamps.put(vote.getAbout(), voteDateTime);
    }

    private void validateVote(VoteDTO vote) {
        if (vote.getArtist() == null || vote.getArtist().isBlank()) {
            throw new IllegalArgumentException("Исполнитель не выбран");
        }

        for (String genre : vote.getGenre()) {
            if (genre == null || genre.isBlank()) {
                throw new IllegalArgumentException("Жанр не выбран");
            }
        }
        if (vote.getGenre().length < 3) {
            throw new IllegalArgumentException("Необходимо указать не менее 3 жанров");
        }

        if (vote.getAbout() == null || vote.getAbout().isBlank()) {
            throw new IllegalArgumentException("Поле не заполнено");
        }
    }

    @Override
    public Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();

        Map<String, Integer> finalArtistVotes = initializeAndSortResults(voteStorage.getArtist(), DEFAULT_ARTISTS);
        Map<String, Integer> finalGenreVotes = initializeAndSortResults(voteStorage.getGenre(), DEFAULT_GENRES);

        results.put("artists", finalArtistVotes);
        results.put("genres", finalGenreVotes);
        results.put("aboutVotes", formatVoteResults());

        return results;
    }

    private List<String> formatVoteResults() {
        return voteTimestamps.entrySet().stream()
                .sorted(Map.Entry.<String, LocalDateTime>comparingByValue().reversed())
                .map(entry -> entry.getKey() + " - " + entry.getValue().format(formatter))
                .collect(Collectors.toList());
    }

    private Map<String, Integer> initializeAndSortResults(Map<String, Integer> actualVotes, List<String> defaults) {
        Map<String, Integer> resultMap = new HashMap<>();
        defaults.forEach(key -> resultMap.put(key, actualVotes.getOrDefault(key, 0)));
        return sortMapByValueDescending(resultMap);
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
    public static VoteService getInstance() {
        return instance;
    }
}