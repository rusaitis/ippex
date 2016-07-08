var width = window.innerWidth
|| document.documentElement.clientWidth
|| document.body.clientWidth;

var height = window.innerHeight
|| document.documentElement.clientHeight
|| document.body.clientHeight;

function writeMessage(message) {
    text.text(message);
    layer.draw();
}

var colors = ['#DC3522', '#374140', '#D9CB9E', '#00A388', '#BEEB9F', '#FF6138', '#787746', '#703030', '#2F343B', '#C77966', '#7E827A', '#01B0F0', '#FF358B', '#AEEE00', '#F5A503', '#36B1BF', '#FFEEAD', '#8F8164', '#593325'];

function getRandomColor() {
    return colors[Math.round(Math.random() * (colors.length-1))];
}

// function addRectTextPair(id, x, y, w, h, fill, stroke) {
//     // new div with same color as Konva rect
//     var div = document.createElement("div");
//     div.id = "div" + id;
//     div.className = "boundXY";
//     div.style.background = fill;
//     // div.style.position = "absolute";
//     // div.style.left = 40;
//     // div.style.right = 40;

//     div.innerHTML = "X/Y:";
//     // add xy text inputs
//     div.appendChild(newTextInput("x" + id, x));
//     div.appendChild(newTextInput("y" + id, y));
//     // add div to body
//     document.body.appendChild(div);

//     // change rect's X when the textInputX changes
//     $('#x' + id).change(function (e) {
//         var id = e.target.id.slice(1);
//         var rect = layer.get("#rect" + id)[0];
//         rect.setX(parseInt($(this).val()));
//         layer.draw();
//     });
// }

function makeSlider(variable, callback, SliderX, SliderY, length, slidertext, liveReading) {
                var minValue = 0;
                var maxValue = 100;
                var units = "";
                var colour = '#bcbcbc';

                // var colour = variable.colour;
                // var units = variable.units;
                // var minValue = variable.min;
                // var maxValue = variable.max;
                var range = maxValue - minValue;
                var nobRadius = 20;
                var density = length/range;
                var readout = (liveReading == 'active') ? 1 : 0;
                var text_colour = '#bcbcbc';
                var sliderID = String('slider'+variable.name);
                // console.log(sliderID);

                var SliderNobOut = new Konva.Circle({
                    x: 0,
                    y: 0,
                    radius: nobRadius,
                    fill : 'white',
                    opacity : 0.6,
                    stroke: '#dcdcdc',
                    strokeWidth: 1,
                });

                var SliderNobIn = new Konva.Circle({
                    x: 0,
                    y: 0,
                    // radius: 0.6*nobRadius,
                    radius: nobRadius*(0.2+0.7*(variable.value-minValue)/range),
                    fill : colour,
                    opacity : 1,
                    name: 'nobIn',
                });

                // ACTUAL SLIDER
                var Slider = new Konva.Group({
                    x: Math.round((variable.value-minValue)*density+SliderX),
                    y:SliderY,
                    draggable: true,
                    dragBoundFunc: function(pos) {
                              return {
                                x: (pos.x < (SliderX)) ? (SliderX) : ((pos.x > (SliderX+length)) ? (SliderX+length) : pos.x),
                                y: this.getAbsolutePosition().y
                              }
                            },
                    id: sliderID,
                });

                Slider.add(SliderNobIn);
                Slider.add(SliderNobOut);

                var SliderRange = new Konva.Rect({
                    x: SliderX,
                    y: Math.round(SliderY-nobRadius*0.3/2),
                    width: length,
                    height: nobRadius*0.3,
                    cornerRadius: 3,
                    stroke: '#f1eeee',
                    strokeWidth: 0,
                });
                var SliderProgress = new Konva.Rect({
                    x: SliderX,
                    y: Math.round(SliderY-nobRadius*0.3/2),
                    width: variable.value,
                    height: nobRadius*0.3,
                    fill : colour,
                    cornerRadius:3,
                    id: (sliderID+'Progress'),
                });
                var SliderBoxBG = new Konva.Rect({
                    x:SliderX-nobRadius,
                    y:SliderY-2.7*nobRadius,
                    width: length+2*nobRadius,
                    height: 3.7*nobRadius,
                    fill : '#a0aec6',
                    // fill : '#000000',
                    opacity : 0,
                    cornerRadius: 5,
                });
                var SliderBG = new Konva.Rect({
                    x:SliderX-nobRadius,
                    y:SliderY-nobRadius,
                    width: length+2*nobRadius,
                    height: 40,
                    fill : '#3d3d3d',
                    opacity : 0,
                    cornerRadius: 4,
                });

                // var SliderText = new Konva.Text({
                //         x: SliderX-4.1*nobRadius,
                //         y: SliderY-(1.4+0.5*readout)*nobRadius,
                //         fontSize: 25,
                //         fontFamily: 'Calibri',
                //         text: (variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units,
                //         fill: text_colour,
                //         padding: 15,
                //         id: (sliderID+'Input'),
                // });

                var div = document.createElement("div");
                div.id = "div" + sliderID;
                div.className = "boundXY";
                div.style.left = SliderX-4.1*nobRadius + 'px';
                div.style.top = SliderY-(1.4+0.5*readout)*nobRadius + 'px';
                // div.style.marginLeft = 2*nobRadius;
                // div.style.marginTop = 2*nobRadius;
                div.style.margin = 0.6*nobRadius+'px';
                // div.style.background = fill;
                // div.innerHTML = "X/Y:";
                var input = document.createElement("input");
                    input.id = sliderID + 'Input';
                    input.value = (variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units;
                    input.type = "text";
                    input.className = "TextInput";
                    // input.style.width = "25px";
                    // input.style.marginLeft = "2px";
                div.appendChild(input);
                // div.appendChild(newTextInput("x" + id, x));
                document.body.appendChild(div);     // add div to body
                // change rect's X when the textInputX changes
                $('#' + sliderID + 'Input').change(function (e) {
                    var id = e.target.id;
                    var value = e.target.value;
                    if ((maxValue-minValue)>10) {
                    // variable.value = Math.round((Slider.getX()-SliderX)/density+minValue);
                        variable.value = value;
                    }else{
                        // variable.value = Math.round(((Slider.getX()-SliderX)/density+minValue)*100)/100;
                        variable.value = Math.round(value*100)/100;
                    }
                    Slider.setX(Math.round((variable.value-minValue)*density+SliderX));
                    // SliderText.setText((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units);
                    // $('#' + id + "Input").val(parseInt(this.getX()));
                    // $('#' + sliderID + "Input").val((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units);
                    SliderNobIn.setRadius(nobRadius*(0.2+0.7*(variable.value-minValue)/range));
                    if (readout==0) (SliderProgress.setWidth((variable.value-minValue)*density) );
                    // callback(variable);
                    callback();
                });

                var SliderTextActive = new Konva.Text({
                        x: SliderX-4.1*nobRadius,
                        y: SliderY-(1.4-0.5*readout)*nobRadius,
                        fontSize: 25,
                        fontFamily: 'Calibri',
                        text: (variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units,
                        fill: colour,
                        padding: 15,
                        id: (sliderID+'Readout'),
                });
                var SliderInfo = new Konva.Text({
                        x: SliderX-4.1*nobRadius,
                        y: SliderY-3*nobRadius-5,
                        fontSize: 22,
                        fontFamily: 'Helvetica',
                        text: slidertext,
                        fill: text_colour,
                        id: (sliderID+'Info'),
                        padding: 15,
                });
                var SliderDown = new Konva.Rect({
                    x:SliderX-nobRadius,
                    y:SliderY,
                    width: nobRadius,
                    height: nobRadius,
                    offset: {x:nobRadius/2, y:nobRadius/2},
                    fill: '#ffcdc2',
                    opacity: 1,
                    cornerRadius: 5,
                    id: sliderID + 'Down'
                });
                var SliderUp = new Konva.Rect({
                    x:SliderX+length+nobRadius,
                    y:SliderY,
                    width: nobRadius,
                    height: nobRadius,
                    offset: {x:nobRadius/2, y:nobRadius/2},
                    fill : '#c8ffda',
                    opacity : 1,
                    cornerRadius: 5,
                    id: sliderID + 'Up'
                });

                SliderDown.on('mousedown touchstart', function() {
                    if ((maxValue-minValue)>10) {
                        // variable.value = Math.round((Slider.getX()-SliderX)/density+minValue);
                        variable.value = ((variable.value-5)<minValue) ? minValue : (variable.value-5);
                    }else{
                        // variable.value = Math.round(((Slider.getX()-SliderX)/density+minValue)*100)/100;
                        variable.value = ((variable.value-5)<minValue) ? (Math.round(minValue*100)/100) : (Math.round((variable.value-5)*100)/100);
                    }
                    Slider.setX(Math.round((variable.value-minValue)*density+SliderX));
                    // SliderText.setText(((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units));
                    $('#' + sliderID + "Input").val((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units);
                    SliderNobIn.setRadius(nobRadius*(0.2+0.7*(variable.value-minValue)/range));
                    if (readout==0) (SliderProgress.setWidth((variable.value-minValue)*density) );
                    // callback(variable);
                    callback();
                });

                SliderUp.on('mousedown touchstart', function() {
                    if ((maxValue-minValue)>10) {
                        // variable.value = Math.round((Slider.getX()-SliderX)/density+minValue);
                        variable.value = ((variable.value+5)>maxValue) ? maxValue : (variable.value+5);
                    }else{
                        // variable.value = Math.round(((Slider.getX()-SliderX)/density+minValue)*100)/100;
                        variable.value = ((variable.value+100)>maxValue) ? (Math.round(maxValue*100)/5) : (Math.round((variable.value+5)*100)/100);
                    }
                    // console.log(variable.value);
                    Slider.setX(Math.round((variable.value-minValue)*density+SliderX));
                    console.log(Slider.x());
                    // SliderText.setText(((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units));
                    $('#' + sliderID + "Input").val((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units);
                    SliderNobIn.setRadius(nobRadius*(0.2+0.7*(variable.value-minValue)/range));
                    if (readout==0) (SliderProgress.setWidth((variable.value-minValue)*density) );
                    // callback(variable);
                    callback();
                });

                // add hover styling
                Slider.on('mouseover', function() {
                  document.body.style.cursor = 'pointer';
                  // Slider.setStroke('#833743');  //'#C8E3F3', //'#006CBF' '#D00017'
                  // sliderLayer.batchDraw();
                });
                Slider.on('mouseout', function() {
                  document.body.style.cursor = 'default';
                  // Slider.setStroke("#cecece");
                  // sliderLayer.batchDraw();
                });
                Slider.on('dragmove', function() {
                    // anim.stop();
                    if ((maxValue-minValue)>10) {
                        variable.value = Math.round((Slider.getX()-SliderX)/density+minValue);
                    }else{
                        variable.value = Math.round(((Slider.getX()-SliderX)/density+minValue)*100)/100;
                    }
                    // SliderText.setText(((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units));
                    $('#' + sliderID + "Input").val((variable.value >= 100) ? variable.value.toFixed(0)+units : variable.value.toFixed(1)+units);
                    SliderNobIn.setRadius(nobRadius*(0.2+0.7*(variable.value-minValue)/range));
                    if (readout==0) (SliderProgress.setWidth((variable.value-minValue)*density) );
                    // callback(variable);
                    callback();
                    // console.log(window[variable]);
                });

                // sliderLayer.add(SliderBoxBG);
                // sliderLayer.add(SliderBG);
                sliderLayer.add(SliderRange);
                graphLayer.add(SliderProgress);
                sliderLayer.add(SliderInfo);
                // sliderLayer.add(SliderText);
                sliderLayer.add(SliderDown);
                sliderLayer.add(SliderUp);
                if (readout==1) graphLayer.add(SliderTextActive);
                sliderLayer.add(Slider);
        }


function makeLabel(name, text, textsize, textcolor, fill, opacity, width, height, posx, posy, drawlayer) {
            var ButtonBG = new Konva.Rect({
                width: width,
                height: height,
                fill: fill,
                opacity: opacity,
                cornerRadius: 10,
                offset: {x: 0, y: 0}
            });
            var ButtonText = new Konva.Text({
                // x: 0,
                y: height/2-textsize,
                width: width,
                height: height,
                fontFamily: 'Helvetica Neue',
                fontSize: textsize,
                text: text,
                fill: textcolor,
                padding: 10,
                align: 'center'
            });
            var ButtonSimple = new Konva.Group({
                x: posx,
                y: posy,
                // x: Math.round(stage.width() * 0.7),
                // y: Math.round(stage.height() * 0.2),
                offset: {x: width/2, y: height/2},
                name: name,
            });
            ButtonSimple.add(ButtonBG);
            ButtonSimple.add(ButtonText);
            drawlayer.add(ButtonSimple);
            drawlayer.draw();

            ButtonSimple.on('mouseover mousedown touchstart', function() {
                this.scale({x: 0.98, y: 0.98});
                buttonLayer.batchDraw();
            });

            ButtonSimple.on('mouseout mouseup touchend', function() {
                this.scale({x: 1, y: 1});
                buttonLayer.batchDraw();
            });
        }