package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.VoteDTO;

import java.util.Map;

public interface IVoteService {
    void create(VoteDTO vote);
    Map<String, Object> getResults();
}
