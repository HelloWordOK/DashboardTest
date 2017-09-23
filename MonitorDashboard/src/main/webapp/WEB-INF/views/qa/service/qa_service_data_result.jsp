<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Data Analysis Result</h3>
	<table id="qaServiceDataTable" class="table table-striped table-hover table-bordered" style="white-space: nowrap;">
		<thead>
			<tr>
				<th>operation</th>
				<c:forEach items="${headTitle}" var="title">
					<th>${title}</th>
				</c:forEach>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${dataSource}" var="dataKey">
				<tr>
					<td style="text-align: center;"><button class="btn btn-success" onclick="editFlag(${dataKey.key});">edit</button></td>
					<c:forEach items="${dataKey.value }" var="data">
						<td>${data}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="modal fade" id="flag_modal" tabindex="-1" role="dialog"
		aria-labelledby="flagModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="flagModalLabel">Set SLA Real Breach</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="serviceId" name="serviceId">
					<div>
						<label class="control-label">Real Breach:</label>
						<label class="radio-inline">
							<input type="radio" name="real_breach" value="TRUE" checked="checked">TRUE
						</label>
						<label class="radio-inline">
							<input type="radio" name="real_breach" value="FALSE">FALSE
						</label>
					</div>
					<div>
						<label class="control-label">Comments:</label>
						<textarea class="form-control" rows="5" id="comment" placeholder="Comments when data is changed" ></textarea>
					</div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="flag_save">Save</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$("#qaServiceDataTable").DataTable({
    dom: "<'row'<'col-sm-6'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
    buttons: [{
        extend: "excel",
        className: "btn-sm btn-success"
    },{
        extend: "colvis",
        className: "btn-sm btn-success"
    }],
    lengthMenu: [[10,20,50,-1],[10,20,50,"All"]],
    scrollY: "350px",
    scrollX: true,
    scrollCollapse: true,
    responsive: true,
    autoWidth:false,
    fixedColumns:true
});

function editFlag(id){
	if(confirm("Do you want to edit?")){
		$("#serviceId").val(id);
		$('#flag_modal').modal();
	}
}

$("#flag_modal").on("hidden.bs.modal", function() {
	$("#comment").val(null);
});

$("#flag_save").click(function(){
	var sla_breach = [];
	$('input[name="sla_breach"]:checked').each(function(){
		sla_breach.push($(this).val());
	});
	/* var data_status = [];
	$('input[name="data_status"]:checked').each(function(){
		data_status.push($(this).val());
	}); */
	var id = $("#serviceId").val();
	var real_breach = $('input:radio[name="real_breach"]:checked').val();
	var comment = $("#comment").val();
	var param = {
			serviceCode : $("#serviceCode").val(),
	    	dataType : $("#data_type").val(),
	    	/* dataStatus : data_status, */
	    	startMonth : $("#response_time_start").val(),
	    	endMonth : $("#response_time_end").val(),
			slaBreach : sla_breach,
			id:id,
			real_breach:real_breach,
			comment:comment
		};
	if(comment.trim()==""){
		alert("Please input Comments.");
	}else{
		$('#flag_modal').modal('hide');
		$(".modal-backdrop").remove();
		$.ajax({
			  type:"post",
			  url:path+"/qa/data_real_breach_submit",
			  data:{param:JSON.stringify(param)},
			  success:function(data){
				  alert("Successfully.");
				  $("#echart").html(data);
			  }
		});
	}
})
</script>