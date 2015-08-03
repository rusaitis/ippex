// PHYSICS CONSTANTS
var g = 10; //ms^-2
var Grav = 6.674e-11; //m^3kg^-1s^-2
// var Grav = 6e-11; //m^3kg^-1s^-2
var Me = 5.97219e24; //kg
// var Me = 6e23; //kg
var Re = 6.371e6;  //m


function square(x) {
  return x * x;
};

function cube (x) {
  return square(x) * x;
};

function getDistance(p1, p2) {
    return Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
}

// FACTORIAL
function f(n) {
    if (n <= 1) {return 1;}
    else { return n * f(n - 1);}
}
// console.log(f(4)); // prints 24. (it's 4×3×2×1)


/*
 * Vector math functions
 */

function dot(a, b) {
  return ((a.x * b.x) + (a.y * b.y));
}
function magnitude(a) {
  return Math.sqrt((a.x * a.x) + (a.y * a.y));
}
function normalize(a) {
  var mag = magnitude(a);

  if(mag == 0) {
    return {
      x: 0,
      y: 0
    };
  }
  else {
    return {
      x: a.x / mag,
      y: a.y / mag
    };
  }
}

function add(a, b) {
  return {
    x: a.x + b.x,
    y: a.y + b.y
  };
}

function subtract(a, b) {
  return {
    x: a.x - b.x,
    y: a.y - b.y
  };
}

function angleBetween(a, b) {
  return Math.acos(dot(a, b) / (magnitude(a) * magnitude(b)));
}
function angle(a) {
  return Math.atan2(a.y,a.x);
}
function rotate(a, angle) {
  var ca = Math.cos(angle);
  var sa = Math.sin(angle);
  var rx = a.x * ca - a.y * sa;
  var ry = a.x * sa + a.y * ca;
  return {
    x: rx * -1,
    y: ry * -1
  };
}
function multiply(a, t) {
  return {
    x: a.x * t,
    y: a.y * t
  };
}
function invert(a) {
  return {
    x: a.x * -1,
    y: a.y * -1
  };
}

function getBaseLog(x, y) {
  return Math.log(y) / Math.log(x);
}

function getMod(x, y) {
  return Math.floor(x/y);
}
//getBaseLog(10, 1000) it returns 2.99...

// Returns a random number between min (inclusive) and max (exclusive)
function getRandom(min, max) {
  return Math.random() * (max - min) + min;
}