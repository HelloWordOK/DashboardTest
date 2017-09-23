<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style type="text/css">
.modal-body label {
	padding-top: 7px;
}
.modal-body .col-sm-12 {
	margin-top: 12px;
}
</style>
<h2 class="sub-header">Search Results</h2>
<div class="col-sm-12" id="resultTable" style="margin-top: 20px;">
	<div class="col-sm-11">
		<h4 style="text-align: center;">${resultTable.per.project} ${resultTable.per.environment} ${resultTable.per.version} Performance Testing Result:</h4>
	</div>
	<div class="col-sm-1">
		<button class="btn btn-success btn-xs" type="button" onclick=""  autocomplete="off" data-loading-text="Searching..." id="exp" >Export PDF</button>
	</div>
</div>
	<h5>Baseline Results:</h5>
	<table class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>Function Type</th>
				<th>Response Time BaseLine</th>
				<th>Response Time Range</th>
				<th>Throughput BaseLine</th>
				<th>Throughput Range</th>
				<th>Error BaseLine</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach	items="${resultTable.baseLineList}" var="baseLine">
				<tr>
					<td>${baseLine.functionType}</td>
					<td><strong style="color: red;">${baseLine.rtBaseline} ms</strong></td>
					<td><strong style="color: red;">±${baseLine.rtRange} ms</strong></td>
					<td><strong style="color: red;">${baseLine.throughputBaseline} tps</strong></td>
					<td><strong style="color: red;"> ±${baseLine.throughputRange} tps</strong></td>
					<td><strong style="color: red;">≤${baseLine.errorBaseline}%</strong></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h5>Testing Results:</h5>
	<table id="performanceTable" class="table table-striped table-hover table-bordered">
		<thead>
           <tr>
           	   <th>Function Type</th>
           	   <th>Test Date</th>
               <th>Samples</th>
	           <th>Average</th>
	           <th>Median</th>
               <th>90% Line</th>
	           <th>Min</th>
	           <th>Max</th>
               <th>Error</th>
               <th>Throughput</th>
               <th>KB/sec</th>
               <th>Result</th>
	       </tr>
       </thead>

       <tbody>
       		<c:forEach items="${resultTable.performanceList}" var="performance">
       			<tr>
       				<td>${performance.functionType}</td>
       				<td>${performance.testDate}</td>
       				<td>${performance.samples}</td>
       				<td>${performance.average} ms</td>
       				<td>${performance.median} ms</td>
       				<td>${performance.ninetyPecLine} ms</td>
       				<td>${performance.min} ms</td>
       				<td>${performance.max} ms</td>
       				<td>${performance.error}%</td>
       				<td>${performance.throughput} tps</td>
       				<td>${performance.kbSec} kb/sec</td>
       				<c:if test="${performance.result eq 'Pass'}">
       					<td style="color: green;">${performance.result}</td>
       				</c:if>
       				<c:if test="${performance.result eq 'Fail'}">
       					<td style="color: red;">${performance.result}</td>
       				</c:if>
       			</tr>
       		</c:forEach>
       </tbody>
	</table>
<div class="col-sm-9 col-sm-offset-1" id="chart1" style="height: 250px; margin-top: 30px;"></div>
<div class="col-sm-9 col-sm-offset-1" id="chart2" style="height: 250px; margin-top: 20px;"></div>

<form class="form-horizontal hidden" action="./exportPerformancePDF" method="post" id="imgInfo" target="_blank">
<input class="form-control hidden" id="project1" name="project1" type="text" />
<input class="form-control hidden" id="environment1" name="environment1" type="text" />
<input class="form-control hidden" id="testVersion1" name="testVersion1" type="text" />
<input class="form-control hidden" id="summaryIMG" name="summaryIMG" type="text" />
<input class="form-control hidden" id="thoughputIMG" name="thoughputIMG" type="text" />
<input class="form-control hidden" id="reporter1" name="reporter1" type="text" />
<textarea class="form-control hidden" id="background1" name="background1" ></textarea>
<textarea class="form-control hidden" id="purpose1" name="purpose1"></textarea>
<input class="form-control hidden" id="test_server1" name="test_server1" type="text" />
<input class="form-control hidden" id="test_port1" name="test_port1" type="text" />
<input class="form-control hidden" id="test_network1" name="test_network1" type="text" />
<input class="form-control hidden" id="test_tool1" name="test_tool1" type="text" />
<input class="form-control hidden" id="test_target1" name="test_target1" type="text" />
<textarea class="form-control hidden" id="test_data1" name="test_data1" ></textarea>
<input class="form-control hidden" id="tester_start1" name="tester_start1" type="text" />
<input class="form-control hidden" id="tester_end1" name="tester_end1" type="text" />
<textarea class="form-control hidden" id="tester_conclusion1" name="tester_conclusion1" ></textarea>
<textarea class="form-control hidden" id="tester_suggestion1" name="tester_suggestion1" ></textarea>
</form>

<div class="modal fade" id="major_modal" role="dialog" >
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
				<h4 class="modal-title" id="majorModalLabel">Customer Information:</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">
				
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">Basic Information</a></li>
						<li><a href="#test" data-toggle="tab">Test Environment</a></li>
						<li><a href="#tester" data-toggle="tab">Tester Environment</a></li>
					</ul>
				
					<div id="myTabContent" class="tab-content">
						<%-- Basic information input area --%>
						<div class="tab-pane fade in active" id="home">
							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Reporter:</label>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="reporter" placeholder="Reporter Name">
								</div>
							</div>
	
							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Background:</label>
								</div>
								<div class="col-sm-9">
									<textarea class="form-control" placeholder="Input test background here..." id="background" rows=5></textarea>
								</div>
							</div>
							
							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Purpose:</label>
								</div>
								<div class="col-sm-9">
									<textarea class="form-control" placeholder="Input your test purpose here..." id="purpose" rows=5></textarea>
								</div>
							</div>
						</div>
	
						<%-- Test environment server information --%>
						<div class="tab-pane fade" id="test">
							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Test Server:</label>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="test_server">
								</div>
							</div>

							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Server port:</label>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="test_port">
								</div>
							</div>

							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Test Network:</label>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="test_network">
								</div>
							</div>

							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Test Tool:</label>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="test_tool">
								</div>
							</div>

							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Target Site:</label>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="test_target">
								</div>
							</div>

							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Test Data:</label>
								</div>
								<div class="col-sm-9">
									<textarea class="form-control" placeholder="Input your test data here..." id="test_data" rows=5></textarea>
								</div>
							</div>
						</div>
	
						<%-- Tester machine information --%>
						<div class="tab-pane fade" id="tester">
							<div class="col-sm-12">
								<label for="response_time" class="col-sm-3 control-label">Time Range:</label>
								<div class="col-sm-9" id="response_time">
									<input class="form-control response_time_start" id="response_time_start" name="response_time_start" type="text"  placeholder="Start Time" style="width: 160px"/>
									<p style="padding: 6px 12px;">-</p>
									<input class="form-control response_time_end" id="response_time_end" name="response_time_end" type="text"  placeholder="End Time" style="width: 160px"/>
								</div>
							</div>

							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Conclusion:</label>
								</div>
								<div class="col-sm-9">
									<textarea class="form-control" placeholder="Input your conclusion here..." id="tester_conclusion" rows=5></textarea>
								</div>
							</div>
							
							<div class="col-sm-12">
								<div class="col-sm-3">
									<label class="control-label">Suggestion:</label>
								</div>
								<div class="col-sm-9">
									<textarea class="form-control" placeholder="Input your suggestion here..." id="tester_suggestion" rows=5></textarea>
								</div>
							</div>
						</div>

					</div>
				</div>
		</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_close">Close</button>
				<button type="button" class="btn btn-primary" id="major_save">Export</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">

	var chart1 = echarts.init(document.getElementById('chart1'));
	var chart2 = echarts.init(document.getElementById('chart2'));
	var dataSource = ${dataSource};
	if(dataSource.chart1!=null){
		var option1 = {
			title : {
				show : true,
				text : dataSource.chart1.title.text,
				textAlign : 'left',
				left : 'center',
				textStyle :{
					fontSize  : 15
				}
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			legend : {
				top:'8%',
				itemWidth : 15,
				itemHeight : 10,
				data : dataSource.chart1.legend.data
			},
			xAxis : {
				type : dataSource.chart1.xAxis.type,
				name : dataSource.chart1.xAxis.name,
				axisLabel  :{
					interval :'0',
					textStyle:{
						fontSize : 12
					}
				},
				nameLocation : 'end',
				nameGap : 1,
				data : dataSource.chart1.xAxis.data
			},
			yAxis : {
				name : dataSource.chart1.yAxis.name,
				max:maxValue(dataSource.chart1),
				type : dataSource.chart1.yAxis.type,
			},
			toolbox : {
				show : false,
				feature : {
					saveAsImage : {
						title : 'Save as picture',
						type : 'png',
						pixelRatio : 1
					}
				}
			},
			series :[{
				name : dataSource.chart1.legend.data[0],
				type : 'bar',
				data : dataSource.chart1.average,
				itemStyle :{
					normal :{
						color :'#9bbb5c'
					}
				}
			},{
				name : dataSource.chart1.legend.data[1],
				type : 'bar',
				data : dataSource.chart1.median,
				itemStyle :{
					normal :{
						color :'#1ea185'
					}
				}
			},{
				name : dataSource.chart1.legend.data[2],
				type : 'bar',
				data : dataSource.chart1.ninetyline,
				itemStyle :{
					normal :{
						color :'#bd392f'
					}
				}
			},{
				name : dataSource.chart1.legend.data[3],
				type : 'bar',
				data : dataSource.chart1.min,
				itemStyle :{
					normal :{
						color :'#f29b26'
					}
				}
			},{
				name : dataSource.chart1.legend.data[4],
				type : 'bar',
				data : dataSource.chart1.max,
				itemStyle :{
					normal :{
						color :'#1E90FF'
					}
				}
			}]
		}
		chart1.clear();
		chart1.setOption(option1);
		$("#project1").val("${resultTable.per.project}");
		$("#environment1").val("${resultTable.per.environment}");
		$("#testVersion1").val("${resultTable.per.version}");
		$("#summaryIMG").val(chart1.getDataURL('png'));
	};

	if(dataSource.chart2!=null){
		var option2 = {
			title : {
				show : true,
				text : dataSource.chart2.title.text,
				textAlign : 'left',
				left : 'center',
				textStyle :{
					fontSize  : 15
				}
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				}
			},
			legend : {
				top:'8%',
				itemWidth : 15,
				itemHeight : 10,
				data : dataSource.chart2.legend.data
			},
			xAxis : {
				type : dataSource.chart2.xAxis.type,
				name : dataSource.chart2.xAxis.name,
				axisLabel  :{
					interval :'0',
					textStyle:{
						fontSize : 12
					}
				},
				nameLocation : 'end',
				nameGap : 2,
				data : dataSource.chart2.xAxis.data
			},
			yAxis : {
				max:maxValue(dataSource.chart2),
				name : dataSource.chart2.yAxis.name,
				type : dataSource.chart2.yAxis.type,
			},
			toolbox : {
				show : false,
				feature : {
					saveAsImage : {
						title : 'Save as picture',
						type : 'png',
						pixelRatio : 1
					}
				}
			},
			series :[{
				name : dataSource.chart2.legend.data[0],
				type : 'bar',
				data : dataSource.chart2.throughputData,
				itemStyle :{
					normal :{
						color :'#1ea185'
					}
				}
			}]
		}
		chart2.clear();
		chart2.setOption(option2);
		$("#thoughputIMG").val(chart2.getDataURL('png'));
	};

	$("#exp").on("click", function(){
		$('#major_modal').modal();
	});

	$("#btn_close").on("click", function(){
		$('#major_modal input').val("");
		$('#major_modal textarea').val("");
	});

	$(".response_time_start").datetimepicker({
		format : "yyyy-mm-dd hh:ii",
		autoclose : true,
		minuteStep : 5,
		todayBtn : true,
		todayHighlight : true,
		pickerPosition : "bottom-left"
	}).on("click",function(ev) {
		var end = $(".response_time_end").val();
		if (end != null && end.trim() != "") {
			$(".response_time_start").datetimepicker("setEndDate", $(".response_time_end").val());
		} else {
			$(".response_time_start").datetimepicker("setEndDate", new Date());
		}
	});
	$(".response_time_end").datetimepicker({
		format : "yyyy-mm-dd hh:ii",
		autoclose : true,
		minuteStep : 5,
		todayBtn : true,
		todayHighlight : true,
		pickerPosition : "bottom-left"
	}).on("click",function(ev) {
		$(".response_time_end").datetimepicker("setStartDate", $(".response_time_start").val());
		$(".response_time_end").datetimepicker("setEndDate", new Date());
	});

	$("#major_save").on("click", function(){
		var project = $("#project").val();
		var environment = $('input:radio[name="environment_type"]:checked').val();
		var version = $("#functionType").val();
		var reporter = $("#reporter").val();
		var background = $("#background").val();
		var purpose = $("#purpose").val();

		var test_server = $("#test_server").val();
		var test_port = $("#test_port").val();
		var test_network = $("#test_network").val();
		var test_tool = $("#test_tool").val();
		var test_target = $("#test_target").val();
		var test_data = $("#test_data").val();

		var response_time_start = $("#response_time_start").val();
		var response_time_end = $("#response_time_end").val();
		var tester_conclusion = $("#tester_conclusion").val();
		var tester_suggestion = $("#tester_suggestion").val();

		$("#reporter1").val(reporter);
		$("#background1").val(background);
		$("#purpose1").val(purpose);
		$("#test_server1").val(test_server);
		$("#test_port1").val(test_port);
		$("#test_network1").val(test_network);
		$("#test_tool1").val(test_tool);
		$("#test_target1").val(test_target);
		$("#test_data1").val(test_data);
		$("#tester_start1").val(response_time_start);
		$("#tester_end1").val(response_time_end);
		$("#tester_conclusion1").val(tester_conclusion);
		$("#tester_suggestion1").val(tester_suggestion);
		$("#imgInfo").submit();
	});
</script>