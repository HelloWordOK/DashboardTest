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
</head>
 <body>
    <%@include file="../common/header.jsp" %>
    <div class="container-fluid">
      <div class="row">
        <%@include file="../common/sidebar_to_er.jsp" %>
        <div class="col-sm-10 col-sm-offset-2 main">
          <h2 class="sub-header">Export Search</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post" id="forminfo">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Bundle</label>
								<div class="col-sm-7">
									<select class="form-control searchselect" id="bundleId" onchange="ajaxSprintList();">
										<c:forEach items="${bundleList}" var="bundle">
											<option value="${bundle.id}">${bundle.bundleName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div id="sprintList"></div>
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
    
    <%@include file="../common/js.jsp" %>
    
    <script type="text/javascript">
    $(document).ready(function() {
    	  $(".searchselect").select2();
    	  ajaxSprintList();
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
    
    function validate(bundleId,sprint_start,sprint_end){
    	if(null==bundleId||""==bundleId.trim()){
    		alert("Please choose Bundle.");
    		return false;
    	}

    	if(null==sprint_start||""==sprint_start){
    		alert("Please choose Start Sprint.");
    		return false;
    	}
    	
    	if(null==sprint_end||""==sprint_end){
    		alert("Please choose End Sprint.");
    		return false;
    	}
    	return true;
    }
    
    function search(){
    	var bundleId = $("#bundleId").val();
    	var sprint_start = $("#sprint_start").val();
    	var sprint_end = $("#sprint_end").val();
    	if(validate(bundleId,sprint_start,sprint_end)){
    		showloading();
    		$.ajax({
           		url:path+"er/exportsubmit",
           		type:"POST",
           		data:"bundleId="+bundleId+"&sprintStart="+sprint_start+"&sprintEnd="+sprint_end,
           		success:function(data){
           			$("#echart").html(data);
           			hideloading();
           		}
           	});
    	}
    }
    </script>
  </body>

</html>