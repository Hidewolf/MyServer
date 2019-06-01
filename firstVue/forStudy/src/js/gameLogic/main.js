/*
抽象对象：
	流程对象
	地图对象
	角色对象
	玩家对象
	宝箱对象
*/
//流程对象：
function MainFunction(playerNumber,mapSize,chessPerPlayer){
	//创建玩家
	this.users = new Users(playerNumber);
	//创建地图块
	this.map = new Map(mapSize);
	//渲染地图
	//创建角色
	//设置地图
	//游戏开始
}

MainFunction.prototype.drawGame = function() {
};


