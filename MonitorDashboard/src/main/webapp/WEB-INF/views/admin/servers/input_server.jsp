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
				<h2 class="sub-header">Input Server Data</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="serviceId">
												<c:forEach items="${serviceList}" var="service">
													<option value="${service.id}">${service.serviceName}
														+ ${service.serviceCode }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Server Name</label>
										<div class="col-sm-7">
											<input class="form-control" id="serverName"
												required="required" type="text"
												placeholder="please input Server Name" />
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Ip Address</label>
										<div class="col-sm-7">
											<input class="form-control" id="ipAddress"
												required="required" type="text"
												placeholder="please input Ip Address" />
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Server Details</label>
										<div class="col-sm-7">
											<input class="form-control" id="serverDetails"
												required="required" type="text"
												placeholder="please input Server Details" />
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
				<div id="serverList"></div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="serverModal" role="dialog" aria-labelledby="serverModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	                <h4 class="modal-title" id="serverModalLabel">Update Server</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="row">
	            		<input type="hidden" id="reid">
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service</label>
								<div class="col-sm-7">
									<select class="form-control searchselect1" id="reserviceId">
										<c:forEach items="${serviceList}" var="service">
											<option value="${service.id}">${service.serviceName}+
												${service.serviceCode }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Server Name</label>
								<div class="col-sm-7">
									<input class="form-control" id="reserverName" required="required"
										type="text" placeholder="please input Server Name" />
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Ip Address</label>
								<div class="col-sm-7">
									<input class="form-control" id="reipAddress" required="required"
										type="text" placeholder="please input IP Address" />
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Server Details</label>
								<div class="col-sm-7">
									<input class="form-control" id="reserverDetails" required="required"
										type="text" placeholder="please input Server Details" />
								</div>
							</div>
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">closed</button>
	                <button type="button" class="btn btn-primary" onclick="updateServer()">submit</button>
	            </div>
	        </div>
	    </div>
	</div>

	<%@include file="../../common/js.jsp"%>

	<script type="text/javascript">
    $(document).ready(function() {
    	$.fn.modal.Constructor.prototype.enforceFocus =function(){};
    	$(".searchselect").select2();
    	ajaxServerList();
    });
    
    var path = '<%=basePath%>';

		function validate(param) {
			var serviceId = $('#serviceId').val();
			param["serviceId"] = serviceId;

			var serverName = $("#serverName").val();
			if (null == serverName || "" == serverName) {
				alert("Please input the Server Name.");
				return false;
			}
			param["serverName"] = $("#serverName").val();

			var ipAddress = $("#ipAddress").val();
			if (null == ipAddress || "" == ipAddress) {
				alert("Please input the Ip Address.");
				return false;
			}
			param["ipAddress"] = $("#ipAddress").val();
			var serverDetails = $("#serverDetails").val();
			if (null == serverDetails || "" == serverDetails) {
				alert("Please input the Server Details.");
				return false;
			}
			param["serverDetails"] = $("#serverDetails").val();
			return true;
		}

		function add() {
			var param = {};
			if (validate(param) && confirm("Do you want to add?")) {
				$.ajax({
					url : path + "admin/input_server_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						alert("Successfully");
						$("#serverName").val(null);
						$("#ipAddress").val(null);
						$("#serverDetails").val(null);
						ajaxServerList();
					}
				});
			}
		}
		
		function ajaxServerList(){
			$.ajax({
				url : path + "admin/server_list",
				type : "GET",
				success : function(data) {
					$("#serverList").html(data);
				}
			});
		}
		
		function updateServer(){
			var param = {};
			if (validateReServer(param) && confirm("Do you want to update?")) {
				$.ajax({
					url : path + "admin/update_server_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						alert("Successfully");
						$('#serverModal').modal('hide');
						$(".modal-backdrop").remove();
						ajaxServerList();
					}
				});
			}
		}
		
		function validateReServer(param) {
			var id = $("#reid").val();
			param["id"] = id;
			var serviceId = $('#reserviceId').val();
			param["serviceId"] = serviceId;

			var serverName = $("#reserverName").val();
			if (null == serverName || "" == serverName) {
				alert("Please input the Server Name.");
				return false;
			}
			param["serverName"] = serverName;
			
			var ipAddress = $("#reipAddress").val();
			if (null == ipAddress || "" == ipAddress) {
				alert("Please input the IP Address.");
				return false;
			}
			param["ipAddress"] = ipAddress;
			
			var serverDetails = $("#reserverDetails").val();
			if (null == serverDetails || "" == serverDetails) {
				alert("Please input the Server Details.");
				return false;
			}
			param["serverDetails"] = serverDetails;
			
			return true;
		}
		
		$("#serverModal").on("shown.bs.modal", function(){
            $(".searchselect1").select2();
 		}).on("hidden.bs.modal", function() {
			$("#reid").val(null);
			$("#reserviceId").val(null);
   			$("#reserverName").val(null);
   			$("#reipAddress").val(null);
   			$("#reserverDetails").val(null);
	    });
	</script>
</body>

</html>