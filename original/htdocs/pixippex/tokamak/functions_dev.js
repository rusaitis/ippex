function updateSliderBut(but, value, min, max, left, right){
  var pos = left + (value - min)/(max - min)*(right - left);
  but.x = pos;
}

function soundSetup(){
    shoot = sounds["../sound.js/sounds/shoot.wav"],
    music = sounds["../sound.js/sounds/music.wav"],
    explosion = sounds["../sound.js/sounds/explosion.wav"];
    wilhelm = sounds["../sound.js/sounds/wilhelm.wav"];

}


function loadProgressHandler(loader, resource) {
        //this can be used in the future for a loading bar
        // console.log("progress: " + loader.progress + "%")
    }

function getRandomReal(min, max) {
  return Math.random() * (max - min + 1) + min;
}
function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function plasmaPath(arg, phase, eps){
        // return (0.5 + 0.5 * Math.pow((Math.sin(2*arg), 2)));
        // return Math.pow(arg, 2);
        // return 0.5 + 0.5 * Math.pow(Math.sin(2 * (arg + phase)), 2);
        return (1-eps) + (2*eps) * Math.pow(Math.sin((arg + phase)/q), 2); 
}
function plasmaPathScale(arg, phase){
        // return (0.5 + 0.5 * Math.pow((Math.sin(2*arg), 2)));
        // return Math.pow(arg, 2);
        // return 0.5 + 0.5 * Math.pow(Math.sin(2 * (arg + phase)), 2);
        return 0.4 + 0.6 * Math.pow(Math.sin((arg + phase +  Math.PI/2)/q), 2);
        // return 1;
}

function plasmaPathD(arg, phase){
        return Math.sin(2 * (arg + phase));
}

function plasmaPathN(arg, phase){
        return (plasmaPathD(arg, phase) * Math.sin(arg + phase) + plasmaPath(arg, phase) * Math.cos(arg + phase))/(plasmaPathD(arg, phase) * Math.cos(arg + phase) - plasmaPath(arg, phase) * Math.sin(arg + phase));
}



function makeAntenna1 (obj,R,l) {
    obj.moveTo(Math.cos(-rad)*R,Math.sin(-rad)*R);
    obj.lineTo(Math.cos(-3*rad/4)*R,Math.sin(-3*rad/4)*R);
    obj.lineTo(Math.cos(-rad/2)*R,Math.sin(-rad/2)*R);
    obj.lineTo(Math.cos(-rad/4)*R,Math.sin(-rad/4)*R);
    obj.lineTo(Math.cos(0)*R,Math.sin(0)*R);
    obj.lineTo(Math.cos(rad/4)*R,Math.sin(rad/4)*R);
    obj.lineTo(Math.cos(rad/2)*R,Math.sin(rad/2)*R);
    obj.lineTo(Math.cos(3*rad/4)*R,Math.sin(3*rad/4)*R);
    obj.lineTo(Math.cos(rad)*R,Math.sin(rad)*R);
    obj.lineTo(Math.cos(rad)*R+l,Math.sin(rad)*R);
    obj.lineTo(Math.cos(-rad)*R+l,Math.sin(-rad)*R);
    obj.lineTo(Math.cos(-rad)*R,Math.sin(-rad)*R);
    // alert(R+' '+l+' '+rad);
}

function makeCoil (obj,l,w,quad) {
    var sign1 = (quad == 3 || quad == 4) ? 1 : -1;
    var sign2 = (quad == 1 || quad == 4) ? 1 : -1;
    obj.drawRoundedRect(-l/2,-w/2,l,w,w/8);
    obj.position = ({x:sign1*Torus.R/Math.sqrt(2),y:sign2*Torus.R/Math.sqrt(2)});
    obj.rotation = sign1*sign2*(Math.PI/4);
    obj.alpha = 0.5; 
}

function picknumtexture(num,color,dot) {

        frameRectangle.clear();
        frameRectangle.drawRect(num*320,dot ? 0 : 500,320,500);
        // frameRectangle = new PIXI.Rectangle(num*280,dot ? 0 : 500,280,450);
        // rectangles.push(rectangle);
        if (color == 'red') var texture = loader.resources.numbersRed.texture;
        texture.frame = frameRectangle;
        return texture
}

function formatNumber(obj, cornerX, cornerY, scalefactor) {
        obj.x = cornerX;
        obj.y = cornerY;
        obj.scale={'x':scale*scalefactor*1.e-4,'y':scale*scalefactor*1.e-4};
}

function updateDigital(obj,digi,dec) {
    // console.log(dig[0]);
    var numlength = digi.length;
    for (var j = 0; j < numlength; j++) {
        // console.log(obj.value);
        var thisdig = Math.floor(obj.value / Math.pow(10,(numlength - dec - 1 - j)) % 10);
        if (j == numlength -dec - 1) {
            var txt = 'red' + thisdig +'d.png';
        } else {
            var txt = 'red' + thisdig +'.png';
        };
        // console.log(txt);
        digi[j].texture = PIXI.loader.resources["numbersred"].textures[txt];
        digi[j].num = thisdig;
    };
}

function buttonize(obj,data) {
obj.interactive = true;
obj.buttonMode = true;
obj
    .on('mouseover', overfun)
    .on('mouseup', releasedfun)
    .on('mousedown', pressedfun)
    .on('touchstart',pressedfun)
    .on('touchend', releasedfun)
    .on('mouseout', releasedfun)
    .on('touchendoutside',releasedfun);
}

function pressedfun() {
  if (!buttonState[this.txt]) stateVar[this.txt] = !stateVar[this.txt];
  buttonState[this.txt] = true;
  // console.log("pressed "+ buttonState[this.txt]);
}
function releasedfun() {
  buttonState[this.txt] = false;
    // console.log("released" + buttonState[this.txt]);
}

function overfun() {
    // this.defaultCursor = 'pointer';
    // console.log("I'm over "+buttonState[this.txt]);
}


function arrowClicked() {
    var thisobj = this;
    var txtpressed = (this.para2.dir == 'up') ? "upredpressed.png" : "downredpressed.png";
    var txt = (this.para2.dir == 'up') ? "upred.png" : "downred.png";
    this.texture = PIXI.loader.resources.numbersred.textures[txtpressed];
    setTimeout(function(){
        thisobj.texture = PIXI.loader.resources.numbersred.textures[txt];
    },100);
}

function arrowPressed() {
    var txtpressed = (this.para2.dir == 'up') ? "upredpressed.png" : "downredpressed.png";
    this.texture = PIXI.loader.resources.numbersred.textures[txtpressed];
    this.defaultCursor = 'crosshair';
    var sign = (this.para2.dir == 'up') ? 1 : -1;

    this.para1.obj.value += sign*Math.pow(10,this.para2.exp);
    this.para1.obj.value = (this.para1.obj.value > this.para1.obj.max) ? this.para1.obj.max : (this.para1.obj.value < this.para1.obj.min) ? this.para1.obj.min : this.para1.obj.value;

    updateDigital(this.para1.obj,this.para1.arr,this.para1.dec);
    // console.log(this.para1.obj.value);   

}

function arrowReleased() {
    var txt = (this.para2.dir == 'up') ? "upred.png" : "downred.png";
    this.texture = PIXI.loader.resources.numbersred.textures[txt];
    this.defaultCursor = 'pointer';
}


function keyboard(keyCode) {
  var key = {};
  key.code = keyCode;
  key.isDown = false;
  key.isUp = true;
  key.press = undefined;
  key.release = undefined;
  //The `downHandler`
  key.downHandler = function(event) {
    if (event.keyCode === key.code) {
      if (key.isUp && key.press) key.press();
      key.isDown = true;
      key.isUp = false;
    }
    event.preventDefault();
  };

  //The `upHandler`
  key.upHandler = function(event) {
    if (event.keyCode === key.code) {
      if (key.isDown && key.release) key.release();
      key.isDown = false;
      key.isUp = true;
    }
    event.preventDefault();
  };

  //Attach event listeners
  window.addEventListener(
    "keydown", key.downHandler.bind(key), false
  );
  window.addEventListener(
    "keyup", key.upHandler.bind(key), false
  );
  return key;
}