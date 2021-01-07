package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entitäten.Score;


public interface ScoreRepository  extends JpaRepository<Score, Long> {
	@Query("select r from Score r where r.player.id = :id ")
	List<Score> findByIdPlayer(@Param("id") long playerid);

}