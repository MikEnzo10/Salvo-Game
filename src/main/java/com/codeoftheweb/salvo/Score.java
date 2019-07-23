package com.codeoftheweb.salvo;


import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Score {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date finishDate;
    private double score;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;


    //Constructores (Hibernate pide uno vacio), y el otro con los parámetros.
    public Score() {
    }

    public Score(Date finishDate, double score) {
        this.finishDate = finishDate;
        this.score = score;
    }

//    Metodos getters y setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void add(Score scores) {
    }


    //Metodos



    public Map<String, Object> makeScoreDTO() {
        return new LinkedHashMap<String, Object>(){{
            put("id", id);
            put("score", score);
            put("finishDate", finishDate);
        }};
    }


}
