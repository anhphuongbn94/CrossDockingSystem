<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
	<title>ADMIN</title>
	<meta charset="utf-8">
	
	<link href="resources/css/css.css" rel="stylesheet">
	<link href="resources/css/style.css" rel="stylesheet">
  	<link href="resources/css/style-responsive.css" rel="stylesheet">
  	<link href="resources/css/timepicker.css" rel="stylesheet">
  	<link href="resources/css/datepicker-custom.css" rel="stylesheet">
  	<link href="resources/css/datepicker.min.css" rel="stylesheet">
  	<link href="resources/css/select2.css" rel="stylesheet">
</head>
<body class="boxed-view">
	<section><section class="container">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="leftside" />
		<section class="main-content">
			<tiles:insertAttribute name="rightside" />
			<tiles:insertAttribute name="content" />
		</section>
		
		
		<script src="resources/js/jquery-1.10.2.min.js"></script>
		<script src="resources/js/bootstrap.min.js"></script>
		<script src="resources/js/jquery.nicescroll.js"></script>
		<script src="resources/js/bootstrap-datetimepicker.js"></script>
		<script src="resources/js/bootstrap-datepicker.js"></script>
		<script src="resources/js/bootstrap-timepicker.js"></script>
		<script src="resources/js/pickers-init.js"></script>
		<script src="resources/js/scripts.js"></script>
		<script src="resources/js/js.js"></script>
		<script src="resources/js/select2.js"></script>
		<script type="text/javascript">
			$('.s-vehicle').select2({
				placeholder: "Please select code vehicle",
				allowClear: true,
			});
		</script>
	</section></section>
</body>
</html>
