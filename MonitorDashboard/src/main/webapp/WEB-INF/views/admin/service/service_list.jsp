<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Service List</h3>
	<table id="servicesTable"
		class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>Service Name</th>
				<th>Service Code</th>
				<th>Operation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${serviceList}" var="service">
				<tr>
					<td>${service.id}</td>
					<td>${service.serviceName}</td>
					<td>${service.serviceCode}</td>
					<td style="width: 150px;">
						<button class="btn btn-success" type="button" id="btn_update" onclick="updateServicePanel(${service.id})">update</button>
						<button class="btn btn-success" type="button" id="btn_delete" onclick="deleteService(${service.id})">delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#servicesTable').DataTable( {
	    	dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	    	lengthMenu: [[-1,30,50,100],["All",30,50,100]]
	    } );
    });
    
    function updateServicePanel(id){
   		$.ajax({
			url : path + "admin/update_service",
			type : "POST",
			data : {
				id:id
			},
			success : function(data) {
				$('#serviceModal').modal();
				$("#reid").val(data["id"]);
	   			$("#reserviceName").val(data["serviceName"]);
	   			$("#reserviceCode").val(data["serviceCode"]);
			}
		});
    }
    
    function deleteService(id){
    	if(confirm("Do you want to delete it?")){
    		$.ajax({
				url : path + "admin/delete_service",
				type : "POST",
				data : {
					id:id
				},
				success : function(data) {
					ajaxServiceList();
				}
			});
    	}
    }
</script>