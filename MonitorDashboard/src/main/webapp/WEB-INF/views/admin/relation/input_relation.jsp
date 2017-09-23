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
				<h2 class="sub-header">Set Domain Bundle Service Relations</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Domain</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="domainId">
												<c:forEach items="${domainList}" var="domain">
													<option value="${domain.id}">${domain.domainName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Bundle</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="bundleId">
												<option value="">Not Select</option>
												<c:forEach items="${bundleList}" var="bundle">
													<option value="${bundle.id}">${bundle.bundleName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
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
				<div id="relationList"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="relationModal" role="dialog" aria-labelledby="relationModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	                <h4 class="modal-title" id="relationModalLabel">Update Domain Bundle Service Relations</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="row">
	            		<input type="hidden" id="reid">
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Domain</label>
								<div class="col-sm-7">
									<select class="form-control searchselect1" id="redomainId">
										<c:forEach items="${domainList}" var="domain">
											<option value="${domain.id}">${domain.domainName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Bundle</label>
								<div class="col-sm-7">
									<select class="form-control searchselect1" id="rebundleId">
										<option value="">Not Select</option>
										<c:forEach items="${bundleList}" var="bundle">
											<option value="${bundle.id}">${bundle.bundleName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service</label>
								<div class="col-sm-7">
									<select class="form-control searchselect1" id="reserviceId">
										<c:forEach items="${serviceList}" var="service">
											<option value="${service.id}">${service.serviceName}
												+ ${service.serviceCode }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">closed</button>
	                <button type="button" class="btn btn-primary" onclick="updateRelation()">submit</button>
	            </div>
	        </div>
	    </div>
	</div>
	<%@include file="../../common/js.jsp"%>

	<script type="text/javascript">
	    $(document).ready(function() {
	    	$(".searchselect").select2();
	    	ajaxRelationList();
	    });
	    
	    var path = '<%=basePath%>';

		function add() {
			var param = {};
			param["domainId"] = $("#domainId").val();
			param["bundleId"] = $("#bundleId").val();
			param["serviceId"] = $("#serviceId").val();
			if (confirm("Do you want to add?")) {
				$.ajax({
					url : path + "admin/input_relation_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						if(data=='fail'){
							alert("Please use another relations.")
						}else{
							alert("Successfully");
							ajaxRelationList();
						}
					}
				});
			}
		}
		
		function ajaxRelationList(){
			$.ajax({
				url : path + "admin/relation_list",
				type : "GET",
				success : function(data) {
					$("#relationList").html(data);
				}
			});
		}
		
		function updateRelation(){
			var param = {};
			param["id"] = $("#reid").val();
			param["domainId"] = $("#redomainId").val();
			param["bundleId"] = $("#rebundleId").val();
			param["serviceId"] = $("#reserviceId").val();
			if (confirm("Do you want to update?")) {
				$.ajax({
					url : path + "admin/update_relation_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						if(data=='fail'){
							alert("Please use another relations.")
						}else{
							alert("Successfully");
							$('#relationModal').modal('hide');
							$(".modal-backdrop").remove();
							ajaxRelationList();
						}
					}
				});
			}
		}
		
		$("#relationModal").on("shown.bs.modal", function(){
            $(".searchselect1").select2();
 		})
	</script>
</body>

</html>