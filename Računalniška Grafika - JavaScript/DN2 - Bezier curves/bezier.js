class krivulja {

    constructor(tocke, id){
		this.id = id;
        this.bernstein = new matrica(4, 4, [1, -3, 3, -1,
                                            0, 3, -6,  3,
                                            0, 0,  3, -3,
                                            0, 0,  0,  1]);
        this.tocka = new Array(4);
        for(var i = 0; i < 4; i++){
            this.tocka[i] = tocke[i];
        }
        var temp = new Array(2*4);
        for(i = 0; i < 2; i++){
            for(var j = 0; j < 4; j++){
                temp[i*4+j] = this.tocka[j].array[i];
            }
        }
        this.matricaTock = new matrica(4, 2, temp);
        this.zmnozeneMatrice = matrica.multiply(this.matricaTock, this.bernstein);
		this.steviloPik = (tocka.oddaljenost(this.tocka[0], this.tocka[2]) + tocka.oddaljenost(this.tocka[1], this.tocka[2]))/10 ;
    }

    interpolirajTocko(t){
        var vektorT = new matrica(1, 4, [1, t, t*t, t*t*t]);
        var zmnozek = matrica.multiply(this.zmnozeneMatrice, vektorT);
        return new tocka(zmnozek.matrix[0][0], zmnozek.matrix[1][0]);
    }

    izrisiKrivuljo(canvas){
        canvas.moveTo(this.tocka[0].x, this.tocka[0].y);
        for(var i = 0.0; i <= this.steviloPik; i = i + 1.0){
            var interpoliranePike = this.interpolirajTocko(i/this.steviloPik);
			canvas.lineTo(interpoliranePike.x, interpoliranePike.y);
			canvas.stroke();
			canvas.moveTo(interpoliranePike.x, interpoliranePike.y);
        }
		canvas.lineTo(this.tocka[3].x, this.tocka[3].y);
		canvas.stroke();
		for(i = 0; i < 4; i++){
			this.tocka[i].izrisi(canvas);
		}
    }

}

class matrica {
    constructor(m, n, array){
        this.m = m;
        this.n = n;
        this.matrix = new Array(this.n);
        for(var i = 0; i < this.n; i++){
            this.matrix[i] = new Array(this.m);
            for(var j = 0; j < this.m; j++){
                this.matrix[i][j] = array[i*this.m+j];
            }
        }
    }

    static multiply(input1, input2) {
        if (input1.m != input2.n){
            return input1;
        }
        var temp = new Array(input1.n * input2.m);
        var result;
        for(var i = 0; i < input1.n; i++){
            for(var j = 0; j < input2.m; j++){
                var sum = 0;
                for(var k = 0; k < input1.m; k++) {
                    sum = sum + input1.matrix[i][k] * input2.matrix[k][j];
                }
                temp[i*input2.m+j] = sum;
            }
        }
        result = new matrica(input2.m, input1.n, temp);
        return result;
    }
}

class tocka {
    constructor(x, y, interpolirana){
        this.x = x;
        this.y = y;
        this.updateArray();
		this.inter = interpolirana;
    }
    updateArray(){
        this.array = [this.x, this.y];
    }
    move(newx, newy){
        this.x = newx;
        this.y = newy;
        this.updateArray();
    }
    izrisi(ctx){
		if(this.inter){
			ctx.fillRect(this.x-5, this.y-5, 10, 10);
		}else{
			ctx.beginPath();
			ctx.arc(this.x, this.y, 10, 0, Math.PI*2);
			ctx.stroke();
		}
    }
	static oddaljenost(tockaA, tockaB){
		var x = Math.abs(tockaA.x - tockaB.x);
		var y = Math.abs(tockaA.y - tockaB.y);
		return Math.sqrt(x*x + y*y);
	}
}

class pointManager {
	constructor(){
		this.pointArray = new Array(4);
		this.counter = 0;
	}
	addPoint(x, y){
		var inter = (this.counter > 0) && (this.counter < 3);
		var point = new tocka(x, y, inter);
		if(this.counter <= 3){
			this.pointArray[this.counter] = point;
			this.counter++;
		}
	}
}

class curveManager {
	constructor(){
		this.curveArray = [];
		this.counter = 0;
	}
	addCurve(curve){
		this.curveArray[this.counter] = curve;
		this.counter++;
		this.updateList();
	}
	updateList(){
		var selectBox = document.getElementById("izbris");
		selectBox.options.length = 0;
		for(var i = 0; i < this.curveArray.length; i++){
			var option = document.createElement("option");
			option.text = this.curveArray[i].id;
			selectBox.add(option);
		}
	}
	
	removeCurve(id){
		var tempArray = [];
		this.counter = 0;
		for(var i = 0; i < this.curveArray.length; i++){
			if(this.curveArray[i].id != id){
				tempArray[this.counter++] = this.curveArray[i];
			}
		}
		this.curveArray = tempArray;
		this.updateList();
	}
	drawCurves(canvas){
		for(var i = 0; i < this.curveArray.length; i++){
			this.curveArray[i].izrisiKrivuljo(canvas);
		}
	}
}

class prikaz{
	constructor(){
		this.c = document.getElementById("canvas");
		this.ctx = this.c.getContext("2d");
		this.pm = new pointManager();
		this.cm = new curveManager();
		this.currentId = 0;
	}
	addPoint(x, y){
		this.pm.addPoint(x, y);
		for(var i = 0; i < this.pm.counter; i++){
			this.pm.pointArray[i].izrisi(this.ctx);
		}
		if(this.pm.counter == 4){
			this.cm.addCurve(new krivulja(this.pm.pointArray, this.currentId++));
			this.cm.drawCurves(this.ctx);
			this.pm = new pointManager();
		}
	}
	removeCurve(){
		var selectBox = document.getElementById("izbris");
		var id = selectBox.options[selectBox.selectedIndex].value;
		this.cm.removeCurve(id);
		this.ctx.clearRect(0, 0, 1600, 900);
		this.cm.drawCurves(this.ctx);
		this.pm = new pointManager();
	}
}

var test = new prikaz();
test.c.addEventListener("click", function(event){if((event.pageX < 1600) && (event.pageY < 900)) test.addPoint(event.pageX, event.pageY);});

