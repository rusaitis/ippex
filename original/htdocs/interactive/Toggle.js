var toggle_f = new Array(true,false,false,false,false,false,false,false,false,false,false);
function Tgl(nameOfElement,index) {
	//alert("I start here");
	var obj = document.getElementById(nameOfElement);
		if (obj) {
			if(toggle_f[index]) {
				obj.style.display = "block";
				toggle_f[index] = false;
				//alert("false");
			}else {
				obj.style.display = "none";
				toggle_f[index] = true;
				//alert("true");
			}
		} else {
			alert("Couldn't get object: " + nameOfElement + ", " + index);
		}
}