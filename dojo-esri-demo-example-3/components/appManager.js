define(["dojo/_base/declare", 
	"dojo/_base/lang",
	'dojo/topic'],
    function (declare,lang,topic) {
		//单例，全局配置，公共方法等
		var appManager= {
			hosts:{
				serverHost:""
			},
			
			
		}
		return appManager;
    }
)