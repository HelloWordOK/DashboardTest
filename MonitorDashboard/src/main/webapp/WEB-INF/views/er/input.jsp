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
			<%@include file="../common/sidebar_to_er.jsp"%>
			<div class="col-sm-10 col-sm-offset-2 main">
				<h2 class="sub-header">Data Submit</h2>
				<div class="form">
					<form class="form-horizontal" action="" method="post" id="forminfo">
					<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Bundle</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="bundleId">
												<c:forEach items="${bundleList}" var="bundle">
													<option value="${bundle.id}">${bundle.bundleName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Release Sprint</label>
										<div class="col-sm-7">
											 <input type="text" class="form-control" placeholder="S1612-13" aria-describedby="sizing-addon2" id="release_sprint">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Plan Velocity</label>
										<div class="col-sm-7">
											 <input type="text" class="form-control" aria-describedby="sizing-addon2" id="plan_velocity">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Actual Velocity</label>
										<div class="col-sm-7">
											 <input type="text" class="form-control" aria-describedby="sizing-addon2" id="actual_velocity">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Plan Capacity</label>
										<div class="col-sm-7">
											 <input type="text" class="form-control" aria-describedby="sizing-addon2" id="plan_capacity">
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Release Note</label>
										<div class="col-sm-7">
											<textarea rows="3" cols="20" class="form-control" id="release_note" wrap="hard" maxlength="300"></textarea>
										</div>
									</div>
								</div>
								<div class="col-sm-12">
									<div class="form-group">
										<label for="response_time" class="col-sm-3 control-label">Time Range</label>
										<div class="col-sm-9" id="response_time">
											<input class="form-control response_time_start" id="response_time_start" name="response_time_start" type="text" placeholder="Start Time" />
											<p style="padding: 6px 12px;">-</p>
											<input class="form-control response_time_end" id="response_time_end" name="response_time_end" type="text" placeholder="End Time" />
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-offset-5 col-sm-7">
								<button class="btn btn-success" type="button" onclick="save();" autocomplete="off" data-loading-text="Loading..." id="btn_submit">Submit</button>
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
    $(document).ready(function() {
    	$(".searchselect").select2();
    });

    var path = '<%=basePath%>';

		function validate(param) {
			var reg = /^(([0-9]+[\.]?[0-9]+)|[0-9])$/;
			
			param["bundleId"] =$("#bundleId").val();
			
			var bundleName = $("#bundleId").find("option:selected").text();
			param["bundleName"] =bundleName;
			
			var bundleId = $("#bundleId").val();
			if (null == bundleId || "" == bundleId.trim()) {
				alert("Please select a Bundle.");
				return false;
			}
			
			var release_sprint = $("#release_sprint").val();
			if (null == release_sprint || "" == release_sprint.trim()) {
				alert("Please input the Release Sprint.");
				return false;
			}
			param["releaseSprint"] = release_sprint;

			var plan_velocity = $("#plan_velocity").val();
			if (null == plan_velocity || "" == plan_velocity) {
				alert("Please input the Plan Velocity.");
				return false;
			} else if (!reg.exec(plan_velocity)){
				alert("Please input a positive number.");
				return false;
			}
			param["planVelocity"] = plan_velocity;

			var actual_velocity = $("#actual_velocity").val();
			if (null == actual_velocity || "" == actual_velocity) {
				alert("Please input the Actual Velocity.");
				return false;
			} else if (!reg.exec(actual_velocity)){
				alert("Please input a positive number.");
				return false;
			}
			param["actualVelocity"] = actual_velocity;

			var plan_capacity = $("#plan_capacity").val();
			if (null == plan_capacity || "" == plan_capacity) {
				alert("Please input the Plan Capacity.");
				return false;
			} else if (!reg.exec(plan_capacity)){
				alert("Please input a positive number.");
				return false;
			}
			param["planCapacity"] = plan_capacity;

			var release_note = $("#release_note").val();
			if (null == release_note || "" == release_note) {
				alert("Please input Release Note.");
				return false;
			}
			param["releaseNote"] = release_note;
			
			var start_date = $("#response_time_start").val();
			if (null == start_date || "" == start_date) {
				alert("Please input the Start Date.");
				return false;
			}
			param["startDate"] = start_date;

			var end_date = $("#response_time_end").val();
			if (null == end_date || "" == end_date) {
				alert("Please input the End Date.");
				return false;
			}
			param["endDate"] = end_date;
			return true;
		};

		function save() {
			
			var param = {};
			if (validate(param)) {
				showloading();
				if (!confirm("Are you sure to submit these data?")){
					return;
				}
				$.ajax({
					url : path + "er/inputsubmit",
					type : "POST",
					data : {param:JSON.stringify(param)},
		 			dataType:"json",
					success : function(data) {
						if (data =="1"){
							alert("The data has been inserted.");
							$("#forminfo").val(null);
							$("input").val('');
							$("textarea").val(''); 
							//$('#forminfo')[0].reset();
						} else if (data == "2"){
							alert("The data has been updated.");
							
							//$('#forminfo')[0].reset();
							$("#forminfo").val(null);
							$("input").val('');
							$("textarea").val(''); 
						
						} else {
							alert("Sorry, a system error occurred.");
							
							//$('#forminfo')[0].reset();
							$("#forminfo").val(null);
							$("input").val('');
							$("textarea").val(''); 
							
						}
						hideloading();
					}
				});
			}
		};

		$(".response_time_start").datetimepicker({
			format : "yyyy-mm-dd",
			autoclose : true,
			todayBtn : true,
			todayHighlight : true,
			startView : 2,
			minView : 2,
			maxView : 4,
			pickerPosition : "bottom-left"
		}).on("click",function(ev) {
			$(".response_time_start").datetimepicker("setEndDate", $(".response_time_end").val());
		});
		$(".response_time_end").datetimepicker({
			format : "yyyy-mm-dd",
			autoclose : true,
			todayBtn : true,
			todayHighlight : true,
			startView : 2,
			minView : 2,
			maxView : 4,
			pickerPosition : "bottom-left"
		}).on("click",function(ev) {
			$(".response_time_end").datetimepicker("setStartDate", $(".response_time_start").val());
		});
	</script>
</body>

</html>