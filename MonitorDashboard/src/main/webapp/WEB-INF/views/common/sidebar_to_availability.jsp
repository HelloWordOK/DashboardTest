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
    	<a href="#availMeun" class="collapsed" data-toggle="collapse">Server Avail.</a>
    	<ul id="availMeun" class="menu-second nav collapse in">
	        <li><a href="<%=basePath%>search" >Server Avail. Search</a></li>
    		<li><a href="<%=basePath%>dynamic" >Response Time in 4Hours</a></li>
	        <li><a href="<%=basePath%>sla/daily_website_chart">Daily WebSite Response Time</a></li>
	        <li><a href="<%=basePath%>sla/server_avail_report">Server Avail. Report</a></li>
 		</ul>
 	</li>
 	<li>
    	<a href="#activeMeun" class="collapsed" data-toggle="collapse">Active Users</a>
    	<ul id="activeMeun" class="menu-second nav collapse in">
	        <li><a href="<%=basePath%>active/users_chart" >Active Users Chart</a></li>
    		<li><a href="<%=basePath%>active/users_input" >Input Data</a></li>
 		</ul>
 	</li>
 	<li><a href="<%=basePath%>shinepoint/major_incident_list">Major Incident List</a></li>
  </ul>
</div>