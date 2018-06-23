"use strict";
class point {

	constructor(x, y){
		this.x = x;
		this.y = y;
	}
}

class ball {
	constructor(center, w, h, direction){
		this.MOVE_PX = 5;
		this.width = w;
		this.height = h;
		this.center = center;
		this.direction = direction;
		this.color = 'white';
	}
	move(){
		this.center = new point(this.center.x + (this.MOVE_PX * this.direction.x), this.center.y + (this.MOVE_PX * this.direction.y));
	}
	bounceX(){
		this.direction.x = this.direction.x * -1;
	}
	bounceY(){
		this.direction.y = this.direction.y * -1;
	}
	draw(ctx){
		ctx.beginPath();
		ctx.rect(this.center.x - this.width/2, this.center.y - this.height/2, this.width, this.height);
		ctx.fillStyle = this.color;
		ctx.fill();
		ctx.lineWidth = 1;
		ctx.strokeStyle = 'black';
		ctx.stroke();
	}
	colisionWithWall(maxDimension){
		var edgesX = [];
		edgesX[0] = this.center.x - this.width/2;
		edgesX[1] = this.center.x + this.width/2;
		var edgesY = [];
		edgesY[0] = this.center.y - this.height/2;
		edgesY[1] = this.center.y + this.height/2;
		if ((edgesX[0] <= 0) || (edgesX[1] >= maxDimension)) this.bounceX();
		if ((edgesY[0] <= 0) || (edgesY[1] >= maxDimension)) this.bounceY();
	}
	colisionWithBall(which){
		if((this.center.x - this.width/2 < which.center.x + which.width/2) &&
		   (this.center.x + this.width/2 > which.center.x - which.width/2) &&
		   (this.center.y - this.height/2 < which.center.y + which.height/2) &&
		   (this.center.y + this.height/2 > which.center.y - which.height/2))
		{
			this.color = 'red';
			which.color = 'red';
			return 1;
		}
		return 0;
	}
}

class area {
	constructor(center, hw){
		this.center = center;
		this.halfWidth = hw;
	}
	containsItem(item){
		var edgePoints = [];
		edgePoints[0] = new point(item.center.x - item.width/2, item.center.y - item.height/2);
		edgePoints[1] = new point(item.center.x - item.width/2, item.center.y + item.height/2);
		edgePoints[2] = new point(item.center.x + item.width/2, item.center.y - item.height/2);
		edgePoints[3] = new point(item.center.x + item.width/2, item.center.y + item.height/2);
		for(var i = 0; i < 4; i++){
			if(this.containsPoint(edgePoints[i])){
				return true;
			}
		}
		return false;
	}
	containsPoint(which){
		return ((which.x >= this.center.x - this.halfWidth) 
			 && (which.x <= this.center.x + this.halfWidth)
			 && (which.y >= this.center.y - this.halfWidth)
			 && (which.y <= this.center.y + this.halfWidth));
	}

	intersectsArea(which){		
		return ((this.center.x - this.halfWidth <= which.center.x - which.halfWidth) &&
			    (this.center.x + this.halfWidth >= which.center.x + which.halfWidth) &&
			    (this.center.y - this.halfWidth <= which.center.y - which.halfWidth) &&
			    (this.center.y + this.halfWidth >= which.center.y + which.halfWidth));
	}
}

class quadTree {
	constructor(bounds, depth){
		this.MAX_CAP = 4;
		this.MAX_DEPTH = 4;
		this.bounds = bounds;
		this.depth = depth;
		this.items = [];
		this.children = [];
		this.hasChildren = false;
	} 
	addItem(item){
		if(!(this.bounds.containsItem(item))){
			return false;
		}
		var len = this.items.length;
		if((len < this.MAX_CAP)){
			this.items.push(item);
			return true;
		}else{
			if(this.children.length == 0) this.divide();
			for(var i = 0; i < 4; i++){
				if(this.children[i].addItem(item)) return true;
			}
			return false;
		}
	}
	divide(){
		var quartWidth = this.bounds.halfWidth/2;
		var centerPoint = this.bounds.center;
		this.children[0] = new quadTree(new area(new point(centerPoint.x - quartWidth, centerPoint.y - quartWidth), quartWidth), this.depth+1);
		this.children[1] = new quadTree(new area(new point(centerPoint.x + quartWidth, centerPoint.y - quartWidth), quartWidth), this.depth+1);
		this.children[2] = new quadTree(new area(new point(centerPoint.x - quartWidth, centerPoint.y + quartWidth), quartWidth), this.depth+1);
		this.children[3] = new quadTree(new area(new point(centerPoint.x + quartWidth, centerPoint.y + quartWidth), quartWidth), this.depth+1);
		this.hasChildren = true;
	}
	draw(ctx){
		if(this.hasChildren){
			for(var i = 0; i < 4; i++){
				this.children[i].draw(ctx);
			}
		}
		ctx.beginPath();
		ctx.rect(this.bounds.center.x - this.bounds.halfWidth, this.bounds.center.y - this.bounds.halfWidth, this.bounds.halfWidth*2, this.bounds.halfWidth*2);
		ctx.lineWidth = 1;
		ctx.strokeStyle = 'black';
		ctx.stroke();
	}
	findBallsInArea(area){
		var ballsInArea = [];
		if(!(this.bounds.intersectsArea(area))){
			return ballsInArea;
		}
		for(var i = 0; i < this.items.length; i++) {
			if(area.containsItem(this.items[i])){
				ballsInArea.push(this.items[i]);
			}
		}
		if(!(this.hasChildren)){
			return ballsInArea;
		}
		for(var i = 0; i < 4; i++){
			ballsInArea = ballsInArea.concat(this.children[i].findBallsInArea(area));
		}
		return ballsInArea;
	}
}

class test {
	constructor() {
		this.ballNum = 100;
		this.balls = [];
		this.canvas = document.getElementById("canvas");
		this.context = this.canvas.getContext("2d");
		this.maxDimension = this.canvas.scrollHeight;
		this.center = new point(this.maxDimension/2, this.maxDimension/2);
		this.rootQT = new quadTree(new area(this.center, this.maxDimension/2), 0);
		this.ballPadding = 50;
		this.intervalMs = 30;
		this.ballDimMax = 11;
		this.ballDimMin = 8;
		this.collisionChecks = 0;
		this.collisions = 0;
		for(var i = 0; i < this.ballNum; i++){
			var ballCenter = new point(Math.random()*(this.maxDimension - this.ballPadding*2) + this.ballPadding,
									   Math.random()*(this.maxDimension - this.ballPadding*2) + this.ballPadding);
			var ballHeight = Math.random() * (this.ballDimMax - this.ballDimMin) + this.ballDimMin;
			var ballWidth = Math.random() * (this.ballDimMax - this.ballDimMin) + this.ballDimMin;
			var ballDir = new point(Math.random() * 1 - 0.5, Math.random() * 1 - 0.5);
			this.balls[i] = new ball(ballCenter, ballHeight, ballWidth, ballDir);

			this.rootQT.addItem(this.balls[i]);
			this.balls[i].color = 'white';
			this.balls[i].draw(this.context);
		}
		this.rootQT.draw(this.context);
		var self = this;
		this.loop = setInterval(function(){self.moveBalls();}, this.intervalMs);
	}
	colisionCheck(qt){
		if(qt.hasChildren){
			for(var i = 0; i < 4; i++){
				this.colisionCheck(qt.children[i]);
			}
		}else{
			var ballsInArea = this.rootQT.findBallsInArea(qt.bounds);
			for(var i = 0; i < ballsInArea.length; i++){
				for(var j = 0; j < ballsInArea.length; j++){
					if(!(i == j)){
						this.collisions += ballsInArea[i].colisionWithBall(ballsInArea[j]);
						this.collisionChecks++;
					}
				}
			}
		}
	}
	changeBallNr(){
		for(var i = 0; i < this.ballNum; i++){
			var ballCenter = new point(Math.random()*(this.maxDimension - this.ballPadding*2) + this.ballPadding,
									   Math.random()*(this.maxDimension - this.ballPadding*2) + this.ballPadding);
			var ballHeight = Math.random() * (this.ballDimMax - this.ballDimMin) + this.ballDimMin;
			var ballWidth = Math.random() * (this.ballDimMax - this.ballDimMin) + this.ballDimMin;
			var ballDir = new point(Math.random() * 1 - 0.5, Math.random() * 1 - 0.5);
			this.balls[i] = new ball(ballCenter, ballHeight, ballWidth, ballDir);
		}
	}
	moveBalls(){
		this.context.clearRect(0, 0, this.maxDimension, this.maxDimension);
		var desiredBalls = document.getElementById("num").value;
		if(this.ballNum != desiredBalls){
			this.ballNum = desiredBalls;
			this.changeBallNr();
		}
		var text = document.getElementById("text");
		text.innerHTML = "Colision Checks: " + this.collisionChecks + "\n" + "Collisions: " + this.collisions + "\n" + "Brute force collision checks: " + this.ballNum*this.ballNum;
		this.collisionChecks = 0;
		this.collisions = 0;
		this.rootQT = new quadTree(new area(this.center, this.maxDimension/2), 0);
		for(var i = 0; i < this.ballNum; i++){
			this.balls[i].colisionWithWall(this.maxDimension);
			this.balls[i].move();
			this.rootQT.addItem(this.balls[i]);
			this.balls[i].color = 'white';
		}
		this.colisionCheck(this.rootQT);
		for(var i = 0; i < this.ballNum; i++){
			this.balls[i].draw(this.context);
		}
		if(document.getElementById("cb").checked) this.rootQT.draw(this.context);
	}
}

var asd = new test();