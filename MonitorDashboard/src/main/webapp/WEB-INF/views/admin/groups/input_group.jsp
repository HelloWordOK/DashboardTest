<%@include file="../../common/tag.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>MonitorDashboard</title>
<%@include file="../../common/css.jsp"%>
</head>
<body>
	<%@include file="../../common/header.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="../../common/sidebar_to_admin.jsp"%>
			<div class="col-sm-10 col-sm-offset-2 main">
				<h2 class="sub-header">Input Group Data</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="serviceCode">
												<c:forEach items="${serviceList}" var="service">
													<option value="${service.serviceCode}">${service.serviceName}
														+ ${service.serviceCode }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Group Name</label>
										<div class="col-sm-7">
											<input class="form-control" id="groupName"
												required="required" type="text"
												placeholder="please input Group Name" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-offset-5 col-sm-7">
								<button class="btn btn-success" type="button" onclick="add();"
									autocomplete="off" data-loading-text="Loading..."
									id="btn_search">Add</button>
								<button class="btn btn-success" type="reset" autocomplete="off"
									data-loading-text="Reset" id="btn_reset">Reset</button>
							</div>
						</div>
					</form>
				</div>
				<div id="groupList"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="groupModal" role="dialog" aria-labelledby="groupModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	                <h4 class="modal-title" id="groupModalLabel">Update Group</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="row">
	            		<input type="hidden" id="reid">
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service</label>
								<div class="col-sm-7">
									<select class="form-control searchselect1" id="reserviceCode">
										<c:forEach items="${serviceList}" var="service">
											<option value="${service.serviceCode}">${service.serviceName}+
												${service.serviceCode }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Group Name</label>
								<div class="col-sm-7">
									<input class="form-control" id="regroupName" required="required"
										type="text" placeholder="please input Group Name" />
								</div>
							</div>
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">closed</button>
	                <button type="button" class="btn btn-primary" onclick="updateGroup()">submit</button>
	            </div>
	        </div>
	    </div>
	</div>
	<%@include file="../../common/js.jsp"%>

	<script type="text/javascript">
    $(document).ready(function() {
    	$.fn.modal.Constructor.prototype.enforceFocus =function(){};
    	$(".searchselect").select2();
    	ajaxGroupList();
    });
    
    var path = '<%=basePath%>';

		function validate(param) {
			var serviceCode = $('#serviceCode').val();
			param["serviceCode"] = serviceCode;

			var groupName = $("#groupName").val();
			if (null == groupName || "" == groupName) {
				alert("Please input the Group Name.");
				return false;
			}
			param["groupName"] = groupName;
			
			return true;
		}

		function add() {
			var param = {};
			if (validate(param) && confirm("Do you want to add?")) {
				$.ajax({
					url : path + "admin/input_group_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						alert("Successfully");
						$("#groupName").val(null);
						ajaxGroupList();
					}
				});
			}
		}
		
		function ajaxGroupList(){
			$.ajax({
				url : path + "admin/group_list",
				type : "GET",
				success : function(data) {
					$("#groupList").html(data);
				}
			});
		}
		
		function updateGroup(){
			var param = {};
			if (validateReGroup(param) && confirm("Do you want to update?")) {
				$.ajax({
					url : path + "admin/update_group_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						alert("Successfully");
						$('#groupModal').modal('hide');
						$(".modal-backdrop").remove();
						ajaxGroupList();
					}
				});
			}
		}
		
		function validateReGroup(param) {
			var id = $("#reid").val();
			param["id"] = id;
			var serviceCode = $('#reserviceCode').val();
			param["serviceCode"] = serviceCode;

			var groupName = $("#regroupName").val();
			if (null == groupName || "" == groupName) {
				alert("Please input the Group Name.");
				return false;
			}
			param["groupName"] = groupName;
			
			return true;
		}
		
		$("#groupModal").on("shown.bs.modal", function(){
            $(".searchselect1").select2();
 		}).on("hidden.bs.modal", function() {
 			$("#reid").val(null);
	    	$("#reserviceCode").val(null);
	    	$("#regroupName").val(null);
	    });
	</script>
</body>

</html>