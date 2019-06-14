const path = require("path");
const copyWebpackPlugin=require('copy-webpack-plugin');

module.exports={
	entry:"./src/index.js",
	output:{
		path:path.resolve(__dirname,"dist"),
		filename:"base-layer.js",
		library:'baseLayer'
	},
	plugins:[
		new copyWebpackPlugin([
			{
				from:__dirname+'/theme',
				to:'./theme'
			}
		])
	],
	externals:{
		jquery: 'jQuery'
	}
}