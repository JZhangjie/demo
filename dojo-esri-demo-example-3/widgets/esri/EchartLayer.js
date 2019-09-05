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
    'dijit/_WidgetBase',
    'dijit/_TemplatedMixin'
  ],
  function(
		  declare,lang,url, html,domConstruct,query,domClass,topic,on,dom,cache,Map,
		  _WidgetBase, _TemplatedMixin) {
		var TileBorderLayerView2D = BaseLayerView2D.createSubclass({
			   // Example of a render implementation that draws tile boundaries
			   render(renderParameters) {
				 var tileSize = this.layer.tileInfo.size[0];
				 var state = renderParameters.state;
				 var pixelRatio = state.pixelRatio;
				 var width = state.size[0];
				 var height = state.size[1];
				 var context = renderParameters.context;
				 var coords = [0, 0];

				 context.fillStyle = "rgba(0,0,0,0.25)";
				 context.fillRect(0, 0, width * pixelRatio, height * pixelRatio);
				 }
			 });

		 var CustomTileLayer = Layer.createSubclass({
		   tileInfo: TileInfo.create({ spatialReference: { wkid: 3857 }}),

		   createLayerView(view) {
			 if (view.type === "2d") {
			   return new TileBorderLayerView2D({
				 view: view,
				 layer: this
			   });
			 }
		   }
		 });
		 
		 return CustomTileLayer;
});