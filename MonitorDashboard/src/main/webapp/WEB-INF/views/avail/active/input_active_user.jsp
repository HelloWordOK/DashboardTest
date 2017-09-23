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
          <h2 class="sub-header">Input Active Users</h2>
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
											<option value="${service.serviceId}">${service.serviceName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">User Counts</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" id="usercounts">
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label for="start_time" class="col-sm-3 control-label">Start Time</label>
								<div class="col-sm-7" id="start_time">
									<input class="form-control" id="countTime" name="countTime" type="text"  placeholder="Start Time" readonly="readonly"/>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-7">
						<button class="btn btn-success" type="button" onclick="search();" autocomplete="off" data-loading-text="Loading..." id="btn_search">Add</button>
						<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button>
					</div>
				</div>
			</form>
          </div>
        </div>
      </div>
    </div>
    
    <%@include file="../../common/js.jsp" %>
    
    <script type="text/javascript">
    $(document).ready(function() {
    	  $(".searchselect").select2();
    	  $("#countTime").val(getCurrDate(true,false,false));
    });
    
    var path = '<%=basePath%>';
    
    function validate(param){
    	param["serviceId"] = $("#serviceId").val();
    	
    	var userCounts = $("#usercounts").val();
    	var reg = /^[0-9]*$/;
    	if (null == userCounts || "" == userCounts) {
			alert("Please input the User Counts.");
			return false;
		} else if (!reg.test(userCounts)){
			alert("Please input a positive number.");
			return false;
		}
    	param["userCounts"] = userCounts;
    	
    	var countTime = $("#countTime").val();
    	if(""==countTime.trim()){
    		alert("Please choose Start Time.");
    		return false;
    	}
    	param["countTime"] = countTime;
    	
    	return true;
    }
    
    function search(){
    	var param = {};
    	if(validate(param)){
    		$.ajax({
           		url:path+"active/users_input_submit",
           		type:"POST",
           		data:{param:JSON.stringify(param)},
           		success:function(data){
           			alert("Successfully");
           			/* $('#forminfo')[0].reset(); */
           			$("#usercounts").val(null);
           		}
           	});
    	}
    }
    
    $("#countTime").datetimepicker({
        format: "yyyy-mm",
        autoclose: true,
        todayBtn: true,
        todayHighlight:true,
        startView:3,
        minView:3,
        maxView:4,
        pickerPosition: "bottom-left"
    }).on("click",function(ev){
    	$("#countTime").datetimepicker("setEndDate", new Date());
    });
	</script>
</body>

</html>