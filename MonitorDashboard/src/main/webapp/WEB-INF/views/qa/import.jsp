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
        <%@include file="../common/sidebar_to_qa.jsp" %>
        <div class="col-sm-10 col-sm-offset-2 main">
          <h2 class="sub-header">Import</h2>
          <div class="form">
   			<form class="form-horizontal" action="<%=basePath%>qa/import" method="post" enctype="multipart/form-data">
				<div class="col-sm-1"></div>
				<div class="col-sm-8">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Import File</label>
								<div class="col-sm-7">
									<input class="form-control" style="height: auto;" required="required" type="file" name="file" id="import_file" placeholder="please choose file"/>
								</div>
							</div>
						</div>
						
						<div class="col-sm-12">
							<div class="form-group">
								<label class="col-sm-3 control-label">Import Type</label>
								<div class="col-sm-7">
									<label class="radio-inline">
									  <input type="radio" name="import_type" id="incident" value="incident" checked="checked">Incident
									</label>
									<label class="radio-inline">
									  <input type="radio" name="import_type" id="problem" value="problem">Problem
									</label>
									<label class="radio-inline">
									  <input type="radio" name="import_type" id="sr" value="sr">SR
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-offset-5 col-sm-7" id="x">
						<button class="btn btn-success" type="submit"  autocomplete="off" data-loading-text="Importing..." id="btn_search" >Import</button>
					</div>
				</div>
			</form>
          </div>
        </div>
      </div>
    </div>
    <script type="text/javascript">
    	$("#btn_search").click(function(){
			var type= $('input:radio[name="import_type"]:checked').val();
    		if(type==null){
    			alert("Please Choose Import Type");
    			return false;
    		}
    		var import_type=document.getElementsByName("import_type");
   			var data;
   		    for(var i=0;i<import_type.length;i++){
   		         if(import_type[i].checked){
   		        	 data = import_type[i].nextSibling.nodeValue.trim();
   		       }
   		    }
   		    if(confirm("Do you want to import " + data + "?")){
   		    	showloading();
   		    }else{
   		    	return false;
   		    }
    	});
    </script>
  </body>
</html>