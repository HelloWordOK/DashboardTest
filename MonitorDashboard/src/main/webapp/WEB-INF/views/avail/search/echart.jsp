<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
</head>
<h2 class="sub-header">Search Results</h2>
<div class="col-sm-12" id="search" style="height:450px;margin-top: 20px;"></div>
<div class="col-sm-12" style="margin: 20px auto"></div>
<div class="col-sm-12" id="message">
	<div class="col-sm-3 col-sm-offset-2"><p style="color: red;"><b>Top Value: </b>${msg.top}</p></div>
	<div class="col-sm-3"><p style="color: red;"><b>Average Value: </b>${msg.avg}</p></div>
	<div class="col-sm-3"><p style="color: red;"><b>Lowest Value: </b>${msg.lowest}</p></div>
</div>
<div class="col-sm-12" style="margin: 20px auto"></div>
<div class="col-sm-12" id="clientMessage">
	<h3 style="text-align: center;">${server.serverName} Error Message</h3>
	<table id="errorMessageTable" class="table table-striped table-hover table-bordered" title="ErrorMessage">
       <thead>
           <tr>
               <th>Message</th>
               <th>Response Time</th>
               <th>Status</th>
           </tr>
       </thead>

       <tbody>
       		<c:forEach items="${errorMessageList}" var="errorMessage">
	       		<tr>
	               <td>${errorMessage.message}</td>
	               <td>${errorMessage.responseTime}</td>
	               <td>${errorMessage.status}</td>
	           </tr>
       		</c:forEach>
       </tbody>
   </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	    $('#errorMessageTable').DataTable( {
	    	dom: "<'row'<'col-sm-12'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	        buttons: ['copy','excel', 'pdf'],
	        lengthMenu: [[15,30,50,-1],[15,30,50,"All"]]
	    } );
	} );
	//基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('search'));
	var data = ${data};
	// 指定图表的配置项和数据
	var option = {
		title : {
			show : true,
			text : data.title.text,
			target : "self",
			
		},
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {
	            type : 'shadow'
	        }
	    },
		legend : {
			data : data.legend.data
		},grid: [{
			show:true,
	        containLabel:true
	    }],
		xAxis : [
				{
					position : '',
					type:data.xAxis.type,
					name : data.xAxis.name,
					nameLocation : 'end',
					nameGap : 0,
					splitLine:false,
					data : data.xAxis.data,
				}],
		yAxis : {
			type:data.yAxis.type,
			name:data.yAxis.name,
			max:maxValue(data)
		},
		dataZoom : [ {
			type: 'inside'
		} ,{
			type : 'slider',
			left:'16%',
			right:'16%',
		} ],
		toolbox : {
			show : true,
			feature : {
				dataView: {
					show: true, 
					readOnly: true,
					title:'DataView',
					lang:['DataView','Close'],
				},
				magicType : {
					type : [ 'line', 'bar' ],
					title : {
						line : 'Change to line',
						bar : 'Change to bar'
					}
				},
				saveAsImage : {
					title : 'Save as picture',
					type : 'png',
					pixelRatio :1
				}
			}
		},
		series : [
				{
					name : data.legend.data[0],
					type : 'line',
					data : data.xData,
					markLine: {
						symbol:'none',
						precision:3,
						data: markLine(data)
					},
					label:{
						normal:{
							show:true,
							position:'top',
						}
					}
				}]
	};
	myChart.clear();
	myChart.setOption(option);
</script>
