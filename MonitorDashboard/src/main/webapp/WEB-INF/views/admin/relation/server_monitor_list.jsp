<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Server Monitor List</h3>
	<table id="serverMonitorTable" class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>Server Name</th>
				<th>Monitor Name</th>
				<th>Operation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${serverMonitorList}" var="serverMonitor">
				<tr>
					<td>${serverMonitor.id}</td>
					<td>${serverMonitor.serverName}</td>
					<td>${serverMonitor.monitorName}</td>
					<td style="width: 150px;">
						<button class="btn btn-success" type="button" id="btn_update" onclick="updateServerMonitorPanel(${serverMonitor.id})">update</button>
						<button class="btn btn-success" type="button" id="btn_delete" onclick="deleteServerMonitor(${serverMonitor.id})">delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#serverMonitorTable').DataTable( {
	    	dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	    	lengthMenu: [[-1,30,50,100],["All",30,50,100]]
	    } );
    });
    
    function updateServerMonitorPanel(id){
   		$.ajax({
			url : path + "admin/update_server_monitor",
			type : "POST",
			data : {
				id:id
			},
			success : function(data) {
				$('#serverMonitorModal').modal();
				$("#reid").val(data["id"]);
				$("#reserverId").val(data["serverId"]).trigger("change");
				$("#remonitorId").val(data["monitorId"]).trigger("change");
			}
		});
    }
    
    function deleteServerMonitor(id){
    	if(confirm("Do you want to delete it?")){
    		$.ajax({
				url : path + "admin/delete_server_monitor",
				type : "POST",
				data : {
					id:id
				},
				success : function(data) {
					ajaxServerMonitorList();
				}
			});
    	}
    }
</script>