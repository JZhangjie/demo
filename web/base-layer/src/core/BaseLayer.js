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
	state:Constants.dialog.states[0]    //'normal','full','min' 
}

//界面生成入口
BaseLayer.fn.create=function(){
	var that = this;

	this.initDom();
	
	this.setContentContainerSize();
	
	this.initEvents();
	$(window).resize(function(){
		that.setContentContainerSize();
	});
}

//生成dom对象
BaseLayer.fn.initDom=function(){
	this.dom.append(this.createShade());
	this.dom.append(this.createContentContainer());
	this.dom.append(this.createMovePanel());
	$("body").append(this.dom);
}

//添加移动、缩放等事件
BaseLayer.fn.initEvents=function(){
	var that = this;
	if(this.dom){
			//移动事件
			var title = this.dom.find('.base-layer-title');
			var move = this.dom.find('.base-layer-move');
			
			title.mousedown(function(e){
				that.dom.data('moving',true);
				that.dom.data('X',e.clientX);
				that.dom.data('Y',e.clientY);
				move.show();
			});
			$(window).mousemove(function(e){
				if(that.dom.data('moving')){
					var x = e.clientX-that.dom.data('X');
					var y = e.clientY-that.dom.data('Y');
					that.dom.data('X',e.clientX);
					that.dom.data('Y',e.clientY);
					if(x || y){
						that.resizePosition(x?x:0,y?y:0);
					}
				}
			});
			$(window).mouseup(function(e){
				if(that.dom.data('moving')){
					that.dom.data('moving',false);
					that.dom.data('X',NaN);
					that.dom.data('Y',NaN);
					move.hide();
				}
			});
	}
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
	return title;
}

//生成标题
BaseLayer.fn.createMovePanel=function(){
	var that = this;
	var move= $("<div>");
	move.addClass("base-layer-move");
	return move;
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
	var viewWidth=window.innerWidth;
	var viewHeight=window.innerHeight;
	if(this.config.state==Constants.dialog.states[1]){
		container.css('left',0);
		container.css('top',0);
	}else{
		var left = container.position().left+mx;
		var top = container.position().top+my;
		var maxtop =  viewHeight-this.config.height-1;
		var maxleft = viewWidth-this.config.width-1;
		top=top<1?1:(top>maxtop?maxtop:top);
		left=left<1?1:(left>maxleft?maxleft:left)
		container.css('left',left+"px");
		container.css('top',top+"px");
	}
}

//关闭
BaseLayer.fn.close=function(){
	this.dom.remove();
}

module.exports=BaseLayer;