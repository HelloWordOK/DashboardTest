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
	    <li>
            <a href="#testingMeun" class="collapsed" data-toggle="collapse">Performance Testing</a>
            <ul id="testingMeun" class="menu-second nav collapse">
            	<li><a href="<%=basePath%>testing/performance" >Search</a></li>
            	<li><a href="<%=basePath%>testing/performancebyversion" >Search By Version</a></li>
            	<li><a href="<%=basePath%>testing/baseline" >Baseline</a></li>
            </ul>
	    </li>
	    <li><a href="<%=basePath%>testing/functional" >Function Testing Search</a></li>
    </ul>
</div>