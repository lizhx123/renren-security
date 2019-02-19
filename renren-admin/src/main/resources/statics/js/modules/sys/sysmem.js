$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/sysmem/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '类型', name: 'type', index: 'type', width: 80 }, 			
			{ label: '身份证号', name: 'card', index: 'card', width: 80 }, 			
			{ label: '简介', name: 'summary', index: 'summary', width: 80 }, 			
			{ label: '排序', name: 'orderNum', index: 'order_num', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '删除标记  -1：已删除  0：正常', name: 'delFlag', index: 'del_flag', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		sysMem: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysMem = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysMem.id == null ? "sys/sysmem/save" : "sys/sysmem/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.sysMem),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/sysmem/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/sysmem/info/"+id, function(r){
                vm.sysMem = r.sysMem;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
        importInfo:function(){
            layer.open({
                type: 1,
                area: ['400px', '200px'],
                shadeClose: false,
                shade:0.6,
                skin: 'layui-layer-molv',
                title:"导入数据",
                content:$("#importBox"),
                btn: ['确定', '关闭'],
                btn1: function(index,layero){
                    var formData = new FormData();
                    formData.append("file",$("#importFile")[0].files[0]);
                    $.ajax({
                        url: baseURL+"sys/sysmem/import",
                        dataType: 'json',
                        type: 'POST',
                        data: formData,
                        cache: false,
                        processData: false,  // 不处理数据
                        contentType: false,
                        success: function(r){
                            console.log(r);
                            if(r.code == 0){
                                alert("导入成功！");
                                vm.reload();

                            }else{
                                alert(r.msg);
                            }
                        }
                    });
                    layer.close(index);
                },
                btn2: function(index){
                    $("#jqGrid").trigger("reloadGrid");
                    layer.close(index);
                }
			})

              //  $("#jqGrid").trigger("reloadGrid");
		}
	}
});