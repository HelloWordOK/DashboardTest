<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Server List</h3>
	<table id="serversTable"
		class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>Server Name</th>
				<th>Ip Address</th>
				<th>Server Detail</th>
				<th>Service</th>
				<th>Operation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${serverList}" var="server">
				<tr>
					<td>${server.id}</td>
					<td>${server.serverName}</td>
					<td>${server.ipAddress}</td>
					<td>${server.serverDetails}</td>
					<td>${server.serviceName} + ${server.serviceCode}</td>
					<td style="width: 150px;">
						<button class="btn btn-success" type="button" id="btn_update" onclick="updateServerPanel(${server.id})">update</button>
						<button class="btn btn-success" type="button" id="btn_delete" onclick="deleteServer(${server.id})">delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#serversTable').DataTable( {
	    	dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	    	lengthMenu: [[-1,30,50,100],["All",30,50,100]]
	    } );
    });
    
    function updateServerPanel(id){
   		$.ajax({
			url : path + "admin/update_server",
			type : "POST",
			data : {
				id:id
			},
			success : function(data) {
				$('#serverModal').modal();
				$("#reid").val(data["id"]);
				$("#reserviceId").val(data["serviceId"]).trigger("change");
	   			$("#reserverName").val(data["serverName"]);
	   			$("#reipAddress").val(data["ipAddress"]);
	   			$("#reserverDetails").val(data["serverDetails"]);
			}
		});
    }
    
    function deleteServer(id){
    	if(confirm("Do you want to delete it?")){
    		$.ajax({
				url : path + "admin/delete_server",
				type : "POST",
				data : {
					id:id
				},
				success : function(data) {
					ajaxServerList();
				}
			});
    	}
    }
</script>