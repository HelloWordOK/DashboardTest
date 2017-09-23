function markLine(data){
    var upper_Spec_Threshold = null;
	if(data.legend.upperSpecThreshold!=null){
		upper_Spec_Threshold = {
			name:'Upper Spec Threshold',
            yAxis: data.legend.upperSpecThreshold,
            lineStyle:{
            	normal:{
            		color:'#ff0000',
            		type:'dashed',
            	}
            }
		}
	}
	var upper_Control_Threshold = null;
	if(data.legend.upperControlThreshold!=null){
		upper_Control_Threshold = {
			name:'Upper Control Threshold',
            yAxis: data.legend.upperControlThreshold,
            lineStyle:{
            	normal:{
            		color:'#00ff00',
            		type:'dashed',
            	}
            }
		}
	}
	var average_Threshold = null;
	if(data.legend.averageThreshold!=null){
		average_Threshold = {
			name:'Average',
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
			name:'Lower Control Threshold',
            yAxis: data.legend.lowerControlThreshold,
            lineStyle:{
            	normal:{
            		color:'#00ff00',
            		type:'dashed',
            	}
            }
		}
	}
	var lower_Spec_Threshold = null;
	if(data.legend.lowerSpecThreshold!=null){
		lower_Spec_Threshold = {
			name:'Lower Spec Threshold',
            yAxis: data.legend.lowerSpecThreshold,
            lineStyle:{
            	normal:{
            		color:'#ff0000',
            		type:'dashed',
            	}
            }
		}
	}
	
	var thresholdCritical = null;
	if(data.legend.thresholdCritical!=null){
		thresholdCritical = {
			name:'Critical Threshold',
            yAxis: data.legend.thresholdCritical,
            lineStyle:{
            	normal:{
            		color:'rgb(223,12,122)',
            		type:'solid',
            	}
            }
		}
	}
	
	var thresholdWarning = null;
	if(data.legend.thresholdWarning!=null){
		thresholdWarning = {
			name:'Warning Threshold',
            yAxis: data.legend.thresholdWarning,
            lineStyle:{
            	normal:{
            		color:'rgb(255,80,133)',
            		type:'solid',
            	}
            }
		}
	}
	
	var thresholdCritical = null;
	if(data.legend.thresholdCritical!=null){
		thresholdCritical = {
			name:'Critical Threshold',
            yAxis: data.legend.thresholdCritical,
            lineStyle:{
            	normal:{
            		color:'rgb(223,12,122)',
            		type:'solid',
            	}
            }
		}
	}
	
	var markLineData = [];
	if(upper_Spec_Threshold!=null){
		markLineData.push(upper_Spec_Threshold);
	}
	if(upper_Control_Threshold!=null){
		markLineData.push(upper_Control_Threshold);
	}
	if(average_Threshold!=null){
		markLineData.push(average_Threshold);
	}
	if(lower_Control_Threshold!=null){
		markLineData.push(lower_Control_Threshold);
	}
	if(lower_Spec_Threshold!=null){
		markLineData.push(lower_Spec_Threshold);
	}
	
	if(thresholdCritical!=null){
		markLineData.push(thresholdCritical);
	}
	if(thresholdWarning!=null){
		markLineData.push(thresholdWarning);
	}
	return markLineData;
};

function maxValue(data){
	var maxValue = [];
	if(data.yAxis.max!=null){
		maxValue.push(data.yAxis.max);
	}else{
		maxValue = {};
	}
	return maxValue;
};

function showloading() {
	var btn_search = $("#btn_search");
	var btn_reset = $("#btn_reset");
	btn_search.button('loading');
	btn_reset.button('loading');
};

function hideloading() {
	var btn_search = $("#btn_search");
	var btn_reset = $("#btn_reset");
	btn_search.button('reset');
	btn_reset.button('reset');
};