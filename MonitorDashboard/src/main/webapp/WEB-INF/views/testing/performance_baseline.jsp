<%@include file="../common/tag.jsp" %>
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
<%@include file="../common/css.jsp" %>
<%@include file="../common/js.jsp" %>
</head>
 <body>
    <%@include file="../common/header.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@include file="../common/sidebar_to_testing.jsp" %>
        <div class="col-sm-10 col-sm-offset-2 main">
          <h2 class="sub-header">Performance Test Baseline</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post" id = "forminfo">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Project</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Project Name" aria-describedby="sizing-addon2" id="project">
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Environment</label>
								<div class="col-sm-7">
									<label class="radio-inline">
									  <input type="radio" name="environment_type" value="PROD" checked="checked">PROD
									</label>
									<label class="radio-inline">
									  <input type="radio" name="environment_type" value="QA">QA
									</label>
									<label class="radio-inline">
									  <input type="radio" name="environment_type" value="INT">INT
									</label>
									<label class="radio-inline">
									  <input type="radio" name="environment_type" value="DEV">DEV
									</label>
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Function Type</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Function Type" aria-describedby="sizing-addon2" id="functionType">
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Response BaseLine</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Response Time BaseLine(ms)" aria-describedby="sizing-addon2" id="rtBaseline">
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Range</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Response Time BaseLine Range(ms)" aria-describedby="sizing-addon2" id="rtRange">
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Throughput BaseLine</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Throughput BaseLine(tps)" aria-describedby="sizing-addon2" id="tBaseline">
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Range</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Throughput BaseLine Range(tps)" aria-describedby="sizing-addon2" id="tRange">
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Error BaseLine</label>
								<div class="col-sm-7">
									<input type="text" class="form-control" placeholder="Error BaseLine(number)" aria-describedby="sizing-addon2" id="eBaseline">
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-7">
						<button class="btn btn-success" type="button" onclick="search();"  autocomplete="off" data-loading-text="Submitting..." id="btn_search" >Submit</button>
					</div>
				</div>
			</form>
          </div>
        </div>
      </div>
    </div>
    <script type="text/javascript">
	    $(document).ready(function() {
	  	  	$(".searchselect").select2();
	  	});
	    
	    var path = '<%=basePath%>';
	   
	    function search(){
        	var param = {};
        	if(validate(param)){
        		showloading();
        		$.ajax({
               		url:path+"testing/baselinesubmit",
               		type:"POST",
               		data:"param="+JSON.stringify(param),
               		success:function(data){
               			if ("1" == data){
               				alert("Adding baseline successful!");
               				$('#forminfo')[0].reset();
               			} else {
               				alert("Adding baseline failed!");
               			}
               			hideloading();
               		}
               	});
        	}
	    };
	    
	    function validate(param){
	    	var project = $("#project").val();
	    	if(null == project || "" == project.trim()){
	    		alert("Please enter the Project Name!");
	    		return false;
	    	}
	    	param["project"] = project;
	    	
	    	var environment = $('input:radio[name="environment_type"]:checked').val();
	    	if(null == environment || ""== environment.trim()){
	    		alert("Please choose environment!");
	    		return false;
	    	}
	    	param["environment"] = environment;
	    	
	    	var functionType = $("#functionType").val();
	    	if(null == functionType || "" == functionType.trim()){
	    		alert("Please enter the Function Type!");
	    		return false;
	    	}
	    	param["functionType"] = functionType;
	    	
	    	var rtBaseline = $("#rtBaseline").val();
	    	if(null == rtBaseline ||"" == rtBaseline.trim()){
	    		alert("Please enter the Response Time Baseline!");
	    		return false;
	    	}
	    	param["rtBaseline"] = rtBaseline;
	    	
	    	var rtRange = $("#rtRange").val();
	    	if(null == rtRange ||"" == rtRange.trim()){
	    		alert("Please enter the Response Time Baseline Range!");
	    		return false;
	    	}
	    	param["rtRange"] = rtRange;
	    	
	    	var tBaseline = $("#tBaseline").val();
	    	if(null == tBaseline ||"" == tBaseline.trim()){
	    		alert("Please enter the Throughput BaseLine!");
	    		return false;
	    	}
	    	param["tBaseline"] = tBaseline;
	    	
	    	var tRange = $("#tRange").val();
	    	if(null == tRange ||"" == tRange.trim()){
	    		alert("Please enter the Throughput BaseLine Range!");
	    		return false;
	    	}
	    	param["tRange"] = tRange;
	    	
	    	param["eBaseline"] = $("#eBaseline").val();
	    	return true;
	    }
    </script>
  </body>
</html>