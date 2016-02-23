    function endgame() {
        introContainer.visible = false;
        gameContainer.visible = false;
        endContainer.visible = true;
        endText2.visible = false;
        endText3.visible = false;
        highScoreText.visible = false;
        restartBut.visible = false;
        forceCalculation = true;
        if (stateVar.endonce) {
            endContainer.alpha = 1;
            alarm.stop();
            shutdown.play();
        }


        if (stateVar.countStart.state){
            // startManual.visible = false;
            var dif = count - stateVar.countStart.timer;
            endText1.visible = true;
            if (dif >15) {
                endText2.visible = true;
                // endText3.text = highScore.toFixed(0);
                endText3.visible = true;
                highScoreText.text = highScore.toFixed(0);
                restartBut.visible =true;
                highScoreText.visible = true;
                // console.log(highScore.toFixed(0));


            }

            if (stateVar.countRestart.state){
                var dif = count - stateVar.countRestart.timer;
                endContainer.alpha = 1 - Math.pow(dif/5.,2);
                console.log('here');
                if (dif>5) {
                    state = play;
                    disruptions=0;
                    densBut.position.x = contPos.densSlider.pos[0] + contPos.arrowWidth + contPos.buffer;
                    magBut.position.x = contPos.magSlider.pos[0] + contPos.arrowWidth + contPos.buffer;
                    auxBut.position.x = contPos.auxSlider.pos[0] + contPos.arrowWidth + contPos.buffer;
                    stateVar.countStart.state = false;
                    stateVar.countRestart.state = false;
                }

            }
        }

        stateVar.endonce = false;
    }