"use strict";
function printMatrix(input){
    for(var i = 0; i < input.matrix.length; i++){
        var text = "";
        for(var j = 0; j < input.matrix[i].length; j++){
            text += input.matrix[i][j].toString() + " ";
        }
        console.log(text);
    }

}
class Vector4f {
    constructor(x, y, z, h) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.h = h;
        this.matrix = [[x],[y],[z],[h]]
    }
    static negate(input) {
        return new Vector4f(0-input.x, 0-input.y, 0-input.z, input.h);
    }
    static length(input) {
        return Math.sqrt(Math.pow(input.x, 2) + Math.pow(input.y, 2) + Math.pow(input.z, 2));
    }
    static add(input1, input2) {
        return new Vector4f(input1.x + input2.x, input1.y + input2.y, input1.z + input2.z, Math.floor((input1.h + input2.h)/2));
    }
    static scalarProduct(input1, input2) {
        return new Vector4f(input1 * input2.x, input1 * input2.y, input1 * input2.z, input2.h);
    }
    static dotProduct(input1, input2) {
        return (input1.x * input2.x) + (input1.y * input2.y) + (input1.z * input2.z);
    }
    static crossProduct(input1, input2) {
        return new Vector4f(input1.y * input2.z - input1.z * input2.y, input1.z * input2.x - input1.x * input2.z, input1.x * input2.y - input1.y * input2.x, Math.floor((input1.h + input2.h)/2));
    }
    static normalize(input) {
        var length = this.length(input);
        return new Vector4f(input.x / length, input.y / length, input.z / length, input.h);
    }
    static project(input1, input2) {
        return this.scalarProduct(this.dotProduct(input2, input1) / this.dotProduct(input2, input2), input2);
    }
    static cosPhi(input1, input2) {
        return this.dotProduct(input1, input2) / (this.length(input1) * this.length(input2));
    }
}
class Matrix4f {
    constructor(array){
        this.matrix = new Array(4);
        for(var i = 0; i < 4; i++){
            this.matrix[i] = new Array(4);
        }
        for(i = 0; i < 4; i++){
            for(var j = 0; j < 4; j++){
                this.matrix[i][j] = array[i][j];
            }
        }
    }
    static negate(input) {
        var temp = new Matrix4f(input.matrix);
        for(var i = 0; i < 4; i++){
            for(var j = 0; j < 4; j++){
                temp.matrix[i][j] = 0-input.matrix[i][j];
            }
        }
        return temp;
    }
    static add(input1, input2){
        var temp = new Matrix4f(input1.matrix);
        for(var i = 0; i < 4; i++){
            for(var j = 0; j < 4; j++){
                temp.matrix[i][j]+=input2.matrix[i][j];
            }
        }
        return temp;
    }
    static transpose(input) {
        var temp = new Matrix4f([[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]);
        for(var i = 0; i < 4; i++){
            for(var j = 0; j < 4; j++){
                temp.matrix[i][j] = input.matrix[j][i];
            }
        }
        return temp;
    }
    static multiplyScalar(input1, input2) {
        var temp = new Matrix4f(input2.matrix);
        for(var i = 0; i < 4; i++){
            for(var j = 0; j < 4; j++){
                temp.matrix[i][j] *= input1;
            }
        }
        return temp;
    }
    static multiply(input1, input2) {
        var n = input1.matrix.length;
        var m = input1.matrix[0].length;
        var p = input2.matrix[0].length;
        var temp = [];
        var result;
        console.log(n.toString() + " x " + m.toString() + ", " + m.toString() + " x " + p.toString());
        for(var a = 0; a < n; a++){
            temp[a] = [];
        }
        for(var i = 0; i < n; i++){
            for(var j = 0; j < p; j++){
                var sum = 0;
                for(var k = 0; k < m; k++) {
                    sum = sum + input1.matrix[i][k] * input2.matrix[k][j];
                }
                temp[i][j] = sum;
            }
        }
        if(p == 1){
            result = new Vector4f(temp[0][0], temp[1][0], temp[2][0], input2.h);
        }else{
            result = new Matrix4f(temp);
        }
        printMatrix(result);
        return result;
    }
}
class Transformation{
    constructor(){
        this.matrika = new Matrix4f([[1, 0, 0, 0], [0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]]);
    }
    translate(input) {
        var translacijskaMatrika = new Matrix4f([[1, 0, 0, input.x], [0, 1, 0, input.y], [0, 0, 1, input.z], [0, 0, 0, 1]]);
        this.matrika = Matrix4f.multiply(this.matrika, translacijskaMatrika);
    }
    scale(input) {
        var skalarnaMatrika = new Matrix4f([[input.x, 0, 0, 0], [0, input.y, 0, 0], [0, 0, input.z, 0], [0, 0, 0, 1]]);
        this.matrika = Matrix4f.multiply(this.matrika, skalarnaMatrika);
    }
    rotateX(input) {
        var rotacijskaMatrika = new Matrix4f([[1, 0, 0, 0], [0, Math.cos(input), 0-Math.sin(input), 0], [0, Math.sin(input), Math.cos(input), 0], [0, 0, 0, 1]]);
        this.matrika = Matrix4f.multiply(this.matrika, rotacijskaMatrika);
    }
    rotateY(input) {
        var rotacijskaMatrka = new Matrix4f([[Math.cos(input), 0, Math.sin(input), 0], [0, 1, 0, 0], [0-Math.sin(input), 0, Math.cos(input), 0], [0, 0, 0, 1]]);
        this.matrika = Matrix4f.multiply(this.matrika, rotacijskaMatrka);
    }
    rotateZ(input) {
        var rotacijskaMatrika = new Matrix4f([[Math.cos(input), 0-Math.sin(input), 0, 0], [Math.sin(input), Math.cos(input), 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]]);
        this.matrika = Matrix4f.multiply(this.matrika, rotacijskaMatrika);
    }
    transformPoint(input) {
        return Matrix4f.multiply(this.matrika, input);
    }
}
class PointManager {
    constructor(){
        this.points = [];
    }
    readPoints(text){
        var lineArray = text.split("\n");
        for(var i = 0; i < lineArray.length; i++){
            var values = lineArray[i].split(" ");
            this.points[i] = new Vector4f(parseInt(values[1]), parseInt(values[2]), parseInt(values[3]), 0);
        }
    }

    pointsToText(){
        var text = "";
        for(var i = 0; i < this.points.length; i++){
            text += "V " + this.points[i].x + " " + this.points[i].y + " " + this.points[i].z + "\n";
        }
        return text;
    }

    clearPoints() {
        this.points = [];
    }
}
class prikaz {
    constructor() {
        this.pm = new PointManager();
        this.trans = new Transformation();
        this.trans.translate(new Vector4f(1.24, 0, 0, 1));
        this.trans.rotateZ(Math.PI/3);
        this.trans.translate(new Vector4f(0, 0, 4.13, 1));
        this.trans.translate(new Vector4f(0, 3.14, 0, 1));
        this.trans.scale(new Vector4f(1.12, 1.12, 1, 1));
        this.trans.rotateY(5 * Math.PI / 8);
    }
    button(){
        this.pm.clearPoints();
        this.pm.readPoints(document.getElementById("vnos").value);
        for(var i = 0; i < this.pm.points.length; i++){
            this.pm.points[i] = this.trans.transformPoint(this.pm.points[i]);
        }
        document.getElementById('izpis').innerHTML = this.pm.pointsToText();
    }
}


var test = new prikaz();