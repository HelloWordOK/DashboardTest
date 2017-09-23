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
          <h2 class="sub-header">Functional Test Search</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Project</label>
								<div class="col-sm-7">
									<select class="form-control searchselect" id="project" onchange="ajaxVersion();">
										<c:forEach items="${projectList}" var="project">
											<option value="${project}">${project}</option>
										</c:forEach>
									</select>
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
									<!-- <label class="radio-inline">
									  <input type="radio" name="environment_type" value="FAILOVER">FAILOVER
									</label> -->
								</div>
							</div>
						</div>
						
						<div id="version"></div>
						<div id="cycle"></div>
					</div>
					<div class="col-sm-offset-5 col-sm-7">
						<button class="btn btn-success" type="button" onclick="search();"  autocomplete="off" data-loading-text="Searching..." id="btn_search" >Search</button>
					</div>
				</div>
			</form>
          </div>
        </div>
      	<div class="col-sm-10 col-sm-offset-2 main" id="resultData">
        </div>
      </div>
    </div>
    <script type="text/javascript">
	    $(document).ready(function() {
	  	  	$(".searchselect").select2();
	  	  	ajaxVersion();
	  	});
	    
	    var path = '<%=basePath%>';
	    
	    function ajaxVersion() {
	    	var project = $("#project").val();
	    	$.ajax({
	    		url : path + "testing/ajaxfunctiontestversion",
	    		type : "POST",
	    		data : "project=" + project,
	    		success : function(data) {
	    			$("#version").html(data);
	    			$(".searchselect").select2();
	    			ajaxCycle();
	    		}
	    	});
	    };
	    
	    $('input:radio[name="environment_type"]').click(function(){
	    	ajaxCycle();
	    })
	    function ajaxCycle() {
	    	var project = $("#project").val();
	    	var testVersion = $("#testVersion").val();
	    	var environment = $('input:radio[name="environment_type"]:checked').val();
	    	$.ajax({
	    		url : path + "testing/ajaxfunctiontestcycle",
	    		type : "POST",
	    		data : {
	    				project:project,
	    				testVersion:testVersion,
	    				environment:environment},
	    		success : function(data) {
	    			$("#cycle").html(data);
	    			$(".searchselect").select2();
	    		}
	    	});
	    };

	    function search(){
    		showloading();
    		var project = $("#project").val();
    		var environment = $('input:radio[name="environment_type"]:checked').val();
    		var testVersion = $("#testVersion").val();
    		var testCycle = $("#testCycle").val();
    		$.ajax({
           		url:path+"testing/functionalsubmit",
           		type:"POST",
           		data:"project="+project+"&environment="+environment+"&testVersion="+testVersion+"&testCycle="+testCycle,
           		success:function(data){
           			$("#resultData").html(data);
           			hideloading();
           		}
           	});
	    };

		function exportPDF() {
			var project = $("#project").val();
			var environment = $('input:radio[name="environment_type"]:checked').val();
			var testVersion = $("#testVersion").val();
			var testCycle = $("#testCycle").val();
			var picBase64Info = $("#imgURL").val();
			var param = {project:project,
	    				environment:environment,
	    				testVersion:testVersion,
	    				testCycle:testCycle,
	    				picBase64Info:picBase64Info
			};
			$("#imgInfo").submit();
		};
	</script>
  </body>
</html>