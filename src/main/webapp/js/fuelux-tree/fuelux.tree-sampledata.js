var DataSourceTree = function(options) {
	this._data 	= options.data;
	this._delay = options.delay;
}

DataSourceTree.prototype.data = function(options, callback) {
	var self = this;
	var $data = null;

	if(!("name" in options) && !("type" in options)){
		$data = this._data;//the root tree
		callback({ data: $data });
		return;
	}
	else if("type" in options && options.type == "folder") {
		if("additionalParameters" in options && "children" in options.additionalParameters)
			$data = options.additionalParameters.children;
		else $data = {}//no data
	}

	if($data != null)//this setTimeout is only for mimicking some random delay
		setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);

	//we have used static data here
	//but you can retrieve your data dynamically from a server using ajax call
	//checkout examples/treeview.html and examples/treeview.js for more info
};

		var tree_data = {
			'foodmart' : {name: 'first[0].name', type: 'folder'},
			'seating' : {name: '销售', type: 'folder'},
			'flowers' : {name: '设计', type: 'folder'}
		}
		tree_data['foodmart']['additionalParameters'] = {
			'children' : {
				'appetizer' : {name: '前端', type: 'item'},
				'main-course' : {name: '后端', type: 'item'},
				'dessert' : {name: '设计', type: 'item'}

			}
		}

		tree_data['seating']['additionalParameters'] = {
			'children' : {
				'two-seaters' : {name: '产品销售', type: 'item'},
				'four-seaters' : {name: '技术销售', type: 'item'},
				'eight-seaters' : {name: '文化销售', type: 'item'}
			}
		}
		tree_data['flowers']['additionalParameters'] = {
			'children' : {
				'roses' : {name: '平面设计', type: 'item'},
				'lilies' : {name: 'UI 设计', type: 'item'},
				'orchids' : {name: '产品设计', type: 'item'}
			}
		}
		var treeDataSource = new DataSourceTree({data: tree_data});



var tree_data_2 = {
	'pictures' : {name: '设计', type: 'folder', 'icon-class':'red'}	,
	'music' : {name: '研发', type: 'folder', 'icon-class':'orange'}	,
	'videos' : {name: '销售', type: 'folder', 'icon-class':'blue'}	,
	'contacts' : {name: '人事', type: 'folder'}
}

tree_data_2['pictures']['additionalParameters'] = {
	'children' : {
		'camera' : {name: '设计一部', type: 'folder', 'icon-class':'pink'},
		'picasa' : {name: '研发一部', type: 'folder', 'icon-class':'pink'},
		'sample-txt' : {name: '<i class="fa fa-user"></i> 研发一组', type: 'item'},
		'sample-zip' : {name: '<i class="fa fa-user"></i> 研发二组', type: 'item'},
		'sample-html' : {name: '<i class="fa fa-user"></i> 研发三组', type: 'item'}
	}
}
tree_data_2['pictures']['additionalParameters']['children']['camera']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'}
	]
}
tree_data_2['pictures']['additionalParameters']['children']['picasa']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'}
	]
}

tree_data_2['music']['additionalParameters'] = {
	'children' : {
		'camera' : {name: '设计一部', type: 'folder', 'icon-class':'pink'},
		'picasa' : {name: '研发一部', type: 'folder', 'icon-class':'pink'},
		'sample-txt' : {name: '<i class="fa fa-user"></i> 研发一组', type: 'item'},
		'sample-zip' : {name: '<i class="fa fa-user"></i> 研发二组', type: 'item'},
		'sample-html' : {name: '<i class="fa fa-user"></i> 研发三组', type: 'item'}
	}
}
tree_data_2['music']['additionalParameters']['children']['camera']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'}
	]
}
tree_data_2['music']['additionalParameters']['children']['picasa']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'}
	]
}
tree_data_2['videos']['additionalParameters'] = {
	'children' : {
		'camera' : {name: '设计一部', type: 'folder', 'icon-class':'pink'},
		'picasa' : {name: '研发一部', type: 'folder', 'icon-class':'pink'},
		'sample-txt' : {name: '<i class="fa fa-user"></i> 研发一组', type: 'item'},
		'sample-zip' : {name: '<i class="fa fa-user"></i> 研发二组', type: 'item'},
		'sample-html' : {name: '<i class="fa fa-user"></i> 研发三组', type: 'item'}
	}
}
tree_data_2['videos']['additionalParameters']['children']['camera']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'}
	]
}
tree_data_2['videos']['additionalParameters']['children']['picasa']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'}
	]
}
tree_data_2['contacts']['additionalParameters'] = {
	'children' : {
		'camera' : {name: '设计一部', type: 'folder', 'icon-class':'pink'},
		'picasa' : {name: '研发一部', type: 'folder', 'icon-class':'pink'},
		'sample-txt' : {name: '<i class="fa fa-user"></i> 研发一组', type: 'item'},
		'sample-zip' : {name: '<i class="fa fa-user"></i> 研发二组', type: 'item'},
		'sample-html' : {name: '<i class="fa fa-user"></i> 研发三组', type: 'item'}
	}
}
tree_data_2['contacts']['additionalParameters']['children']['camera']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 设计一组', type: 'item'}
	]
}
tree_data_2['contacts']['additionalParameters']['children']['picasa']['additionalParameters'] = {
	'children' : [
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'},
		{name: '<i class="fa fa-user"></i> 研发四组', type: 'item'}
	]
}
var treeDataSource2 = new DataSourceTree({data: tree_data_2});

var tree_data_3 = {
	'weekly-reports' : {name: 'Weekly Reports', type: 'folder'},
	'employees' : {name: 'Employees', type: 'folder'},
	'departments' : {name: 'Departments', type: 'item'},
	'benefits' : {name: 'Benefits', type: 'item'}
}
tree_data_3['weekly-reports']['additionalParameters'] = {
	'children' : {
		'company-sales' : {name: 'Company Sales', type: 'item'},
		'employee-sales' : {name: 'Employee Sales', type: 'item'},
		'foodmart-sales' : {name: 'Foodmart Sales', type: 'item'},
		'product-catalog' : {name: 'Product Catalog', type: 'item'},
		'productline-sales' : {name: 'Product Line Sales', type: 'item'},
		'discounted-sales' : {name: 'Discounted sales', type: 'item'},
		'sales-detail' : {name: 'Sales Order Detail', type: 'item'}
	}
}
tree_data_3['employees']['additionalParameters'] = {
	'children' : {
		'cooks' : {name: 'Cooks', type: 'item'},
		'assistants' : {name: 'Assistants', type: 'item'},
		'waters' : {name: 'Waiters', type: 'item'}
	}
}

var treeDataSource3 = new DataSourceTree({data: tree_data_3});