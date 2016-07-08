function addVector(baseLength, baseWidth, baseRotation, xstart, ystart, color, name, id, style, label, labelstyle,drawlayer) {

    // var initialX = (baseLength-arrowRadius) * Math.cos(baseRotation);
    // var initialY = (baseLength-arrowRadius) * Math.sin(baseRotation);
    // var vectorCount = drawlayer.getChildren().length;
    var vectorCount = drawlayer.find('.vector').length;
    // baseRotation = -baseRotation;
    baseRotation *= -1;

    var initialX = (baseLength) * Math.cos(baseRotation*Math.PI/180);
    var initialY = (baseLength) * Math.sin(baseRotation*Math.PI/180);


    baseLength = Math.round(baseLength);
    // baseRotation = Math.round(baseRotation*180/Math.PI);
    var baseRotationArc = Math.abs(360-baseRotation);
    // var baseRotationArc = (baseRotation);
    // console.log(baseRotationArc);


    var mainVectorbase = new Kinetic.Line({
        points: [0, 0, initialX, initialY],
        stroke: color,
        // strokeWidth: baseWidth,
        strokeWidth: (style == 'dashed' ? baseWidth-3 : baseWidth),
        // dash: (name == 'vectorSum' ? [10,10] : 0),
        dash: (style == 'dashed' ? [10,10] : 0),
        opacity: 0.8,
        lineCap: 'round',
        name: 'vectorbase'
    });

    var mainVectorTriangle = new Kinetic.RegularPolygon({
        sides: 3,
        // radius: baseWidth+6,
        radius: (style == 'dashed' ? baseWidth-3 : baseWidth+6),
        // baseWidth
        offset: [0, Math.round(arrowRadius / 2)],
        fill: color,
        opacity: 0.8,
    });

    var mainVectorbaseX = new Kinetic.Line({
        points: [0, 0, initialX, 0],
        stroke: 'black',
        // stroke: (name == 'vectorSum' ? '#88aee3' : color),
        // strokeWidth: baseWidth-1,
        strokeWidth: (name == 'vectorSum' ? baseWidth+10 : (baseWidth-4)),
        // dash: [10,10],
        opacity: 0,
        lineCap: 'square',
        name: 'vectorbaseX',
        listening: false
    });
    var mainVectorbaseY = new Kinetic.Line({
        points: [0, 0, 0, initialY],
        stroke: 'black',
        // stroke: (name == 'vectorSum' ? '#e34c4f' : color),
        strokeWidth: (name == 'vectorSum' ? baseWidth+10 : (baseWidth-4)),
        // strokeWidth: baseWidth-1,
        // dash: [10,10],
        opacity: 0,
        lineCap: 'square',
        name: 'vectorbaseY',
        listening: false
    });
    var mainVectorbaseXDash = new Kinetic.Line({
        points: [initialX, 0, initialX, initialY],
        // stroke: color,
        stroke: 'black',
        // strokeWidth: baseWidth-1,
        strokeWidth: (baseWidth-4),
        dash: [5,5],
        opacity: 0,
        lineCap: 'square',
        name: 'vectorbaseXDash',
        listening: false
    });
    var mainVectorbaseYDash = new Kinetic.Line({
        points: [0, initialY, initialX, initialY],
        // stroke: color,
        stroke: 'black',
        strokeWidth: (baseWidth-4),
        // strokeWidth: baseWidth-1,
        dash: [5,5],
        opacity: 0,
        lineCap: 'square',
        name: 'vectorbaseYDash',
        listening: false
    });

    var mainVectorCircle = new Kinetic.Circle({
        radius: 25,
        // fill: '#4f4f4f',
        stroke: 'black',
        dash: [1,1],
        // dash: [2,2],
        strokeWidth: 2,
        opacity: 0,
    });

    var mainVectorArc = new Kinetic.Arc({
        angle: baseRotationArc,
        rotation: -baseRotationArc,
        // angle: 290,
        // innerRadius: 30,
        outerRadius: 30,
        // fill: 'grey',
        stroke: 'black',
        dash: [4,4],
        strokeWidth: 1,
        opacity: 0.2,
        listening: false
    });


    // var AngleTooltip = new Kinetic.Label({
    //     x: initialX/2+60,
    //     y: initialY/2+10,
    //     opacity: 0.75,
    //     // cornerRadius: 10,
    //     offset: {x: 80/2, y: 30/2}
    // });
    // AngleTooltip.add(new Kinetic.Tag({
    //     // fill: '#dedede',
    //     // pointerDirection: 'down',
    //     // pointerWidth: 10,
    //     // pointerHeight: 10,
    //     // lineJoin: 'round',
    //     // shadowColor: 'black',
    //     // shadowBlur: 10,
    //     // shadowOffset: {x:2,y:4},
    //     // shadowOpacity: 0.5,
    //     // cornerradius: 10,
    // }));

    var mainVectorLabel2 = new Kinetic.Text({
        x: 60*Math.cos((-baseRotationArc+180)*Math.PI/180),
        y: 60*Math.sin((-baseRotationArc+180)*Math.PI/180),
        text: (baseRotationArc)+'\xB0',
        // text: 'Tooltip pointing down',
        fontFamily: 'Calibri',
        // fontFamily: 'Neue Helvetica',
        fontSize: 32,
        padding: 10,
        fill: '#918f8a',
        // fill: 'white',
        // fill: color,
        name: 'vectorlabel',
        // shadowColor: '#000000',
        // shadowColor: color,
        // shadowBlur: 5,
        // shadowOffset: {x:0,y:0},
        offset: {x: 80/2, y: 30/2},
        listening: false,
        opacity: 0,
    });


    var mainVectorLabel = new Kinetic.Text({
        // x: initialX+100,
        // y: initialY-20,
        x: 60*Math.cos((-baseRotationArc+180)*Math.PI/180),
        y: 60*Math.sin((-baseRotationArc+180)*Math.PI/180)-20,
        text: baseLength+' N',
        fontFamily: 'Calibri',
        fontSize: 35,
        padding: 5,
        fill: '#4a4845',
        // fill: 'white',
        name: 'vectorlabel2',
        // shadowColor: 'white',
        // shadowBlur: 3,
        // shadowOffset: {x:0,y:0},
        offset: {x: 70/2, y: 40/2},
        listening: false,
        opacity: 0,
    });
    console.log(label);
    var mainVectorLabel3 = new Kinetic.Text({
        x: (labelstyle=='label-tip' ? initialX*1.3 : initialX/2),
        y: (labelstyle=='label-tip' ? initialY*1.3 : initialY/2),
        // x: 100*Math.cos((-baseRotationArc+180)*Math.PI/180),
        // y: 100*Math.sin((-baseRotationArc+180)*Math.PI/180)-20,
        text: label,
        fontFamily: 'Calibri',
        fontSize: 24,
        padding: 5,
        fill: color,
        // fill: 'white',
        name: 'vectorlabel3',
        // shadowColor: 'white',
        // shadowBlur: 3,
        // shadowOffset: {x:0,y:0},
        offset: {x: 20/2, y: 20/2},
        listening: false,
        opacity: 1,
    });

    var mainVectortip = new Kinetic.Group({
        x: initialX,
        y: initialY,
        name: 'vectortip',
        rotation:(90+baseRotation),
        draggable: (name=='vectorSum' ? false : true)
    });

    mainVectortip.add(mainVectorTriangle);
    mainVectortip.add(mainVectorCircle);
    // mainVectortip.add(mainVectorLabel3);

    var mainVector = new Kinetic.Group({
        x: stage.width() * .5 + xstart,
        y: stage.height() * .5 + ystart,
        draggable: true,
        name: name,
        // id: id,
        id: ('vector'+vectorCount),
        // rotation: -350,
    });
    // console.log('id:'+id+(', vector'+vectorCount));

    mainVector.on('dragmove', function() {
        // console.log(this.x());
        stickPositions(this.id());
        drawlayer.batchDraw();
    });

    mainVector.on('mouseover mousedown touchstart', function() {
        if (projectVectors==1 && this.name()=='vector'){
            mainVectorbaseX.opacity(0.6);
            mainVectorbaseY.opacity(0.6);
            mainVectorbaseXDash.opacity(0.4);
            mainVectorbaseYDash.opacity(0.4);
        }
        mainVectorCircle.opacity((mainVector.name() == 'vectorSum' ? 0 : 0.1));
        mainVectorLabel.opacity(1);
        mainVectorLabel2.opacity(1);
        mainVectorArc.opacity(0.6);
        mainVectortip.scaleX(1.2);
        mainVectortip.scaleY(1.2);
        this.setZIndex(10);
        document.body.style.cursor = 'pointer';
        drawlayer.batchDraw();
    });

    mainVector.on('mouseout mouseup touchend', function() {
        if (projectVectors==1 && this.name()=='vector'){
            mainVectorbaseX.opacity(0);
            mainVectorbaseY.opacity(0);
            mainVectorbaseXDash.opacity(0);
            mainVectorbaseYDash.opacity(0);
        }
        mainVectorCircle.opacity(0);
        mainVectorLabel.opacity(0);
        mainVectorLabel2.opacity(0);
        mainVectorArc.opacity(0.2);
        mainVectortip.scaleX(1.0);
        mainVectortip.scaleY(1.0);
        this.setZIndex(1);
        document.body.style.cursor = 'default';
        drawlayer.batchDraw();
    });

    mainVectortip.on('dragend', function() {
        checkForces();
        updateStatus();
    });

    mainVectortip.on('dragmove', function() {

        var tip=[];
        tip.x = mainVectortip.x();
        tip.y = -(mainVectortip.y());
        var baselength = Math.sqrt(Math.pow(tip.x,2)+ Math.pow(tip.y,2));
        var angle = Math.atan2(tip.y, tip.x)*180/Math.PI;
        var angleBody = angle + window[bodyRotation];
        if (angle < 0) angle+=360;
        // console.log('Vector angle:'+angle);
        if (quantizeLength==1){
            baselength = Math.round(baselength/5) * 5;
        }
        else{
            baselength = Math.round(baselength);
        }

        var pos=[];
        var posBodyFrame=[];
        pos.x = baselength*Math.cos(angle*Math.PI/180);
        pos.y = -baselength*Math.sin(angle*Math.PI/180);
        mainVectortip.position(pos);
        mainVectorbase.points([0, 0, pos.x, pos.y]);

        // var pos=[];
        // pos.x = baselength*Math.cos(angle*Math.PI/180);
        // pos.y = -baselength*Math.sin(angle*Math.PI/180);
        // mainVectortip.position(pos);
        // mainVectorbase.points([0, 0, pos.x, pos.y]);

        var stickyAngle;

        $.each(stickyAngles,function() {
            if (Math.abs(this-angle) < 5) stickyAngle = this;
        });

        if (stickyAngle && stickAngles) {
            angle = stickyAngle;
            pos.x = baselength*Math.cos(stickyAngle*Math.PI/180);
            pos.y = -baselength*Math.sin(stickyAngle*Math.PI/180);
            posBodyFrame.x = baselength*Math.cos((stickyAngle+window[bodyRotation])*Math.PI/180);
            posBodyFrame.y = -baselength*Math.sin((stickyAngle+window[bodyRotation])*Math.PI/180);
            mainVectortip.position(pos);
            mainVectorbase.points([0, 0, pos.x, pos.y]);
            // if (projectVectors==1){
                mainVectorbaseX.points([0, 0, posBodyFrame.x, 0]);
                mainVectorbaseY.points([0, 0, 0, posBodyFrame.y]);
                mainVectorbaseX.rotation(window[bodyRotation]);
                mainVectorbaseY.rotation(window[bodyRotation]);

                mainVectorbaseXDash.points([posBodyFrame.x, 0, posBodyFrame.x, posBodyFrame.y]);
                mainVectorbaseYDash.points([0, posBodyFrame.y, posBodyFrame.x, posBodyFrame.y]);
                mainVectorbaseXDash.rotation(window[bodyRotation]);
                mainVectorbaseYDash.rotation(window[bodyRotation]);
            // }
            mainVectortip.rotation(90-stickyAngle);
            showVectorSum();
        }
        else
        {
            mainVectortip.rotation(90-angle);
            mainVectorbase.points([0, 0, tip.x, -tip.y]);
            posBodyFrame.x = baselength*Math.cos((angle+window[bodyRotation])*Math.PI/180);
            posBodyFrame.y = -baselength*Math.sin((angle+window[bodyRotation])*Math.PI/180);
            // if (projectVectors==1){
                mainVectorbaseX.points([0, 0, posBodyFrame.x, 0]);
                mainVectorbaseY.points([0, 0, 0, posBodyFrame.y]);
                mainVectorbaseX.rotation(window[bodyRotation]);
                mainVectorbaseY.rotation(window[bodyRotation]);

                mainVectorbaseXDash.points([posBodyFrame.x, 0, posBodyFrame.x, posBodyFrame.y]);
                mainVectorbaseYDash.points([0, posBodyFrame.y, posBodyFrame.x, posBodyFrame.y]);
                mainVectorbaseXDash.rotation(window[bodyRotation]);
                mainVectorbaseYDash.rotation(window[bodyRotation]);
            // }
            showVectorSum();
        }
        // console.log('drag angle: '+angle);
        // console.log('platform angle: '+(angle+window[bodyRotation]));

        mainVectorArc.angle(angle);
        mainVectorArc.rotation(-angle);

        // mainVectorLabel.x(mainVectortip.x()/2+60);
        // mainVectorLabel.y(mainVectortip.y()/2+10);
        mainVectorLabel2.x(60*Math.cos((-angle+180)*Math.PI/180));
        mainVectorLabel2.y(60*Math.sin((-angle+180)*Math.PI/180));
        // AngleTooltip.find('Text')[0].text(Math.round(angle)+'\xB0'); //\xB0
        mainVectorLabel2.text(Math.round(angle)+'\xB0'); //\xB0

        // MagnitudeTooltip.x(-mainVectortip.x()/2*Math.cos(-angle*Math.PI/180*3/4));
        // MagnitudeTooltip.y(mainVectortip.y()/2*Math.sin(-angle*Math.PI/180*3/4));
        mainVectorLabel.x(60*Math.cos((-angle+180)*Math.PI/180));
        mainVectorLabel.y(60*Math.sin((-angle+180)*Math.PI/180)-20);

        // mainVectorLabel.x(mainVectortip.x()+100);
        // mainVectorLabel.y(mainVectortip.y()-20);
        mainVectorLabel.text(baselength + ' N'); //\xB0
    });

    mainVector.add(mainVectorArc);
    mainVector.add(mainVectorbaseXDash);
    mainVector.add(mainVectorbaseYDash);
    mainVector.add(mainVectorbaseX);
    mainVector.add(mainVectorbaseY);
    mainVector.add(mainVectorbase);
    mainVector.add(mainVectortip);
    // mainVector.add(mainVectorLabel);
    mainVector.add(mainVectorLabel2);
    mainVector.add(mainVectorLabel);
    mainVector.add(mainVectorLabel3);
    drawlayer.add(mainVector);

    vectors.push({direction: baseRotation, magnitude: baseLength, name: id, compPlatformX: initialX, compPlatformY: initialY});
}