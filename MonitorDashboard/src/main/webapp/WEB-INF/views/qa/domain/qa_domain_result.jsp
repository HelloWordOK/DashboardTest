<%@page import="com.nokia.charts.util.JsonUtils"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.nokia.charts.util.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../../common/tag.jsp" %>
<h2 class="sub-header">Search Results</h2>
<div class="col-sm-12" id="chart1" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart2" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart3" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart4" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart5" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart6" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-6" id="chart7" style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" style="margin: 20px auto"></div>
<script type="text/javascript">
	var chart1 = echarts.init(document.getElementById('chart1'));
	var chart2 = echarts.init(document.getElementById('chart2'));
	var chart3 = echarts.init(document.getElementById('chart3'));
	var chart4 = echarts.init(document.getElementById('chart4'));
	var chart5 = echarts.init(document.getElementById('chart5'));
	var chart6 = echarts.init(document.getElementById('chart6'));
	var chart7 = echarts.init(document.getElementById('chart7'));
	var dataSource = ${dataSource};
	var path = '<%=basePath%>';
	// Get Service code and period type
	var param = $("#domain").val();
	var period_type= $('input:radio[name="period_type"]:checked').val();
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
				data : dataSource.chart1.xAxis.data
			},
			yAxis : [{
						type : dataSource.chart1.yAxis.type,
						splitLine :{
							show:true
						}},
					{
						type : dataSource.chart1.yAxis.type,
						splitLine :{
							show:false
						},
						axisLabel : {
							formatter : '{value} %'
						}
					}],
			toolbox : {
				show : true,
				feature : {
					/* saveAsImage : {
						title : 'Save as picture',
						type : 'png',
						pixelRatio : 1
					}, */
					myDownload :{
						show: true,
						left:'left',
		                title: 'Data Export',
		                icon: 'path://M4.7,22.9L29.3,45.5L54.7,23.4M4.6,43.6L4.6,58L53.8,58L53.8,43.6M29.2,45.1L29.2,0',
		                onclick: function (){
		                	location.href = path+"qa/qa_data_export?param=" + param + "&periodType=" + period_type + "&chartType=incident&isDomain=true";
		                }
					}
				}
			},
			series : [ {
				name : dataSource.chart1.legend.data[0],
				type : 'bar',
				data : dataSource.chart1.created,
				itemStyle : {
					normal : {
						color : '#9bbb5c'
					}
				}
				/* label : {
					normal : {
						show : true,
						position : 'top',
					}
				} */
			}, {
				name : dataSource.chart1.legend.data[1],
				type : 'bar',
				data : dataSource.chart1.closed,
				itemStyle : {
					normal : {
						color : '#1ea185'
					}
				}
				/* label : {
					normal : {
						show : true,
						position : 'top',
					}
				} */
			}, {
				name : dataSource.chart1.legend.data[2],
				type : 'line',
				data : dataSource.chart1.backlog,
				symbol : 'emptyTriangle',
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#bd392f'
					}
				}
			},
			{
				name : dataSource.chart1.legend.data[3],
				type : 'line',
				yAxisIndex: 1,
				data : dataSource.chart1.sla,
				label : {
					normal : {
						show : true,
						position : 'top',
						formatter: '{c}%'
					}
				},
				itemStyle : {
					normal : {
						color : '#f29b26'
					}
				}
			}]
		};
		chart1.clear();
		chart1.setOption(option1);
	}
	
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
				data : dataSource.chart2.xAxis.data,
				axisLabel  :{
					interval :'auto'
				}
			},
			yAxis : {
				type : dataSource.chart2.yAxis.type,
				splitLine :{
					show:true
				},
				axisLabel : {
					formatter : '{value} %'
				}
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
			series : [ {
				name : dataSource.chart2.legend.data[0],
				type : 'bar',
				data : dataSource.chart2.slap1,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#bd392f'
					}
				}
			}, {
				name : dataSource.chart2.legend.data[1],
				type : 'bar',
				data : dataSource.chart2.slap2,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#f29b26'
					}
				}
			}, {
				name : dataSource.chart2.legend.data[2],
				type : 'bar',
				data : dataSource.chart2.slap3,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#1ea185'
					}
				}
			},
			{
				name : dataSource.chart2.legend.data[3],
				type : 'bar',
				data : dataSource.chart2.slap4,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				itemStyle : {
					normal : {
						color : '#9bbb5c'
					}
				}
			}]
		};
		chart2.clear();
		chart2.setOption(option2);
	}
	
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
				data : dataSource.chart3.xAxis.data
			},
			yAxis : [{
						type : dataSource.chart3.yAxis.type,
						splitLine :{
							show:true
						}},
					{
						type : dataSource.chart3.yAxis.type,
						splitLine :{
							show:false
						},
						axisLabel : {
							formatter : '{value} %'
						}
					}],
			toolbox : {
				show : true,
				feature : {
					/* saveAsImage : {
						title : 'Save as picture',
						type : 'png',
						pixelRatio : 1
					}, */
					myDownload :{
						show: true,
						left:'left',
		                title: 'Data Export',
		                icon: 'path://M4.7,22.9L29.3,45.5L54.7,23.4M4.6,43.6L4.6,58L53.8,58L53.8,43.6M29.2,45.1L29.2,0',
		                onclick: function (){
		                	location.href = path+"qa/qa_data_export?param="+param+"&periodType="+period_type + "&chartType=SR&isDomain=true";
		                }
					}
				}
			},
			series : [ {
				name : dataSource.chart3.legend.data[0],
				type : 'bar',
				data : dataSource.chart3.created,
				itemStyle : {
					normal : {
						color : '#9bbb5c'
					}
				}
				/* label : {
					normal : {
						show : true,
						position : 'top',
					}
				} */
			}, {
				name : dataSource.chart3.legend.data[1],
				type : 'bar',
				data : dataSource.chart3.closed,
				itemStyle : {
					normal : {
						color : '#1ea185'
					}
				}
				/* label : {
					normal : {
						show : true,
						position : 'top',
					}
				} */
			}, {
				name : dataSource.chart3.legend.data[2],
				type : 'line',
				data : dataSource.chart3.backlog,
				symbol : 'emptyTriangle',
				itemStyle : {
					normal : {
						color : '#bd392f'
					}
				},
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				}
			},
			{
				name : dataSource.chart3.legend.data[3],
				type : 'line',
				yAxisIndex: 1,
				data : dataSource.chart3.sla,
				itemStyle : {
					normal : {
						color : '#f29b26'
					}
				},
				label : {
					normal : {
						show : true,
						position : 'top',
						formatter: '{c}%'
					}
				}
			}]
		};
		chart3.clear();
		chart3.setOption(option3);
	}

	if(dataSource.chart4!=null){
		var option4 = {
			title : {
				show : true,
				text : dataSource.chart4.title.text,
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
				data : dataSource.chart4.legend.data
			},
			xAxis : {
				type : dataSource.chart4.xAxis.type,
				data : dataSource.chart4.xAxis.data,
				axisLabel  :{
					interval :'auto'
				}
			},
			yAxis : {
				type : dataSource.chart4.yAxis.type,
				splitLine :{
					show:true
				},
				axisLabel : {
					formatter : '{value} %'
				}
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
			series : [ {
				name : dataSource.chart4.legend.data[0],
				type : 'bar',
				data : dataSource.chart4.slap1,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				}
			}, {
				name : dataSource.chart4.legend.data[1],
				type : 'bar',
				data : dataSource.chart4.slap2,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				}
			}, {
				name : dataSource.chart4.legend.data[2],
				type : 'bar',
				data : dataSource.chart4.slap3,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				}
			},
			{
				name : dataSource.chart4.legend.data[3],
				type : 'bar',
				data : dataSource.chart4.slap4,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				}
			}]
		};
		chart4.clear();
		chart4.setOption(option4);
	}else{
		$("#chart4").attr("style","display:none");
	}
	
	if(dataSource.chart5!=null){
		var option5 = {
			title : {
				show : true,
				text : dataSource.chart5.title.text,
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
				data : dataSource.chart5.legend.data
			},
			xAxis : {
				type : dataSource.chart5.xAxis.type,
				name : dataSource.chart5.xAxis.name,
				axisLabel  :{
					interval :'auto',
					textStyle:{
						fontSize : 12
					}
				},
				data : dataSource.chart5.xAxis.data
			},
			yAxis : [{
						type : dataSource.chart5.yAxis.type,
						splitLine :{
							show:true
						}},
					{
						type : dataSource.chart5.yAxis.type,
						splitLine :{
							show:false
						},
						axisLabel : {
							formatter : '{value} %'
						}
					}],
			toolbox : {
				show : true,
				feature : {
/* 					saveAsImage : {
						title : 'Save as picture',
						type : 'png',
						pixelRatio : 1
					}, */
					myDownload :{
						show: true,
						left:'left',
		                title: 'Data Export',
		                icon: 'path://M4.7,22.9L29.3,45.5L54.7,23.4M4.6,43.6L4.6,58L53.8,58L53.8,43.6M29.2,45.1L29.2,0',
		                onclick: function (){
		                	location.href = path+"qa/qa_data_export?param="+param+"&periodType="+period_type + "&chartType=problem&isDomain=true";
		                }
					}
				}
			},
			series : [{
					name : dataSource.chart5.legend.data[0],
					type : 'bar',
					data : dataSource.chart5.created,
					itemStyle : {
						normal : {
							color : '#9bbb5c'
						}
					}
					/* label : {
						normal : {
							show : true,
							position : 'top',
						}
					} */
				}, {
					name : dataSource.chart5.legend.data[1],
					type : 'bar',
					data : dataSource.chart5.closed,
					itemStyle : {
						normal : {
							color : '#1ea185'
						}
					}
					/* label : {
						normal : {
							show : true,
							position : 'top',
						}
					} */
				}, {
					name : dataSource.chart5.legend.data[2],
					type : 'line',
					data : dataSource.chart5.backlog,
					symbol : 'emptyTriangle',
					itemStyle : {
						normal : {
							color : '#bd392f'
						}
					},
					label : {
						normal : {
							show : true,
							position : 'top',
						}
					}
				},
				{
					name : dataSource.chart5.legend.data[3],
					type : 'line',
					yAxisIndex: 1,
					data : dataSource.chart5.sla,
					itemStyle : {
						normal : {
							color : '#f29b26'
						}
					},
					label : {
						normal : {
							show : true,
							position : 'top',
							formatter: '{c}%'
						}
					}
				}]
		};
		chart5.clear();
		chart5.setOption(option5);
	}

	if(dataSource.chart6!=null){
		var option6 = {
			title : {
				show : true,
				text : dataSource.chart6.title.text,
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
				data : dataSource.chart6.legend.data
			},
			xAxis : {
				type : dataSource.chart6.xAxis.type,
				data : dataSource.chart6.xAxis.data,
				axisLabel  :{
					interval :'auto'
				}
			},
			yAxis : {
				type : dataSource.chart6.yAxis.type,
				splitLine :{
					show:true
				},
				axisLabel : {
					formatter : '{value} %'
				}
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
			series : [ {
				name : dataSource.chart6.legend.data[0],
				type : 'bar',
				data : dataSource.chart6.slap1,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#bd392f'
					}
				}
			}, {
				name : dataSource.chart6.legend.data[1],
				type : 'bar',
				data : dataSource.chart6.slap2,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#f29b26'
					}
				}
			}, {
				name : dataSource.chart6.legend.data[2],
				type : 'bar',
				data : dataSource.chart6.slap3,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				},
				itemStyle : {
					normal : {
						color : '#1ea185'
					}
				}
			},
			{
				name : dataSource.chart6.legend.data[3],
				type : 'bar',
				data : dataSource.chart6.slap4,
				label : {
					normal : {
						show : true,
						position : 'top'
					}
				},
				itemStyle : {
					normal : {
						color : '#9bbb5c'
					}
				}
			}]
		};
		chart6.clear();
		chart6.setOption(option6);
	}
	
	if(dataSource.chart7!=null){
		var option7 = {
			title : {
				text : dataSource.chart7.title.text,
				x : 'center',
				textStyle :{
					fontSize  : 15
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				
				top:'12%',
				itemWidth : 15,
				itemHeight : 10,
				data : dataSource.chart7.legend.data
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
			series : [ {
				name : dataSource.chart7.title.text,
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : [ {
					value : dataSource.chart7.resolved,
					name : dataSource.chart7.legend.data[0],
					itemStyle : {
						normal : {
							color : '#9bbb5c'
						}
					}
				}, {
					value : dataSource.chart7.pending,
					name : dataSource.chart7.legend.data[1],
					itemStyle : {
						normal : {
							color : '#1ea185'
						}
					}
				}, {
					value : dataSource.chart7.workInProgress,
					name : dataSource.chart7.legend.data[2],
					itemStyle : {
						normal : {
							color : '#bd392f'
						}
					}
				},
				{
					value : dataSource.chart7.ownerAssinged,
					name : dataSource.chart7.legend.data[3],
					itemStyle : {
						normal : {
							color : '#f29b26'
						}
					}
				}],
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					}
				}
			} ]
		};
		chart7.clear();
		chart7.setOption(option7);
	}else{
		$("#chart7").attr("style","text-align: center;");
		$("#chart7").html("<b>No Result For Ticket Status</b>");
	}
</script>