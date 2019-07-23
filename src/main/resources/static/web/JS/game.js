const vm = new Vue({
    el: '#appGame',
    data: {
        gamePlayerId: new URL(window.location.href).searchParams.get("Gp"),
        myJsonGame: [],
        arrayFilas: ["A", "B", "C", "D", "E", "F" , "G" , "H" , "I" , "J"],
        arrayColumnas: [1, 2, 3, 4 ,5 ,6 ,7 ,8 ,9 ,10],
        player1Name: "",
        player2Name: "",
    },

    methods: {

        llamarFetchGame() {

            fetch("http://localhost:8080/api/game_view/"+this.gamePlayerId, {

                method: "GET",

            }).then(response => {

                if(response.ok){
                    return response.json();
                }

                // throw new Error(response.statusText);
            }).then(json => {


                this.myJsonGame = json;

                this.gamePlayers = this.myJsonGame.gamePlayer;

                console.log(this.myJsonGame)

                // const idCeldas = document.querySelectorAll('td');
                //
                // const celdasArray = Array.from(idCeldas);
                //
                // console.log (celdasArray);

                this.pintarBarcos();

                this.pintarSalvos();

                this.player1Name = this.mostrarPlayer1();

                this.player2Name = this.mostrarPlayer2();

            }).
            catch(function (error) {
                console.log("Request failed: " + error.message);
            });
        },

        pintarBarcos(){

            let gpFiltrado = this.myJsonGame.gamePlayer.filter(gamePlayer => gamePlayer.id == this.gamePlayerId)

            let gpPlayerId = gpFiltrado[0].player.id


            this.myJsonGame.ships.flatMap(ship => ship.location).
            forEach(position => document.getElementById('ship' + position).style.backgroundColor = "powderblue");

            this.myJsonGame.salvos.filter(salvo => salvo.player_id != gpPlayerId)
                .forEach(salvo => salvo.turnLocation.forEach( position => {
                    document.getElementById('ship' + position).style.backgroundColor = "red";
                    document.getElementById('ship' + position).innerHTML = salvo.turn;
                }))
        },

        pintarSalvos(){

            let gpFiltrado = this.myJsonGame.gamePlayer.filter(gamePlayer => gamePlayer.id == this.gamePlayerId)

            let gpPlayerId = gpFiltrado[0].player.id

            this.myJsonGame.salvos.filter(salvo => salvo.player_id == gpPlayerId)
                .forEach(salvo => salvo.turnLocation.forEach( position => {
                    document.getElementById('salvo' + position).style.backgroundColor = "red";
                    document.getElementById('salvo' + position).innerHTML = salvo.turn;
            }))

        },

        mostrarPlayer1(){

            let gp1 = this.myJsonGame.gamePlayer.find(gp => gp.id == this.gamePlayerId)

            return gp1.player.name + "(you)";
        },

        mostrarPlayer2(){

            let gp2 = this.myJsonGame.gamePlayer.find(gp => gp.id != this.gamePlayerId)

            return gp2.player.name;
        },

    },

    mounted() {
        this.llamarFetchGame();

    }
});