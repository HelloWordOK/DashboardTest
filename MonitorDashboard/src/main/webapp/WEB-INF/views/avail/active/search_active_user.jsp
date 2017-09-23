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
          <h2 class="sub-header">Active Users Search</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post" id="forminfo">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-2 control-label" style="padding-top: 0px;">Domain</label>
								<div class="col-sm-10">
									<c:forEach items="${domainList}" var="domain">
										<c:if test="${domain.domainId !=0 }">
											<div class="col-sm-6">
												<input type="checkbox" id="domainId" name="domainId${domain.domainId}" value="${domain.domainId}"><text>${domain.domainName}</text>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-2 control-label" style="padding-top: 0px;">Bundle</label>
								<div class="col-sm-10">
									<c:forEach items="${bundleList}" var="bundle">
										<c:if test="${bundle.bundleId!=0}">
											<div class="col-sm-4" style="display: none;" id="bundleIdStyle${bundle.bundleId}">
												<input type="checkbox" id="bundleId" name="bundleId${bundle.bundleId}" value="${bundle.bundleId}"><text>${bundle.bundleName}</text>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-2 control-label" style="padding-top: 0px;">Service</label>
								<div class="col-sm-10">
									<c:forEach items="${serviceList}" var="service">
										<c:if test="${service.serviceId!=0 }">
											<div class="col-sm-4" style="display: none;" id="serviceIdStyle${service.serviceId}">
												<input type="checkbox" id="serviceId"  name="serviceId${service.serviceId}" value="${service.serviceId}"><text>${service.serviceName}</text>
											</div>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								<label for="response_time" class="col-sm-2 control-label">Time Range</label>
								<div class="col-sm-10" id="response_time">
									<input class="form-control response_time_start" id="response_time_start" name="response_time_start" type="text"  placeholder="Start Time" readonly="readonly"/>
									<p style="padding: 6px 12px;">-</p>
									<input class="form-control response_time_end" id="response_time_end" name="response_time_end" type="text"  placeholder="End Time" readonly="readonly" />
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-7">
						<button class="btn btn-success" type="button" onclick="search();" autocomplete="off" data-loading-text="Loading..." id="btn_search">Search</button>
						<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button>
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
    	  $(':checkbox').each(function() {
              this.checked = false;
          });
    });
    
    var path = '<%=basePath%>';
    
    <c:forEach items="${relationList}" var="relation" >
		$(":checkbox[name='domainId${relation.domainId}']").click(function(){
	    	var check = $(":checkbox[name='domainId${relation.domainId}']")[0].checked;
	    	if(check==true){
	    		$("#bundleIdStyle${relation.bundleId}").attr("style","display:block;");
	    		$("#serviceIdStyle${relation.serviceId}").attr("style","display:block;");
	    	}else{
		    	$(":checkbox[name='bundleId${relation.bundleId}']").each(function() {
		            this.checked = false;
	    		});
		    	$(":checkbox[name='serviceId${relation.serviceId}']").each(function() {
		            this.checked = false;
		        });
	    		$("#bundleIdStyle${relation.bundleId}").attr("style","display:none");
	    		$("#serviceIdStyle${relation.serviceId}").attr("style","display:none;");
	    	}
		});
		
		$(":checkbox[name='bundleId${relation.bundleId}']").click(function(){
	    	var check = $(":checkbox[name='bundleId${relation.bundleId}']")[0].checked;
	    	$(":checkbox[name='bundleId${relation.bundleId}']").each(function() {
	            this.checked = check;
	        	$(":checkbox[name='serviceId${relation.serviceId}']").each(function() {
		            this.checked = check;
		        });
	        });
		});
		
	</c:forEach>

    /* $(":checkbox[id='serviceId']").click(function(){
    	var c=0;
    	$(":checkbox[id='serviceId']").each(function() {
            if(this.checked){
            	c++;
            }
		});
    	if(c>6){
    		alert("You have reached the maximum choice.");
    		this.checked = false;
    	}
    }); */
    function validate(param){
    	var serviceIdList = [];
    	$('input[id="serviceId"]:checked').each(function(){
    		serviceIdList.push($(this).val());
    	});
    	if(null==serviceIdList||""==serviceIdList){
    		alert("Please choose Service.");
    		return false;
    	}
    	param["serviceIdList"] = serviceIdList;
    	var response_time_start = $("#response_time_start").val();
    	if(""==response_time_start.trim()){
    		alert("Please choose Start Time.");
    		return false;
    	}
    	param["startTime"] = response_time_start;
    	
    	var response_time_end = $("#response_time_end").val();
    	if(""==response_time_end.trim()){
    		alert("Please choose End Time.");
    		return false;
    	}
    	param["endTime"] = response_time_end;
    	
    	return true;
    }
    
    function search(){
    	var param = {};
    	if(validate(param)){
    		showloading();
    		$.ajax({
           		url:path+"active/users_chart_submit",
           		type:"POST",
           		data:{param:JSON.stringify(param)},
           		success:function(data){
           			$("#echart").html(data);
           			hideloading();
           		}
           	});
    	}
    }
    
    $(".response_time_start").datetimepicker({
        format: "yyyy-mm",
        autoclose: true,
        todayBtn: true,
        todayHighlight:true,
        startView:3,
        minView:3,
        maxView:4,
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
        format: "yyyy-mm",
        autoclose: true,
        todayBtn: true,
        todayHighlight:true,
        startView:3,
        minView:3,
        maxView:4,
        pickerPosition: "bottom-left"
    }).on("click", function (ev) {
    	$(".response_time_end").datetimepicker("setEndDate", new Date());
        $(".response_time_end").datetimepicker("setStartDate", $(".response_time_start").val());
    });
    </script>
  </body>

</html>