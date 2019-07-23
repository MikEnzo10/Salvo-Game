package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;

//Estas instrucciones de hibernate solo afectan a la sentencia siguiente a las mismas.
@Entity
public class Player {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;


    private String userName;
    private String password;

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers = new LinkedHashSet<>();

    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<Score> scores = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private GamePlayer gamePlayer;


    //Constructores (Hibernate pide uno vacio), y el otro con los parámetros.
    public Player() {
    }

    public Player(String user, String password) {

        userName = user;
        this.password = password;
    }

    //Métodos Getters y Setters
    public Long getId() {
        return this.id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public long getWin(){
        return scores.stream().filter(score -> score.getScore()==1).count();
    }

    public long getLoose(){
        return scores.stream().filter(score -> score.getScore()==0).count();
    }

    public long getTie(){
        return scores.stream().filter(score -> score.getScore()==0.5).count();
    }

    public Set<Score> getScores (){
        return scores;
    }

    public double getTotal (){

        return scores.stream().map(s -> s.getScore()).reduce((a,b) -> a + b).orElse(0.0); //con map, transformamos los elementos del array en scores (solo la puntuacion
                                                                                                // ) con reduce lo que hacemos es sumar los elementos del array 1 a 1, reduce
                                                                                                //devuelve un optional, con orelse le decimos que devuelva ese valor, y si el optional esta vacio que devuelva 0
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Metodos.
    public String toString() {
        return userName;
    }


    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayers.add(gamePlayer);
    }

    public Map<String, Object> makePlayerDTO() {
       return new LinkedHashMap<String, Object>(){{
            put("id", id);
            put("name", userName);
            put("score", scores.stream().map(score -> score.makeScoreDTO()));
            put("total", getTotal());
            put("wins", getWin());
            put("loose", getLoose());
            put("ties", getTie());


        }};
    }

    public Map<String, Object> makePlayerAuthDTO() {
        return new LinkedHashMap<String, Object>(){{
            put("id", id);
            put("name", userName);
        }};
    }




    public void addScore(Score score){
        score.setPlayer(this);
        scores.add(score);
    }

}