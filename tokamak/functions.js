function shuffle(array) {
  var m = array.length, t, i;

  // While there remain elements to shuffle…
  while (m) {

    // Pick a remaining element…
    i = Math.floor(Math.random() * m--);

    // And swap it with the current element.
    t = array[m];
    array[m] = array[i];
    array[i] = t;
  }

  return array;
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

    function buttonize(obj,overfun,pressedfun,releasedfun,clickedfun) {
    obj.interactive = true;
    obj.buttonMode = true;
    obj
        .on('mouseover', overfun)
        .on('mouseup',releasedfun)
        .on('mousedown',pressedfun)
        .on('touchstart',pressedfun)
        .on('touchend',releasedfun)
        .on('mouseout',releasedfun)
        .on('touchendoutside',releasedfun)
        .on('click',clickedfun)
        .on('tap',clickedfun);        
    }



    function buttonOver() {
        this.defaultCursor = 'pointer';
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

    function backPressed() {
        townContainer.visible = false;
    }

    function exitPressed() {
        townContainer.visible = true;
    }