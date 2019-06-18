var BaseLayer=require('./core/BaseLayer.js') ;
var MsgLayer=require('./Layer/MsgLayer.js') ;

var baseLayer={
	v:"1.0.0",
	index:10000,
	open:function(settings){
		settings=settings?settings:{};
		settings.index=this.index++;
		var layer=new BaseLayer(settings);
		layer.create();
		return settings.index;
	},
	msg:function(content){
		let layer = new MsgLayer(content);
		layer.create();
	}
}

module.exports=baseLayer;