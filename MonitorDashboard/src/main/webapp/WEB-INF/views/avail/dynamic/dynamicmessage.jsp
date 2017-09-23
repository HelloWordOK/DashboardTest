<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="col-sm-12">
	<h3 style="text-align: center;">${server.serverName} Error Message</h3>
	<table id="errorMessageTable" class="table table-striped table-hover table-bordered" title="ErrorMessage">
       <thead>
           <tr>
               <th>Message</th>
               <th>Response Time</th>
               <th>Status</th>
           </tr>
       </thead>

       <tbody>
       		<c:forEach items="${errorMessageList}" var="errorMessage">
	       		<tr>
	               <td>${errorMessage.message}</td>
	               <td>${errorMessage.responseTime}</td>
	               <td>${errorMessage.status}</td>
	           </tr>
       		</c:forEach>
       </tbody>
   </table>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	    $('#errorMessageTable').DataTable( {
	    	dom: "<'row'<'col-sm-12'B>>"+"<'row'<'col-sm-6'l><'col-sm-6'f>>" + "<'row'<'col-sm-12'tr>>" + "<'row'<'col-sm-4'i><'col-sm-8'p>>",
	        buttons: ['copy','excel', 'pdf'],
	        lengthMenu: [[15,30,50,-1],[15,30,50,"All"]]
	    } );
	} );
</script>