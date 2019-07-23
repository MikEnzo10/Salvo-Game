package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Salvo {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private int turnNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salvo_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    Set<String> salvoLocations = new LinkedHashSet<>();



    //Constructores (Hibernate pide uno vacio), y el otro con los parámetros.
    public Salvo() {
    }

    public Salvo(int turnNumber, Set<String> salvoLocations) {
        this.turnNumber = turnNumber;
        this.salvoLocations = salvoLocations;
    }

    //Metodos Getters y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Set<String> getSalvoLocations() {
        return salvoLocations;
    }

    public void setSalvoLocations(Set<String> salvoLocations) {
        this.salvoLocations = salvoLocations;
    }


//Metodos

    public Map<String, Object> makeSalvoDTO() {
        return new LinkedHashMap<String, Object>() {{
            put("turn", getTurnNumber());
            put("player_id", gamePlayer.getPlayer().getId());
            put("turnLocation", getSalvoLocations());

        }};
    }
}