var BaseLayer=require('./core/BaseLayer.js') ;

var baseLayer={
	v:"1.0.0",
	index:10000,
	open:function(settings){
		settings.index=this.index++;
		var layer=new BaseLayer(settings);
		return settings.index;
	}
}

module.exports=baseLayer;