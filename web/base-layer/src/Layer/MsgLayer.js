var $=require('jquery');
var Constants=require('../core/constants.js');
var util=require('../core/util.js');
var BaseLayer=require('../core/BaseLayer.js') ;

var config={
	hasTitle:false,
	canMove:false,			//是否可以移动
	canZoom:false,			//是否可以改变大小
	hasClose:false,
	hasMin:false,
	hasMax:false,
	hasShade:false,			//有遮罩
	shadeClickable:false,  //遮罩点击关闭
	time:3000,			//窗口存在时间  0表示一直存在
	width:-1,
	height:-1,
	minWidth:160,
	minHeight:60,
	top:-1,
	left:-1,
	state:Constants.dialog.states[1]    //'min' ,'normal','max',
}

var MsgLayer=function(setting){
	if(util.isString(setting)){
		this.config.content=setting;
	}
	else{
		this.config = $.extend(this.config,setting);
	}
}

MsgLayer.fn=MsgLayer.prototype=new BaseLayer(config);

//重写内容
MsgLayer.fn.createContent=function(){
	var content = $("<div class='base-layer-msg'>");
	content.html(this.config.content);
	return content;
}

module.exports=MsgLayer;