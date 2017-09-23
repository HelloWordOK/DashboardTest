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
				<h2 class="sub-header">Input Domain Data</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Domain Name</label>
										<div class="col-sm-7">
											<input class="form-control" id="domainName"
												required="required" type="text"
												placeholder="please input Domain Name" />
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
				<div id="domainList"></div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="domainModal" role="dialog" aria-labelledby="domainModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	                <h4 class="modal-title" id="domainModalLabel">Update Domain</h4>
	            </div>
	            <div class="modal-body">
	            	<div class="row">
	            		<input type="hidden" id="reid">
						<div class="col-sm-12 form-group">
							<div class="form-group">
								<label class="col-sm-3 control-label">Domain Name</label>
								<div class="col-sm-7">
									<input class="form-control" id="redomainName" required="required"
										type="text" placeholder="please input Domain Name" />
								</div>
							</div>
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">closed</button>
	                <button type="button" class="btn btn-primary" onclick="updateDomain()">submit</button>
	            </div>
	        </div>
	    </div>
	</div>
	<%@include file="../../common/js.jsp"%>

	<script type="text/javascript">
    $(document).ready(function() {
    	$(".searchselect").select2();
    	ajaxDomainList();
    });
    
    var path = '<%=basePath%>';

		function validate(param) {
			var domainName = $("#domainName").val();
			if (null == domainName || "" == domainName) {
				alert("Please input the Domain Name.");
				return false;
			}
			param["domainName"] = $("#domainName").val();
			
			return true;
		}

		function add() {
			var param = {};
			if (validate(param) && confirm("Do you want to add?")) {
				$.ajax({
					url : path + "admin/input_domain_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						if(data=='fail'){
							alert("Please use another Domain Name.")
						}else{
							alert("Successfully");
							$("#domainName").val(null);
							ajaxDomainList();
						}
					}
				});
			}
		}
		
		function ajaxDomainList(){
			$.ajax({
				url : path + "admin/domain_list",
				type : "GET",
				success : function(data) {
					$("#domainList").html(data);
				}
			});
		}
		
		function updateDomain(){
			var param = {};
			if (validateReDomain(param) && confirm("Do you want to update?")) {
				$.ajax({
					url : path + "admin/update_domain_submit",
					type : "POST",
					data : {
						param : JSON.stringify(param)
					},
					success : function(data) {
						if(data=='fail'){
							alert("Please use another Domain Name.")
						}else{
							alert("Successfully");
							$('#domainModal').modal('hide');
							$(".modal-backdrop").remove();
							ajaxDomainList();
						}
					}
				});
			}
		}
		
		function validateReDomain(param) {
			var id = $("#reid").val();
			param["id"] = id;
			var domainName = $("#redomainName").val();
			if (null == domainName || "" == domainName) {
				alert("Please input the Domain Name.");
				return false;
			}
			param["domainName"] = domainName;
			
			return true;
		}
		
		$("#domainModal").on("shown.bs.modal", function(){
            $(".searchselect1").select2();
 		}).on("hidden.bs.modal", function() {
 			$("#reid").val(null);
	    	$("#redomainName").val(null);
	    });
	</script>
</body>

</html>