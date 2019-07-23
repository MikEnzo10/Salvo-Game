package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

//Estas instrucciones de hibernate solo afectan a la sentencia siguiente a las mismas.
@Entity
public class Game {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date date;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers = new LinkedHashSet<>();

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<Score> scores = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private GamePlayer gamePlayer;


    //Constructores (Hibernate pide uno vacio, y el otro con los parámetros.
    public Game() { }

    public Game(Date date ) {
        this.date = date;
    }

    //Métodos Getters y Setters
    public long getId () {
        return id;
    }

    public Date getDate(){
        return date;
    }

    public void setDate (Date date){
        this.date = date;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public Set<Score> getScores(){
        return scores;
    }


    //Metodos

    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayers.add(gamePlayer);
    }

    public Map<String, Object> makeGameDTO() {
        return new LinkedHashMap<String, Object>(){{
            put("id", id);
            put("date", date);
            put("gamePlayer", gamePlayers.stream().map(gameplayer -> gameplayer.makeGamePlayerDTO()));
        }};
    }

    public void addScore(Score score){
        score.setGame(this);
        scores.add(score);
    }

    }


