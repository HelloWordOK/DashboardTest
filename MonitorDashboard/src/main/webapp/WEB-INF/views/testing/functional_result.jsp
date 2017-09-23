<%@include file="../common/tag.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<h2 class="sub-header">Search Results</h2>
<div class="col-sm-12" id="resultTable" style="margin-top: 10px;">
	<div class="col-sm-11">
		<h4 style="text-align: center;">${resultTable.fun.project} ${resultTable.fun.environment} ${resultTable.fun.version} ${resultTable.fun.cycle} Functional Testing Result:</h4>
	</div>
	<div class="col-sm-1">
		<button class="btn btn-success btn-xs" type="button" onclick="exportPDF();"  autocomplete="off" data-loading-text="Searching..." id="btn_search" >Export PDF</button>
	</div>
</div>
<div class="col-sm-12" style="margin-top: 20px;">
	<h4 style="text-align: center;">Summary Report</h4>
	<table id="SummaryReport" class="table table-striped table-hover table-bordered">
		<thead>
           <tr>
               <th>Test Date</th>
               <th>Version</th>
	           <th>Cycle</th>
               <th>TestCase</th>
               <th>Time(/s)</th>
               <c:if test="${resultTable.fun.project eq 'ACOS'}">
                   <th>Assertions</th>
               </c:if>
               <th>Pass</th>
               <th>Fail</th>
               <th>Error</th>
               <th>Skip</th>
               <th>Incomplete</th>
               <th>Risk</th>
               <th>Pass Rate(%)</th>
               <th>Final Result</th>
	       </tr>
       </thead>
       <tbody>
       		<c:forEach	items="${resultTable.summaryList}" var="summary">
       			<tr>
       				<td>${summary.testDate}</td>
       				<td>${summary.version}</td>
       				<td>${summary.cycle}</td>
       				<td>${summary.testCase}</td>
       				<td>${summary.time}</td>
       				<c:if test="${resultTable.fun.project eq 'ACOS'}">
       					<td>${summary.assertions}</td>
       				</c:if>
       				<td style="color: green;">${summary.pass}</td>
       				<td style="color: red;">${summary.fail}</td>
       				<td style="color: #FFC000;">${summary.error}</td>
       				<td>${summary.skip}</td>
       				<td>${summary.incomplete}</td>
       				<td>${summary.risk}</td>
       				<td>${summary.passRate}</td>
       				<c:if test="${summary.finalResult eq 'Fail'}">
       					<td style="color: red;">${summary.finalResult}</td>
       				</c:if>
       				<c:if test="${summary.finalResult eq 'Pass'}">
       					<td style="color: green;">${summary.finalResult}</td>
       				</c:if>
       			</tr>
       		</c:forEach>
       </tbody>
	</table>
</div>
<div class="col-sm-12" style="margin-top: 20px;">
	<h4 style="text-align: center;">Module Detail Report</h4>
	<table id="ModuleDetailReport" class="table table-striped table-hover table-bordered">
		<thead>
           <tr>
	           <th>Module</th>
               <th>TestCase</th>
               <th>Pass</th>
               <th>Fail</th>
               <th>Error</th>
               <th>Skip</th>
               <th>Incomplete</th>
               <th>Risk</th>
               <th>Pass Rate(%)</th>
               <th>Result</th>
	       </tr>
       </thead>
       <tbody>
       		<c:forEach	items="${resultTable.moduleList}" var="module">
       			<tr>
       				<td>${module.module}</td>
       				<td>${module.moduleTestCase}</td>
       				<td style="color: green;">${module.pass}</td>
       				<td style="color: red;">${module.fail}</td>
       				<td style="color: #FFC000;">${module.error}</td>
       				<td>${module.skip}</td>
       				<td>${module.incomplete}</td>
       				<td>${module.risk}</td>
       				<td>${module.modulePassRate}</td>
       				<c:if test="${module.moduleResult eq 'Fail'}">
       					<td style="color: red;">${module.moduleResult}</td>
       				</c:if>
       				<c:if test="${module.moduleResult eq 'Pass'}">
       					<td style="color: green;">${module.moduleResult}</td>
       				</c:if>
       			</tr>
       		</c:forEach>
       </tbody>
	</table>
</div>

<div class="col-sm-12" style="margin-top: 20px;">
	<h4 style="text-align: center;">Detail Information For Failed Test Case</h4>
	<table id="DetailInformationForFailedTestCase" class="table table-striped table-hover table-bordered">
		<thead>
           <tr>
	           <th>Module</th>
               <th>Function</th>
               <th>CaseName</th>
               <th>Screenshot</th>
               <th>Log</th>
               <th>Result</th>
	       </tr>
       </thead>
       <tbody>
       		<c:forEach	items="${resultTable.faillogList}" var="faillog">
       			<tr>
       				<td>${faillog.module}</td>
       				<td>${faillog.function}</td>
       				<td>${faillog.caseName}</td>
       				<c:choose>
       					<c:when test="${faillog.screenshot eq ''}">
       						<td><a href="javascript:void(0);">No ScreenShot URL</a></td>
       					</c:when>
       					<c:otherwise>
       						<td><a  target="_blank" href="${faillog.screenshot}">ScreenShot URL</a></td>
       					</c:otherwise>
       				</c:choose>
       				<td><a target="_blank" href="${faillog.log}">Log URL</a></td>
       				<td>${faillog.caseResult}</td>
       			</tr>
       		</c:forEach>
       </tbody>
	</table>
</div>
<div class="col-sm-12" id="chart1" style="height: 250px; margin-top: 20px;"></div>
<form class="form-horizontal hidden" action="./exportPDF" method="post" id="imgInfo" target="_blank">
<input class="form-control hidden" id="project1" name="project1" type="text" />
<input class="form-control hidden" id="environment1" name="environment1" type="text" />
<input class="form-control hidden" id="testVersion1" name="testVersion1" type="text" />
<input class="form-control hidden" id="testCycle1" name="testCycle1" type="text" />
<input class="form-control hidden" id="img" name="img" type="text" />
</form>
<div class="col-sm-12 hidden" id="imgURL"></div>
<script type="text/javascript">

	$("#ModuleDetailReport").DataTable({
    	dom: "<'row'<'col-sm-12'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
        buttons: ['copy','excel', 'pdf'],
        lengthMenu: [[15,30,50,-1],[15,30,50,"All"]],
        responsive: true,
        autoWidth:false
	});
	$("#DetailInformationForFailedTestCase").DataTable({
    	dom: "<'row'<'col-sm-12'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
        buttons: ['copy','excel', 'pdf'],
        lengthMenu: [[15,30,50,-1],[15,30,50,"All"]],
        responsive: true,
        autoWidth:false
	});
	var chart1 = echarts.init(document.getElementById('chart1'));
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
					interval :'auto',
					textStyle:{
						fontSize : 12
					}
				},
				nameLocation : 'end',
				nameGap : 2,
				data : dataSource.chart1.xAxis.data
			},
			yAxis : {
				type : dataSource.chart1.yAxis.type,
			},
			toolbox : {
				show : true,
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
				data : dataSource.chart1.fail,
				itemStyle :{
					normal :{
						color :'#FF0000'
					}
				}
			},
			{
				name : dataSource.chart1.legend.data[1],
				type : 'bar',
				data : dataSource.chart1.error,
				itemStyle :{
					normal :{
						color :'#FFC000'
					}
				}
			},
			{
				name : dataSource.chart1.legend.data[2],
				type : 'bar',
				data : dataSource.chart1.skip,
				itemStyle :{
					normal :{
						color :'#A6A6A6'
					}
				}
			}]
		}
		chart1.clear();
		chart1.setOption(option1);
		$("#project1").val("${resultTable.fun.project}");
		$("#environment1").val("${resultTable.fun.environment}");
		$("#testVersion1").val("${resultTable.fun.version}");
		$("#testCycle1").val("${resultTable.fun.cycle}");
		$("#img").val(chart1.getDataURL('png'));
		$("#imgURL").val(chart1.getDataURL('png'));
	}

</script>