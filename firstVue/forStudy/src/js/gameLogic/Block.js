/**
 *	---1---
 *	|	  |
 *	4	  2
 *	|	  |
 *	---3---
 *
 *1号口所对应的方向即是block的方向
 *	N、E、S、W
 *
 *
 *
 *
 */
function Block(){
	this.isStartBlock = false;
	waitToClikc();
}

Block.prototype.isStart = function() {
	this.isStartBlock = true;
};