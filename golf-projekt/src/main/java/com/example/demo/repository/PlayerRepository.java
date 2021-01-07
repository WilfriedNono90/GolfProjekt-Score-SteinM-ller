package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entitäten.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
