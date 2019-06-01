function Map(mapSize){
	this.blocks = [];
	this.createMapBlock(mapSize);
}

Map.prototype.createMapBlock = function(reSeq) {
	var block = new Block();
	if(this.blocks.length==0){
		block.isStart();
	}
	this.blocks.push(block);
	if(reSeq!=0){
		this.createMapBlock(reSeq-1);
	}
};