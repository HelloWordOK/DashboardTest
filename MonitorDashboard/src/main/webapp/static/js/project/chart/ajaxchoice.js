function ajaxBundle() {
	var domainId = $("#domain").val();
	$.ajax({
		url : path + "search/searchbundle",
		type : "POST",
		data : "domainId=" + domainId,
		success : function(data) {
			$("#domainbundle").html(data);
			$(".searchselect").select2();
			ajaxService();
		}
	});
};

function ajaxService() {
	var bundleId = $("#bundle").val();
	$.ajax({
		url : path + "search/searchservice",
		type : "POST",
		data : "bundleId=" + bundleId,
		success : function(data) {
			$("#bundleservice").html(data);
			$(".searchselect").select2();
			ajaxServer();
		}
	});
};

function ajaxServer() {
	var serviceId = $("#serviceId").val();
	$.ajax({
		url : path + "search/searchserver",
		type : "POST",
		data : "serviceId=" + serviceId,
		success : function(data) {
			$("#serviceserver").html(data);
			$(".searchselect").select2();
			ajaxMonitor();
		}
	});
};

function ajaxMonitor() {
	var serverId = $("#server").val();
	$.ajax({
		url : path + "search/searchmonitor",
		type : "POST",
		data : "serverId=" + serverId,
		success : function(data) {
			$("#servermonitor").html(data);
			$(".searchselect").select2();
		}
	});
};

function ajaxSprintList() {
	var bundleId = $("#bundleId").val();
	$.ajax({
		url : path + "er/searchsprint",
		type : "POST",
		data : "bundleId=" + bundleId,
		success : function(data) {
			$("#sprintList").html(data);
			$(".sprintselect").select2();
		}
	});
};

function ajaxTempList() {
	var serviceId = $("#serviceId").val();
	$.ajax({
		url : path + "mail/searchTemplate",
		type : "POST",
		data : "serviceId=" + serviceId,
		success : function(data) {
			$("#tempList").html(data);
			$(".searchselect").select2();
			if(mtype == "1"){
				var templateId = $("#tempId").val();
				$("#tmp").val(templateId).trigger("change");
			}
			ajaxTempData();
		}
	});
	
};

function ajaxTempData() {
	var tempId = $("#tmp").val();
	$.ajax({
		url : path + "mail/searchTemplateById",
		type : "POST",
		data : "tempId=" + tempId,
		success : function(data) {
			$("#autoList").html(data);
		}
	});
};

