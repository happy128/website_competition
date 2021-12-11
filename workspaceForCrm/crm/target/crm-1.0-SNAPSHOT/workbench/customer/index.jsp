<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>


	<script type="text/javascript">

		$(function(){

			//为创建按钮绑定事件，打开添加操作的模态窗口
			$("#addBtn").click(function () {

				$(".time").datetimepicker({
					minView: "month",
					language:  'zh-CN',
					format: 'yyyy-mm-dd',
					autoclose: true,
					todayBtn: true,
					pickerPosition: "bottom-left"
				});

				$("#createActivityModal").modal("show");

			})


			//为保存按钮绑定事件，执行添加操作
			$("#saveBtn").click(function () {

				$.ajax({

					url : "workbench/customer/save.do",
					data : {
						"id" : $.trim($("#create-id").val()),
						"company_name" : $.trim($("#create-company-name").val()),
						"address" : $.trim($("#create-address").val()),
						"principal" : $.trim($("#create-principal").val()),
						"industry" : $.trim($("#create-industry").val()),
						"salesman" : $.trim($("#create-salesman").val()),
						"consumer_preferences" : $.trim($("#create-consumer-preferences").val())
					},
					type : "post",
					dataType : "json",
					success : function (data) {

						/*

                            data
                                {"success":true/false}

                         */
						if(data.success){


							pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

							$("#activityAddForm")[0].reset();

							//关闭添加操作的模态窗口
							$("#createActivityModal").modal("hide");



						}else{

							alert("添加客户信息失败");

						}




					}

				})

			})


			//页面加载完毕后触发一个方法
			//默认展开列表的第一页，每页展现两条记录
			pageList(1,2);

			//为查询按钮绑定事件，触发pageList方法
			$("#searchBtn").click(function () {


				$("#hidden-name").val($.trim($("#search-name").val()));
				$("#hidden-owner").val($.trim($("#search-owner").val()));
				$("#hidden-startDate").val($.trim($("#search-startDate").val()));
				$("#hidden-endDate").val($.trim($("#search-endDate").val()));

				pageList(1,2);

			})


			//为全选的复选框绑定事件，触发全选操作
			$("#qx").click(function () {

				$("input[name=xz]").prop("checked",this.checked);

			})

			$("#activityBody").on("click",$("input[name=xz]"),function () {

				$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);

			})

			//为删除按钮绑定事件，执行市场活动删除操作
			$("#deleteBtn").click(function () {

				//找到复选框中所有挑√的复选框的jquery对象
				var $xz = $("input[name=xz]:checked");

				if($xz.length==0){

					alert("请选择需要删除的记录");

					//肯定选了，而且有可能是1条，有可能是多条
				}else{


					if(confirm("确定删除所选中的记录吗？")){

						//url:workbench/activity/delete.do?id=xxx&id=xxx&id=xxx

						//拼接参数
						var param = "";

						//将$xz中的每一个dom对象遍历出来，取其value值，就相当于取得了需要删除的记录的id
						for(var i=0;i<$xz.length;i++){

							param += "id="+$($xz[i]).val();

							//如果不是最后一个元素，需要在后面追加一个&符
							if(i<$xz.length-1){

								param += "&";

							}

						}

						//alert(param);
						$.ajax({

							url : "workbench/customer/delete.do",
							data : param,
							type : "post",
							dataType : "json",
							success : function (data) {

								/*

                                    data
                                        {"success":true/false}

                                 */
								if(data.success){

									//删除成功后
									//回到第一页，维持每页展现的记录数
									pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


								}else{

									alert("删除客户信息管理活动失败");

								}


							}

						})


					}




				}


			})


			//为修改按钮绑定事件，打开修改操作的模态窗口
			$("#editBtn").click(function () {

				var $xz = $("input[name=xz]:checked");

				if($xz.length==0){

					alert("请选择需要修改的记录");

				}else if($xz.length>1){

					alert("只能选择一条记录进行修改");

					//肯定只选了一条
				}else{

					var id = $xz.val();

					$.ajax({

						url : "workbench/customer/getUserListAndActivity.do",
						data : {

							"id" : id

						},
						type : "get",
						dataType : "json",
						success : function (data) {

							/*

                                data
                                    用户列表
                                    市场活动对象

                                    {"uList":[{用户1},{2},{3}],"a":{市场活动}}

                             */

							//处理所有者下拉框
							var html = "<option></option>";

							$.each(data.uList,function (i,n) {

								html += "<option value='"+n.id+"'>"+n.name+"</option>";

							})

							$("#edit-owner").html(html);


							//处理单条activity
							$("#edit-id").val(data.a.id);
							$("#edit-name").val(data.a.name);
							$("#edit-owner").val(data.a.owner);
							$("#edit-startDate").val(data.a.startDate);
							$("#edit-endDate").val(data.a.endDate);
							$("#edit-cost").val(data.a.cost);
							$("#edit-description").val(data.a.description);

							//所有的值都填写好之后，打开修改操作的模态窗口
							$("#editActivityModal").modal("show");

						}

					})

				}

			})

			//为更新按钮绑定事件，执行市场活动的修改操作
			/*

                在实际项目开发中，一定是按照先做添加，再做修改的这种顺序
                所以，为了节省开发时间，修改操作一般都是copy添加操作

             */
			$("#updateBtn").click(function () {

				$.ajax({

					url : "workbench/customer/update.do",
					data : {

						"id" : $.trim($("#edit-id").val()),
						"owner" : $.trim($("#edit-owner").val()),
						"name" : $.trim($("#edit-name").val()),
						"startDate" : $.trim($("#edit-startDate").val()),
						"endDate" : $.trim($("#edit-endDate").val()),
						"cost" : $.trim($("#edit-cost").val()),
						"description" : $.trim($("#edit-description").val())

					},
					type : "post",
					dataType : "json",
					success : function (data) {

						/*

                            data
                                {"success":true/false}

                         */
						if(data.success){

							//修改成功后
							//刷新市场活动信息列表（局部刷新）
							//pageList(1,2);
							/*

                                修改操作后，应该维持在当前页，维持每页展现的记录数

                             */
							pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
									,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));


							//关闭修改操作的模态窗口
							$("#editActivityModal").modal("hide");



						}else{

							alert("修改市场活动失败");

						}




					}

				})

			})


		});

		function pageList(pageNo,pageSize) {

			//将全选的复选框的√干掉
			$("#qx").prop("checked",false);

			//查询前，将隐藏域中保存的信息取出来，重新赋予到搜索框中
			$("#search-name").val($.trim($("#hidden-name").val()));
			$("#search-owner").val($.trim($("#hidden-owner").val()));
			$("#search-startDate").val($.trim($("#hidden-startDate").val()));
			$("#search-endDate").val($.trim($("#hidden-endDate").val()));


			$.ajax({


				url : "workbench/customer/getCustomerList.do",
				data : {

					"pageNo" : pageNo,
					"pageSize" : pageSize,
					"company_name" : $.trim($("#search-company-name").val()),
					"address" : $.trim($("#search-address").val()),
					"principal" : $.trim($("#search-principal").val()),
					"industry" : $.trim($("#search-industry").val()),
					"salesman" : $.trim($("#search-salesman").val()),
					"consumer_preferences" : $.trim($("#search-consumer-preferences").val())
				},
				type : "get",
				dataType : "json",
				success : function (data) {

					var html = "";

					//每一个n就是每一个市场活动对象
					$.each(data,function (i,n) {

						html += '<tr class="active">';
						html += '<td><input type="checkbox" name="xz" value="'+n.id+'"/></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/usermanage/detail.do?id='+n.id+'\';">'+n.company_name+'</a></td>';
						html += '<td>'+n.address+'</td>';
						html += '<td>'+n.principal+'</td>';
						html += '<td>'+n.industry+'</td>';
						html += '<td>'+n.salesman+'</td>';
						html += '<td>'+n.consumer_preferences+'</td>';
						html += '</tr>';

					})

					$("#activityBody").html(html);

					//计算总页数
					var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;

					//数据处理完毕后，结合分页查询，对前端展现分页信息
					$("#activityPage").bs_pagination({
						currentPage: pageNo, // 页码
						rowsPerPage: pageSize, // 每页显示的记录条数
						maxRowsPerPage: 20, // 每页最多显示的记录条数
						totalPages: totalPages, // 总页数
						totalRows: data.total, // 总记录条数

						visiblePageLinks: 3, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						//该回调函数时在，点击分页组件的时候触发的
						onChangePage : function(event, data){
							pageList(data.currentPage , data.rowsPerPage);
						}
					});


				}

			})

		}

	</script>
</head>
<body>

<input type="hidden" id="hidden-name"/>
<input type="hidden" id="hidden-owner"/>
<input type="hidden" id="hidden-startDate"/>
<input type="hidden" id="hidden-endDate"/>


<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel2">修改角色</h4>
			</div>
			<div class="modal-body">

				<form class="form-horizontal" role="form">

					<input type="hidden" id="edit-id"/>

					<div class="form-group">
						<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<select class="form-control" id="edit-owner">



							</select>
						</div>
						<label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-name">
						</div>
					</div>

					<div class="form-group">
						<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="edit-startDate">
						</div>
						<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="edit-endDate">
						</div>
					</div>

					<div class="form-group">
						<label for="edit-cost" class="col-sm-2 control-label">成本</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-cost">
						</div>
					</div>

					<div class="form-group">
						<label for="edit-describe" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-10" style="width: 81%;">
							<!--

                                关于文本域textarea：
                                    （1）一定是要以标签对的形式来呈现,正常状态下标签对要紧紧的挨着
                                    （2）textarea虽然是以标签对的形式来呈现的，但是它也是属于表单元素范畴
                                            我们所有的对于textarea的取值和赋值操作，应该统一使用val()方法（而不是html()方法）

                            -->
							<textarea class="form-control" rows="3" id="edit-description">123</textarea>
						</div>
					</div>

				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
			</div>
		</div>
	</div>
</div>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel1">创建客户</h4>
			</div>
			<div class="modal-body">

				<form id="activityAddForm" class="form-horizontal" role="form">

					<div class="form-group">
						<label for="create-id" class="col-sm-2 control-label">ID<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-id">
						</div>
						<label for="create-company-name" class="col-sm-2 control-label">公司名称<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-company-name">
						</div>
					</div>

					<div class="form-group">
						<label for="create-address" class="col-sm-2 control-label">地址</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-address">
						</div>
						<label for="create-principal" class="col-sm-2 control-label">负责人</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-principal">
						</div>
					</div>

					<div class="form-group">
						<label for="create-industry" class="col-sm-2 control-label">行业</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-industry">
						</div>
						<label for="create-salesman" class="col-sm-2 control-label">销售员</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-salesman">
						</div>
					</div>
					<div class="form-group">
						<label for="create-consumer-preferences" class="col-sm-2 control-label">消费喜好</label>
						<div class="col-sm-10" style="width: 300px;">
							<textarea class="form-control" rows="3" id="create-consumer-preferences"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="create-remark" class="col-sm-2 control-label">备注</label>
						<div class="col-sm-10" style="width: 300px;">
							<textarea class="form-control" rows="3" id="create-remark"></textarea>
						</div>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<!--

                    data-dismiss="modal"
                        表示关闭模态窗口

                -->
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			</div>
		</div>
	</div>
</div>






<div>
	<div style="position: relative; left: 10px; top: -10px;">
		<div class="page-header">
			<h3>客户信息管理</h3>
		</div>
	</div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

		<div class="btn-toolbar" role="toolbar" style="height: 80px;">
			<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">公司名称</div>
						<input class="form-control" type="text">
					</div>
				</div>

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">地址</div>
						<input class="form-control" type="text">
					</div>
				</div>

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">负责人</div>
						<input class="form-control" type="text">
					</div>
				</div>

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">行业</div>
						<input class="form-control" type="text">
					</div>
				</div>

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">销售员</div>
						<input class="form-control" type="text">
					</div>
				</div>

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">销售喜好</div>
						<input class="form-control" type="text">
					</div>
				</div>

				<button type="submit" class="btn btn-default">查询</button>


			</form>
		</div>
		<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
			<div class="btn-group" style="position: relative; top: 18%;">

				<button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				<button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				<button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
			</div>

		</div>
		<div style="position: relative;top: 10px;">
			<table class="table table-hover">
				<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qx"/></td>
					<td>公司名称</td>
					<td>地址</td>
					<td>负责人</td>
					<td>行业</td>
					<td>销售员</td>
					<td>消费喜好</td>
				</tr>
				</thead>
				<tbody id="activityBody">

				</tbody>
			</table>
		</div>

		<div style="height: 50px; position: relative;top: 30px;">

			<div id="activityPage"></div>

		</div>

	</div>

</div>
</body>
</html>