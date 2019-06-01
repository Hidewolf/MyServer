
function Users(playerNumber){
	this.users = [];
	for(var i = 0;i<playerNumber;i++){
		this.users.push(this.createUser(i));
	}
}

Users.prototype.createUser = function(seq){
	var color = '#'+Math.floor(Math.random()*16777215).toString(16);
	while(this.containColor(color)){
		color = '#'+Math.floor(Math.random()*16777215).toString(16);
	}
	return new User(seq,color);
}

Users.prototype.containColor = function(color){
	for(var i = 0;i<this.users.length;i++){
		if(this.users[i].color == color){
			return true;
		}
	}
	return false;
}