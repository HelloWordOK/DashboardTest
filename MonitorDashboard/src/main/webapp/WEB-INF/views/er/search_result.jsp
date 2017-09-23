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
<h2 class="sub-header">Search Results</h2> <h4 style="text-align: center;">${statisticsERSumDto.bundleName}</h4>
<div class="col-sm-2"></div>
<div class="col-sm-8" style="margin: 20px auto">
	<table class="table table-striped table-hover table-bordered">
		<tr>
			<td>Planned Velocity:${statisticsERSumDto.sumPlanVelocity}(points)</td>
			<td>Actual Velocity:${statisticsERSumDto.sumActualVelocity}(points)</td>
		</tr>
		<tr>
			<td>Planned Capacity:${statisticsERSumDto.sumPlanCapacity}(man/day)</td>
			<td></td>
		</tr>
		<tr>
			<td>Productivity:${statisticsERSumDto.sumProductivity}%</td>
			<c:choose>
				<c:when test="${statisticsERSumDto.sumCompletionRate<60}">
					<td style="color: red;">Completion Rate:${statisticsERSumDto.sumCompletionRate}%</td>
				</c:when>
				<c:otherwise>
					<td style="color: green;">Completion Rate:${statisticsERSumDto.sumCompletionRate}%</td>
				</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<td>Start Date ~ End Date:${statisticsERSumDto.startDate} <b>~</b> ${statisticsERSumDto.endDate}</td>
		</tr>
	</table>
</div>
<div class="col-sm-12" id="chart1"
	style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart2"
	style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" id="chart3"
	style="height: 250px; margin-top: 20px;"></div>
<div class="col-sm-12" style="margin: 20px auto"></div>
<script type="text/javascript">
	var chart1 = echarts.init(document.getElementById('chart1'));
	var chart2 = echarts.init(document.getElementById('chart2'));
	var chart3 = echarts.init(document.getElementById('chart3'));
	function setStringReturn(str,len){ 
		if(str==null||str==""){ 
			return "";
	    } if(len==null){ 
	    	len=10;
	    } 
	    var result="";
	    var curlen=0;
	    var patten= /\s+/;
	    var patten_cn =  /[\u4e00-\u9fa5]/;
	    for(var i=0;i<str.length;i++){ 
	    	if(patten_cn.test(str[i])){
	        	curlen+=0.5;
	        }else if(patten.test(str[i])){ 
	    		curlen+=1.5;
	        }
	    	if(curlen>=len){ 
	        	curlen=0;
	            result+="<br/>";
	        } 
	        result+=str[i];
	    } 
	    return result;
	};
	var dataSource = ${dataSource};
	var option1 = {
		title : {
			show : true,
			text : dataSource.chartER1.title.text,
			target : "self",

		},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			},
			formatter: function(params){
				return formatToolTip(params);
			}
		},
		dataZoom : [ {
			type: 'inside'
		} ,{
			type : 'slider',
		} ],
		legend : {
			data : dataSource.chartER1.legend.data
		},
		xAxis : {
			type : dataSource.chartER1.xAxis.type,
			name : dataSource.chartER1.xAxis.name,
			nameLocation : 'end',
			nameGap : 0,
			splitLine : false,
			data : dataSource.chartER1.xAxis.data
		},
		yAxis : {
			type : dataSource.chartER1.yAxis.type,
			name : dataSource.chartER1.yAxis.name
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
			name : dataSource.chartER1.legend.data[0],
			type : 'bar',
			data : dataSource.chartER1.velocityList,
			label : {
				normal : {
					show : true,
					position : 'top',
				}
			}
		}, {
			name : dataSource.chartER1.legend.data[1],
			type : 'bar',
			data : dataSource.chartER1.actualVelocityList,
			label : {
				normal : {
					show : true,
					position : 'top',
				}
			}
		},
		{
			name : "Velocity Threshold",
			type : 'line',
			step: 'end',
			data : dataSource.chartER1.velocityThresholdList,
		},
		{
			name : "Release Note",
			type : 'line',
			data : dataSource.chartER1.releaseNoteListIndex,
			symbolSize:0,
			lineStyle:{
				normal : {
					opacity:0
				}
			}
		}]
	};
	chart1.clear();
	chart1.setOption(option1);
	
	var option2 = {
		title : {
			show : true,
			text : dataSource.chartER2.title.text,
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
		legend : {
			data : dataSource.chartER2.legend.data
		},
		xAxis : {
			type : dataSource.chartER2.xAxis.type,
			name : dataSource.chartER2.xAxis.name,
			nameLocation : 'end',
			nameGap : 0,
			splitLine : false,
			data : dataSource.chartER2.xAxis.data
		},
		yAxis : {
			type : dataSource.chartER2.yAxis.type,
			name : dataSource.chartER2.yAxis.name
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
			name : dataSource.chartER2.legend.data[0],
			type : 'bar',
			data : dataSource.chartER2.capacityList,
			label : {
				normal : {
					show : true,
					position : 'top',
				}
			}
		},
		{
			name : "Capacity Threshold",
			type : 'line',
			step: 'end',
			data : dataSource.chartER2.capacityThresholdList,
			/* lineStyle:{
				normal:{
					color:'red',
				}
			} *//* ,
			label : {
				normal : {
					show : true,
					position : 'top',
				}
			} */
		}]
	};
	chart2.clear();
	chart2.setOption(option2);
	
	var option3 = {
			title : {
				show : true,
				text : dataSource.chartER3.title.text,
				target : "self",

			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow'
				},
			},
			dataZoom : [ {
				type: 'inside'
			} ,{
				type : 'slider',
			} ],
			legend : {
				data : dataSource.chartER3.legend.data
			},
			xAxis : {
				type : dataSource.chartER3.xAxis.type,
				name : dataSource.chartER3.xAxis.name,
				nameLocation : 'end',
				nameGap : 0,
				splitLine : false,
				data : dataSource.chartER3.xAxis.data
			},
			yAxis : {
				type : dataSource.chartER3.yAxis.type,
				name : dataSource.chartER3.yAxis.name
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
			series : [{
				name : dataSource.chartER3.legend.data[0],
				type : 'line',
				data : dataSource.chartER3.productivityList,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				}
			},{
				name : dataSource.chartER3.legend.data[1],
				type : 'line',
				data : dataSource.chartER3.completionRateList,
				label : {
					normal : {
						show : true,
						position : 'top',
					}
				}
			} ]
		};
	chart3.clear();
	chart3.setOption(option3);
	
	function formatToolTip(params){
		var res =params[0].name+"<br/>";
		for(var i=0;i<params.length;i++){
			var color = '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + params[i].color + '"></span>';
			if(params[i].seriesName == 'Release Note'){
				res = res +color+params[i].seriesName+" : <br/>"+setStringReturn(dataSource.chartER1.releaseNoteList[params[i].value],10)+"<br/>"
			}else{
				res = res +color+params[i].seriesName+" : "+params[i].value+"<br/>"
			}
		}
        return res;
	}
</script>