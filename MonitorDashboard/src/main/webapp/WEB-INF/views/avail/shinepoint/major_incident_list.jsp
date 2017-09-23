<%@page import="com.nokia.charts.util.JsonUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
<style type="text/css">
table tr td, table tr th {
	white-space: nowrap;
}
.modal-body label {
	padding-top: 7px;
}
.modal-body .col-sm-12 {
	margin-top: 12px;
}

</style>
</head>
<body>
	<%@include file="../../common/header.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@include file="../../common/sidebar_to_availability.jsp"%>
			<div class="col-sm-10 col-sm-offset-2 main">
				<div class="col-sm-12">
					<h3 style="text-align: center;">Major Incident List</h3>
					<table id="majorIncidentListTable"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>Services</th>
								<th>Start Date-Time</th>
								<th>End Date-Time</th>
								<th>Responsible</th>
								<th>SR Number</th>
								<th>Severity</th>
								<th>Issue Description</th>
								<th>Problem Id</th>
								<th>Root Cause</th>
								<th>Status</th>
								<th>Source From</th>
								<th>Resolution Time</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${majorIncidentList}" var="majorIncident">
								<tr>
									<td><a href="javascript:edit(${majorIncident.id});" >${majorIncident.serviceName}</a></td>
									<td>${majorIncident.startDateTime}</td>
									<td>${majorIncident.endDateTime}</td>
									<td>${majorIncident.responsible}</td>
									<td>${majorIncident.srNumber}</td>
									<td>${majorIncident.severity}</td>
									<td>${majorIncident.issueDescription}</td>
									<td>${majorIncident.problemId}</td>
									<td>${majorIncident.rootCause}</td>
									<td>${majorIncident.status}</td>
									<td>${majorIncident.sourceFrom}</td>
									<td>${majorIncident.resolutionTime}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


				<form class="form-horizontal" action="<%=basePath%>mail/majorIncidentMail" method="post" id="mailInfo" target="_blank">
					<div class="modal fade" id="major_modal" tabindex="-1" role="dialog"
						aria-labelledby="majorModalLabel">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="majorModalLabel">Major Incident List : <span id="items"></span></h4>
								</div>
								<div class="modal-body">
									<div class="container-fluid">
										<input type="hidden" id="id" name="id">
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Service</label>
											</div>
											<div class="col-sm-7">
												<select class="form-control searchselect" id="serviceId" name="serviceId" onchange="ajaxTemplate();">
													<c:forEach items="${serviceList}" var="service">
														<option value="${service.id}">${service.serviceName} + ${service.serviceCode}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Start Date-Time</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="start_date_time" name="startDateTime" readonly="readonly">
											</div>
										</div>
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">End Date-Time</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="end_date_time" name="endDateTime" readonly="readonly">
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Responsible</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="responsible" name="responsible">
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">SR Number</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="sr_number" name="srNumber">
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Severity</label>
											</div>
											<div class="col-sm-7">
												<select class="form-control searchselect" id="severity" name="severity">
													<option value="P1">P1</option>
													<option value="P2">P2</option>
													<option value="P3">P3</option>
												</select>
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Issue Description</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="issue_description" name="issueDescription">
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Problem Id</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="problem_id" name="problemId">
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Root Cause</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="root_cause" name="rootCause">
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Status</label>
											</div>
											<div class="col-sm-7">
												<select class="form-control searchselect" id="status" name="status">
													<option>Detected</option>
													<option>Work in Progress</option>
													<option>Fixed</option>
													<option>In investigation</option>
													<option>Closed</option>
												</select>
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Source From</label>
											</div>
											<div class="col-sm-7">
												<select class="form-control searchselect" id="source_from" name="sourceFrom">
													<option>System Detected</option>
													<option>User Report</option>
												</select>
											</div>
										</div>
										
										<div class="col-sm-12">
											<div class="col-sm-5">
												<label class="control-label">Resolution Time (Hours)</label>
											</div>
											<div class="col-sm-7">
												<input class="form-control" type="text" id="resolution_time" readonly="readonly" name="resolutionTime">
											</div>
										</div>
										
										<div id="template"></div>
										
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary col-sm-2" id="send_mail">Send Mail</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary" id="major_save">Save</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@include file="../../common/js.jsp"%>
	<script type="text/javascript">
	$(document).ready(function() {
	  $.fn.modal.Constructor.prototype.enforceFocus =function(){};	  
  	});
	var path = '<%=basePath%>';
	var table = $('#majorIncidentListTable').DataTable(
		{
			dom : "<'row'<'col-sm-6'B><'col-sm-6'f>>"
					+ "<'row'<'col-sm-12'tr>>"
					+ "<'row'<'col-sm-4'l><'col-sm-4'i><'col-sm-4'p>>",
			buttons : [{
				text : 'New Item',
				key : '1',
				action : function() {
					if(confirm("Do you want to add?")){
						<% request.setAttribute("item", "New Item");%>
						ajaxTemplate();
						$('#major_modal').modal();
						$("#items").html('${item}');
						$("#start_date_time").val(getCurrDate(true,true,true));
					}
				}
			},'excel'],
			/* paging:false, */
			pageLength : -1,
			lengthMenu : [ [ 15, 30, 50, -1 ], [ 15, 30, 50, "All" ] ],
			scrollY : "350px",
			scrollX : true,
			scrollCollapse : true,
			responsive : true,
			autoWidth : false,
			fixedColumns : true,
			"bSort": true,
			"aaSorting": [[ 11, "desc" ]]
		});
		
		$("#start_date_time").datetimepicker({
	        format: "yyyy-mm-dd hh:ii",
	        autoclose: true,
	        minuteStep:5,
	        todayBtn: true,
	        todayHighlight:true,
	        pickerPosition: "bottom-left"
	    }).on("click",function(ev){
	    	var end = $("#end_date_time").val();
	    	if(end!=null&&end.trim()!=""){
		        $("#start_date_time").datetimepicker("setEndDate", $("#end_date_time").val());
	    	}else{
	    		$("#start_date_time").datetimepicker("setEndDate", new Date());
	    	}
	    }).on('changeDate', function(ev){
	    	var end = $("#end_date_time").val();
	    	if(end!=null&&end.trim()!=""){
		        var start = $("#start_date_time").val();
		        var time1 = new Date(start.replace("-", "/").replace("-", "/"));
		        var time2 = new Date(end.replace("-", "/").replace("-", "/"));
		        var between_hour = ((time2.getTime()-time1.getTime())/(60*60*1000)).toFixed(2);
		        $("#resolution_time").val(between_hour);
	    	}
	    });
		
	    $("#end_date_time").datetimepicker({
	        format: "yyyy-mm-dd hh:ii",
	        autoclose: true,
	        minuteStep:5,
	        todayBtn: true,
	        todayHighlight:true,
	        pickerPosition: "bottom-left"
	    }).on("click", function (ev) {
	        $("#end_date_time").datetimepicker("setStartDate", $("#start_date_time").val());
	        $("#end_date_time").datetimepicker("setEndDate", new Date());
	    }).on('changeDate', function(ev){
	    	var start = $("#start_date_time").val();
	    	if(start!=null&&start.trim()!=""){
		        var end = $("#end_date_time").val();
		        var time1 = new Date(start.replace("-", "/").replace("-", "/"));
		        var time2 = new Date(end.replace("-", "/").replace("-", "/"));
		        var between_hour = ((time2.getTime()-time1.getTime())/(60*60*1000)).toFixed(2);
		        $("#resolution_time").val(between_hour);
	    	}
	    });
	    
	    $("#major_modal").on("shown.bs.modal", function(){
            $(".searchselect").select2();
 		}).on("hidden.bs.modal", function() {
 			$("#id").val(null);
	    	$("#end_date_time").val(null);
	    	$("#responsible").val(null);
	    	$("#sr_number").val(null);
	    	$("#issue_description").val(null);
	    	$("#problem_id").val(null);
	    	$("#root_cause").val(null);
	    	$("#resolution_time").val(null);
	    });
	    
	    $("#major_save").on("click",function(){
	    	var id = $("#id").val();
	    	var param = {
	   			serviceId:$("#serviceId").val(),
	   			startDateTime:$("#start_date_time").val(),
	   			endDateTime:$("#end_date_time").val(),
	   			responsible:$("#responsible").val(),
	   			srNumber:$("#sr_number").val(),
	   			severity:$("#severity").val(),
	   			issueDescription:$("#issue_description").val(),
	   			problemId:$("#problem_id").val(),
	   			rootCause:$("#root_cause").val(),
	   			status:$("#status").val(),
	   			sourceFrom:$("#source_from").val(),
	   			resolutionTime:$("#resolution_time").val()
	    	}
	    	$('#major_modal').modal('hide');
			$(".modal-backdrop").remove();
	    	if(id==null||id.trim()==''){
	    		$.ajax({
					  type:"post",
					  url:path+"/shinepoint/major_data_input",
					  data:{param:JSON.stringify(param)},
					  success:function(data){
						  if(data=="success"){
							  alert("Successfully.");
							  window.location.reload();
						  }else{
							  alert("Fail.");
						  }
						  
					  }
				});
	    	}else{
	    		param["id"] = $("#id").val();
	    		$.ajax({
					  type:"post",
					  url:path+"/shinepoint/major_data_update",
					  data:{param:JSON.stringify(param)},
					  success:function(data){
						  if(data=="success"){
							  alert("Successfully.");
							  window.location.reload();
						  }else{
							  alert("Fail.");
						  }
						  
					  }
				});
	    	}
	    });
	    
	    $("#send_mail").on("click",function(){
	    	$('#mailInfo').submit();
	    });
	    
	    
	    function edit(id){
	    	if(confirm("Do you want to edit?")){
	    		<% request.setAttribute("item", "Edit Item");%>
	    		ajaxTemplate();
	    		$.ajax({
					  type:"post",
					  url:path+"/shinepoint/get_major_data",
					  data:{id:id},
					  success:function(data){
						$('#major_modal').modal();
						$("#items").html('${item}');
						$("#id").val(data["id"]);
						$("#serviceId").val(data["serviceId"]).trigger("change");
			   			$("#start_date_time").val(data["startDateTime"]);
			   			$("#end_date_time").val(data["endDateTime"]);
			   			$("#responsible").val(data["responsible"]);
			   			$("#sr_number").val(data["srNumber"]);
			   			$("#severity").val(data["severity"]);
			   			$("#issue_description").val(data["issueDescription"]);
			   			$("#problem_id").val(data["problemId"]);
			   			$("#root_cause").val(data["rootCause"]);
			   			$("#status").val(data["status"]);
			   			$("#source_from").val(data["sourceFrom"]);
			   			$("#resolution_time").val(data["resolutionTime"]);
					  }
				});
	    		
			}
	    }
	    
	    function ajaxTemplate() {
	    	var serviceId = $("#serviceId").val();
	    	$.ajax({
	    		url : path + "avail/searchTemplateByServiceId",
	    		type : "POST",
	    		data : "serviceId=" + serviceId,
	    		success : function(data) {
	    			$("#template").html(data);
	    			$(".searchselect").select2();
	    		}
	    	});
	    };
	    
	    function ajaxAllTemplate() {
	    	$.ajax({
	    		url : path + "avail/searchTemplate",
	    		type : "POST",
	    		success : function(data) {
	    			$("#template").html(data);
	    			$(".searchselect").select2();
	    		}
	    	});
	    };
	    
	    
	</script>
</body>

</html>