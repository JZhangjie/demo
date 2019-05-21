define([
    'dojo/_base/declare',
    'dojo/_base/lang',
    'dojo/_base/url',
    'dojo/_base/html',
    'dojo/dom-construct',
    'dojo/query',
    'dojo/dom-class',
    'dojo/topic',
    'dojo/on',
    'dojo/dom',
    "dojo/cache",
    "libs/vue.min",
    'dijit/_WidgetBase',
    'dijit/_TemplatedMixin'
  ],
  function(
		  declare,lang,url, html,domConstruct,query,domClass,topic,on,dom,cache,
		  Vue,
		  _WidgetBase, _TemplatedMixin) {
	
    var clazz = declare([_WidgetBase, _TemplatedMixin], {
    	vm:null,
		config:{},
    	baseClass:"demowidget_baseclass",
		templateString: cache(new url("./components/Demo/Widget.html"), {
		    sanitize: true
		}),
		
		//构造函数，传入预定义的数据，回调方法等
		constructor:function(config){
			if(config){
				for(let k in Object.keys(config)){
					this.config[k]=config[k];
				}
			}
		},
		//dom对象构建完成，可以使用domNode了。这里调用了initVM()构建Vue对象
		postCreate: function() {
			this.inherited(arguments);
			this.initVM();
		},
		
		startup: function() {
			this.inherited(arguments);
		},
		
		//构建Vue对象，完成后，将domNode对象替换成vm.$el。否则在使用placeAt添加后，两个不是同一个对象
		initVM: function(){
			if(!this.vm){
				this.vm= new Vue({
					el:this.domNode,
					data:{
						items:[]
					},
					methods:{
						click:lang.hitch(this, this.onC),
					}
				});
			}
			this.domNode = this.vm.$el;
		},
		
		//事件监听函数
		onC:function(event){
			console.log(event);
		},
		
		//对外提供的函数
  
  });
  return clazz;
});