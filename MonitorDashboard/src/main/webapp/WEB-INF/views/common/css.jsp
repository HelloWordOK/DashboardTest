<link rel="shortcut icon" href="<%=basePath%>static/img/logo.png" type="image/x-icon" />
<link href="<%=basePath%>static/css/bootstrap3.3.5/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/css/bootstrap3.3.5/bootstrap-table.css" rel="stylesheet" />
<link href="<%=basePath%>static/css/bootstrap3.3.5/bootstrap-datetimepicker.css" rel="stylesheet" />
<link href="<%=basePath%>static/css/bootstrap3.3.5/bootstrap-select.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/css/select2/select2.css" rel="stylesheet" />

<!-- Datatables-->
<link href="<%=basePath%>static/js/datatables-1.10.13/media/css/dataTables.bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/js/datatables-1.10.13/extensions/Buttons/css/buttons.bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/js/datatables-1.10.13/extensions/FixedColumns/css/fixedColumns.bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/js/datatables-1.10.13/extensions/FixedHeader/css/fixedHeader.bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>static/js/datatables-1.10.13/extensions/Select/css/select.bootstrap.min.css" rel="stylesheet" />

<link href="<%=basePath%>static/js/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />

<style type="text/css">
/*
 * Base structure
 */

/* Move down content because we have a fixed navbar that is 50px tall */
body {
  padding-top: 50px;
}

.table > tbody >tr>td{
	border-top: none;
}
.table > tbody >tr>td>label{
	width: 110px;
	text-align: right;
}
.form #div1{
	text-align: center;
	margin-top: 30px;
}

/*
 * Global add-ons
 */

.sub-header {
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

/*
 * Top navigation
 * Hide default border to remove 1px line.
 */
.navbar-fixed-top {
  border: 0;
}

/*
 * Sidebar
 */

/* Hide for mobile, show later */
.sidebar {
  display: none;
}
@media (min-width: 768px) {
  .sidebar {
    position: fixed;
    top: 51px;
    bottom: 0;
    left: 0;
    z-index: 1000;
    display: block;
    padding: 20px;
    overflow-x: hidden;
    overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
    background-color: #f5f5f5;
    border-right: 1px solid #eee;
  }
}

/* Sidebar navigation */
.nav-sidebar {
  margin-right: -21px; /* 20px padding + 1px border */
  margin-bottom: 20px;
  margin-left: -20px;
}
.nav-sidebar > li > a {
  padding-right: 20px;
  padding-left: 20px;
}
.nav-sidebar > .active > a,
.nav-sidebar > .active > a:hover,
.nav-sidebar > .active > a:focus {
  color: #fff;
  background-color: #428bca;
}


/*
 * Main content
 */

.main {
  padding: 20px;
}
@media (min-width: 768px) {
  .main {
    padding-right: 40px;
    padding-left: 40px;
  }
}
.main .page-header {
  margin-top: 0;
}


/*
 * Placeholder dashboard ideas
 */

.placeholders {
  margin-bottom: 30px;
  text-align: center;
}
.placeholders h4 {
  margin-bottom: 0;
}
.placeholder {
  margin-bottom: 20px;
}
.placeholder img {
  display: inline-block;
  border-radius: 50%;
}
#response_time,#sprint{
	display: inline-flex;
}

#resultData .row .col-sm-12,#resultData .row .col-sm-6{
	margin:5px auto;
}
.float_left{
	float: left;
}

.float_right {
	float:right;
}
/* table tr td, table tr th {
	white-space: nowrap;
} */
</style>