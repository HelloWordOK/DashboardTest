<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<h2 class="sub-header">Export Search Results</h2>
<div class="col-sm-12" style="margin: 20px auto; font-size: 12px;">
	<table id="erDataTable" class="table table-striped table-hover table-bordered">
		<thead>
           <tr>
           	   <th>Bundle</th>
               <th>Release Sprint</th>
	           <th>Plan Velocity</th>
               <th>Actual Velocity</th>
               <th>Plan Capacity</th>
               <th>Productivity</th>
               <th>Completion Rate</th>
               <th>Start Date</th>
               <th>End Date</th>
	       </tr>
       </thead>

       <tbody>
       		<c:forEach	items="${statisticsExportERList}" var="statistics">
       			<tr>
       				<td>${statistics.bundleName}</td>
       				<td>${statistics.releaseSprint}</td>
       				<td>${statistics.planVelocity}</td>
       				<td>${statistics.actualVelocity}</td>
       				<td>${statistics.planCapacity}</td>
       				<td>${statistics.productivity}%</td>
       				<td>${statistics.completionRate}%</td>
       				<td>${statistics.startDate}</td>
       				<td>${statistics.endDate}</td>
       			</tr>
       		</c:forEach>
       </tbody>
	</table>
</div>

<script type="text/javascript">
$("#erDataTable").DataTable({
    dom: "<'row'<'col-sm-6'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
    buttons: [{
        extend: "excel",
        className: "btn-sm btn-success"
    }],
    responsive: true,
    autoWidth:false,
    lengthMenu: [[20,50,100,-1],[20,50,100,"All"]]
});
</script>