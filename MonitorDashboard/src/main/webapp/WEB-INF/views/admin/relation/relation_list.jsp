<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Domain Bundle Service Relation List</h3>
	<table id="relationTable"
		class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>Domain Name</th>
				<th>Bundle Code</th>
				<th>Service Name</th>
				<th>Service Code</th>
				<th>Operation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${relationList}" var="relation">
				<tr>
					<td>${relation.id}</td>
					<td>${relation.domainName}</td>
					<td>${relation.bundleName}</td>
					<td>${relation.serviceName}</td>
					<td>${relation.serviceCode}</td>
					<td style="width: 150px;">
						<button class="btn btn-success" type="button" id="btn_update" onclick="updateRelationPanel(${relation.id})">update</button>
						<button class="btn btn-success" type="button" id="btn_delete" onclick="deleteRelation(${relation.id})">delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#relationTable').DataTable( {
	    	dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	    	lengthMenu: [[-1,30,50,100],["All",30,50,100]]
	    } );
    });
    
    function updateRelationPanel(id){
   		$.ajax({
			url : path + "admin/update_relation",
			type : "POST",
			data : {
				id:id
			},
			success : function(data) {
				$('#relationModal').modal();
				$("#reid").val(data["id"]);
				$("#redomainId").val(data["domainId"]).trigger("change");
				$("#rebundleId").val(data["bundleId"]).trigger("change");
				$("#reserviceId").val(data["serviceId"]).trigger("change");
			}
		});
    }
    
    function deleteRelation(id){
    	if(confirm("Do you want to delete it?")){
    		$.ajax({
				url : path + "admin/delete_relation",
				type : "POST",
				data : {
					id:id
				},
				success : function(data) {
					ajaxRelationList();
				}
			});
    	}
    }
</script>