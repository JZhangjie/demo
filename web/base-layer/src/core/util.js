//工具方法封装

module.exports={
	isDom:function(obj){
		if(typeof HTMLElement ==='object'){
			return obj instanceof HTMLElement;
		}else{
			return obj && obj==='object' && obj.nodeType===1 && typeof obj.nodeName==='string'
		}
	},
	isJQuery:function(obj){
		return obj instanceof jQuery;
	},
	isObject:function(obj){
		return typeof obj==='object';
	},
	isString:function(obj){
		return typeof obj=='string';
	}
}