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
				<h2 class="sub-header">Input Service Data</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service Name</label>
										<div class="col-sm-7">
											<input class="form-control" id="serviceName"
												required="required" type="text"
												placeholder="please input Service Name" />
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service Code</label>
										<div class="col-sm-7">
											<input class="form-control" id="serviceCode"
												required="required" type="text"
												placeholder="please input Service Code" />
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
				<div id="serviceList"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="serviceModal" role="dialog" aria-labelledby="serviceModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	                <h4 class="modal-title" id="serviceModalLabel">Update Service</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="row">
	            		<input type="hidden" id="reid">
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service Name</label>
								<div class="col-sm-7">
									<input class="form-control" id="reserviceName" required="required"
										type="text" placeholder="please input Service Name" />
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service Code</label>
								<div class="col-sm-7">
									<input class="form-control" id="reserviceCode" required="required"
										type="text" placeholder="please input Service Code" />
								</div>
							</div>
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">closed</button>
	                <button type="button" class="btn btn-primary" onclick="updateService()">submit</button>
	            </div>
	        </div>
	    </div>
	</div>
	<%@include file="../../common/js.jsp"%>

	<script type="text/javascript">
    $(document).ready(function() {
    	ajaxServiceList();
    });
    
    var path = '<%=basePath%>';

		function validate(param) {
			var serviceName = $("#serviceName").val();
			if (null == serviceName || "" == serviceName) {
				alert("Please input the Service Name.");
				return false;
			}
			param["serviceName"] = $("#serviceName").val();
			
			var serviceCode = $("#serviceCode").val();
			if (null == serviceCode || "" == serviceCode) {
				alert("Please input the Service Code.");
				return false;
			}
			param["serviceCode"] = $("#serviceCode").val();
			
			return true;
		}

		function add() {
			var param = {};
			if (validate(param) && confirm("Do you want to add?")) {
				$.ajax({
					url : path + "admin/input_service_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						if(data=='fail'){
							alert("Please use another Service Code.")
						}else{
							alert("Successfully");
							$("#serviceName").val(null);
							$("#serviceCode").val(null);
							ajaxServiceList();
						}
					}
				});
			}
		}
		
		function ajaxServiceList(){
			$.ajax({
				url : path + "admin/service_list",
				type : "GET",
				success : function(data) {
					$("#serviceList").html(data);
				}
			});
		}
		
		function updateService(){
			var param = {};
			if (validateReService(param) && confirm("Do you want to update?")) {
				$.ajax({
					url : path + "admin/update_service_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						if(data=='fail'){
							alert("Please use another Service Code.")
						}else{
							alert("Successfully");
							$('#serviceModal').modal('hide');
							$(".modal-backdrop").remove();
							ajaxServiceList();
						}
					}
				});
			}
		}
		
		function validateReService(param) {
			var id = $("#reid").val();
			param["id"] = id;

			var serviceName = $("#reserviceName").val();
			if (null == serviceName || "" == serviceName) {
				alert("Please input the Service Name.");
				return false;
			}
			param["serviceName"] = serviceName;
			
			var serviceCode = $("#reserviceCode").val();
			if (null == serviceCode || "" == serviceCode) {
				alert("Please input the Service Code.");
				return false;
			}
			param["serviceCode"] = serviceCode;
			
			return true;
		}
		
		$("#serviceModal").on("hidden.bs.modal", function() {
 			$("#reid").val(null);
	    	$("#reserviceName").val(null);
	    	$("#reserviceCode").val(null);
	    });
	</script>
</body>

</html>