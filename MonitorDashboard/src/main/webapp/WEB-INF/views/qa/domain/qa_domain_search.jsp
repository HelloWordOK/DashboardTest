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
          <h2 class="sub-header">Search</h2>
          <div class="form">
   			<form class="form-horizontal" action="" method="post">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Domain</label>
								<div class="col-sm-7">
									<select class="form-control searchselect" id="domain">
										<c:forEach items="${domainList}" var="domain">
											<option value="${domain.id}">${domain.domainName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Period</label>
								<div class="col-sm-7">
									<label class="radio-inline">
									  <input type="radio" name="period_type" id="weekly" value="weekly">Weekly
									</label>
									<label class="radio-inline">
									  <input type="radio" name="period_type" id="monkly" value="monthly" checked="checked">Monthly
									</label>
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
	  	});
	    
	    var path = '<%=basePath%>';
	    
	    function validate(){
	    	var domain = $("#domain").val();
	    	if(null==domain||""==domain.trim()){
	    		alert("Please choose Domain.");
	    		return false;
	    	}
	    	var period_type= $('input:radio[name="period_type"]:checked').val();
    		if(period_type==null){
    			alert("Please Choose Period");
    			return false;
    		}
	    	return true;
	    }
	    
	    function search(){
	    	if(validate()){
	    		showloading();
	    		var domain = $("#domain").val();
		    	var period_type= $('input:radio[name="period_type"]:checked').val();
	    		$.ajax({
	           		url:path+"qa/qa_domain_search",
	           		type:"POST",
	           		data:"param="+domain+"&periodType="+period_type+"&isDomain=true",
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