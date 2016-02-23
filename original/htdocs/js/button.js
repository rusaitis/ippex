function Button(obj, callback, ctrl, layerButton) {
    var passiveColour = "#4f4f4f";
    var textSize = 2*ctrl.h/5;

    function flickVar() {
        obj.value = ((obj.value == 1) ? 0 : 1);
    }

    function updateButtton() {
        ButtonBG.fill( (obj.value == 1) ? passiveColour : passiveColour);
        ButtonText.fill( (obj.value == 1 ) ? "#ffffff" : "#a1a1a1");
        ButtonOutline.stroke( (obj.value == 1 ) ? obj.colour : passiveColour);
        ButtonOutline.shadowColor( (obj.value == 1 ) ? obj.colour : "#131313");
        ButtonOutline.shadowBlur( (obj.value == 1 ) ? 4 : 10);
        layerButton.batchDraw();
    }

    var ButtonBG = new Konva.Rect({
        width: ctrl.w,
        height: ctrl.h,
        fill: passiveColour,
        // stroke: passiveColour,
        // strokeWidth: 1,
        opacity: 0.8,
        cornerRadius: 5
    });
    var ButtonOutline = new Konva.Rect({
        width: ctrl.w,
        height: ctrl.h,
        // fill: passiveColour,
        // strokeWidth: 0,
        // stroke: obj.colour,
        // strokeWidth: 1,
        opacity: 0.4,
        cornerRadius: 5,
        shadowColor: obj.colour,
        shadowBlur: 10,
        shadowOffset: {x : 0, y : 0},
        shadowOpacity: 1
    });
    var ButtonText = new Konva.Text({
        width: ctrl.w,
        height: ctrl.h,
        fontFamily: 'Helvetica',
        fontStyle: 'Lighter',
        fontSize: textSize,
        offset: {x:0, y:-(ctrl.h-textSize)/2,},
        text: ctrl.text,
        fill: obj.colour,
        opacity: 0.7,
        // padding: 10,
        align: 'center'
    });
    var ButtonGroup = new Konva.Group({
        x: ctrl.x,
        y: ctrl.y,
        name: 'button' + obj.name
    });

    ButtonGroup.add(ButtonOutline);
    ButtonGroup.add(ButtonBG);
    ButtonGroup.add(ButtonText);
    layerButton.add(ButtonGroup);
    updateButtton();
    layerButton.batchDraw();

    ButtonGroup.on('mousedown touchstart', function() {
        // this.scale({x: 0.98, y: 0.98});
        flickVar();
        updateButtton();
        layerButton.batchDraw();
        callback();
    });

    ButtonGroup.on('mouseover touchstart', function() {
        // this.scale({x: 0.98, y: 0.98});
        updateButtton();
        ButtonOutline.shadowColor("#000000");
        ButtonOutline.shadowBlur(4);
        document.body.style.cursor = 'pointer';
        layerButton.batchDraw();
    });

    ButtonGroup.on('mouseout touchend', function() {
        // this.scale({x: 1, y: 1});
        ButtonOutline.shadowColor("#131313");
        if (obj.value == 0) ButtonOutline.shadowBlur(10);
        document.body.style.cursor = 'default';
        layerButton.batchDraw();
    });
}