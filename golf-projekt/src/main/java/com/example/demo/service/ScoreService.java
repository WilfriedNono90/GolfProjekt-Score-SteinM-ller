package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.entitäten.Player;
import com.example.demo.entitäten.Score;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.ScoreRepository;


@Service
public class ScoreService {
private ScoreRepository scoreRepository;
private PlayerRepository playerRepository;
	
	public ScoreService(ScoreRepository scoreRepository, PlayerRepository playerRepository) {
		this.scoreRepository = scoreRepository;
		this.playerRepository = playerRepository;
	}
	
	public List<Score> findAll(){
		return scoreRepository.findAll();
	}
	
	public List<Score> findAll(String filter){
		if (filter == null || filter.isEmpty()) {
			return findAll();
		}else {
			return scoreRepository.findByIdPlayer(Long.parseLong(filter));
			
		}
	}
	
	public Optional<Score> findById(Long id) {
		return scoreRepository.findById(id);
	}
	
	public Boolean findByPlayerID(Long id){
		List<Score> score = scoreRepository.findByIdPlayer(id);
		if (score != null) {
			return true;
		}
		return false;
		
	}
	
	public Score save(Score score) {
		return scoreRepository.save(score);
	}
	
	public boolean delete(Long id) {
		try {
			scoreRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public Score update(Score score, Long id) {
		score.setId(id);
		return save(score);
	}
	
	
	@PostConstruct
	public void dbFüllen() {
		if(scoreRepository.count() == 0) {
			playerRepository.save(new Player((long) 1,null));
			playerRepository.save(new Player((long) 2,null));
			scoreRepository.save(new Score(null,playerRepository.findById((long) 1).get(),2L,2L,(long) 1,new Date()));
			scoreRepository.save(new Score(null,playerRepository.findById((long) 2).get(),2L,2L,(long) 1,new Date()));
		}
	}

}
