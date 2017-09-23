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
			<%@include file="../common/sidebar_to_mail.jsp"%>
			<div class="col-sm-10 col-sm-offset-2 main">
				<h2 class="sub-header">Save Template</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
					<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">

								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service</label>
										<div class="col-sm-9">
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
										<label class="col-sm-3 control-label">Template Name</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" placeholder="Please input the template name" aria-describedby="sizing-addon2" id="mail_temp">
										</div>
									</div>
								</div>
								
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">To</label>
										<div class="col-sm-9">
											 <input type="text" class="form-control" placeholder="Use comma(,) to split multiple address" aria-describedby="sizing-addon2" id="mail_to">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Cc</label>
										<div class="col-sm-9">
											 <input type="text" class="form-control" placeholder="Use comma(,) to split multiple address" aria-describedby="sizing-addon2" id="mail_cc">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Subject</label>
										<div class="col-sm-9">
											 <input type="text" class="form-control" placeholder="Input mail subject here..." aria-describedby="sizing-addon2" id="mail_subject">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Content</label>
										<div class="col-sm-9">
<!-- 											 <input type="text" class="form-control" placeholder="Input mail content here..." aria-describedby="sizing-addon2" id="mail_content"> -->
											<textarea class="form-control" placeholder="Input mail content here..." id="mail_content" rows=8></textarea>
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-offset-5 col-sm-7">
								<button class="btn btn-success" type="button" onclick="save();" autocomplete="off" data-loading-text="Loading..." id="btn_search">Save</button>
								<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button>
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

	function save() {
		var param = {};

		var serviceId = $("#serviceId").val();
		var temp = $("#mail_temp").val();
		var to = $("#mail_to").val();
		var cc = $("#mail_cc").val();
		var subject = $("#mail_subject").val();
		var content = $("#mail_content").val();

		if (validate(temp, to, subject, content)) {
			param = {
				"serviceId" : serviceId,
				"temp" : temp,
				"to" : to,
				"cc" : cc,
				"subject" : subject,
				"content" : content
			};
			showloading();
			$.ajax({
				url : path + "mail/saveTemp",
				type : "POST",
				data : param,
				success : function(data) {
					if ("1" == data) {
						alert("Mail Template has been saved !");
						hideloading();
						window.location = '/MonitorDashboard/mail/template';
					} else if("0" == data) {
						alert("Mail Format Validate Failed!");
						hideloading();
						return false;
					} else {
						alert("Oops, System Error Occurred...");
						hideloading();
						return false;
					}
				}
			});
		}
	}

	function validate(temp, to, subject, content) {
		if (null == temp || "" == temp.trim()) {
			alert("Please input the template name !");
			return false;
		}

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