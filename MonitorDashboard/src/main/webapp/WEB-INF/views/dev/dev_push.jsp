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
				<h2 class="sub-header">Push Code</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
					<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">

								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Team</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="team">
												<option value="TMP_1">ACOS Team</option>
												<option value="TMP_2">JIRA Team</option>
												<option value="TMP_3">Devops Team</option>
												<option value="TMP_4">Q&A Team</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Project</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="project">
												<option value="pj1">Project Demo 1</option>
												<option value="pj2">Project Demo 2</option>
												<option value="pj3">Project Demo 3</option>
												<option value="pj4">Project Demo 4</option>
											</select>
										</div>
									</div>
								</div>
								
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Function</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="function">
												<option value="ft1">Performance Test</option>
												<option value="ft2">Function Test</option>
												<option value="ft3">Create Project</option>
												<option value="ft4">Edit Issue</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Version</label>
										<div class="col-sm-7">
											 <input type="text" class="form-control" placeholder="Please input the version/s" aria-describedby="sizing-addon2" id="version">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Code</label>
										<div class="col-sm-7">
											<input type="text" class="form-control" aria-describedby="sizing-addon2" id="codeURL" readonly value="usr\upload\PerformanceTestCase.rpm">
										</div>
										<div class="col-sm-1">
											<button class="btn btn-info btn-sm" type="button" onclick="" autocomplete="off" data-loading-text="Loading..." id="btn_import">Import</button>
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-offset-5 col-sm-7">
								<button class="btn btn-success" type="button" onclick="" autocomplete="off" data-loading-text="Loading..." id="btn_search">Submit</button>
<!-- 								<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button> -->
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