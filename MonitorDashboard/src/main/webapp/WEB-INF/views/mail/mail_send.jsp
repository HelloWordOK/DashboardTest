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
				<h2 class="sub-header">Send Mail</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
					<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">

								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service</label>
										<div class="col-sm-9">
											<select class="form-control searchselect" id="serviceId" onchange="ajaxTempList();">
												<c:forEach items="${serviceList}" var="service">
													<option value="${service.serviceId}">${service.serviceName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div id="tempList"></div>
								<div id="autoList"></div>
							</div>

							<div class="col-sm-offset-5 col-sm-7">
								<button class="btn btn-success" type="button" onclick="sendMail();" autocomplete="off" data-loading-text="Loading..." id="btn_search">Send</button>
								<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button>
								<button class="btn btn-success" type="button" autocomplete="off" data-loading-text="Loading..." id="btn_preview">Preview</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="tempId"/>
	
	<div class="modal fade" id="mail_preview" role="dialog" ></div>
	<%@include file="../common/js.jsp"%>
	<script type="text/javascript">

	var path = '<%=basePath%>';
	var mtype = ${mtype};
	$(document).ready(function() {
		if(mtype == "1"){
			$("#serviceId").val("${serviceId}");
			$("#tempId").val("${templateId}");			
		}
		
		$(".searchselect").select2();
		ajaxTempList();
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
						alert("Mail Send Successful!");
						hideloading();
						window.location = '/MonitorDashboard/mail';
					} else if("-1" == data) {
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

	$("#btn_preview").on("click", function(){
		var param = {};

		var subject = $("#mail_subject").val();
		var content = $("#mail_content").val();

		param = {
			"subject" : subject,
			"content" : content,
		};
		showloading();
		$.ajax({
			url : path + "mail/mailPreview",
			type : "POST",
			data : param,
			success : function(data) {
				$("#mail_preview").html(data);
				$('#mail_preview').modal();
				hideloading();
			}
		});
	});

	</script>

</body>

</html>