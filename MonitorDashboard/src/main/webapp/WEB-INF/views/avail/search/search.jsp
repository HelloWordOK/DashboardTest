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
        <%@include file="../../common/sidebar_to_availability.jsp" %>
        <div class="col-sm-10 col-sm-offset-2 main">
          <h2 class="sub-header">Server Avail. Search</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post" id="forminfo">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Service</label>
								<div class="col-sm-7">
									<select class="form-control searchselect" id="serviceId" onchange="ajaxServer();">
										<c:forEach items="${serviceList}" var="service">
											<option value="${service.serviceId}">${service.serviceName} + ${service.serviceCode }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div id="serviceserver"></div>
						<div id="servermonitor"></div>
						<div class="col-sm-12">
							<div class="form-group">
								<label for="response_time" class="col-sm-3 control-label">Time Range</label>
								<div class="col-sm-9" id="response_time">
									<input class="form-control response_time_start" id="response_time_start" name="response_time_start" type="text"  placeholder="Start Time" />
									<p style="padding: 6px 12px;">-</p>
									<input class="form-control response_time_end" id="response_time_end" name="response_time_end" type="text"  placeholder="End Time" />
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-9">
						<button class="btn btn-success" type="button" onclick="search();" autocomplete="off" data-loading-text="Loading..." id="btn_search">Search</button>
						<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button>
					</div>
				</div>
			</form>
          </div>
        </div>
        <div class="col-sm-10 col-sm-offset-2 main" id="resultData">
        </div>
      </div>
    </div>
    
    <%@include file="../../common/js.jsp" %>
    
    <script type="text/javascript">
    $(document).ready(function() {
    	  $(".searchselect").select2();
    	  ajaxServer();
    });
    
    $("button[type='reset']").on("click", function(event){
        event.preventDefault();
        var myForm = $(this).closest('form').get(0);
        myForm.reset();
        $("select", myForm).each(
            function () {
                $(this).select2('val','');
            }
        );
    });
    
    var path = '<%=basePath%>';
    
    function validate(param){
    	var serverId = $("#server").val();
    	param["serverId"] = serverId;
    	if(null==serverId||""==serverId.trim()){
    		alert("Please choose Server.");
    		return false;
    	}
    	var monitor = $("#monitor").val();
    	if(null==monitor||""==monitor.trim()){
    		alert("Please choose Monitor.");
    		return false;
    	}
    	param["monitorId"] = monitor;
    	var response_time_start = $("#response_time_start").val();
    	if(""==response_time_start.trim()){
    		alert("Please choose Start Time.");
    		return false;
    	}
    	param["responseTimeStart"] = response_time_start;
    	
    	var response_time_end = $("#response_time_end").val();
    	if(""==response_time_end.trim()){
    		alert("Please choose End Time.");
    		return false;
    	}
    	param["responseTimeEnd"] = response_time_end;
    	
    	return true;
    }
    
    function search(){
    	var param = {};
    	if(validate(param)){
    		showloading();
    		$.ajax({
           		url:path+"search/searchsubmit",
           		type:"POST",
           		data:"param="+JSON.stringify(param),
           		success:function(data){
           			$("#resultData").html(data);
           			hideloading();
           		}
           	});
    	}
    }
    
    $(".response_time_start").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        minuteStep:5,
        todayBtn: true,
        todayHighlight:true,
        pickerPosition: "bottom-left"
    }).on("click",function(ev){
    	var end = $(".response_time_end").val();
    	if(end!=null&&end.trim()!=""){
	        $(".response_time_start").datetimepicker("setEndDate", $(".response_time_end").val());
    	}else{
    		$(".response_time_start").datetimepicker("setEndDate", new Date());
    	}
    });
    $(".response_time_end").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        minuteStep:5,
        todayBtn: true,
        todayHighlight:true,
        pickerPosition: "bottom-left"
    }).on("click", function (ev) {
        $(".response_time_end").datetimepicker("setStartDate", $(".response_time_start").val());
        $(".response_time_end").datetimepicker("setEndDate", new Date());
    });
    
    </script>
  </body>

</html>