<%@include file="../../common/tag.jsp" %>
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
<%@include file="../../common/css.jsp" %>
</head>
 <body>
    <%@include file="../../common/header.jsp" %>

    <div class="container-fluid">
	    <div class="row">
	        <%@include file="../../common/sidebar_to_availability.jsp" %>
	        <div class="col-sm-10 col-sm-offset-2 main">
	        	<h2 class="sub-header">Response Time in 4Hours</h2>
				<div class="form">
	   				<form class="form-horizontal" action="" method="post" id="forminfo">
						<div class="col-sm-1"></div>
						<div class="col-sm-8">
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-3 control-label">Service</label>
										<div class="col-sm-7">
											<select class="form-control searchselect" id="serviceId" onchange="ajaxServer();">
												<c:forEach items="${serviceList}" var="service">
													<option value="${service.serviceId}">${service.serviceName} + ${service.serviceCode }</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div id="serviceserver"></div>
								<div id="servermonitor"></div>
							</div>
							<div class="col-sm-offset-4 col-sm-9">
								<button class="btn btn-success" type="button" onclick="dynamicchart();" autocomplete="off" data-loading-text="Loading..." id="btn_search">Search</button>
								<button class="btn btn-success" type="reset" autocomplete="off" data-loading-text="Reset" id="btn_reset">Reset</button>
							</div>
						</div>
					</form>
				</div>
				<div class="col-sm-12" style="margin: 40px auto"></div>
				<div class="col-sm-12" id="dynamicchart" style="height:450px;margin-top: 40px;"></div>
				<div class="col-sm-12" style="margin: 20px auto"></div>
				<div id="resultData"></div>
			</div>
		</div>
	</div>
    
    <%@include file="../../common/js.jsp" %>
    
    <script type="text/javascript">
		var path = '<%=basePath%>';
		
		$(document).ready(function() {
	    	  $(".searchselect").select2();
	    	  ajaxServer();
	    });
		
		$("button[type='reset']").on("click", function(event){
	        event.preventDefault();
	        var myForm = $(this).closest('form').get(0);
	        myForm.reset();
	        $("select", myForm).each(
	            function () {
	                $(this).select2('val','');
	                /* $(this).select2('val',$(this).find('option:selected').val()) */
	            }
	        );
	    });
		
		function validate(){
	    	var serverId = $("#server").val();
	    	if(null==serverId||""==serverId.trim()){
	    		alert("Please choose Server.");
	    		return false;
	    	}
	    	var monitor = $("#monitor").val();
	    	if(null==monitor||""==monitor.trim()){
	    		alert("Please choose Monitor.");
	    		return false;
	    	}
	    	return true;
	    }
		
		var dChart = echarts.init(document.getElementById('dynamicchart'));
		
		setInterval(dynamicchart, 1000*60*5);
		
	    function dynamicchart(){
	    	if(validate()){
	    		var serverId = $("#server").val();
		    	var monitor = $("#monitor").val();
		    	showloading();
		    	$.ajax({
		       		url:path+"dynamic/4hour",
		       		type:"POST",
		       		data:"serverId="+serverId+"&monitorId="+monitor,
		       		success:function(data){
		       			var option = chart(data);
		       			dChart.clear();
		       			dChart.setOption(option);
		       			dynamicmessage();
		       			hideloading();
		       		}
		       	});
	    	}
	    }
	    
	    function dynamicmessage(){
	    	var serverId = $("#server").val();
	    	var monitor = $("#monitor").val();
	    	$.ajax({
	       		url:path+"dynamic/4hourmessage",
	       		type:"POST",
	       		data:"serverId="+serverId+"&monitorId="+monitor,
	       		success:function(data){
	       			$("#resultData").html(data);
	       		}
	       	});
	    }
    
	    function chart(data){
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
	    			max:maxValue(data),
	    		},
	    		dataZoom : [ {
	    			type: 'inside'
	    		} ,{
	    			type : 'slider',
	    			left:'15%',
	    			right:'15%',
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
	    							position:'top'
	    						}
	    					}
	    				}]
	    	};
	    	return option;
	    };
    </script>
</body>

</html>