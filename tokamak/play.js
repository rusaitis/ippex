function play() {
        stateVar.endonce = true;
        introContainer.visible = false;
        gameContainer.visible = true;
        endContainer.visible = false;
// console.log("here now")
        ext.dens.value = (((densBut.x - contPos.densSlider.pos[0] - contPos.arrowWidth - contPos.buffer)/contPos.sliderRange)*(ext.dens.max-ext.dens.min) + ext.dens.min).toFixed(2);
        ext.aux.value = (((auxBut.x - contPos.auxSlider.pos[0] - contPos.arrowWidth - contPos.buffer)/contPos.sliderRange)*(ext.aux.max-ext.aux.min) + ext.aux.min).toFixed(2);
        ext.mag.value = (((magBut.x - contPos.magSlider.pos[0] - contPos.arrowWidth - contPos.buffer)/contPos.sliderRange)*(ext.mag.max-ext.mag.min) + ext.mag.min).toFixed(2);



        magVal = ext.mag.value;
        densVal = ext.dens.value;
        powVal = ext.aux.value;


        if (count % 2 < 0.1 || count == 0 || forceCalculation) {
            forceCalculation = false;
            finalTemptemp = bisectionAdvanced();
            finalTemp = (isNaN(finalTemptemp) || finalTemptemp<0) ? 0 : finalTemptemp;
            // console.log(finalTemp);

            finalOut = calcScore(finalTemp);
            if (finalOut.betalimited || finalOut.densitylimited) {  //Plasma is off because of limits
                stateVar.plasmaon = false;
                // finalOut.tempkev = 0;
                if (!stateVar.justexploded) {
                    stateVar.justexploded = true;
                    explosion3.play();
                    turnon.stop();
                    running.stop();
                    disruptions += 1;
                    if (disruptions >= maxDisruptions) {
                        state = endgame;
                        stateVar.countStart.state = true;
                        stateVar.countStart.timer = count;


                    }
                }
                } else {
                if (stateVar.justexploded) {
                    turnon.play()
                    running.play();
                }


                stateVar.plasmaon = true;
                stateVar.justexploded = false;

            }
                // running.play();
            
            // shoot.play();
            // if (justexploded) {
            //     // alert("justexploded");
            //     // shoot.play();
            // }
}
        if (stateVar.plasmaon) {
            lastdens = densVal;
            lastaux = powVal;
            lastmag = magVal;
        }

        tempNum.text = finalOut.tempkev.toFixed(3) + " keV";
        elecNum.text = (finalOut.P_elec*1.e-6).toFixed(0) + " MW";
        scoreNum.text = finalOut.score.toFixed(0);
        highScore = Math.max(finalOut.score, highScore);
        if (finalOut.P_elec < 0) elecNum.style.fill = "red"; else elecNum.style.fill = "white";


        if (finalOut.betalimited) {
            betaLimitText.visible = true;
            // console.log("true");
            } else betaLimitText.visible = false;
        if (finalOut.densitylimited) densLimitText.visible = true; else densLimitText.visible = false;

        densNum.text = ext.dens.value;
        auxNum.text = ext.aux.value;
        magNum.text = ext.mag.value;
        // console.log(ext.mag.value)



        waveMove = (count % 6) * 10 ;
        wave.position.x =  torCenter.x - 255 + waveMove;
        // if (playButton.state == "down") shoot.play();
        // iterate through the dudes and update their position
        // finalTemp = "Here";
        // $("#temperature").html("TEMPERATURE:" +finalTemp);
            // document.getElementById("temperature").value = ("TEMPERATURE: " + finalTemp);
                        // console.log(stateVar.plasmaon);
        // console.log(stateVar.plasmaon);
        // console.log(container.visible);
        // console.log(aliens[0].phi);
        for (var i = 0; i < aliens.length; i++)
        {
            var dude = aliens[i];
            dude.rotation = dude.direction;
            if (i > ext.dens.value*totalDudes/ext.dens.max || !(stateVar.plasmaon)) {
                    dude.visible = false;
                } else {
                    dude.visible = true;
                }
            dude.phiRun += dude.speed * 0.2 * Math.sqrt(finalTemp/1000);
            dude.phi = dude.phiRun + dude.phiStart;
            dude.R = Torus.R * plasmaPath(dude.phi, dude.phase, epsi);
            dude.scale.set(0.2*plasmaPathScale(dude.phi, dude.phase));
            dude.alpha = plasmaPathScale(dude.phi, Math.PI/8);

            dude.newPos.x = Math.cos(dude.phi) * dude.R;
            dude.newPos.y = Math.sin(dude.phi) * dude.R;
            var m = (dude.newPos.y - dude.oldPos.y) / (dude.newPos.x - dude.oldPos.x);
            var mPerp = - 1 / m;
            mPerp = Math.abs(mPerp);
            dude.mPerp = mPerp;
            dude.m = m;

            if (Math.tan(dude.phi) > 0) mPerp *= -1;
            var norm = Math.sqrt(1/(1 + mPerp * mPerp));

            dude.x = torCenter.x + Math.round(dude.newPos.x);
            dude.y = torCenter.y + Math.round(dude.newPos.y);

            dude.oldPos.x = dude.newPos.x;
            dude.oldPos.y = dude.newPos.y;

        }

        allcoilsglow.alpha = 1*magVal/ext.mag.max;
        // overLayGlow.alpha = 1;
        antennaTop.alpha = 1 - powVal/ext.aux.max;

        if (disruptions == maxDisruptions-1) {
            outerWallDanger.alpha = 0.5 * (1 + Math.cos(count));
            innerWallDanger.alpha = 0.5 * (1 + Math.cos(count));
            lifeBarDanger.alpha = 0.5 * (1 + Math.cos(count));
            if (!stateVar.lastDisruptionStart) {
                alarm.play();
                stateVar.lastDisruptionStart = true;
            }
        } else {
            outerWallDanger.alpha = 0;
            lifeBarDanger.alpha = 0;
            innerWallDanger.alpha = 0;
            stateVar.lastDisruptionStart = false;
        }

        lifeBar.width = lifeBarPos.barSize[0] * (1/11 + 10/11 * (maxDisruptions - 1 - disruptions)/(maxDisruptions - 1));

        if (stateVar.reactor) {
            reactorBut.texture = id["reactorButOn.png"];
            exchangerBut.texture = id["exchangerButOff.png"];
            cityBut.texture = id["cityButOff.png"];
            reactorBot.visible = true;
            reactorTop.visible = true;
            exchangerContainer.visible = false;
            cityContainer.visible = false;
        }

        if (stateVar.exchanger) {
            reactorBut.texture = id["reactorButOff.png"];
            exchangerBut.texture = id["exchangerButOn.png"];
            cityBut.texture = id["cityButOff.png"];
            reactorBot.visible = false;
            reactorTop.visible = false;
            exchangerContainer.visible = true;
            cityContainer.visible = false;
        }
        if (stateVar.city) {
            reactorBut.texture = id["reactorButOff.png"];
            exchangerBut.texture = id["exchangerButOff.png"];
            cityBut.texture = id["cityButOn.png"];
            reactorBot.visible = false;
            reactorTop.visible = false;
            exchangerContainer.visible = false;
            cityContainer.visible = true;
        }

        muteBut.texture = (stateVar.mute) ? id["mute.png"] : id["audio.png"];
        if (stateVar.mute) Howler.mute(); else Howler.unmute();

        // bisecBut.texture = (stateVar.bisec) ? id["buttonOn.png"] : id["buttonOff.png"];
        // if (densBut.x > contPos.densSlider.pos[0] + contPos.arrowWidth + contPos.buffer + contPos.sliderBoxLength/2) 
        //     densBut.x = contPos.densSlider.pos[0] + contPos.arrowWidth + contPos.buffer;

        
    }