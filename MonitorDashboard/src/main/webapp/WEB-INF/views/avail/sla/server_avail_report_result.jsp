<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">Server Avail. Report</h3>
	<table id="serverAvailTable" class="table table-striped table-hover table-bordered">
       <thead>
           <tr>
           		<th>Server</th>
           	   <c:forEach items="${yearList}" var="year">
           	   		<th>${year}</th>
           	   </c:forEach>
           </tr>
       </thead>

       <tbody>
       		<c:forEach items="${dataList}" var="data">
	       		<tr>
	               <td>${data.server}</td>
	               <c:forEach items="${data.valueList}" var="value">
	               		<c:if test="${value>=95}">
	               			<td style="color: green;">${value}%</td>
	               		</c:if>
	               		<c:if test="${value<95}">
	               			<td style="color: red;">${value}%</td>
	               		</c:if>
	               </c:forEach>
	           </tr>
       		</c:forEach>
       </tbody>
   </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	    $('#serverAvailTable').DataTable( {
	    	dom: "<'row'<'col-sm-12'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	        buttons: ['copy','excel', 'pdf'],
	        lengthMenu: [[15,30,50,-1],[15,30,50,"All"]],
	        scrollY:false,
	        scrollX: true,
	        responsive: true,
	        autoWidth:false,
	        fixedColumns:true
	    } );
	} );
</script>