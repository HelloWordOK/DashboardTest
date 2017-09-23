<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<div class="col-sm-2"></div>
<div class="col-sm-12" id="chart1" style="height: 250px; margin-top: 20px;"></div>
<script type="text/javascript">
	var chart1 = echarts.init(document.getElementById('chart1'));
	var dataSource = ${dataSource};
	var option = {
		title : {
			show : true,
			text : dataSource.title.text,
			target : "self",

		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		dataZoom : [ {
			type: 'inside'
		} ,{
			type : 'slider',
		} ],
		legend : getLegend(dataSource.xDataMap),
		xAxis : {
			type : dataSource.xAxis.type,
			name : dataSource.xAxis.name,
			nameLocation : 'end',
			nameGap : 0,
			splitLine : false,
			data : dataSource.xAxis.data
		},
		yAxis : {
			type : dataSource.yAxis.type,
			name : dataSource.yAxis.name
		},
		toolbox : {
			show : true,
			feature : {
				/* saveAsImage : {
					title : 'Save as picture',
					type : 'png',
					pixelRatio : 1
				} */
				magicType: {
					type: ['line', 'bar'],
					title:{
						line:'Toggle Line',
						bar:'Toggle Bar'
					}
				},
				myDownload :{
					show: true,
					left:'left',
	                title: 'Data Export',
	                icon: 'path://M4.7,22.9L29.3,45.5L54.7,23.4M4.6,43.6L4.6,58L53.8,58L53.8,43.6M29.2,45.1L29.2,0',
	                onclick: function (){
	                	var param = {};
	                	var exportParam = ${exportParam};
	                	var serviceIdList = [];
	                	$('input[id="serviceId"]:checked').each(function(){
	                		serviceIdList.push($(this).val());
	                	});
	                	param["serviceIdList"] = serviceIdList;
	                	var response_time_start = $("#response_time_start").val();
	                	param["startTime"] = response_time_start;
	                	
	                	var response_time_end = $("#response_time_end").val();
	                	param["endTime"] = response_time_end;
	                	
	                	location.href = path+"active/users_data_export?param=" + JSON.stringify(exportParam);
	                }
				}
			}
		},
		series : getSeries(dataSource.xDataMap)
	};
	chart1.clear();
	chart1.setOption(option);
	
	function getSeries(xDataMap){
		var series = [];
		for(var i in xDataMap){
			var d = {
					name : i,
					type : 'line',
					data : xDataMap[i],
					label : {
						normal : {
							show : true,
							position : 'top',
						}
					}
				};
			series.push(d);
		}
		return series;
	}
	
	function getLegend(xDataMap){
		var mylegend = [];
		var md = [];
		var j=0;
		for(var i in xDataMap){
			if(md.length==6){
				var d = {
						data : md,
						x : 'center',
						top:8*j+'%'
					};
				mylegend.push(d);
				md = [];
				j++;
			}
			md.push(i);
		}
		var d = {
				data : md,
				x : 'center',
				top:8*j+'%'
			};
		mylegend.push(d);
		console.log(JSON.stringify(mylegend));
		return mylegend;
	}
</script>