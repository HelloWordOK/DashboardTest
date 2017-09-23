<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.nav-sidebar li a:hover {
    background-color: #66c3ec;
}

.menu-second li a{
    font-size: 12px;
    margin-left: 20px;
}

</style>
<div class="col-sm-2 sidebar">
	<ul class="nav nav-sidebar">
	    <%-- <li><a href="<%=basePath%>qa/domain" >Domain Search</a></li>
	    <li><a href="<%=basePath%>qa/bundle" >Bundle Search</a></li> --%>
	<li>
    	<a href="#serviceMeun" class="collapsed" data-toggle="collapse">By Service</a>
    	<ul id="serviceMeun" class="menu-second nav collapse in">
	        <li><a href="<%=basePath%>qa/service?isDomain=false">Chart Analysis</a></li>
	        <li><a href="<%=basePath%>qa/service/data">Data Analysis</a></li>
 		</ul>
 	</li>
	<li>
    	<a href="#domainMeun" class="collapsed" data-toggle="collapse">By Domain</a>
    	<ul id="domainMeun" class="menu-second nav collapse in">
	        <li><a href="<%=basePath%>qa/domain?isDomain=true">Chart Analysis</a></li>
 		</ul>
 	</li>
	    <%-- <li><a href="<%=basePath%>qa/import" >Data Import</a></li> --%>
    </ul>
</div>