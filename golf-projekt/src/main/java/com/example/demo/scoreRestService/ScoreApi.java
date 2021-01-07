package com.example.demo.scoreRestService;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entitäten.Player;
import com.example.demo.entitäten.Score;
import com.example.demo.service.PlayerService;
import com.example.demo.service.ScoreService;

@RestController
@RequestMapping("/api")
public class ScoreApi {

	@Autowired
    private ScoreService scoreService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private KafkaTemplate< String, String> kafkatemplate;
	
	@GetMapping(path = "/score" , produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Score> getScore() {
		return scoreService.findAll();
		
	}
	
	@GetMapping(path = "/score/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Score getOneScore(@PathVariable(value = "id") Long id) {
		return scoreService.findById(id).get();
	}
	
	
	@PostMapping(path = "/score", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Score save(@RequestBody Score score) {
		Optional<Player> player = playerService.findById(score.getPlayer().getId());
		if (player.isPresent()) {
			
			return scoreService.save(score);
		}else {
			playerService.save(score.getPlayer());
			//ToDo Player zu Mathias Schicken
			kafkatemplate.send("neuplayerscore", score.getPlayer().getId().toString());
			return scoreService.save(score);
		}
		
		
	}
	
	@PutMapping(path = "/score/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Score save(@RequestBody Score score, @PathVariable(value = "id") Long id) {
		return scoreService.update(score,id);
	}
	
	@DeleteMapping(path = "/score/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public boolean delete(@PathVariable Long id) {
		
		return scoreService.delete(id);
		
	}
	
	@PostConstruct
	public void startDbAbgleichen() {
		kafkatemplate.send("bdabgleichen", "envoi");
	}

}
