var $=require('jquery');
var Constants=require('./Constants.js');

//窗体类
var BaseLayer=function(settings){
	this.config = $.extend({},this.config,settings);
	this.dom=$("<div>");
	this.dom.addClass("base-layer");
	this.create();
}
BaseLayer.fn=BaseLayer.prototype;

//默认初始配置
BaseLayer.fn.config={
	width:400,
	height:600,
	top:0,
	left:0,
	state:Constants.dialog.states[0]    //'full','normal','min' 
}

//界面生成入口
BaseLayer.fn.create=function(){
	var that = this;
	this.dom.append(this.createShade());
	this.dom.append(this.createContentContainer());
	$("body").append(this.dom);
	
	this.setContentContainerSize();
	
	$(window).resize(function(){
		that.setContentContainerSizeAndPosition();
	});
}

//生成遮罩
BaseLayer.fn.createShade=function(){
	var that = this;
	var shade = $("<div>");
	shade.addClass("base-layer-shade");
	shade.click(function(e){
		that.close();
	});
	return shade;
}
//内容容器，会添加一些预定义组件，如多个按钮等
BaseLayer.fn.createContentContainer=function(){
	var container= $("<div>");
	container.addClass("base-layer-container");
	container.append(this.createTitle());
	container.append(this.createTools());
	container.append(this.createContent());
	return container;
}

//生成标题
BaseLayer.fn.createTitle=function(){
	var that = this;
	var title= $("<div>");
	title.addClass("base-layer-title");
	title.mousedown(function(e){
		title.data('moving',true);
		title.data('X',e.clientX);
		title.data('Y',e.clientY);
		console.log("down",e.clientX,e.clientY);
	});
	title.mousemove(function(e){
		if(title.data('moving')){
			var x = e.clientX-title.data('X');
			var y = e.clientY-title.data('Y');
			title.data('X',e.clientX);
			title.data('Y',e.clientY);
			if(x || y){
				that.resizePosition(x?x:0,y?y:0);
			}
			console.log("move",e.clientX,e.clientY);
		}
	});
	title.mouseup(function(e){
		title.data('moving',false);
		title.data('X',NaN);
		title.data('Y',NaN);
	});
	return title;
}

//生成内容 ，外部传入
BaseLayer.fn.createContent=function(){
	return "测试";
}

//生成tools关闭、最大最小化，大小改变
BaseLayer.fn.createTools=function(){
	var tool = $("<div>");
	tool.addClass("base-layer-tools");
	
	var close = $("<div>");
	close.addClass("base-layer-icon");
	close.addClass("base-layer-close");
	
	tool.append(close);
	
	return tool;
}

//设置窗口大小和位置
BaseLayer.fn.setContentContainerSize=function(){
	if(this.dom){
		var container = this.dom.find(".base-layer-container");
	}
	var viewWidth=window.innerWidth;
	var viewHeight=window.innerHeight;
	if(this.config.state==Constants.dialog.states[1]){
		container.css('height',viewHeight+"px");
		container.css('width',viewWidth+"px");
		container.css('left',0);
		container.css('top',0);
	}else{
		container.css('height',this.config.height+"px");
		container.css('width',this.config.width+"px");
		container.css('left',(viewWidth-this.config.width)/2+"px");
		container.css('top',(viewHeight-this.config.height)/2+"px");
	}
}
//设置窗口大小和位置
BaseLayer.fn.resizePosition=function(mx,my){
	if(this.dom){
		var container = this.dom.find(".base-layer-container");
	}
	if(this.config.state==Constants.dialog.states[1]){
		container.css('left',0);
		container.css('top',0);
	}else{
		container.css('left',container.offset().left+mx+"px");
		container.css('top',container.offset().top+my+"px");
	}
}

//关闭
BaseLayer.fn.close=function(){
	this.dom.remove();
}

module.exports=BaseLayer;