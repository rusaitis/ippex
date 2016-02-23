function Slider(obj, callback, ctrl, layerStatic, layerDynamic) {

    var range = obj.max - obj.min;
    // var
    var readout = (ctrl.comm == 'bi') ? 1 : 0;
    var incr = (ctrl.incr == 'true') ? 1 : 0;
    var sliderID = 'slider' + obj.name;
    var displayValue = {in: 0, out: 0};
    var displayW = 60 + 5*Math.round(getBaseLog(10, obj.max)); //BIGGER DISPLAY FOR BIGGER NUMBERS
    var textColour = '#b4b4b4';
    //BY DEFAULT THE KNOB IS COLOURED, NOT THE DIV BOX
    var style = (typeof(ctrl.style) !== 'undefined') ? ctrl.style : 1;
    var knobR = 20;
    var textCentered = !((typeof(ctrl.bg) !== 'undefined') || (ctrl.style == "2"));
    var ReadColour = obj.colour;
    var arrowW = 20;
    var BGopacity = (typeof(ctrl.op) !== 'undefined') ? ctrl.op : 0.5;


    var slider = {x: ctrl.x + displayW + knobR + arrowW, y: ctrl.y + ctrl.h/2, w: ctrl.w-displayW-2*knobR-2*arrowW, h: 5, r: knobR, R: knobR};
    var density = slider.w/range;
    //TYPE I:
    var text = {x: 0, y: 0, xRead: 0, yRead: ctrl.h/2, w: displayW, h: ctrl.h/2,
                xInfo: ctrl.x + textCentered*displayW/2, yInfo: ctrl.y-2*ctrl.h/5, wInfo: ctrl.w, hInfo: 2*ctrl.h/5, colour: '#b4b4b4', margin: '-30px 0 0 0'};
    var divSlider = {id: sliderID + "div", class: "sliderTextDiv", left: ctrl.x, top: ctrl.y, width: text.w, height: text.h};
    var title = {txt: ctrl.text, id: sliderID+'Info'};
    var input1 = {id: sliderID + "Input", value: displayValue.in, class: "sliderTextInput", w: text.w, h: ((readout == 0) ? ctrl.h : text.h), fontSize: text.h-5, colour: "white"};
    var input2 = {id: sliderID + "Readout", value: displayValue.out, class: "sliderTextInput", w: text.w, h: text.h, fontSize: text.h-5, colour: ((style == 1) ? obj.colour : ReadColour)};

    var linearTicks = [1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 100000, 1000000];
    var ticks = [];
    ctrl.translate = function(x){ return Math.round((x-slider.x)/density+obj.min);}

    Math.round((obj.value-obj.min)*density+slider.x)

    switch (ctrl.axis){
        case 'linear':  ctrl.transform = function(param){return param}; // FUNCTIONAL COORD DEPENDENCE ON POSITION
                        ctrl.inverse = function(param){return param};   // INVERSE FUNCTION OF Y TO X COORDS
                        ctrl.norm = function(param){return (obj.min + param / slider.w * (obj.max - obj.min))}; // SLIDER UI TO AXIS COORDS
                        ctrl.invNorm = function(param){return ((param-obj.min) * slider.w / (obj.max-obj.min))}; // TO SLIDER UI COORDS
                        ctrl.stepUp = function(param){  return (param+5 > obj.max ? obj.max : param+5);}
                        ctrl.stepDn = function(param){  return (param-5 < obj.min ? obj.min : param-5);}
                        ctrl.ticksMax = getMod(slider.w, 40);
                        ctrl.ticksSep = (range/ctrl.ticksMax);
                        var linearTicksMatch = linearTicks.map(function(num) { return Math.abs(num - ctrl.ticksSep); });
                        var indexOfMinValue = linearTicksMatch.reduce(function(iMin,x,i,a) {return x<a[iMin] ? i : iMin;}, 0);
                        ctrl.optSep = linearTicks[indexOfMinValue];
                        for (var i = obj.min; i <= obj.max; i+=ctrl.optSep) {
                            ticks.push({value: i, label:String(i)});
                        };
                        break;
        case 'exp':     ctrl.transform = function(param){return Math.exp(param)};
                        ctrl.inverse = function(param){return Math.log(param)};
                        ctrl.norm = function(param){return (param-10)};
                        ctrl.ticksMax = getMod(slider.w, 40);
                        ctrl.ticksSep = (range/ctrl.ticksMax);
                        break;
        case 'log':
                        ctrl.ticksMax = getMod(slider.w, 30);
                        ctrl.startExpExact = getBaseLog(10, obj.min);
                        ctrl.startExp = Math.ceil(ctrl.startExpExact);
                        ctrl.endExpExact = getBaseLog(10, obj.max);
                        ctrl.endExp = Math.round(ctrl.endExpExact);
                        if (obj.min === 0) {ctrl.startExp = 0; ctrl.startExpExact = 0;}
                        if (Math.pow(10, ctrl.endExp) > obj.max) ctrl.endExp -= 1;
                        ctrl.transform = function(param){return Math.pow(10, (param))}; // FUNCTIONAL COORD DEPENDENCE ON POSITION
                        ctrl.inverse = function(param){return (param !== 0 ? getBaseLog(10, param) : 0)};   // INVERSE FUNCTION OF Y TO X COORDS
                        ctrl.norm = function(param){return (ctrl.startExpExact + param / slider.w * (ctrl.endExpExact-ctrl.startExpExact))}; // SLIDER UI TO AXIS COORDS
                        ctrl.invNorm = function(param){return ((param - ctrl.startExpExact)* slider.w / (ctrl.endExpExact-ctrl.startExpExact))};   // TO SLIDER UI COORDS
                        ctrl.stepUp = function(param){  if (param < 1)
                                                             var nextVal = ctrl.transform(1)
                                                        else var nextVal = ctrl.transform(Math.ceil(ctrl.inverse(param) + 0.001));
                                                        return (nextVal > obj.max ? obj.max : nextVal);}
                        ctrl.stepDn = function(param){  if (param <= 1)
                                                             var nextVal = ctrl.transform(0)
                                                        else var nextVal = ctrl.transform(Math.floor(ctrl.inverse(param) - 0.001));
                                                        return (nextVal < obj.min ? obj.min : nextVal);}
                        ctrl.ticksNum = ctrl.endExp - ctrl.startExp;
                        ctrl.optSep = Math.round(ctrl.ticksNum/ctrl.ticksMax);
                        if (ctrl.optSep === 0) ctrl.optSep = 1;
                        for (var i = ctrl.startExp; i <= ctrl.endExp; i+=ctrl.optSep) {
                            ticks.push({value: Math.pow(10,i), label:'10^{'+String(i)+'}'});
                        };
                        break;
    }
    // console.log(ticks);
    function updateSlider() {
        // if (range > 10) {
            displayValue.in = obj.value;
            displayValue.out = obj.value;
        // } else {
            // displayValue.in = Math.round(obj.value*100)/100;
            // displayValue.out = Math.round(obj.value*100)/100;
        // }
        // console.log(Math.round(ctrl.invNorm(ctrl.inverse(obj.value))+slider.x));
        // Slider.setX(Math.round((obj.value-obj.min)*density+slider.x));
        Slider.setX(Math.round(ctrl.invNorm(ctrl.inverse(obj.value))+slider.x));
        slider.r = slider.R*(0.3+0.6*((Slider.getX()-slider.x)/slider.w));
        KnobIn.radius(Math.round(slider.r));
        // if (readout == 0) SliderProgressBar.setWidth((obj.value-obj.min)*density);
        SliderProgressBar.width(Math.round(Slider.getX()-slider.x));
        $('#' + sliderID + "Input").val(displayValue.in);
    }

    var KnobOut = new Konva.Circle({
        x: 0,
        y: 0,
        radius: slider.R,
        fill : 'white',
        // stroke: '#dcdcdc',
        stroke: (style == 1) ? "#dcdcdc" : "#dcdcdc",
        strokeWidth: 1,
        opacity : 0.5
    });

    var KnobIn = new Konva.Circle({
        x: 0,
        y: 0,
        radius: slider.r,
        fill : (style == 1) ? obj.colour : "#ffffff",
        name: 'nobIn',
        shadowColor: obj.colour
    });

    var Slider = new Konva.Group({
        x: slider.x,
        y: slider.y,
        draggable: true,
        dragBoundFunc: function(pos) {
                  return {
                    x: (pos.x < (slider.x)) ? (slider.x) : ((pos.x > (slider.x+slider.w)) ? (slider.x+slider.w) : pos.x),
                    y: this.getAbsolutePosition().y
                  }
                },
        zindex: 100,
        id: sliderID,
    });
    Slider.add(KnobIn);
    Slider.add(KnobOut);

    var SliderRange = new Konva.Rect({
        x: slider.x,
        y: slider.y,
        width: slider.w,
        height: slider.h,
        offset: {x:0, y:slider.h/2},
        cornerRadius: 2,
        fill: "#242424",
        // stroke: '#000000',
        // strokeWidth: 1,
        opacity: 0.4
    });
    var SliderProgressBar = new Konva.Rect({
        x: slider.x,
        y: slider.y,
        width: 0,
        height: slider.h,
        offset: {x:0, y:slider.h/2},
        // fill : obj.colour,
        fill : (style == 1) ? obj.colour : ReadColour,
        cornerRadius: 2,
        id: sliderID + 'Progress',
        shadowColor: obj.colour,
        shadowBlur: 5,
        shadowOffset: {x : 0, y : 0},
        shadowOpacity: 1
    });
    var SliderDivBG = new Konva.Rect({
        x: ctrl.x,
        y: ctrl.y,
        width: ctrl.w,
        height: ctrl.h + ((typeof(ctrl.axis) !== 'undefined') ? 15 : 0),
        fill : (typeof(ctrl.bg) !== 'undefined') ? ctrl.bg : obj.colour,
        opacity : (typeof(ctrl.bg) !== 'undefined') ? BGopacity : ((style == 1) ? 0 : BGopacity),
        cornerRadius: 5,
        id: sliderID + 'BG'
    });

    var tickSep = 10;
    var tickGroup = new Konva.Group({
        x: slider.x,
        y: slider.y+15
    });

    var div = createDiv(divSlider);
    var para = createP(text);
    var textDOM = createText(title);

    para.appendChild(textDOM);
    div.appendChild(para);

    var inputDOM = createInput(input1);
    div.appendChild(inputDOM);
    if (readout !== 0){
        var input2DOM = createInput(input2);
        div.appendChild(input2DOM);
    }
    console.log(ticks);
    console.log(ticks[0].value);
    for (var i = 0; i < ticks.length; i++) {
    // while ( tickX < slider.w){

        var tickX = ctrl.invNorm(ctrl.inverse(ticks[i].value));
        if (tickX > slider.w) break;

        var tick = new Konva.Line({
            points: [tickX, 0,
                    tickX, 10],
            stroke: '#ffffff',
            strokeWidth: 1,
            // lineCap: 'round',
            // lineJoin: 'round',
            opacity: 0.5,
        });
        var div0 = createDiv({id: sliderID + "div" + i, class: "sliderAxis", left: displayW+ knobR + arrowW+tickX-20, top: ctrl.h/2+25, w: 40, h: 10});
        var para0 = createP({x: 0, y: 0, w: 40, h: 10, colour: '#919191', margin: '0 0 0 0'});
        var textDOM0 = createText({txt: (obj.min + i * ctrl.optSep), id: sliderID+'Axis'+i});
        para0.appendChild(textDOM0);
        div0.appendChild(para0);
        katex.render(ticks[i].label, div0);
        div.appendChild(div0);
        tickGroup.add(tick);
    };
    layerStatic.add(tickGroup);

    document.body.appendChild(div);     // add div to body

    // CHANGE KNOB POSITION WHEN THE TEXT INPUT CHANGES
    $('#' + sliderID + 'Input').change(function (e) {
        var id = e.target.id;
        obj.value = (e.target.value < obj.max) ? ((e.target.value > obj.min) ? e.target.value : obj.min) : obj.max;
        updateSlider();
        e.target.value = displayValue.in;
        layerStatic.batchDraw();
        layerDynamic.batchDraw();
        callback();
    });

    function buildArrow(direction){
        var increment = 5;
        var sign = (direction == "up" || direction == "right") ? 1 : -1;

        var Arrow = new Konva.Line({
            points: [sign*(-1)*slider.R/2*Math.cos(Math.PI/4), -slider.R/2*Math.sin(Math.PI/4),
                    0, 0,
                    sign*(-1)*slider.R/2*Math.cos(Math.PI/4), slider.R/2*Math.sin(Math.PI/4)],
            stroke: '#1b1b1b',
            strokeWidth: 3,
            lineCap: 'round',
            lineJoin: 'round',
            opacity: 0.1,
            shadowColor: obj.colour,
            shadowBlur: 0,
            shadowOffset: {x : 0, y : 0},
            shadowOpacity: 1
        });
        var ArrowCircle = new Konva.Circle({
            radius: slider.R
        });
        var ArrowGroup = new Konva.Group({
            x: Math.round(slider.x + slider.w *(sign == 1) + sign*1.5*slider.R),
            y: slider.y,
            id: sliderID + 'Down'
        });
        ArrowGroup.add(Arrow, ArrowCircle);

        ArrowGroup.on('mouseover touchstart', function() {
            Arrow.opacity(0.5);
            Arrow.stroke(obj.colour);
            Arrow.shadowBlur(10);
            document.body.style.cursor = 'pointer';
            layerStatic.batchDraw();
        });
        ArrowGroup.on('mouseout touchend', function() {
            Arrow.opacity(0.1);
            Arrow.stroke("#1b1b1b");
            Arrow.shadowBlur(0);
            document.body.style.cursor = 'default';
            layerStatic.batchDraw();
        });
        ArrowGroup.on('mousedown touchstart', function() {
            if (sign > 0 )   obj.value = ctrl.stepUp(obj.value)
                        else obj.value = ctrl.stepDn(obj.value);

            // obj.value = (obj.value+increment*sign < obj.min) ? obj.min
                      // : (obj.value+increment*sign > obj.max ? obj.max : (obj.value+increment*sign));
            $('#' + sliderID + "Input").val(displayValue.in + obj.units);
            updateSlider();
            layerDynamic.batchDraw();
            // layerStatic.batchDraw();
            callback();
        });
        layerStatic.add(ArrowGroup);
    }

    Slider.on('mouseover', function() {
      document.body.style.cursor = 'pointer';
    });

    Slider.on('mouseout', function() {
      document.body.style.cursor = 'default';
    });

    Slider.on('dragmove', function() {
        obj.value = ctrl.transform(ctrl.norm(Slider.getX()-slider.x));
        // obj.value = Math.round((Slider.getX()-slider.x)/density+obj.min);
        updateSlider();
        $('#' + sliderID + "Input").val(displayValue.in + obj.units);
        layerDynamic.batchDraw();
        callback();
    });

    layerStatic.add(SliderDivBG);
    layerStatic.add(SliderRange);
    layerDynamic.add(SliderProgressBar);
    buildArrow("left");
    buildArrow("right");
    layerDynamic.add(Slider);
    updateSlider();
}