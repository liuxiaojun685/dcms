 

$(function(){
 
 
	
	var sortableChange = function(e, ui){
		//拖拽子项目
		if(ui.sender){
			var w = ui.element.width();
			ui.placeholder.width(w);
			ui.helper.css("width",ui.element.children().width());
		}
	};
	
	var sortableUpdate = function(e, ui){
		//更新模块（用户回收站清空后）
		if(ui.element[0].id == "trashcan"){
			emptyTrashCan(ui.item);
		}
	};
	
	$(function(){
		//引用主页面中的所有块
		var els = ['.disp'];
		var $els = $(els.toString());
		
	 
	 
		//使用jQuery插件
		$els.sortable({
			items: '> #dc',	//拖拽对象
			handle: '#dart',	//可触发该事件的对象
			cursor: 'move',	//鼠标样式
			opacity: 0.5,	//拖拽时透明
			appendTo: 'body',
			connectWith: els,
			start: function(e,ui) {
				ui.helper.css("width", ui.item.width());
			},
			change: sortableChange,
			update: sortableUpdate		//用于回收站
		});
	});
});