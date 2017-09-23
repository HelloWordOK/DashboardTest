<%@include file="../../common/tag.jsp" %>
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
<%@include file="../../common/css.jsp" %>
</head>
 <body>
    <%@include file="../../common/header.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@include file="../../common/sidebar_to_qa.jsp" %>
        <div class="col-sm-10 col-sm-offset-2 main">
          <h2 class="sub-header">Data Analysis Search</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service</label>
								<div class="col-sm-7">
									<select class="form-control searchselect" id="serviceCode">
										<c:forEach items="${serviceList}" var="service">
											<option value="${service.serviceCode}">${service.serviceName} + ${service.serviceCode}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Source Type</label>
								<div class="col-sm-7">
									<select class="form-control searchselect" id="data_type">
										<option value="incident">Incident</option>
										<option value="sr">SR</option>
										<option value="problem">Problem</option>
									</select>
								</div>
							</div>
						</div>
						
						<!-- <div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Source Status</label>
								<div class="col-sm-7">
									<label class="checkbox-inline">
									  <input type="checkbox" name="data_status" value="closed">closed
									</label>
								</div>
							</div>
						</div> -->
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">SLA Breach</label>
								<div class="col-sm-7">
									<label class="checkbox-inline">
									  <input type="checkbox" name="sla_breach" value="true">True
									</label>
									<label class="checkbox-inline">
									  <input type="checkbox" name="sla_breach" value="false">False
									</label>
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Time Range</label>
								<div class="col-sm-7" id="response_time">
									<input class="form-control response_time_start" id="response_time_start" name="response_time_start" type="text"  placeholder="Start Time" />
									<p style="padding: 6px 12px;">-</p>
									<input class="form-control response_time_end" id="response_time_end" name="response_time_end" type="text"  placeholder="End Time"/>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-7">
						<button class="btn btn-success" type="button" onclick="search();"  autocomplete="off" data-loading-text="Searching..." id="btn_search" >Search</button>
					</div>
				</div>
			</form>
          </div>
        </div>
      	<div class="col-sm-10 col-sm-offset-2 main" id="echart">
        </div>
      </div>
    </div>
	<%@include file="../../common/js.jsp" %>
    <script type="text/javascript">
	    $(document).ready(function() {
	  	  	$(".searchselect").select2();
	  	  	if(getSelectDataFromCookie()){
	  	  		search();
	  	  	}
	  	});
	    
	    var path = '<%=basePath%>';
	    
	    function validate(){
	    	var serviceCode = $("#serviceCode").val();
	    	if(null==serviceCode||""==serviceCode.trim()){
	    		alert("Please choose Service.");
	    		return false;
	    	}
	    	
	    	var data_type = $("#data_type").val();
	    	if(null==data_type||""==data_type.trim()){
	    		alert("Please choose Source Type.");
	    		return false;
	    	}
	    	
	    	/* var data_status = [];
    		$('input[name="data_status"]:checked').each(function(){
    			data_status.push($(this).val());
        	});
	    	if(data_status.length==0){
	    		alert("Please Choose Select At Least One Source Status!");
	    		return false;
	    	} */
	    	
	    	var response_time_start = $("#response_time_start").val();
	    	if(null==response_time_start||""==response_time_start.trim()){
	    		alert("Please choose Start Time.");
	    		return false;
	    	}
	    	
	    	var response_time_end = $("#response_time_end").val();
	    	if(null==response_time_end||""==response_time_end.trim()){
	    		alert("Please choose End Time.");
	    		return false;
	    	}
	    	
	    	return true;
	    }
	    
	    function search(){
	    	if(validate()){
	    		showloading();
	    		var sla_breach = [];
	    		$('input[name="sla_breach"]:checked').each(function(){
	    			sla_breach.push($(this).val());
	        	});
	    		/* var data_status = [];
	    		$('input[name="data_status"]:checked').each(function(){
	    			data_status.push($(this).val());
	        	}); */
	    		var param = {
	        		serviceCode : $("#serviceCode").val(),
    		    	dataType : $("#data_type").val(),
    		    	/* dataStatus : data_status, */
    		    	startMonth : $("#response_time_start").val(),
    		    	endMonth : $("#response_time_end").val(),
    	    		slaBreach : sla_breach
	    		};
	    		$.ajax({
	           		url:path+"qa/qa_service_data_search",
	           		type:"POST",
	           		data:{
	           			param:JSON.stringify(param)
	           		},
	           		success:function(data){
	           			$("#echart").html(data);
	           			hideloading();
	           		}
	           	});
	    	}
	    }
	    
	    $(".response_time_start").datetimepicker({
	        format: "yyyy-mm-dd",
	        autoclose: true,
	        /* minuteStep:1, */
	        /* todayBtn: true, */
	        todayHighlight:true,
	        pickerPosition: "bottom-left",
	        startView:2,
	        minView:2,
	        maxView:4
	    }).on("click",function(ev){
	    	var end = $(".response_time_end").val();
	    	if(end!=null&&end.trim()!=""){
		        $(".response_time_start").datetimepicker("setEndDate", end);
	    	}else{
	        	$(".response_time_start").datetimepicker("setEndDate", new Date());
	    	}
	    });
	    $(".response_time_end").datetimepicker({
	        format: "yyyy-mm-dd",
	        autoclose: true,
	        /* minuteStep:1, */
	        /* todayBtn: true, */
	        todayHighlight:true,
	        pickerPosition: "bottom-left",
	        startView:2,
	        minView:2,
	        maxView:4
	    }).on("click", function (ev) {
	        $(".response_time_end").datetimepicker("setEndDate", new Date());
	        $(".response_time_end").datetimepicker("setStartDate", $(".response_time_start").val());
	    });
	    
	    function getSelectDataFromCookie(){
		    var serviceCode = getCookie("serviceCode");
		    if(serviceCode!=null){
		    	$("#serviceCode").val(serviceCode).trigger("change");
		    	delCookie("serviceCode");
		    }else{
		    	return false;
		    }
		    var dataType = getCookie("dataType");
			if(dataType!=null){
			    $("#data_type").val(dataType).trigger("change");
			    delCookie("dataType");
		    }else{
		    	return false;
		    }
		    var startMonth = getCookie("startMonth");
			if(startMonth!=null){
			    $("#response_time_start").val(startMonth);
			    delCookie("startMonth");
		    }else{
		    	return false;
		    }
		    var endMonth = getCookie("endMonth");
			if(endMonth!=null){
			    $("#response_time_end").val(endMonth);
			    delCookie("endMonth");
		    }else{
		    	return false;
		    }
		    var slaBreach = getCookie("slaBreach");
			if(slaBreach!=null){
				var sla_breach = slaBreach.split(",");
			    $(":checkbox[name='sla_breach']").each(function(){
			    	for(var i=0;i<sla_breach.length;i++){
			    		if($(this).val()==sla_breach[i]){
			    			this.checked=true;
			    			break;
			    		}
			    	}
			    });
			    delCookie("slaBreach");
		    }else{
		    	return false;
		    }
			return true;
	    }
    </script>
  </body>
</html>