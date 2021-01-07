package com.example.demo.entitäten;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Player {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER,
			mappedBy = "player")
	private List<Score> score = new ArrayList<Score>();
	
	public void addScore (Score scoreElement) {
		score.add(scoreElement);
	}
	
	public void removeScore (Score scoreElement) {
		score.remove(scoreElement);
	}
	

}
