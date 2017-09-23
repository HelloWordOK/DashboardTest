<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Group List</h3>
	<table id="groupsTable"
		class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>Group Name</th>
				<th>Service</th>
				<th>Operation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${groupList}" var="group">
				<tr>
					<td>${group.id}</td>
					<td>${group.groupName}</td>
					<td>${group.serviceName} + ${group.serviceCode}</td>
					<td style="width: 150px;">
						<button class="btn btn-success" type="button" id="btn_update" onclick="updateGroupPanel(${group.id})">update</button>
						<button class="btn btn-success" type="button" id="btn_delete" onclick="deleteGroup(${group.id})">delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#groupsTable').DataTable( {
	    	dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	    	lengthMenu: [[-1,30,50,100],["All",30,50,100]]
	    } );
    });
    
    function updateGroupPanel(id){
   		$.ajax({
			url : path + "admin/update_group",
			type : "POST",
			data : {
				id:id
			},
			success : function(data) {
				$('#groupModal').modal();
				$("#reid").val(data["id"]);
				$("#reserviceCode").val(data["serviceCode"]).trigger("change");
	   			$("#regroupName").val(data["groupName"]);
			}
		});
    }
    
    function deleteGroup(id){
    	if(confirm("Do you want to delete it?")){
    		$.ajax({
				url : path + "admin/delete_group",
				type : "POST",
				data : {
					id:id
				},
				success : function(data) {
					ajaxGroupList();
				}
			});
    	}
    }
</script>