<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<h2 class="sub-header">Search Results</h2>
<div class="col-sm-12" id="resultTable" style="margin-top: 20px;">
	<h4 style="text-align: center;">${resultTable.per.project} ${resultTable.per.environment} ${resultTable.per.functionType} Performance Testing Result:</h4>
	<div class="col-sm-8">
		<table class="table" style="font-weight: bolder;">
			<tr>
				<td>Response Time BaseLine: <strong style="color: red;">${resultTable.perBaseline.rtBaseline} ms</strong></td>
				<td>Response Time Range: <strong style="color: red;">±${resultTable.perBaseline.rtRange} ms</strong></td>
			</tr>
			<tr>
				<td>Throughput BaseLine: <strong style="color: red;">${resultTable.perBaseline.throughputBaseline} tps</strong></td>
				<td>Throughput Range:<strong style="color: red;"> ±${resultTable.perBaseline.throughputRange} tps</strong></td>
			</tr>
			<tr>
				<td>Error BaseLine: <strong style="color: red;">≤${resultTable.perBaseline.errorBaseline}%</strong></td>
			</tr>
		</table>
	</div>
	<table id="performanceTable" class="table table-striped table-hover table-bordered">
		<thead>
           <tr>
           	   <th>Version</th>
           	   <th>Test Date</th>
               <th>Samples</th>
	           <th>Average</th>
               <th>90% Line</th>
               <th>Error</th>
               <th>Throughput</th>
               <th>KB/sec</th>
               <th>Result</th>
	       </tr>
       </thead>

       <tbody>
       		<c:forEach	items="${resultTable.performanceList}" var="performance">
       			<tr>
       				<td>${performance.version}</td>
       				<td>${performance.testDate}</td>
       				<td>${performance.samples}</td>
       				<td>${performance.average} ms</td>
       				<td>${performance.ninetyPecLine} ms</td>
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
</div>
<div class="col-sm-9 col-sm-offset-1" id="chart1" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-9 col-sm-offset-1" id="chart2" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-9 col-sm-offset-1" id="chart3" style="height: 250px; margin-top: 20px;"></div>
<script type="text/javascript">

	/* $("#performanceTable").DataTable({
	    dom: "<'row'<'col-sm-12'tr>>",
	    responsive: false,
	    lengthChange : true,
	    autoWidth:true,
	    sort:false,
	    scrollX:"100%",
	    lengthMenu: [[20,50,100,-1],[20,50,100,"All"]],
	    "aoColumns":[null,null,null,{"bSortable":false},{"bSortable":false},{"bSortable":false},{"bSortable":false},{"bSortable":false},{"bSortable":false}]
	}); */
	var chart1 = echarts.init(document.getElementById('chart1'));
	var chart2 = echarts.init(document.getElementById('chart2'));
	var chart3 = echarts.init(document.getElementById('chart3'));
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
				max:maxValue(dataSource.chart1),
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
				data : dataSource.chart1.averageData,
				itemStyle :{
					normal :{
						color :'#1ea185'
					}
				},
				markLine: {
					symbol:'none',
					data: markLineChart(dataSource.chart1)
				}
			}]
		}
		chart1.clear();
		chart1.setOption(option1);
	}
	
	function markLineChart(data){
	    
		var upper_Control_Threshold = null;
		if(data.legend.upperControlThreshold!=null){
			upper_Control_Threshold = {
				name:'Upper Control BaseLine',
	            yAxis: data.legend.upperControlThreshold,
	            lineStyle:{
	            	normal:{
	            		color:'#FF0000',
	            		type:'dashed',
	            	}
	            }
			}
		}
		var average_Threshold = null;
		if(data.legend.averageThreshold!=null){
			average_Threshold = {
				name:'BaseLine',
	            yAxis: data.legend.averageThreshold,
	            lineStyle:{
	            	normal:{
	            		color:'#0000FF',
	            		type:'dashed',
	            	}
	            }
			}
		}
		var lower_Control_Threshold = null;
		if(data.legend.lowerControlThreshold!=null){
			lower_Control_Threshold = {
				name:'Lower Control BaseLine',
	            yAxis: data.legend.lowerControlThreshold,
	            lineStyle:{
	            	normal:{
	            		color:'#FF0000',
	            		type:'dashed',
	            	}
	            }
			}
		}
		
		var markLineData = [];
		
		if(upper_Control_Threshold!=null){
			markLineData.push(upper_Control_Threshold);
		}
		if(average_Threshold!=null){
			markLineData.push(average_Threshold);
		}
		if(lower_Control_Threshold!=null){
			markLineData.push(lower_Control_Threshold);
		}
		return markLineData;
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
					interval :'auto',
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
				type : dataSource.chart2.yAxis.type,
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
				name : dataSource.chart2.legend.data[0],
				type : 'bar',
				data : dataSource.chart2.throughputData,
				itemStyle :{
					normal :{
						color :'#1ea185'
					}
				},
				markLine: {
					symbol:'none',
					data: markLineChart(dataSource.chart2)
				}
			}]
		}
		chart2.clear();
		chart2.setOption(option2);
	};
	
	if(dataSource.chart3!=null){
		var option3 = {
			title : {
				show : true,
				text : dataSource.chart3.title.text,
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
				data : dataSource.chart3.legend.data
			},
			xAxis : {
				type : dataSource.chart3.xAxis.type,
				name : dataSource.chart3.xAxis.name,
				axisLabel  :{
					interval :'auto',
					textStyle:{
						fontSize : 12
					}
				},
				nameLocation : 'end',
				nameGap : 2,
				data : dataSource.chart3.xAxis.data
			},
			yAxis : {
				max:maxValue(dataSource.chart3),
				type : dataSource.chart3.yAxis.type,
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
				name : dataSource.chart3.legend.data[0],
				type : 'bar',
				data : dataSource.chart3.errorData,
				itemStyle :{
					normal :{
						color :'#1ea185'
					}
				},
				markLine: {
					symbol:'none',
					data: markLineChart3(dataSource.chart3)
				}
			}]
		}
		chart3.clear();
		chart3.setOption(option3);
	};
	
	function markLineChart3(data){
		var average_Threshold = null;
		if(data.legend.averageThreshold!=null){
			average_Threshold = {
				name:'BaseLine',
	            yAxis: data.legend.averageThreshold,
	            lineStyle:{
	            	normal:{
	            		color:'#FF0000',
	            		type:'dashed',
	            	}
	            }
			}
		}
		
		var markLineData = [];
		
		if(average_Threshold!=null){
			markLineData.push(average_Threshold);
		}
		return markLineData;
	};
</script>