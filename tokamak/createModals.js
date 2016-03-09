function createModals() {
	    modalRight = new PIXI.Sprite(id["modaltest.png"]);
        modalRight.position.set(torCenter.x + Machine.diam/2,torCenter.y);
        modalRight.alpha = 0.1;
        tokaModals.addChild(modalRight);

	    modalVessel = new PIXI.Sprite(id["modaltest.png"]);
        modalVessel.position.set(torCenter.x + Machine.diam/2,torCenter.y);
        modalVessel.alpha = 0.8;
        tokaModals.addChild(modalVessel);

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

        modalCity = new PIXI.Sprite(id["modaltest.png"]);
        modalCity.position.set(100,100);
        modalCity.alpha = 0.8;
        cityModals.addChild(modalCity);

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
        modalCitySpace.txt = "city";
        buttonizeModal(modalCitySpace);
        modalCitySpace.alpha = 0;
        




}










