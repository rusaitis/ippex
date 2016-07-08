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

// function rgbToHex(r, g, b) {
    // return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
// }

function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}

function rgbToHex(r, g, b) {
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

var colors = ['#DC3522', '#374140', '#D9CB9E', '#00A388', '#BEEB9F', '#FF6138', '#787746', '#703030', '#2F343B', '#C77966', '#7E827A', '#01B0F0', '#FF358B', '#AEEE00', '#F5A503', '#36B1BF', '#FFEEAD', '#8F8164', '#593325'];

function getRandomColor() {
    return colors[Math.round(Math.random() * (colors.length-1))];
}


// Create a DOM <div> element
function createDiv(obj) {
    var div = document.createElement("div");
        div.id =  obj.id;
        div.className = obj.class;
        div.style.left = obj.left + 'px';
        div.style.top = obj.top + 'px';
        // div.style.margin = 0.6*slider.R+'px';
        // div.style.margin = obj.margin;
        div.style.width = obj.w + "px";
        div.style.height = obj.h + "px";
    return div;
}

// Create a DOM <p> element
function createP(obj) {
    para = document.createElement("P");
        para.style.position = (obj.pos || "absolute");
        para.style.margin = (obj.margin || '0 0 0 0');
        para.style.width = (obj.wInfo || '100') + "px";
        para.style.height = (obj.hInfo || '30') + "px";
        para.style.fontSize = (obj.hInfo || '22') + "px";
        para.style.color = (obj.colour || '#ffffff');
        para.style.align = (obj.center || 'center');
        para.className = (obj.class || '');
    return para;
}
// Create a DOM text element
function createText(obj) {
    text = document.createTextNode(obj.txt); // Create a text node
        text.type = "text";
        text.id = obj.id;
    return text;
}

// Create a DOM <input> element
function createInput(obj) {
    var input = document.createElement("input");
        input.type = "text";
        input.id = obj.id;
        input.value = obj.value;
        input.className = obj.class;
        input.style.width = obj.w + "px";
        input.style.height = obj.h + "px";
        input.style.fontSize = obj.fontSize + "px";
        input.style.color = obj.colour;
    return input;
}