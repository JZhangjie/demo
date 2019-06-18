var $=require('jquery');
var Constants=require('./constants.js');
var util=require('./util.js');

//窗体类
var BaseLayer=function(settings){
	this.config = $.extend({},this.config,settings);
}

BaseLayer.fn=BaseLayer.prototype;

//默认初始配置
BaseLayer.fn.config={
	title:'弹窗',
	content:'',
	hasTitle:true,
	titleHeight:36,
	titleWidth:100,			//最小化时使用的宽度
	canMove:true,			//是否可以移动
	canZoom:true,			//是否可以改变大小
	hasClose:true,
	hasMin:true,
	hasMax:true,
	hasShade:true,			//有遮罩
	shadeClickable:true,  //遮罩点击关闭
	time:0,						//窗口存在时间  0表示一直存在
	width:200,
	height:100,
	minWidth:0,
	minHeight:0,
	top:-1,
	left:-1,
	state:Constants.dialog.states[1]    //'min' ,'normal','max',
}

//界面生成入口
BaseLayer.fn.create=function(){
	var that = this;
	
	this.initConfig();
	
	this.initDom();
	
	this.setContentContainerSizeAndPosition();
	
	this.initEvents();
}

//初始化配置参数
BaseLayer.fn.initConfig=function(){
	var that = this;
	
	this.config.minWidth=this.config.width==-1?this.config.titleWidth:this.config.width;

	this.config.minHeight=this.config.height==-1?this.config.titleHeight:this.config.height;
	
}

//生成dom对象
BaseLayer.fn.initDom=function(){
	this.shadeDom=this.createShade();
	$("body").append(this.shadeDom);
	
	if(!this.config.hasShade){
		this.shadeDom.hide();
	}
	
	this.containerDom=this.createContentContainer();
	this.moveDom= this.createMovePanel();
	$("body").append(this.containerDom);
	$("body").append(this.moveDom);
}

//添加移动、缩放等事件
BaseLayer.fn.initEvents=function(){
	var that = this;
	//窗口改变
	$(window).resize(function(){
		that.setContentContainerSizeAndPosition();
	});
	
	//定时关闭
	if(this.config.time){
		setTimeout(function(e){
			that.close();
		},this.config.time);
	}
	
	if(this.containerDom){
			var move = this.moveDom;
			//缩放事件
			if(this.config.canZoom){
				var zoom = this.containerDom.find('.base-layer-zoom');
				zoom.mousedown(function(e){
					that.containerDom.data('zooming',true);
					that.containerDom.data('X',e.clientX);
					that.containerDom.data('Y',e.clientY);
					move.css('cursor','se-resize');
					move.show();
					e.preventDefault?e.preventDefault():e.returnValue=false;
				});
			}

			if(this.config.canMove){
				//移动事件
				var title = this.containerDom.find('.base-layer-title');
				title.mousedown(function(e){
					that.containerDom.data('moving',true);
					that.containerDom.data('X',e.clientX);
					that.containerDom.data('Y',e.clientY);
					move.css('cursor','move');
					move.show();
					e.preventDefault?e.preventDefault():e.returnValue=false;
				});
			}
			
			//鼠标事件缩放、移动
			$(document).mousemove(function(e){
				var x = e.clientX-that.containerDom.data('X');
				var y = e.clientY-that.containerDom.data('Y');
				that.containerDom.data('X',e.clientX);
				that.containerDom.data('Y',e.clientY);
				
				if(that.containerDom.data('moving')){
					if(x || y){
						that.resetPosition(x?x:0,y?y:0);
					}
				}
				
				if(that.containerDom.data('zooming')){
					if(x || y){
						that.resetSize(x?x:0,y?y:0);
					}
				}
			});
			$(document).mouseup(function(e){
				if(that.containerDom.data('moving')){
					that.containerDom.data('moving',false);
				}
				if(that.containerDom.data('zooming')){
					that.containerDom.data('zooming',false);
				}
				that.containerDom.data('X',NaN);
				that.containerDom.data('Y',NaN);
				move.hide();
			});
			
			//点击事件
			var close = this.containerDom.find('.base-layer-close');
			close.click(function(e){
				that.close();
			});
			var min = this.containerDom.find('.base-layer-min');
			min.click(function(e){
				that.min();
			});
			var max = this.containerDom.find('.base-layer-max');
			max.click(function(e){
				that.max();
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
	if(this.config.hasTitle){
		container.append(this.createTitle());
	}
	container.append(this.createContent());
	
	container.append(this.createTools());
	if(this.config.canZoom){
		var zoom= $("<div>");
		zoom.addClass("base-layer-zoom");
		container.append(zoom);
	}
	return container;
}

//生成标题
BaseLayer.fn.createTitle=function(){
	var that = this;
	var title= $("<div>");
	title.addClass("base-layer-title");
	title.html(this.config.title);
	return title;
}

//生成移动、缩放的底板
BaseLayer.fn.createMovePanel=function(){
	var that = this;
	var move= $("<div>");
	move.addClass("base-layer-move");
	return move;
}

//生成内容 ，外部传入
BaseLayer.fn.createContent=function(){
	var content = $("<div class='base-layer-content'>");
	if(this.config.content){
		var temp = $(this.config.content);
		var p = temp.parent();
		if(p && p.length>0){
			temp.remove();
		}
		content.append(temp);
	}
	return content;
}

//生成tools关闭、最大最小化，大小改变
BaseLayer.fn.createTools=function(){
	var tool = $("<div>");
	tool.addClass("base-layer-tools");
	
	if(this.config.hasMin){
		var min = $("<div>");
		min.addClass("base-layer-icon");
		min.addClass("base-layer-min");
		tool.append(min);
	}
	if(this.config.hasMax){
		var max = $("<div>");
		max.addClass("base-layer-icon");
		max.addClass("base-layer-max");
		tool.append(max);
	}
	if(this.config.hasClose){
		var close = $("<div>");
		close.addClass("base-layer-icon");
		close.addClass("base-layer-close");
		tool.append(close);
	}
	return tool;
}

//设置窗口大小和位置
BaseLayer.fn.setContentContainerSizeAndPosition=function(){
	if(!this.containerDom){
		return;
	}
	var container = this.containerDom;
	var viewWidth=this.shadeDom.width();
	var viewHeight=this.shadeDom.height();

	switch (this.config.state){
		case Constants.dialog.states[0]:
			container.css('height',this.config.titleHeight+"px");
			container.css('width',this.config.titleWidth+"px");
			container.css('left',"8px");
			container.css('top',(viewHeight-this.config.titleHeight-8)+"px");
			break;
		case Constants.dialog.states[1]:
			//使用传入的width、height
			if(this.config.width!=-1){
				container.css('width',this.config.width+"px");
			}else{
				container.css('width','auto');
			}	
			if(this.config.height!=-1){
				container.css('height',this.config.height+"px");
			}else{
				container.css('height','auto');
			}
			
			var cWidth=container.width();
			var cHeight=container.height();
			//使用传入的top、left
			if(this.config.top!=-1){
				container.css('top',this.config.top+"px");
			}else{
				container.css('top',(viewHeight-cHeight)/2+"px");
			}
			if(this.config.left!=-1){
				container.css('left',this.config.left+"px");
			}else{
				container.css('left',(viewWidth-cWidth)/2+"px");
			}

			break;
		case Constants.dialog.states[2]:
			container.css('height',viewHeight-2+"px");
			container.css('width',viewWidth-2+"px");
			container.css('left',1);
			container.css('top',1);
			break;
		default:
			break;
	}
}

//缩放窗口大小
BaseLayer.fn.resetSize=function(mx,my){
	if(!this.containerDom){
		return;
	}
	var container = this.containerDom;
	var viewWidth=this.shadeDom.width();
	var viewHeight=this.shadeDom.height();
	var height=container.height();
	var width=container.width();
	var left = container.position().left;
	var top = container.position().top;
	var maxwidth =  viewWidth-left-1;
	var maxheight = viewHeight-top-1;
	height = height+my>maxheight?maxheight:(height+my<this.config.minHeight?this.config.minHeight:height+my);
	this.config.height=height;
	width = width+mx>maxwidth?maxwidth:(width+mx<this.config.minWidth?this.config.minWidth:width+mx);
	this.config.width=width;
	container.css('height',this.config.height+"px");
	container.css('width',this.config.width+"px");
}

//拖拽窗口位置
BaseLayer.fn.resetPosition=function(mx,my){
	if(!this.containerDom){
		return;
	}
	var container = this.containerDom;
	var viewWidth=this.shadeDom.width();
	var viewHeight=this.shadeDom.height();
	if(this.config.state==Constants.dialog.states[2]){
		container.css('left',0);
		container.css('top',0);
	}else{
		var left = container.position().left+mx;
		var top = container.position().top+my;
		var maxtop =  viewHeight-container.height()-1;
		var maxleft = viewWidth-container.width()-1;
		top=top<1?1:(top>maxtop?maxtop:top);
		left=left<1?1:(left>maxleft?maxleft:left);
		this.config.top=top;
		this.config.left=left;
		container.css('left',left+"px");
		container.css('top',top+"px");
	}
}

//关闭
BaseLayer.fn.close=function(){
	this.moveDom.remove();
	this.containerDom.remove();
	this.shadeDom.remove();
}
//最小
BaseLayer.fn.min=function(){
	var min = this.containerDom.find('.base-layer-min');
	var max = this.containerDom.find('.base-layer-max');
	max.show();
	switch (this.config.state){
		case Constants.dialog.states[1]:
			this.config.state=Constants.dialog.states[0];
			min.hide();
			break;
		case Constants.dialog.states[2]:
			this.config.state=Constants.dialog.states[1];
			min.show();
			break;
		default:
			break;
	}
	this.setContentContainerSizeAndPosition();
}
//最大
BaseLayer.fn.max=function(){
	var min = this.containerDom.find('.base-layer-min');
	var max = this.containerDom.find('.base-layer-max');
	min.show();
	switch (this.config.state){
		case Constants.dialog.states[0]:
			this.config.state=Constants.dialog.states[1];
			max.show();
			break;
		case Constants.dialog.states[1]:
			this.config.state=Constants.dialog.states[2];
			max.hide();
			break;
		default:
			break;
	}
	this.setContentContainerSizeAndPosition();
}

module.exports=BaseLayer;