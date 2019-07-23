package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@SpringBootApplication
public class SalvoApplication extends SpringBootServletInitializer {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);


	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepo, GameRepository gameRepo, GamePlayerRepository gamePlayerRepo, ShipRepository shipRepo, SalvoRepository salvoRepo, ScoreRepository scoreRepo) {
		final CommandLineRunner commandLineRunner = (args) -> {

			Calendar calendar = Calendar.getInstance();

			//Creacion de players
			Player player1 = new Player("jack@ujl.com", passwordEncoder.encode("aSa12"));
			Player player2 = new Player("chloe@jhr.com", passwordEncoder.encode("gEd34"));
			Player player3 = new Player("kim@gtr.com", passwordEncoder.encode("kPu98"));
			Player player4 = new Player("david@cfd.com", passwordEncoder.encode("yTi62"));
			Player player5 = new Player("michelle@dhf.com", passwordEncoder.encode("hUt72"));
			Player player6 = new Player("clinteastwood@west.com", passwordEncoder.encode("fBs45"));

			//Creacion de games
			Game game1 = new Game(calendar.getTime());
			//Le sumamos una hora
			calendar.add(Calendar.HOUR, 1);
			Game game2 = new Game(calendar.getTime());
			//Le sumamos otra hora por tanto tiene 2 horas más que inicialmente.
			calendar.add(Calendar.HOUR, 1);
			//Le sumamos otra hora por tanto tiene 3 horas más que inicialmente y asi sucesivamente
			Game game3 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);
			Game game4 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);
			Game game5 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);
			Game game6 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);
			Game game7 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);
			Game game8 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);
			Game game9 = new Game(calendar.getTime());
			calendar.add(Calendar.HOUR, 1);


			//Creacion de gameplayers
			GamePlayer gp1 = new GamePlayer(Calendar.getInstance().getTime(), player1 , game1);
			GamePlayer gp2 = new GamePlayer(Calendar.getInstance().getTime(), player2 , game1);
			GamePlayer gp3 = new GamePlayer(Calendar.getInstance().getTime(), player3 , game2);
			GamePlayer gp4 = new GamePlayer(Calendar.getInstance().getTime(), player4 , game2);
			GamePlayer gp5 = new GamePlayer(Calendar.getInstance().getTime(), player5 , game3);
			GamePlayer gp6 = new GamePlayer(Calendar.getInstance().getTime(), player6 , game3);
			GamePlayer gp7 = new GamePlayer(Calendar.getInstance().getTime(), player2 , game4);
			GamePlayer gp8 = new GamePlayer(Calendar.getInstance().getTime(), player5 , game4);
			GamePlayer gp9 = new GamePlayer(Calendar.getInstance().getTime(), player3 , game5);
			GamePlayer gp10 = new GamePlayer(Calendar.getInstance().getTime(), player2 , game5);
			GamePlayer gp11 = new GamePlayer(Calendar.getInstance().getTime(), player4 , game6);
			GamePlayer gp12 = new GamePlayer(Calendar.getInstance().getTime(), player1 , game6);
			GamePlayer gp13 = new GamePlayer(Calendar.getInstance().getTime(), player5 , game7);
			GamePlayer gp14 = new GamePlayer(Calendar.getInstance().getTime(), player3 , game7);
			GamePlayer gp15 = new GamePlayer(Calendar.getInstance().getTime(), player4 , game8);
			GamePlayer gp16 = new GamePlayer(Calendar.getInstance().getTime(), player2 , game8);
			GamePlayer gp17 = new GamePlayer(Calendar.getInstance().getTime(), player5 , game9);

			//Creacion de ships
			Ship barco1 = new Ship("Destroyer", new HashSet<>(Arrays.asList("A1", "A2", "A3")));
			Ship barco2 = new Ship("Carrier", new HashSet<>(Arrays.asList("D1", "D2", "D3" , "D4", "D5")));
			Ship barco3 = new Ship("Battleship" , new HashSet<>(Arrays.asList("F9", "G9", "H9", "I9", "J9")));
			Ship barco4 = new Ship("Submarine" , new HashSet<>(Arrays.asList("B8", "B9", "B10")));
			Ship barco5 = new Ship("Patrol Boat" , new HashSet<>(Arrays.asList("G3", "H3", "I3")));
			Ship barco6 = new Ship("Destroyer", new HashSet<>(Arrays.asList("J1", "J2", "J3")));
			Ship barco7 = new Ship("Carrier", new HashSet<>(Arrays.asList("J6", "J7", "J8" , "J9", "J10")));
			Ship barco8 = new Ship("Battleship" , new HashSet<>(Arrays.asList("C2", "C3", "C4", "C5", "C6")));
			Ship barco9 = new Ship("Submarine" , new HashSet<>(Arrays.asList("I3", "I4", "I5")));
			Ship barco10 = new Ship("Submarine" , new HashSet<>(Arrays.asList("J3", "J4", "J5")));
			Ship barco11 = new Ship("Patrol Boat" , new HashSet<>(Arrays.asList("F5", "G5", "H5")));
			Ship barco12 = new Ship("Destroyer" , new HashSet<>(Arrays.asList("E6", "E7", "E8")));
			Ship barco13 = new Ship("Carrier" , new HashSet<>(Arrays.asList("A5", "B5", "C5", "D5", "E5")));
			Ship barco14 = new Ship("Battleship" , new HashSet<>(Arrays.asList("A5", "A6", "A7", "A8", "A9")));
			Ship barco15 = new Ship("Submarine" , new HashSet<>(Arrays.asList("C5", "C6", "C7")));
			Ship barco16 = new Ship("Patrol Boat" , new HashSet<>(Arrays.asList("G9", "G10", "G11")));
			Ship barco17 = new Ship("Carrier" , new HashSet<>(Arrays.asList("C5", "C6", "C7", "C8", "C9")));
			Ship barco18 = new Ship("Submarine" , new HashSet<>(Arrays.asList("A3", "B3", "C3")));
			Ship barco19 = new Ship("Destroyer" , new HashSet<>(Arrays.asList("H10", "I10", "J10")));
			Ship barco20 = new Ship("Battleship" , new HashSet<>(Arrays.asList("B8", "C8", "D8", "E8", "F8")));
			Ship barco21 = new Ship("Patrol Boat" , new HashSet<>(Arrays.asList("G9", "G10", "G11")));
			Ship barco22 = new Ship("Submarine" , new HashSet<>(Arrays.asList("A9", "A10", "A11")));
			Ship barco23 = new Ship("Battleship" , new HashSet<>(Arrays.asList("F9", "G9", "H9", "I9", "J9")));
			Ship barco24 = new Ship("Destroyer" , new HashSet<>(Arrays.asList("H10", "I10", "J10")));
			Ship barco25 = new Ship("Carrier" , new HashSet<>(Arrays.asList("D1", "D2", "D3" , "D4", "D5")));
			Ship barco26 = new Ship("Patrol Boat" , new HashSet<>(Arrays.asList("A1", "A2", "A3")));
			Ship barco27 = new Ship("Submarine" , new HashSet<>(Arrays.asList("C5", "C6", "C7")));
			Ship barco28 = new Ship("Carrier" , new HashSet<>(Arrays.asList("C5", "C6", "C7", "C8", "C9")));
			Ship barco29 = new Ship("Battleship" , new HashSet<>(Arrays.asList("A5", "A6", "A7", "A8", "A9")));
			Ship barco30 = new Ship("Patrol Boat" , new HashSet<>(Arrays.asList("D1", "D2", "D3")));

			//Creacion de salvos
			Salvo salvo1 = new Salvo(1, new HashSet<>(Arrays.asList("D4", "G8", "C1")));
			Salvo salvo2 = new Salvo(1, new HashSet<>(Arrays.asList("E4", "J8", "A1")));
			Salvo salvo3 = new Salvo(2, new HashSet<>(Arrays.asList("F4", "D8", "F1")));
			Salvo salvo4 = new Salvo(2, new HashSet<>(Arrays.asList("B4", "B8", "A1")));
			Salvo salvo5 = new Salvo(5, new HashSet<>(Arrays.asList("E9", "E8", "E7")));
			Salvo salvo6 = new Salvo(4, new HashSet<>(Arrays.asList("H4", "I8", "J1")));
			Salvo salvo7 = new Salvo(7, new HashSet<>(Arrays.asList("H4", "D8", "B1")));
			Salvo salvo8 = new Salvo(5, new HashSet<>(Arrays.asList("B8", "C5", "F3")));

			//Creacion de puntuacion
			Score score1 = new Score(Calendar.getInstance().getTime(), 0.5);
			Score score2 = new Score(Calendar.getInstance().getTime(), 1);
			Score score3 = new Score(Calendar.getInstance().getTime(), 0);
			Score score4 = new Score(Calendar.getInstance().getTime(), 0.5);
			Score score5 = new Score(Calendar.getInstance().getTime(), 1);
			Score score6 = new Score(Calendar.getInstance().getTime(), 0);
			Score score7 = new Score(Calendar.getInstance().getTime(), 0.5);
			Score score8 = new Score(Calendar.getInstance().getTime(), 1);
			Score score9 = new Score(Calendar.getInstance().getTime(), 1);
			Score score10 = new Score(Calendar.getInstance().getTime(), 0.5);
			Score score11 = new Score(Calendar.getInstance().getTime(), 0);
			Score score12 = new Score(Calendar.getInstance().getTime(), 0);
			Score score13 = new Score(Calendar.getInstance().getTime(), 1);
			Score score14 = new Score(Calendar.getInstance().getTime(), 0);


			//Añadimos juegos a gameplayers
			game1.addGamePlayer(gp1);
			game1.addGamePlayer(gp2);
			game2.addGamePlayer(gp3);
			game2.addGamePlayer(gp4);
			game3.addGamePlayer(gp5);
			game3.addGamePlayer(gp6);
			game4.addGamePlayer(gp7);
			game4.addGamePlayer(gp8);
			game5.addGamePlayer(gp9);
			game5.addGamePlayer(gp10);
			game6.addGamePlayer(gp11);
			game6.addGamePlayer(gp12);
			game7.addGamePlayer(gp13);
			game7.addGamePlayer(gp14);
			game8.addGamePlayer(gp15);
			game8.addGamePlayer(gp16);
			game9.addGamePlayer(gp17);

			//Añadimos jugadores a gameplayers
			player1.addGamePlayer(gp1);
			player2.addGamePlayer(gp2);
			player3.addGamePlayer(gp3);
			player4.addGamePlayer(gp4);
			player5.addGamePlayer(gp5);
			player6.addGamePlayer(gp6);
			player2.addGamePlayer(gp7);
			player5.addGamePlayer(gp8);
			player3.addGamePlayer(gp9);
			player2.addGamePlayer(gp10);
			player4.addGamePlayer(gp11);
			player1.addGamePlayer(gp12);
			player5.addGamePlayer(gp13);
			player3.addGamePlayer(gp14);
			player4.addGamePlayer(gp15);
			player2.addGamePlayer(gp16);
			player5.addGamePlayer(gp17);

			//Añadimos barcos a gameplayers
			gp1.addShip(barco1);
			gp1.addShip(barco2);
			gp1.addShip(barco3);
			gp1.addShip(barco4);
			gp1.addShip(barco5);
			gp2.addShip(barco6);
			gp2.addShip(barco7);
			gp2.addShip(barco8);
			gp2.addShip(barco9);
			gp2.addShip(barco10);
			gp3.addShip(barco11);
			gp3.addShip(barco12);
			gp3.addShip(barco13);
			gp3.addShip(barco14);
			gp3.addShip(barco15);
			gp4.addShip(barco16);
			gp4.addShip(barco17);
			gp4.addShip(barco18);
			gp4.addShip(barco19);
			gp4.addShip(barco20);
			gp5.addShip(barco21);
			gp5.addShip(barco22);
			gp5.addShip(barco23);
			gp5.addShip(barco24);
			gp5.addShip(barco25);
			gp6.addShip(barco26);
			gp6.addShip(barco27);
			gp6.addShip(barco28);
			gp6.addShip(barco29);
			gp6.addShip(barco30);


			//Añadimos salvos a gameplayer
         	gp1.addSalvo(salvo1);
         	gp2.addSalvo(salvo2);
         	gp1.addSalvo(salvo3);
         	gp2.addSalvo(salvo4);
         	gp3.addSalvo(salvo5);
         	gp4.addSalvo(salvo6);
         	gp3.addSalvo(salvo7);
         	gp4.addSalvo(salvo8);

         	//Añadimos puntuacion a games
            game1.addScore(score1);
            game1.addScore(score4);
            game2.addScore(score3);
            game2.addScore(score2);
            game3.addScore(score5);
            game3.addScore(score6);
            game4.addScore(score7);
            game4.addScore(score10);
            game5.addScore(score13);
            game5.addScore(score14);
            game6.addScore(score8);
            game6.addScore(score11);
            game7.addScore(score9);
            game7.addScore(score12);
            game8.addScore(score1);
            game8.addScore(score7);


            //Añadimos puntuacion a players
            player1.addScore(score1);
            player2.addScore(score4);
            player3.addScore(score3);
            player4.addScore(score2);
            player5.addScore(score5);
            player6.addScore(score6);
            player6.addScore(score8);
            player5.addScore(score9);
            player4.addScore(score10);
            player3.addScore(score11);
            player2.addScore(score12);
            player2.addScore(score13);
            player2.addScore(score14);

			//Guardamos jugadores
			playerRepo.save(player1);
			playerRepo.save(player2);
			playerRepo.save(player3);
			playerRepo.save(player4);
			playerRepo.save(player5);
			playerRepo.save(player6);

			//Guardamos juegos
			gameRepo.save(game1);
			gameRepo.save(game2);
			gameRepo.save(game3);
			gameRepo.save(game4);
			gameRepo.save(game5);
			gameRepo.save(game6);
			gameRepo.save(game7);
			gameRepo.save(game8);
			gameRepo.save(game9);

			//Guardamos gameplayers
			gamePlayerRepo.save(gp1);
			gamePlayerRepo.save(gp2);
			gamePlayerRepo.save(gp3);
			gamePlayerRepo.save(gp4);
			gamePlayerRepo.save(gp5);
			gamePlayerRepo.save(gp6);
			gamePlayerRepo.save(gp7);
			gamePlayerRepo.save(gp8);
			gamePlayerRepo.save(gp9);
			gamePlayerRepo.save(gp10);
			gamePlayerRepo.save(gp11);
			gamePlayerRepo.save(gp12);
			gamePlayerRepo.save(gp13);
			gamePlayerRepo.save(gp14);
			gamePlayerRepo.save(gp15);
			gamePlayerRepo.save(gp16);
			gamePlayerRepo.save(gp17);

			//Guardamos ships
			shipRepo.save(barco1);
			shipRepo.save(barco2);
			shipRepo.save(barco3);
			shipRepo.save(barco4);
			shipRepo.save(barco5);
			shipRepo.save(barco6);
			shipRepo.save(barco7);
			shipRepo.save(barco8);
			shipRepo.save(barco9);
			shipRepo.save(barco10);
			shipRepo.save(barco11);
			shipRepo.save(barco12);
			shipRepo.save(barco13);
			shipRepo.save(barco14);
			shipRepo.save(barco15);
			shipRepo.save(barco16);

			//Guardamos salvos
			salvoRepo.save(salvo1);
			salvoRepo.save(salvo2);
			salvoRepo.save(salvo3);
			salvoRepo.save(salvo4);
			salvoRepo.save(salvo5);
			salvoRepo.save(salvo6);
			salvoRepo.save(salvo7);
			salvoRepo.save(salvo8);

			//Guardamos puntuaciones
            scoreRepo.save(score1);
            scoreRepo.save(score2);
            scoreRepo.save(score3);
            scoreRepo.save(score4);
            scoreRepo.save(score5);
            scoreRepo.save(score6);
            scoreRepo.save(score7);
            scoreRepo.save(score8);
            scoreRepo.save(score9);
            scoreRepo.save(score10);
            scoreRepo.save(score11);
            scoreRepo.save(score12);
            scoreRepo.save(score13);
            scoreRepo.save(score14);


		};
		return commandLineRunner;

	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Optional<Player> opPlayer = playerRepository.findByUserName(inputName);
			if (opPlayer.isPresent()) {
				return new User(opPlayer.get().getUserName(), opPlayer.get().getPassword(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/**")
				.permitAll()
				.anyRequest()
				.hasAuthority("USER");

		http.formLogin().loginPage("/api/login").usernameParameter("userName").passwordParameter("password");

		http.logout().logoutUrl("/api/logout");

		// desactivar la comprobación de tokens CSRF
		http.csrf().disable();

		// Si el usuario no está autenticado, simplemente envíe una respuesta de error de autenticación
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// Si el inicio de sesión es exitoso, simplemente borre las banderas solicitando autenticación
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		//Si el inicio de sesión falla, simplemente envíe una respuesta de error de autenticación
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		//Si el cierre de sesión es exitoso, simplemente envíe una respuesta exitosa
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}