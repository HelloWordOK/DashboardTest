<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">bundle List</h3>
	<table id="bundlesTable"
		class="table table-striped table-hover table-bordered">
		<thead>
			<tr>
				<th>id</th>
				<th>bundle Name</th>
				<th>Operation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${bundleList}" var="bundle">
				<tr>
					<td>${bundle.id}</td>
					<td>${bundle.bundleName}</td>
					<td style="width: 150px;">
						<button class="btn btn-success" type="button" id="btn_update" onclick="updateBundlePanel(${bundle.id})">update</button>
						<button class="btn btn-success" type="button" id="btn_delete" onclick="deleteBundle(${bundle.id})">delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
    $(document).ready(function() {
    	$('#bundlesTable').DataTable( {
	    	dom: "<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	        lengthMenu: [[-1,30,50,100],["All",30,50,100]]
	    } );
    });
    
    function updateBundlePanel(id){
   		$.ajax({
			url : path + "admin/update_bundle",
			type : "POST",
			data : {
				id:id
			},
			success : function(data) {
				$('#bundleModal').modal();
				$("#reid").val(data["id"]);
	   			$("#rebundleName").val(data["bundleName"]);
			}
		});
    }
    
    function deleteBundle(id){
    	if(confirm("Do you want to delete it?")){
    		$.ajax({
				url : path + "admin/delete_bundle",
				type : "POST",
				data : {
					id:id
				},
				success : function(data) {
					ajaxBundleList();
				}
			});
    	}
    }
</script>