 if (navigator.userAgent.toLowerCase().indexOf('dingtalk') > -1) {
    document.writeln('<script src="https://appx/web-view.min.js"' + '>' + '<' + '/' + 'script>');

    dd.onMessage = function(e) {
      console.log(e); //{'sendToWebView': '1'}
      
    }
  }
 
function jumpToDetail(postion){
	if(position && postion.lng && postion.lat){		
		dd.reLaunch({url: '/pages/index/index?lng='+postion.lng+"&lat="+postion.lat});
	}
	else{
		dd.reLaunch({url: '/pages/index/index'});
	}
	
}

