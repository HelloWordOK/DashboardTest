<style>
.nav-sidebar li a:hover {
    background-color: #66c3ec;
}

.menu-second li a{
    font-size: 12px;
    margin-left: 20px;
}
/* .menu-third li a{
    font-size: 12px;
    margin-left: 30px;
} */

</style>
<div class="col-sm-2 sidebar">
  <ul class="nav nav-sidebar">
 	<li>
     	<a href="#resourceMenu" class="collapsed" data-toggle="collapse">Resource Management</a>
     	<ul id="resourceMenu" class="menu-second nav collapse in">
     		<li>
      			<a href="<%=basePath%>admin/input_domain" >Input Domain</a>
     		</li>
     		<li>
      			<a href="<%=basePath%>admin/input_bundle" >Input Bundle</a>
     		</li>
     		<li>
      			<a href="<%=basePath%>admin/input_service" >Input Service</a>
     		</li>
     		<li>
      			<a href="<%=basePath%>admin/input_server" >Input Server</a>
     		</li>
     		<li>
      			<a href="<%=basePath%>admin/input_group" >Input Group</a>
     		</li>
     	</ul>
     </li>
     <li>
      	<a href="#relationMenu" class="collapsed" data-toggle="collapse">Config Relation</a>
     	<ul id="relationMenu" class="menu-second nav collapse in">
     		<li>
      	 		<a href="<%=basePath%>admin/input_relation" >Domain Bundle Service</a>
     		</li>
     		<li>
      	 		<a href="<%=basePath%>admin/config_server_monitor" >Server Monitor</a>
     		</li>
     	</ul>
     </li>
  </ul>
</div>