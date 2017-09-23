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
        <%@include file="../common/sidebar_to_qa.jsp" %>
        <div class="col-sm-10 col-sm-offset-2 main">
          <h2 class="sub-header">Import Success</h2>
          	<button class="btn btn-success" type="button" id="btn_back" onclick="javascript:history.go(-1);" >Return To Import</button>
        </div>
      </div>
    </div>
    
    <%@include file="../common/js.jsp" %>
  </body>

</html>