var $=require('jquery');
var Constants=require('../core/constants.js');
var util=require('../core/util.js');
var BaseLayer=require('../core/BaseLayer.js') ;

var config={
	anchor:null,
	anchorPosition:Constants.dialog.tipPositions[2],
	hasTitle:false,
	canMove:false,			//是否可以移动
	canZoom:false,			//是否可以改变大小
	hasClose:false,
	hasMin:false,
	hasMax:false,
	hasShade:false,			//有遮罩
	shadeClickable:false,  //遮罩点击关闭
	time:0,			//窗口存在时间  0表示一直存在
	width:-1,
	height:-1,
	minWidth:160,
	minHeight:60,
	top:-1,
	left:-1,
	state:Constants.dialog.states[1]    //'min' ,'normal','max',
}

var TipLayer=function(setting){
	if(util.isString(setting)){
		this.config.content=setting;
	}
	else{
		this.config = $.extend(this.config,setting);
	}
}

TipLayer.fn=TipLayer.prototype=new BaseLayer(config);

//初始化配置参数
TipLayer.fn.initConfig=function(){
	BaseLayer.fn.initConfig.apply(this);
	
	if(this.config.anchor){
		this.config.anchor=$(this.config.anchor);
	}
}

//重写内容
TipLayer.fn.createContent=function(){
	var content = $("<div class='base-layer-tip'>");
	var arrow = $("<span>");
	var index=Constants.dialog.tipPositions.indexOf(this.config.anchorPosition);
	index=(index+1)%2==0?index-1:index+1;
	var cname=Constants.dialog.tipPositions[index];
	arrow.addClass(cname+'-arrow');
	content.append(arrow);
	content.append(this.config.content);
	return content;
}

//重写位置和大小
TipLayer.fn.setContentContainerSizeAndPosition=function(){
	if(!this.containerDom){
		return;
	}
	BaseLayer.fn.setContentContainerSize.apply(this);
	var container = this.containerDom;
	var anchor = this.config.anchor;
	var wStop = $(window).scrollTop();
	var wSleft = $(window).scrollLeft();
	var top =0;
	var letf = 0;
	switch (this.config.anchorPosition){
		case Constants.dialog.tipPositions[0]: //top
			top=anchor.offset().top-container.height();
			left=anchor.offset().left-15;
			break;
		case Constants.dialog.tipPositions[1]: //bottom
			top=anchor.offset().top+anchor.outerHeight();
			left=anchor.offset().left-15;
			break;
		case Constants.dialog.tipPositions[2]: //left
			top=anchor.offset().top-15;
			left=anchor.offset().left-container.width();
			break;
		case Constants.dialog.tipPositions[3]: //right
			top=anchor.offset().top-15;
			left=anchor.offset().left+anchor.outerWidth();
			break;
		default:
			break;
	}
	this.config.top=top-wStop;
	this.config.left=left-wSleft;
	BaseLayer.fn.setContentContainerPosition.apply(this);
	
}

module.exports=TipLayer;