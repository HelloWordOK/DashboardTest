<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<!-- <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button> -->
			<a class="navbar-brand" href="<%=basePath%>search">MonitorDashboard</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right" style="padding-right: 150px;">
				<li class="dropdown"><a href="<%=basePath%>search"> Capacity</a></li>
				<%-- <li class="dropdown"><a href="<%=basePath%>sr">Tickets Dashboard(R&D)</a></li> --%>
				<li class="dropdown"><a href="<%=basePath%>qa">Service</a></li>
				<li class="dropdown"><a href="<%=basePath%>er"> ER(Agile)</a></li>
				<li class="dropdown"><a href="<%=basePath%>testing/functional"> Testing</a></li>
<%-- 				<li class="dropdown"><a href="<%=basePath%>mail"> Mail</a></li> --%>
<%-- 				<li class="dropdown"><a href="<%=basePath%>code"> Code Management</a></li> --%>
<%-- 				<li class="dropdown"><a href="<%=basePath%>dev"> Devops</a></li> --%>
				<li class="dropdown"><a href="<%=basePath%>admin/input_domain">Management</a></li>
			</ul>
			<!-- <form class="navbar-form navbar-right">
        <input type="text" class="form-control" placeholder="Search...">
      </form> -->
		</div>
	</div>
</nav>