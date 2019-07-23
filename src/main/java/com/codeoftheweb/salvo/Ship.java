package com.codeoftheweb.salvo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Ship {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ship_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    Set<String> locations = new LinkedHashSet<>();

    //Constructores (Hibernate pide uno vacio), y el otro con los parámetros.
    public Ship() {
    }

    public Ship(String type, Set<String> locations) {
        this.type = type;
        this.locations = locations;
    }

    //Metodos getters y setters

    public long getId () {
        return id;
    }

    public String getType(){
        return type;
    }

    @JsonIgnore
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Set<String> getLocations() {
        return locations;
    }

    //Metodos

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Map<String, Object> makeShipDTO() {
        return new LinkedHashMap<String, Object>(){{
            put("type", getType());
            put("location", getLocations());

        }};
    }


}
