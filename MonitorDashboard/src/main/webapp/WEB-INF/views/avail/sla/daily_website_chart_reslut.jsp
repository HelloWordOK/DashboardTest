<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h2 class="sub-header">Daily WebSite Response Time Chart</h2>
<div class="col-sm-12" id="dailyresponsetime" style="height:450px;margin-top: 20px;"></div>
<script type="text/javascript">
	var dailyChart = echarts.init(document.getElementById('dailyresponsetime'));
	var data = ${data};
	
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
					data : data.xAxis.data
				}],
		yAxis : {
			type:data.yAxis.type,
			name:data.yAxis.name,
			max:data.yAxis.max
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
					label:{
						normal:{
							show:true,
							position:'top'
						}
					}
				}]
	};
	dailyChart.clear();
	dailyChart.setOption(option);
</script>
