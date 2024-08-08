package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.VoteDTO;
import by.it_academy.jd2.service.api.IVoteService;
import by.it_academy.jd2.storage.VoteStorage;
import by.it_academy.jd2.storage.api.IVoteStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class VoteService implements IVoteService {

    private final static VoteService instance = new VoteService();
    private final static IVoteStorage voteStorage = VoteStorage.getInstance();


    private VoteService() {
    }
//to-do инициализировать значения artist и genre нулями!

    @Override
    public void create(VoteDTO vote) {
        if(vote.getArtist()==null||vote.getArtist().isBlank()){
            throw new IllegalArgumentException("Артист пуст");

            //TODO добавить сюда валидации
        }
        voteStorage.create(vote);
    }

    @Override
    public Map<String, Object> getResults() {
        return Map.of();
    }

//    private Map<String, Integer> sortMapByValueDescending(Map<String, Integer> map) {
//        return map.entrySet()
//                .stream()
//                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        LinkedHashMap::new
//                ));
//    }
//
//    private List<VoteDTO> sortVotesByTimestampDescending(List<VoteDTO> votes) {
//        return votes.stream()
//                .sorted((v1, v2) -> v2.getTimestamp().compareTo(v1.getTimestamp()))
//
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Map<String, Object> getResults() {
//        Map<String, Object> results = new HashMap<>();
//
//        results.put("artists", sortMapByValueDescending(artist));
//        results.put("genres", sortMapByValueDescending(genre));
//
//        Map<String, List<VoteDTO>> sortedAboutVotes = new HashMap<>();
//        for (Map.Entry<String, List<VoteDTO>> entry : about.entrySet()) {
//            List<VoteDTO> sortedVotes = sortVotesByTimestampDescending(entry.getValue());
//            sortedAboutVotes.put(entry.getKey(), sortedVotes);
//        }
//
//        results.put("aboutVotes", sortedAboutVotes);
//
//        return results;
//    }

    public static VoteService getInstance() {
        return instance;
    }
}
