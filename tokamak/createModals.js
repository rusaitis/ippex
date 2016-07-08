document.addEventListener('keydown', onKeyDown);

function onKeyDown(key) {
    if (key.keyCode == 77) {
        if (stateVar.modal.coils) window.open("http://www.google.com");
        if (stateVar.modal.city) window.open("http://www.facebook.com");
        if (stateVar.modal.aux) window.open("https://www.iter.org/mach/heating");
        if (stateVar.modal.reactor) window.open("https://en.wikipedia.org/wiki/Tokamak")
    }
    if (key.keyCode == 82) {
        if (stateVar.modal.reactor) window.open("http://scied-web.pppl.gov/rgdx/")
    }
}

function createModals() {

        modalPos = {
            left1 : {
                x : 18.2,
                y : 90
            },
            right1 : {
                x : 650,
                y : 90
            },
            center1 : {
                x : 165,
                y : 90
            }
        };


        // greenModal = new PIXI.RoundedRectangle(0,0,modalPos.coils.w,modalPos.coils.h);
        

	    modalMagnet = new PIXI.Sprite(id["modalMagnet.png"]);
        modalMagnet.position.set(modalPos.left1.x,modalPos.left1.y);
        modalMagnet.alpha = 0.8;
        tokaModals.addChild(modalMagnet);

        modalAux = new PIXI.Sprite(id["modalAux.png"]);
        modalAux.position.set(modalPos.right1.x,modalPos.right1.y);
        modalAux.alpha = 0.8;
        tokaModals.addChild(modalAux);

        modalCentralSolenoid = new PIXI.Sprite(id["modalCentralSolenoid.png"]);
        modalCentralSolenoid.position.set(modalPos.left1.x,modalPos.left1.y);
        modalCentralSolenoid.alpha = 0.8;
        tokaModals.addChild(modalCentralSolenoid);

        modalVessel = new PIXI.Sprite(id["modalVessel.png"]);
        modalVessel.position.set(modalPos.left1.x,modalPos.left1.y);
        modalVessel.alpha = 0.8;
        tokaModals.addChild(modalVessel);

        modalBlanket = new PIXI.Sprite(id["modalBlanket.png"]);
        modalBlanket.position.set(modalPos.right1.x,modalPos.right1.y);
        modalBlanket.alpha = 0.8;
        exchangerModals.addChild(modalBlanket);

        modalTritium = new PIXI.Sprite(id["modalTritium.png"]);
        modalTritium.position.set(modalPos.right1.x,modalPos.right1.y);
        modalTritium.alpha = 0.8;
        exchangerModals.addChild(modalTritium);

        modalHeat = new PIXI.Sprite(id["modalHeat.png"]);
        modalHeat.position.set(modalPos.left1.x,modalPos.left1.y);
        modalHeat.alpha = 0.8;
        exchangerModals.addChild(modalHeat);

        modalSteam = new PIXI.Sprite(id["modalSteam.png"]);
        modalSteam.position.set(modalPos.left1.x,modalPos.left1.y);
        modalSteam.alpha = 0.8;
        exchangerModals.addChild(modalSteam);

        modalReactor = new PIXI.Sprite(id["modalReactor.png"]);
        modalReactor.position.set(modalPos.center1.x,modalPos.center1.y);
        modalReactor.alpha = 0.8;
        topModals.addChild(modalReactor);

        modalExchanger = new PIXI.Sprite(id["modalExchanger.png"]);
        modalExchanger.position.set(modalPos.center1.x,modalPos.center1.y);
        modalExchanger.alpha = 0.8;
        topModals.addChild(modalExchanger);

        modalCity = new PIXI.Sprite(id["modalCity.png"]);
        modalCity.position.set(modalPos.center1.x,modalPos.center1.y);
        modalCity.alpha = 0.8;
        topModals.addChild(modalCity);

        modalVesselSpace = new PIXI.Sprite(id["outerWall.png"]);
        modalVesselSpace.anchor.set(0.5,0.5);
        modalVesselSpace.width = Machine.diam;
        modalVesselSpace.height = Machine.diam;
        modalVesselSpace.position.set(torCenter.x,torCenter.y);
        modalVesselSpace.alpha = 0.0;
        tokaModals.addChild(modalVesselSpace);
        modalVesselSpace.txt = "vessel";
        buttonizeModal(modalVesselSpace);

        modalLeft = new PIXI.Sprite(id["modaltest.png"]);
        modalLeft.position.set(torCenter.x - 2*Machine.diam/2,torCenter.y);
        modalLeft.alpha = 0.8;
        tokaModals.addChild(modalLeft);


        modalCenterStackSpace = new PIXI.Sprite(id["innerWall.png"]);
        modalCenterStackSpace.anchor.set(0.5,0.5);
        modalCenterStackSpace.width = 2 * (Torus.R - Torus.r) - 2 * Machine.sol;
        modalCenterStackSpace.height = 2 * (Torus.R - Torus.r) - 2 * Machine.sol;
        modalCenterStackSpace.position.set(torCenter.x,torCenter.y);
        modalCenterStackSpace.alpha = 0.0;
        tokaModals.addChild(modalCenterStackSpace);
        modalCenterStackSpace.txt = "centerStack";
        buttonizeModal(modalCenterStackSpace);

        modalAntennaSpace = new PIXI.Sprite(id["antennaRed.png"]);
	    modalAntennaSpace.position.set(antennaPos[0],antennaPos[1]);
        modalAntennaSpace.alpha = 0.0;
        tokaModals.addChild(modalAntennaSpace);
        modalAntennaSpace.txt = "aux";
        buttonizeModal(modalAntennaSpace);

        coilSquares = [1/Math.sqrt(2)*(Torus.R-Torus.r-Machine.sol/2),1/Math.sqrt(2)*Torus.R,1/Math.sqrt(2)*(Torus.R+Torus.r+Machine.sol/2),40]
        
        allCoilSquares = [];
        coilGraph = new PIXI.Graphics();
        coilGraph.beginFill(0x8bc5ff, 0.4);
        coilGraph.drawRect(0,0,coilSquares[3],coilSquares[3]);

        for(var i=0 ; i<12 ; i++) {
	        modalCoil = new PIXI.Sprite(coilGraph.generateTexture(false));
	        modalCoil.anchor.set(0.5,0.5);
	        modalCoil.position.set(torCenter.x+Math.pow(-1,Math.floor(i/6)%2)*coilSquares[i%3],torCenter.y+Math.pow(-1,Math.floor(i/3)%2)*coilSquares[i%3]);  
	        // modalCoil.position.set(torCenter.x+Math.pow(-1,(Math.floor(i/6)%2))*coilSquares[1],torCenter.y+coilSquares[1];        	      	
            allCoilSquares.push(modalCoil);
            tokaModals.addChild(modalCoil);
            modalCoil.txt="coils";
            buttonizeModal(modalCoil);
            modalCoil.alpha=0.;
        }

        modalTririumSpace = new PIXI.Sprite(id["tritiumRed.png"]);
        modalTririumSpace.position.set(328.1,259);
        exchangerModals.addChild(modalTririumSpace);
        modalTririumSpace.txt = "tritium";
        buttonizeModal(modalTririumSpace);
        modalTririumSpace.alpha = 0;

        modalHeatSpace = new PIXI.Sprite(id["heatRed.png"]);
        modalHeatSpace.position.set(462.5,257.78);
        exchangerModals.addChild(modalHeatSpace);
        modalHeatSpace.txt = "heat";
        buttonizeModal(modalHeatSpace);
        modalHeatSpace.alpha = 0; 

        modalSteamSpace = new PIXI.Sprite(id["steamRed.png"]);
        modalSteamSpace.position.set(713.3,251.5);
        exchangerModals.addChild(modalSteamSpace);
		modalSteamSpace.txt = "steam";
		buttonizeModal(modalSteamSpace);
		modalSteamSpace.alpha = 0;

        modalBlanketSpace = new PIXI.Sprite(id["redBlanket.png"]);
        modalBlanketSpace.position.set(96.4,206.73);
        exchangerModals.addChild(modalBlanketSpace);
        modalBlanketSpace.txt = "blanket";
        buttonizeModal(modalBlanketSpace);
        modalBlanketSpace.alpha = 0;

        modalCitySpace = new PIXI.Sprite(id["cityRed.png"]);
        modalCitySpace.position.set(405.3,174);
        cityModals.addChild(modalCitySpace);
        modalCitySpace.txt = "cityscape";
        buttonizeModal(modalCitySpace);
        modalCitySpace.alpha = 0;

        healthTextRed = new PIXI.Text("Wall Health:");
        healthTextRed.style = {fill:"red", font:"20px calibri"};
        // healthTextRed.text = "Density: ";
        healthTextRed.anchor.set(0,1);
        healthTextRed.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.healthBot);
        topModals.addChild(healthTextRed);
        healthTextRed.txt = "health";
        buttonizeModal(healthTextRed);


        tempTextRed = new PIXI.Text("Temperature:");
        tempTextRed.style = {fill:"red", font:"20px calibri"};
        // tempTextRed.text = "Density: ";
        tempTextRed.anchor.set(0,1);
        tempTextRed.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.tempBot);
        topModals.addChild(tempTextRed);
        tempTextRed.txt = "temperature";
        buttonizeModal(tempTextRed);

        elecTextRed = new PIXI.Text("Electric Power:");
        elecTextRed.style = {fill:"red", font:"20px calibri"};
        // elecTextRed.text = "Density: ";
        elecTextRed.anchor.set(0,1);
        elecTextRed.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.elecBot);
        topModals.addChild(elecTextRed);
        elecTextRed.txt = "electric";
        buttonizeModal(elecTextRed);

        scoreTextRed = new PIXI.Text("Score:");
        scoreTextRed.style = {fill:"red", font:"40px calibri"};
        scoreTextRed.anchor.set(0,1);
        scoreTextRed.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.scoreBot);
        topModals.addChild(scoreTextRed);
        scoreTextRed.txt = "score";
        buttonizeModal(scoreTextRed);



        
}

function workModals() {

        if (stateVar.modal.vessel) {
            outerWallDanger.alpha = 1;
            innerWallDanger.alpha = 0;
            modalVessel.visible = true;
        } else {
            modalVessel.visible = false;
        }

        if (stateVar.modal.centerStack) {
            modalCentralSolenoid.visible = true;
            outerWallDanger.alpha = 0;
            innerWallDanger.alpha = 1;
        } else {
            modalCentralSolenoid.visible = false;
        }
        if (stateVar.modal.aux) {
            antennaRed.visible = true;
            modalAux.visible = true;
            // modalCenterStack.visible = true;
        } else {
            modalAux.visible = false;
            antennaRed.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.coils) {
            modalMagnet.visible = true;
            coilsRed.visible = true;
            // modalCenterStack.visible = true;
        } else {
            modalMagnet.visible = false;
            coilsRed.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.tritium) {
            modalTritium.visible = true;
            tritiumRed.visible = true;
            // modalCenterStack.visible = true;
        } else {
            modalTritium.visible = false;
            tritiumRed.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.heat) {
            modalHeat.visible = true;
            heatRed.visible = true;
            // modalCenterStack.visible = true;
        } else {
            modalHeat.visible = false;
            heatRed.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.steam) {
            steamRed.visible = true;
            modalSteam.visible = true;
            // modalCenterStack.visible = true;
        } else {
            modalSteam.visible = false;
            steamRed.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.blanket) {
            modalBlanket.visible = true;
            redBlanket.visible = true;
            // modalCenterStack.visible = true;
        } else {
            modalBlanket.visible = false;
            redBlanket.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.cityscape) {

            cityRed.visible = true;
            // modalCenterStack.visible = true;
        } else {

            cityRed.visible = false;
            // modalCenterStack.visible = false;
        }
        if (stateVar.modal.health) {
            healthText.alpha = 0;
            healthTextRed.alpha = 1;
        }
        else {
            healthText.alpha = 1;
            healthTextRed.alpha = 0;
        }
        if (stateVar.modal.temperature) {
            tempText.alpha = 0;
            tempTextRed.alpha = 1;
        }
        else {
            tempText.alpha = 1;
            tempTextRed.alpha = 0;
        }
        if (stateVar.modal.electric) {
            elecText.alpha = 0;
            elecTextRed.alpha = 1;
        }
        else {
            elecText.alpha = 1;
            elecTextRed.alpha = 0;
        }
        if (stateVar.modal.score) {
        	scoreText.alpha = 0;
            scoreTextRed.alpha = 1;
            console.log(stateVar.modal.health);
        }
        else {
            scoreText.alpha = 1;
            scoreTextRed.alpha = 0;
        }
        if (stateVar.modal.reactor) {
            modalReactor.visible = true;
        } else {
            modalReactor.visible = false;
        }
        if (stateVar.modal.exchanger) {
            modalExchanger.visible = true;
        } else {
            modalExchanger.visible = false;
        }
        if (stateVar.modal.city) {
            modalCity.visible = true;
        } else {
            modalCity.visible = false;
        }
}










