<%@include file="../common/tag.jsp"%>
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
<%@include file="../common/css.jsp"%>
</head>
<body>
	<%@include file="../common/header.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@include file="../common/sidebar_to_dev.jsp"%>
			<div class="col-sm-10 col-sm-offset-2 main">
				<h2 class="sub-header">Configure Job</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-12">
							<div class="row">
								<div class="col-xs-6 col-md-3">
								<img alt="" src="<%=basePath%>static/img/demo.png" class="img-rounded">
								</div>
							</div>

						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@include file="../common/js.jsp"%>
	<script type="text/javascript">

	var path = '<%=basePath%>';

	$(document).ready(function() {
		$(".searchselect").select2();
	});

	$("button[type='reset']").on("click", function(event) {
		event.preventDefault();
		var myForm = $(this).closest('form').get(0);
		myForm.reset();
		$("select", myForm).each(function() {
			$(this).select2('val', '');
		});
	});

	function sendMail() {
		var param = {};

		var to = $("#mail_to").val();
		var cc = $("#mail_cc").val();
		var subject = $("#mail_subject").val();
		var content = $("#mail_content").val();
		var timer = $("#mail_timer").val();

		if (validate(to, subject, content)) {
			alert("validate successfully !");
			param = {
				"to" : to,
				"cc" : cc,
				"subject" : subject,
				"content" : content,
				"timer" : timer
			};
			showloading();
			$.ajax({
				url : path + "mail/sendMail",
				type : "POST",
				data : param,
				success : function(data) {
					if ("0" == data) {
						alert("Successful!");
						hideloading();
						window.location = '/MonitorDashboard/mail';
					} else {
						alert("Mail Format Validate Failed!");
						hideloading();
						return false;
					}
				}
			});
		}
	}

	function validate(to, subject, content) {
		if (null == to || "" == to.trim()) {
			alert("Please input the target mail address !");
			return false;
		}
			if (null == subject || "" == subject.trim()) {
			alert("Please input the mail subject !");
			return false;
		}
		if (null == content || "" == content.trim()) {
			alert("Please input the contents !");
			return false;
		}
		return true;
	}
	</script>

</body>

</html>