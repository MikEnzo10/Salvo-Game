package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;


import static java.util.stream.Collectors.toList;

@RequestMapping("/api")
@RestController
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private GamePlayerRepository gamePlayerRepo;

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value="/players", method=RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setupForm(@RequestBody Player player) {
        //Comprobar que el username y el password no estan vacios

        if(player.getUserName().isEmpty() || player.getPassword().isEmpty()){
            return new ResponseEntity<Map<String, Object>>( new LinkedHashMap<String, Object>(){{
                put("error", "your username or password is incorrect");
            }}, HttpStatus.FORBIDDEN );

        }

        //Comprobar que no exista un player con el mismo username ya creado
        if(playerRepo.findByUserName(player.getUserName()).isPresent()){
            return new ResponseEntity<Map<String, Object>>( new LinkedHashMap<String, Object>(){{
                put("error", "your username already exist");
            }},HttpStatus.CONFLICT );
        }

        //Si lo anterior no se cumple, crear al player y guardarlo en el repositorio

        player.setPassword(passwordEncoder.encode(player.getPassword()));
        playerRepo.save(player);



        //Con todas las condiciones, devolver el ResponseEntity adecuado.
        return new ResponseEntity<Map<String, Object>>( new LinkedHashMap<String, Object>(){{
            put("OK", "correct login");
        }}, HttpStatus.OK );

    }

    @RequestMapping(value="/games", method=RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication auth) {
        Optional <Player> playerLogged = getLoggedPlayer(auth);
        if (getLoggedPlayer(auth).isPresent()){
            Game gameNew = new Game(Calendar.getInstance().getTime());
            GamePlayer gamePlayerNew = new GamePlayer(Calendar.getInstance().getTime(), playerLogged.get(), gameNew);
            playerLogged.get().addGamePlayer(gamePlayerNew);
            gameNew.addGamePlayer(gamePlayerNew);
            gameRepo.save(gameNew);
            gamePlayerRepo.save(gamePlayerNew);

            return new ResponseEntity<Map<String, Object>>( new LinkedHashMap<String, Object>(){{
                put("gpId", gamePlayerNew.getId());
            }},HttpStatus.CREATED );

        }else{
            return new ResponseEntity<Map<String, Object>>( new LinkedHashMap<String, Object>(){{
                put("error", "is not logged!");
            }},HttpStatus.UNAUTHORIZED );
        }
    };

    @RequestMapping(value="/games/{gameId}/players", method=RequestMethod.POST)
    public Map<String, Object> joinGame(@PathVariable Long gameId) {
        Optional<Game> opGame = gameRepo.findById(gameId);
        if (getLoggedPlayer(gameId).get().){

        }else{
            return new ResponseEntity<Map<String, Object>>( new LinkedHashMap<String, Object>(){{
                put("error", "is not logged!");
            }},HttpStatus.UNAUTHORIZED );
        }


    }

    @RequestMapping("/games")
    public Map<String, Object> getAll(Authentication auth) {
        Optional <Player> playerLogged = getLoggedPlayer(auth);
        return new LinkedHashMap<String, Object>(){{
            put("games", gameRepo.findAll().stream().map(game -> game.makeGameDTO()).collect(toList()));
            put("player", playerLogged.map(player -> player.makePlayerAuthDTO()).orElse(null));
        }};
    }



    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> findGameId(@PathVariable Long gamePlayerId) {
       Optional<GamePlayer> opGp = gamePlayerRepo.findById(gamePlayerId);
       if (opGp.isPresent()){
           Game game = opGp.get().getGame();
           return new LinkedHashMap<String, Object>(){{
               put("id", game.getId());
               put("date", game.getDate());
               put("gamePlayer" , game.getGamePlayers().stream().map(gamePlayer -> gamePlayer.makeGamePlayerDTO()).collect(toList()));  // lo que hacemos aqui es coger el Set de gameplayers, hacerlo un map (key valor) con los campos que estan puestos en makeGamePlaerDTO y con collect(toList) volverlo a hacer una lista
               put("ships" , opGp.get().getShip().stream().map(ship -> ship.makeShipDTO()).collect(toList()));
               put("salvos" , opGp.get().getGame().getGamePlayers().stream().flatMap(gamePlayer -> gamePlayer.getSalvo().stream().map(salvo -> salvo.makeSalvoDTO())).collect(toList()));
           }};
       }else {
           return null;
       }
       }

       public Optional<Player> getLoggedPlayer(Authentication auth){
        if (auth == null){
            return Optional.empty();
        }else{
            return playerRepo.findByUserName(auth.getName());

        }

       }

    }




