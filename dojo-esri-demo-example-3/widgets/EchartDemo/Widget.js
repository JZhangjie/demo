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
	"esri/map",
    "libs/vue",
	"libs/Echarts3Layer",
	"widgets/esri/EchartLayer",
    'dijit/_WidgetBase',
    'dijit/_TemplatedMixin'
  ],
  function(
		  declare,lang,url, html,domConstruct,query,domClass,topic,on,dom,cache,Map,
		  Vue,Echarts3Layer,EchartLayer,
		  _WidgetBase, _TemplatedMixin) {
	
    var clazz = declare([_WidgetBase, _TemplatedMixin], {
    	vm:null,
		config:{},
    	baseClass:"echartdemowidget_baseclass",
		templateString: cache(new url("./widgets/EchartDemo/Widget.html"), {
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
			this.initMap();
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
		
		initMap:function(){
			var map = new Map( this.vm.$refs.mainMap,{
				basemap: "dark-gray",  //For full list of pre-defined basemaps, navigate to http://arcg.is/1JVo6Wd
				center:[108.94703112653409,34.25946520963723], 
				zoom: 10,
			});
			var l = new EchartLayer();
			map.add(l);
			
			map.on('load', function () {
				var overlay = new Echarts3Layer(map, echarts);
				var chartsContainer = overlay.getEchartsContainer();
				var myChart = overlay.initECharts(chartsContainer);
				var data=[[107.15,34.38,100],[113.29,33.75,10],[108.72,34.36,20],[108.95,34.27,70]]
				var option = {
					// backgroundColor: '#404a59',
					title: {
						text: 'ArcGIS扩展Echarts3之全国主要城市空气质量',
						subtext: 'Data from PM25.in,Develop By WanderGIS',
						sublink: 'https://github.com/wandergis/arcgis-echarts3',
						left: 'center',
						textStyle: {
							color: '#fff'
						}
					},
					tooltip : {
						trigger: 'item'
					},
					legend: {
						orient: 'vertical',
						y: 'bottom',
						x:'right',
						textStyle: {
							color: '#fff'
						}
					},
					geo: {
						map: '',
						label: {
							emphasis: {
								show: false
							}
						},
						roam: true,
						itemStyle: {
							normal: {
								areaColor: '#323c48',
								borderColor: '#111'
							},
							emphasis: {
								areaColor: '#2a333d'
							}
						}
					},
					series : [
						{
							name: 'Top 5',
							type: 'effectScatter',
							coordinateSystem: 'geo',
							data: data,
							symbolSize: function (val) {
								return val[2]
							},
							showEffectOn: 'render',
							rippleEffect: {
								brushType: 'stroke'
							},
							hoverAnimation: true,
							label: {
								normal: {
									formatter: '{b}',
									position: 'right',
									show: true
								}
							},
							itemStyle: {
								normal: {
									color: '#f4e925',
									shadowBlur: 10,
									shadowColor: '#333'
								}
							},
							zlevel: 1
						}
					]
				};
				overlay.setOption(option);
			});
		},
		
		//事件监听函数
		onC:function(event){
			console.log(event);
		},
		
		//对外提供的函数
  
  });
  return clazz;
});