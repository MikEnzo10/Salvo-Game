package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

//Estas instrucciones de hibernate solo afectan a la sentencia siguiente a las mismas.
@Entity
public class GamePlayer {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private Date date;

    //Creamos campo en la tabla game_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    //Creamos campo en la tabla player_id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Ship> ships = new LinkedHashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    private Set<Salvo> salvos = new LinkedHashSet<>();



    //Constructores (Hibernate pide uno vacio, y el otro con los parámetros.
    public GamePlayer() {
    }

    public GamePlayer(Date date, Player player, Game game) {
        this.date = date;
        this.player = player;
        this.game = game;
    }

    //Métodos Getters y Setters
    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Ship> getShip(){
        return ships;
    }

    public Set<Salvo> getSalvo(){
        return salvos;
    }


    public Game getGame() {
        return game;
    }


    //Métodos
    public void addShip(Ship ship){
        ship.setGamePlayer(this);
        ships.add(ship);
    }

    public void addSalvo(Salvo salvo){
        salvo.setGamePlayer(this);
        salvos.add(salvo);
    }


    public Map<String, Object> makeGamePlayerDTO() {
        return new LinkedHashMap<String, Object>(){{  //linkedhashmap es una lista ordenada (normalmente por orden de insercion)
            put("id", id);
            put("date", date);
            put("player" , player.makePlayerDTO());
        }};
    }

    public Score getScore(){
        if (game.getScores().isEmpty()) {
            return null;
        }else {
            List<Score> gameScores = game.getScores().stream().collect(toList());
            List<Score> playerScores = player.getScores().stream().collect(toList());

            if(playerScores.contains(gameScores.get(0))){
                return gameScores.get(0);
            } else {
                return gameScores.get(1);
            }

        }
    }


}


