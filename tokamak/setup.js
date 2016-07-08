function setup() {

        id = loader.resources["img/ippexsprites.json"].textures;


        //intro setup:
        introTitle = new PIXI.Sprite(id["startTitle.png"]);
        introTitle.position.set(0,88/1);
        startMenu.addChild(introTitle);

        introStartBut = new PIXI.Sprite(id["startButOff.png"]);
        introStartBut.position.set(364.1,462.8);
        startMenu.addChild(introStartBut);

        buttonizeStart(introStartBut);

        introManual = new PIXI.Sprite(id["introManual.png"]);
        introManual.position.set(83.394, 95.912);
        startManual.addChild(introManual);
        startManual.visible = false;

        //Game Container Setup

        //Reactor

        //Reactor bottom


        points = [];
        var pointCurve = [];
        var graphPoints = 1000;
        for (var j = 0; j < graphPoints; j++)
        {
            var phi = 2 * Math.PI * ThetaMax / graphPoints * j;
            var r = plasmaPath(phi, 0, epsi) * Torus.R;
            var size = plasmaPathScale(phi, 0);
            pointCurve.push({x: torCenter.x + r * Math.cos(phi), y: torCenter.y + r * Math.sin(phi), size: size});
        }

        points.push(pointCurve);

        bottomLines = new PIXI.Sprite(id["bottomLines.png"]);
        bottomLines.position.set(116.6,225.7);
        bottomLinesContainer.addChild(bottomLines);

        outerWall = new PIXI.Sprite(id["outerWall.png"]);
        outerWall.anchor.set(0.5,0.5);
        outerWall.width = Machine.diam;
        outerWall.height = Machine.diam;
        outerWall.position.set(torCenter.x,torCenter.y);
        vessel.addChild(outerWall);

        outerWallDanger = new PIXI.Sprite(id["outerWallDanger.png"]);
        outerWallDanger.anchor.set(0.5,0.5);
        outerWallDanger.width = Machine.diam;
        outerWallDanger.height = Machine.diam;
        outerWallDanger.position.set(torCenter.x,torCenter.y);
        vessel.addChild(outerWallDanger);
        outerWallDanger.alpha = 0;

        innerWall = new PIXI.Sprite(id["innerWall.png"]);
        innerWall.anchor.set(0.5,0.5);
        innerWall.width = 2 * (Torus.R - Torus.r) - 2 * Machine.sol;
        innerWall.height = 2 * (Torus.R - Torus.r) - 2 * Machine.sol;
        console.log(innerWall.height);
        innerWall.position.set(torCenter.x,torCenter.y);
        vessel.addChild(innerWall);

        innerWallDanger = new PIXI.Sprite(id["innerWallDanger.png"]);
        innerWallDanger.anchor.set(0.5,0.5);
        innerWallDanger.width = 2 * (Torus.R - Torus.r) - 2 * Machine.sol;
        innerWallDanger.height = 2 * (Torus.R - Torus.r) - 2 * Machine.sol;
        console.log(innerWallDanger.height);
        innerWallDanger.position.set(torCenter.x,torCenter.y);
        vessel.addChild(innerWallDanger);
        innerWallDanger.alpha = 0;


        antennaPos = [206.2,247.1,162.8,104.5];

        antennaMask = new PIXI.Graphics();
        antennaMask.beginFill(0x8bc5ff, 0.4);
        antennaMask.drawRect(antennaPos[0],antennaPos[1],antennaPos[2],antennaPos[3]);


        // antenna2.moveTo(torCenter.x+Math.cos(0.25)*Machine.diam/2,torCenter.y+Math.sin(0.25)*Machine.diam/2);
        // antenna2.lineStyle(2,0x7e7e7e7e);
        // antenna2.arc(torCenter.x , torCenter.y , Machine.diam/2 , Math.PI - rad , Math.PI + rad);
        // antenna2.lineTo(torCenter.x-Math.cos(rad)*Machine.diam/2-antennaLength,torCenter.y-Math.sin(rad)*Machine.diam/2);
        // antenna2.lineTo(torCenter.x-Math.cos(rad)*Machine.diam/2-antennaLength,torCenter.y+Math.sin(rad)*Machine.diam/2);
        // antenna2.lineTo(torCenter.x-Math.cos(rad)*Machine.diam/2,torCenter.y+Math.sin(rad)*Machine.diam/2);

        // antenna2.drawRect(550,torCenter.y-50,100,100);
        // stage.addChild(antennaMask);

        antennaTop = new PIXI.Sprite(id["antenna_top.png"]);
        antennaTop.position.set(antennaPos[0],antennaPos[1]);
        rfCover.addChild(antennaTop);


        // stage.addChild(antenna);

        wave = new PIXI.Sprite(id["wave_purple.png"]);
        wave.anchor.set(0.5,0.5);
        wave.position.set(torCenter.x-230,torCenter.y);
        rfContainer.addChild(wave);





        // antennaPurple = new PIXI.Sprite(id["antennaPurple.png"]);
        // antennaPurple.anchor.set(0.5,0.5);
        // antennaPurple.position.set(torCenter.x-264,torCenter.y);

        antennaRed = new PIXI.Sprite(id["antennaRed.png"]);
        antennaRed.position.set(antennaPos[0],antennaPos[1]);
        antennaRed.visible = false;
        antennaRed.alpha = 0.75;
        rfCover.addChild(antennaRed);
 

        // stage.addChild(antennaPurple);
        // rfContainer.addChild(antenna);
        // stage.addChild(wave);

        rfContainer.mask = antennaMask;



        allcoils = new PIXI.Sprite(id["coils_all4.png"]);
        allcoils.anchor.set(0.5,0.5);
        allcoils.position.set(torCenter.x,torCenter.y);
        coilcontainer.addChild(allcoils);
        allcoils.alpha = 0.5;

        // allcoilsglow = new PIXI.Sprite(id["coils_all4_glow.png"]);
        allcoilsglow = new PIXI.Sprite(id["glowcoils_whiter.png"]);
        allcoilsglow.anchor.set(0.5,0.5);
        allcoilsglow.position.set(torCenter.x,torCenter.y);
        coilcontainer.addChild(allcoilsglow);
        allcoilsglow.alpha = 1;

        coilsRed = new PIXI.Sprite(id["coilsRed.png"]);
        coilsRed.anchor.set(0.5,0.5);
        coilsRed.position.set(torCenter.x,torCenter.y);
        coilcontainer.addChild(coilsRed);
        coilsRed.visible = false;





        for (var i = 0; i < totalDudes; i++)

        {
            var angle = Math.random() * 2 * Math.PI * ThetaMax;
            var phase = Math.random() * 2 * Math.PI;
            var dude = new PIXI.Sprite(id["particle.png"]);


            dude.group = (Math.cos(angle) > Math.cos(Math.PI/8)) ? 1 : 0;
            if (angle < 1 * Math.PI) dude.label = 'batch';
            dude.anchor.set(0.5,0.5)
            // dude.scale.set(0.5,0.5);
            // dude.width = 10;
            // dude.length = 10;
            dude.R = 0;
            dude.position.x = 100;
            dude.position.y = 100;
            dude.phi = 0;
            dude.phiRun = 0;
            dude.phiStart = angle;
            dude.phase = phase;
            // dude.phase = 0;
            // dude.phiBias = rand;
            dude.displ = {};
            // dude.displ.x = (Math.random()-1) * 30;
            // dude.displ.y = (Math.random()-1) * 30;
            dude.displ.x = getRandomReal(-1,1) * ParticleSpread;
            dude.displ.y = getRandomReal(-1,1) * ParticleSpread;

            dude.oldPos = {};
            dude.oldPos.x = 0;
            dude.oldPos.y = 0;
            dude.newPos = {};
            dude.newPos.x = 0;
            dude.newPos.y = 0;
            // dude.mPerp = 0;
            dude.speed = stateVar.gaussianSpeeds ? zig.nextGaussian() : 1;
            // dude.speed = 1;
            aliens.push(dude);

            container.addChild(dude);
        }

        // test = new PIXI.Graphics();
        // test.lineStyle(1,0xFFFFFF);
        // test.drawRect(torCenter.x-100/2,torCenter.y-100/2,100,100);
        // container.addChild(test);
        //Control positions and sizes


        contPos = {
            box : {
                pos : [20,520],
                anchor : [0,0],
                size : [970,140]
            },
            textBot : 35,
            numFromText : 70,
            unitsFromNum : 20,
            slidersLeft : 20,
            slidersTop : 60,
            slidersSpace : 45,
            arrowWidth : 15,
            arrowHeight : 20,
            arrowLength : 120,
            arrowThick : 5,
            buttonWidth : 10,
            buttonHeight : 30,
            buffer : 5,
            outputLeft : 660,
            outputRight : 950,
            healthBot : 15,
            tempBot : 52,
            elecBot : 90,
            scoreBot : 137
        };
        contPos.slidersTot = 2 * contPos.arrowWidth + contPos.arrowLength;

        contPos.sliderBoxLength = contPos.arrowLength -  2 * contPos.buffer;
        contPos.sliderRange = contPos.sliderBoxLength - contPos.buttonWidth;
        contPos.densSlider = {
            pos : [contPos.box.pos[0] + contPos.slidersLeft , contPos.box.pos[1] + contPos.slidersTop]
        };        
        contPos.densText = {
            pos : [contPos.densSlider.pos[0] + 0.5 * contPos.slidersTot , contPos.box.pos[1] + contPos.textBot]
        };
        contPos.auxSlider = {
            pos : [contPos.densSlider.pos[0] + contPos.slidersTot + contPos.slidersSpace , contPos.box.pos[1] + contPos.slidersTop]
        };
        contPos.auxText = {
            pos : [contPos.auxSlider.pos[0] + 0.5 * contPos.slidersTot, contPos.box.pos[1] + contPos.textBot]
        };
        contPos.magSlider = {
            pos : [contPos.auxSlider.pos[0] + contPos.slidersTot + contPos.slidersSpace , contPos.box.pos[1] + contPos.slidersTop]
        };
        contPos.magText = {
            pos : [contPos.magSlider.pos[0] + 0.5 * contPos.slidersTot , contPos.box.pos[1] + contPos.textBot]
        };
        





        // controlBox = new PIXI.Graphics();
        // controlBox.lineStyle(0, 0x0a2332);
        // controlBox.beginFill(0x0a2332);
        // controlBox.drawRoundedRect(contPos.box.pos[0] , contPos.box.pos[1] , contPos.box.size[0] , contPos.box.size[1],5);
        // controlBox.endFill();
        // controlBox.alpha = 0.8;
        // // controls.addChild(controlBox);

        controlBox = new PIXI.Sprite(id["controlBox.png"]);
        controlBox.anchor.set(0,1);
        controlBox.position.x = 18.2;
        controlBox.position.y = 660;
        controls.addChild(controlBox);     


        // bottomLines = new PIXI.Sprite(id["bottomlines.png"]);
        // bottomLines.position.set(116.6,225.7);
        // bottomLinesContainer.addChild(bottomlines);   


        densSliderLeft = new PIXI.Sprite(id["greenArrowLeft.png"]);
        densSliderLeft.anchor.set(0,0.5);
        densSliderLeft.width = contPos.arrowWidth;
        densSliderLeft.height = contPos.arrowHeight;
        densSliderLeft.position.x = contPos.densSlider.pos[0];
        densSliderLeft.position.y = contPos.densSlider.pos[1];
        controls.addChild(densSliderLeft);


        densSliderLine = new PIXI.Sprite(id["greenArrowLine.png"]);
        densSliderLine.anchor.set(0,0.5);
        densSliderLine.width = contPos.arrowLength;
        densSliderLine.height = contPos.arrowThick;
        densSliderLine.position.x = contPos.densSlider.pos[0] + contPos.arrowWidth;
        densSliderLine.position.y = contPos.densSlider.pos[1];
        controls.addChild(densSliderLine);

        densSliderRight = new PIXI.Sprite(id["greenArrowRight.png"]);
        densSliderRight.anchor.set(0,0.5);
        densSliderRight.width = contPos.arrowWidth;
        densSliderRight.height = contPos.arrowHeight;
        densSliderRight.position.x = contPos.densSlider.pos[0] + contPos.arrowLength + contPos.arrowWidth;
        densSliderRight.position.y = contPos.densSlider.pos[1];
        controls.addChild(densSliderRight);

        densBut = new PIXI.Sprite(id["greenSlide.png"]);
        densBut.anchor.set(0,0.5);
        densBut.width = contPos.buttonWidth;
        densBut.height = contPos.buttonHeight;
        densBut.position.x = contPos.densSlider.pos[0] + contPos.arrowWidth + contPos.buffer;
        densBut.position.y = contPos.densSlider.pos[1];
        controls.addChild(densBut);

        densBut.buttonMode = true;
        // densBut.on('mouseover',overfun);

        densButBox = new PIXI.Graphics();
        // densButBox.lineStyle(2);
        densButBox.drawRect(contPos.densSlider.pos[0] + contPos.arrowWidth + contPos.buffer,
            contPos.densSlider.pos[1] - contPos.buttonHeight/2 ,
            contPos.sliderBoxLength, contPos.buttonHeight);
        controls.addChild(densButBox);
        densBut.draggable({manager: dragAndDropManager, containment : densButBox , cursor : "pointer" , axis : "x"});

        densText = new PIXI.Text("Density:");
        densText.style = {fill:"white", font:"20px calibri"};
        // densText.text = "Density: ";
        densText.anchor.set(0.5,1);
        densText.position.set(contPos.densText.pos[0],contPos.densText.pos[1]);
        controls.addChild(densText);

        densNum = new PIXI.Text("00.00");
        densNum.style = {fill:"white", font:"20px calibri"};
        // densNum.text = "Density: ";
        densNum.anchor.set(0.5,1);
        densNum.position.set(contPos.densText.pos[0],contPos.densText.pos[1]+contPos.numFromText);
        controls.addChild(densNum);

        densUnits = new PIXI.Text("m^(-3)");
        densUnits.style = {fill:"white", font:"20px calibri"};
        // densUnits.text = "Density: ";
        densUnits.anchor.set(0.5,1);
        densUnits.position.set(contPos.densText.pos[0],contPos.densText.pos[1]+contPos.numFromText+contPos.unitsFromNum);
        controls.addChild(densUnits);


        auxSliderLeft = new PIXI.Sprite(id["purpleArrowLeft.png"]);
        auxSliderLeft.anchor.set(0,0.5);
        auxSliderLeft.width = contPos.arrowWidth;
        auxSliderLeft.height = contPos.arrowHeight;
        auxSliderLeft.position.x = contPos.auxSlider.pos[0];
        auxSliderLeft.position.y = contPos.auxSlider.pos[1];
        controls.addChild(auxSliderLeft);


        auxSliderLine = new PIXI.Sprite(id["purpleArrowLine.png"]);
        auxSliderLine.anchor.set(0,0.5);
        auxSliderLine.width = contPos.arrowLength;
        auxSliderLine.height = contPos.arrowThick;
        auxSliderLine.position.x = contPos.auxSlider.pos[0] + contPos.arrowWidth;
        auxSliderLine.position.y = contPos.auxSlider.pos[1];
        controls.addChild(auxSliderLine);

        auxSliderRight = new PIXI.Sprite(id["purpleArrowRight.png"]);
        auxSliderRight.anchor.set(0,0.5);
        auxSliderRight.width = contPos.arrowWidth;
        auxSliderRight.height = contPos.arrowHeight;
        auxSliderRight.position.x = contPos.auxSlider.pos[0] + contPos.arrowLength + contPos.arrowWidth;
        auxSliderRight.position.y = contPos.auxSlider.pos[1];
        controls.addChild(auxSliderRight);

        auxBut = new PIXI.Sprite(id["purpleSlide.png"]);
        auxBut.anchor.set(0,0.5);
        auxBut.width = contPos.buttonWidth;
        auxBut.height = contPos.buttonHeight;
        auxBut.position.x = contPos.auxSlider.pos[0] + contPos.arrowWidth + contPos.buffer;
        auxBut.position.y = contPos.auxSlider.pos[1];
        controls.addChild(auxBut);

        auxBut.buttonMode = true;
        // auxBut.on('mouseover',overfun);

        auxButBox = new PIXI.Graphics();
        // auxButBox.lineStyle(2);
        auxButBox.drawRect(contPos.auxSlider.pos[0] + contPos.arrowWidth + contPos.buffer, contPos.auxSlider.pos[1] - contPos.buttonHeight/2 , contPos.sliderBoxLength, contPos.buttonHeight);
        controls.addChild(auxButBox);
        auxBut.draggable({manager: dragAndDropManager, containment : auxButBox , cursor : "pointer" , axis : "x"});

        auxText = new PIXI.Text("Auxiliary Power:");
        auxText.style = {fill:"white", font:"20px calibri"};
        // auxText.text = "Density: ";
        auxText.anchor.set(0.5,1);
        auxText.position.set(contPos.auxText.pos[0],contPos.auxText.pos[1]);
        controls.addChild(auxText);

        auxNum = new PIXI.Text("00.00");
        auxNum.style = {fill:"white", font:"20px calibri"};
        // auxNum.text = "Density: ";
        auxNum.anchor.set(0.5,1);
        auxNum.position.set(contPos.auxText.pos[0],contPos.auxText.pos[1]+contPos.numFromText);
        controls.addChild(auxNum);

        auxUnits = new PIXI.Text("MW");
        auxUnits.style = {fill:"white", font:"20px calibri"};
        // auxUnits.text = "Density: ";
        auxUnits.anchor.set(0.5,1);
        auxUnits.position.set(contPos.auxText.pos[0],contPos.auxText.pos[1]+contPos.numFromText+contPos.unitsFromNum);
        controls.addChild(auxUnits);





        magSliderLeft = new PIXI.Sprite(id["blueArrowLeft.png"]);
        magSliderLeft.anchor.set(0,0.5);
        magSliderLeft.width = contPos.arrowWidth;
        magSliderLeft.height = contPos.arrowHeight;
        magSliderLeft.position.x = contPos.magSlider.pos[0];
        magSliderLeft.position.y = contPos.magSlider.pos[1];
        controls.addChild(magSliderLeft);


        magSliderLine = new PIXI.Sprite(id["blueArrowLine.png"]);
        magSliderLine.anchor.set(0,0.5);
        magSliderLine.width = contPos.arrowLength;
        magSliderLine.height = contPos.arrowThick;
        magSliderLine.position.x = contPos.magSlider.pos[0] + contPos.arrowWidth;
        magSliderLine.position.y = contPos.magSlider.pos[1];
        controls.addChild(magSliderLine);

        magSliderRight = new PIXI.Sprite(id["blueArrowRight.png"]);
        magSliderRight.anchor.set(0,0.5);
        magSliderRight.width = contPos.arrowWidth;
        magSliderRight.height = contPos.arrowHeight;
        magSliderRight.position.x = contPos.magSlider.pos[0] + contPos.arrowLength + contPos.arrowWidth;
        magSliderRight.position.y = contPos.magSlider.pos[1];
        controls.addChild(magSliderRight);

        magBut = new PIXI.Sprite(id["blueSlide.png"]);
        magBut.anchor.set(0,0.5);
        magBut.width = contPos.buttonWidth;
        magBut.height = contPos.buttonHeight;
        magBut.position.x = contPos.magSlider.pos[0] + contPos.arrowWidth + contPos.buffer;
        magBut.position.y = contPos.magSlider.pos[1];
        controls.addChild(magBut);

        magBut.buttonMode = true;
        // magBut.on('mouseover',overfun);

        magButBox = new PIXI.Graphics();
        // magButBox.lineStyle(2);
        magButBox.drawRect(contPos.magSlider.pos[0] + contPos.arrowWidth + contPos.buffer, contPos.magSlider.pos[1] - contPos.buttonHeight/2 , contPos.sliderBoxLength, contPos.buttonHeight);
        controls.addChild(magButBox);
        magBut.draggable({manager: dragAndDropManager, containment : magButBox , cursor : "pointer" , axis : "x"});

        magText = new PIXI.Text("Magnetic Field:");
        magText.style = {fill:"white", font:"20px calibri"};
        // magText.text = "Density: ";
        magText.anchor.set(0.5,1);
        magText.position.set(contPos.magText.pos[0],contPos.magText.pos[1]);
        controls.addChild(magText);

        magNum = new PIXI.Text("00.00");
        magNum.style = {fill:"white", font:"20px calibri"};
        // magNum.text = "Density: ";
        magNum.anchor.set(0.5,1);
        magNum.position.set(contPos.magText.pos[0],contPos.magText.pos[1]+contPos.numFromText);
        controls.addChild(magNum);

        magUnits = new PIXI.Text("T");
        magUnits.style = {fill:"white", font:"20px calibri"};
        // magUnits.text = "Density: ";
        magUnits.anchor.set(0.5,1);
        magUnits.position.set(contPos.magText.pos[0],contPos.magText.pos[1]+contPos.numFromText+contPos.unitsFromNum);
        controls.addChild(magUnits);



        healthText = new PIXI.Text("Wall Health:");
        healthText.style = {fill:"white", font:"20px calibri"};
        // healthText.text = "Density: ";
        healthText.anchor.set(0,1);
        healthText.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.healthBot);
        controls.addChild(healthText);

        tempText = new PIXI.Text("Temperature:");
        tempText.style = {fill:"white", font:"20px calibri"};
        // tempText.text = "Density: ";
        tempText.anchor.set(0,1);
        tempText.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.tempBot);
        controls.addChild(tempText);

        elecText = new PIXI.Text("Electric Power:");
        elecText.style = {fill:"white", font:"20px calibri"};
        // elecText.text = "Density: ";
        elecText.anchor.set(0,1);
        elecText.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.elecBot);
        controls.addChild(elecText);

        scoreText = new PIXI.Text("Score:");
        scoreText.style = {fill:"white", font:"40px calibri"};
        scoreText.anchor.set(0,1);
        scoreText.position.set(contPos.box.pos[0] + contPos.outputLeft , contPos.box.pos[1] + contPos.scoreBot);
        controls.addChild(scoreText);



        tempNum = new PIXI.Text("");
        tempNum.style = {fill:"white", font:"20px calibri"};
        // tempNum.text = "Density: ";
        tempNum.anchor.set(1,1);
        tempNum.position.set(contPos.box.pos[0] + contPos.outputRight , contPos.box.pos[1] + contPos.tempBot);
        controls.addChild(tempNum);

        elecNum = new PIXI.Text("");
        elecNum.style = {fill:"white", font:"20px calibri"};
        // elecNum.text = "Density: ";
        elecNum.anchor.set(1,1);
        elecNum.position.set(contPos.box.pos[0] + contPos.outputRight , contPos.box.pos[1] + contPos.elecBot);
        controls.addChild(elecNum);

        scoreNum = new PIXI.Text("");
        scoreNum.style = {fill:"white", font:"40px calibri"};
        // scoreNum.text = "Density: ";
        scoreNum.anchor.set(1,1);
        scoreNum.position.set(contPos.box.pos[0] + contPos.outputRight , contPos.box.pos[1] + contPos.scoreBot);
        controls.addChild(scoreNum);


        topLines = new PIXI.Sprite(id["topLines.png"]);
        topLines.position.set(613,265.9);
        topLinesContainer.addChild(topLines);  


        // infoPos = {
        //     pos : [0,10],
        //     anchor : [0,0],
        //     size : [200,400]
        // }

        // infoBox = new PIXI.Graphics();
        // infoBox.lineStyle(0, 0x7e7e7e7e);
        // infoBox.beginFill(0x322850);
        // infoBox.drawRoundedRect(infoPos.pos[0],infoPos.pos[1],infoPos.size[0],infoPos.size[1],5);
        // infoBox.endFill();
        // infoBox.alpha = 0.5
        // controls.addChild(infoBox);

        lifeBarPos = {
            pos : [950,520],
            barPos : 5,
            barSize : [110,10]
        }


        lifeBar = new PIXI.Sprite(id["lifeBar.png"]);
        lifeBar.anchor.set(1,0);
        lifeBar.position.set(contPos.box.pos[0] + lifeBarPos.pos[0],lifeBarPos.pos[1]);
        lifeBar.width = lifeBarPos.barSize[0];
        lifeBar.height = lifeBarPos.barSize[1];
        controls.addChild(lifeBar);

        lifeBarShell = new PIXI.Sprite(id["lifeBarShell.png"]);
        lifeBarShell.anchor.set(1,0);
        lifeBarShell.position.set(contPos.box.pos[0] + lifeBarPos.pos[0],lifeBarPos.pos[1]);
        lifeBarShell.width = lifeBarPos.barSize[0];
        lifeBarShell.height = lifeBarPos.barSize[1];
        controls.addChild(lifeBarShell);

        lifeBarDanger = new PIXI.Sprite(id["lifeBarDanger.png"]);
        lifeBarDanger.anchor.set(1,0);
        lifeBarDanger.position.set(contPos.box.pos[0] + lifeBarPos.pos[0],lifeBarPos.pos[1]);
        lifeBarDanger.width = lifeBarPos.barSize[0];
        lifeBarDanger.height = lifeBarPos.barSize[1];
        lifeBarDanger.alpha = 0;
        controls.addChild(lifeBarDanger);


        topControlPos = {
            y : 27.2,
            reactorX : 20.5,
            exchangerX : 372.42,
            cityX : 755.5
        };

        reactorBut = new PIXI.Sprite(id["reactorButOn.png"]);
        reactorBut.position.set(topControlPos.reactorX,topControlPos.y);
        topControls.addChild(reactorBut);
        reactorBut.txt = "reactor";
        buttonizeViews(reactorBut);
        

        exchangerBut = new PIXI.Sprite(id["exchangerButOff.png"]);
        exchangerBut.position.set(topControlPos.exchangerX,topControlPos.y);
        topControls.addChild(exchangerBut);
        exchangerBut.txt = "exchanger";
        buttonizeViews(exchangerBut)

        cityBut = new PIXI.Sprite(id["cityButOff.png"]);
        cityBut.position.set(topControlPos.cityX,topControlPos.y);
        topControls.addChild(cityBut);
        cityBut.txt = "city";
        buttonizeViews(cityBut);



        densLimitText = new PIXI.Text("Density Limit Reached!");
        densLimitText.style = {fill:"red"};
        densLimitText.anchor.set(0.5,0.5);
        densLimitText.position.set(torCenter.x , torCenter.y - 15);
        densLimitText.visible = true;
        alertContainer.addChild(densLimitText);

        betaLimitText = new PIXI.Text("Beta Limit Reached!");
        betaLimitText.style = {fill:"red"};
        betaLimitText.anchor.set(0.5,0.5);
        betaLimitText.position.set(torCenter.x , torCenter.y + 15);
        betaLimitText.visible = true;
        alertContainer.addChild(betaLimitText);



        muteBut = new PIXI.Sprite(id["audio.png"]);
        muteBut.anchor.set(0.5,0.5);
        muteBut.position.set(40,640);
        muteBut.txt = "mute";
        controls.addChild(muteBut);

        buttonizeHold(muteBut);



        exchangerSquaresSprite = new PIXI.Sprite(id["exchangerSquares.png"]);
        exchangerSquaresSprite.position.set(328.1,250.7);
        exchangerSquaresContainer.addChild(exchangerSquaresSprite);

        tritiumRed = new PIXI.Sprite(id["tritiumRed.png"]);
        tritiumRed.position.set(328.1,259);
        exchangerSquaresContainer.addChild(tritiumRed);
        tritiumRed.visible = false;        

        heatRed = new PIXI.Sprite(id["heatRed.png"]);
        heatRed.position.set(462.5,257.78);
        exchangerSquaresContainer.addChild(heatRed); 
        heatRed.visible = false;

        steamRed = new PIXI.Sprite(id["steamRed.png"]);
        steamRed.position.set(713.3,251.5);
        exchangerSquaresContainer.addChild(steamRed);
        steamRed.visible = false;

        exchangerLinesSprite = new PIXI.Sprite(id["exchangerLines.png"]);
        exchangerLinesSprite.position.set(178.4,146.3);
        exchangerLinesContainer.addChild(exchangerLinesSprite);

        exchangerBlanketSprite = new PIXI.Sprite(id["exchangerBlanket.png"]);
        exchangerBlanketSprite.position.set(96.4,206.73);
        exchangerBlanketContainer.addChild(exchangerBlanketSprite);

        redBlanket = new PIXI.Sprite(id["redBlanket.png"]);
        redBlanket.position.set(96.4,206.73);
        exchangerBlanketContainer.addChild(redBlanket);
        redBlanket.visible = false;


        citySprite = new PIXI.Sprite(id["city.png"]);
        citySprite.position.set(405.3,174);
        citySubcontainer.addChild(citySprite);

        cityRed = new PIXI.Sprite(id["cityRed.png"]);
        cityRed.position.set(405.3,174);
        citySubcontainer.addChild(cityRed);
        cityRed.visible = false;



        createModals();







        running = new Howl({
          urls: ['../sounds/running.wav'],
          loop:true
        });

        turnon = new Howl({
            urls : ['../sounds/turnon.mp3'],
            volume : 0.4
        });

        explosion3 = new Howl({
            urls : ['../sounds/explosion3.wav']
        });

        explosion = new Howl({
            urls : ['../sounds/explosion.wav']
        });

        wilhelm = new Howl({
            urls : ['../sounds/wilhelm.wav']
        });    

        alarm = new Howl({
            urls : ['../sounds/alarm2.wav'],
            loop : true,
            volume : 0.1
        });

        select = new Howl({
            urls : ['../sounds/select.wav']
        });

        click = new Howl({
            urls : ['../sounds/click.mp3']
        });

        shutdown = new Howl({
            urls : ['../sounds/shutdown.wav']
        });

        // endScreen = new PIXI.Sprite(id['death.jpg']);
        // endScreen.width = 1010;
        // endScreen.height = 660;
        // endContainer.visible = false;
        // endContainer.addChild(endScreen);

        endText1 = new PIXI.Sprite(id["outroText1.png"]);
        endText1.anchor.set(0.5,0);
        endText1.position.set(505,114);
        endContainer.addChild(endText1);

        endText2 = new PIXI.Sprite(id["outroText2.png"]);
        endText2.anchor.set(0.5,0);
        endText2.position.set(505,317);
        endContainer.addChild(endText2);

        restartBut = new PIXI.Sprite(id["restartButOff.png"]);
        restartBut.anchor.set(0,0)
        restartBut.position.set(366.75,437);
        endContainer.addChild(restartBut);

        endText3 = new PIXI.Sprite(id["highScore.png"]);
        endText3.position.set(259.34,547.72);
        endContainer.addChild(endText3);

        highScoreText = new PIXI.Text("");
        highScoreText.position.set(656.17,547.72);
        highScoreText.style = {fill:"white", font:"50px calibri"};
        endContainer.addChild(highScoreText);
        // highScoreText.visible = true;



        buttonizeRestart();



        renderer.render(stage);
        gameLoop();

    }