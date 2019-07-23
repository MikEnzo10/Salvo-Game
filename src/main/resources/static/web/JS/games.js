const vm = new Vue({
    el: '#app',
    data: {

        myJson: [],
        myGames: [],
        myPlayer: [],
        users: [],
        arrayLeaderPlayers: [],
        nietoArray: [],
        inputUser: [],
        inputPass: [],
        idPlayers: [],
    },

    methods: {

        llamarFetch() {

            fetch("http://localhost:8080/api/games", {

                method: "GET",

            }).then(response => {

                if (response.ok) {
                    return response.json();
                }
                // throw new Error(response.statusText);
            }).then(json => {

                this.myJson = json;

                this.myGames = this.myJson.games;

                this.myPlayer = this.myJson.player;

                console.log("myPlayer", this.myPlayer)

                console.log ("myGames", this.myGames);

                console.log ("myJson", this.myJson)

                this.arrayPlayer();

            }).catch(function (error) {
                console.log("Request failed: " + error.message);
            });
        },

        arrayPlayer() {

            let arrayID = [];

            for (let i = 0; i < this.myGames.length; i++) {

                let hijoArray = this.myGames[i].gamePlayer;

                for (let j = 0; j < hijoArray.length; j++) {

                    this.nietoArray = this.myGames[i].gamePlayer[j].player;

                    if (!arrayID.includes(this.nietoArray.id)) {

                        arrayID.push(this.nietoArray.id);

                        this.arrayLeaderPlayers.push(this.nietoArray);
                    }
                }
              }

            },

        login() {

            let inputUser = document.getElementById("username").value;
            console.log(inputUser)
            let inputPass = document.getElementById("password").value;
            console.log(inputPass)


            fetch("http://localhost:8080/api/login", {

                method: "POST",
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json'
                },
                body: `userName=${inputUser}&password=${inputPass}`

            }).then(response => {

                console.log(response)
                if (response.ok) {
                    window.location.reload();
                }

                // throw new Error(response.statusText);
            }).catch(function (error) {
                console.log("Request failed: " + error.message);

            });
        },

        logout() {

            fetch("http://localhost:8080/api/logout", {

                method: "POST",

            }).then(response => {

                console.log(response)
                if (response.ok) {
                    window.location.reload();
                }

                // throw new Error(response.statusText);
            }).catch(function (error) {
                console.log("Request failed: " + error.message);

            });
        },

        register() {

            fetch("http://localhost:8080/api/players", {

                method: "POST",
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({userName:"miguelElFilosofo",password:"5454"})

            }).then(response => {

               console.log(response.status);
               return response.json();

                // throw new Error(response.statusText);
            }).then(json => {
                console.log(json);
            }).catch(function (error) {
                console.log("Request failed: " + error.message);

            });
        },

        checkGame(game){
            let idPlayers = [];
            let gp = game.gamePlayer;

            for (let i=0; i < gp.length; i++){
                console.log(gp[i].player);
                idPlayers.push(gp[i].player.id)
            }
            console.log(idPlayers)
            if(this.myPlayer != null) {
                return idPlayers.includes(this.myPlayer.id)
            } else {
                return false;
            }

            // LO MISMO CON MAP
            // if(this.myPlayer != null) {
            //     return game.gamePlayer.map(gp => gp.player.id).includes(this.myPlayer.id);
            // } else {
            //     return false;
            // }
            //
            // LO MISMO CON MAP Y CONDICIONAL TERNARIO
            // return this.myPlayer
            //     ? game.gamePlayer.map(gp => gp.player.id).includes(this.myPlayer.id)
            //     : false


        },

        createGame() {

            let responseStatus = []


            fetch("http://localhost:8080/api/games", {

                method: "POST",

            }).then(response => {

                console.log(response.status);
                responseStatus = response.status;
                return response.json();
                // throw new Error(response.statusText);
            }).then(json => {
                if(responseStatus == 201){
                    window.location.replace("http://localhost:8080/web/game.html?Gp=" + json.gpId )
                }else if(responseStatus == 401){
                    console.log("UNAUTHORIZED!")
                }else{
                    console.log("something has gone wrong")
                }
                console.log(json.gpId);
            }).catch(function (error) {
                console.log("Request failed: " + error.message);
            });
        },
    },

        mounted() {
            this.llamarFetch();
        }
});