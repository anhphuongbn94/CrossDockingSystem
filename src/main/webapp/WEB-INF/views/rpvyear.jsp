<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="statistics.year.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<div id="ac-wrapper">
			<div class="img-load">
				<center>
					<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
				</center>
			</div>
		</div>
		<section class="row">
		<section class="col-sm-12">
		<section class="panel">
		<section class="panel-body">
			<div id="chartContainer" style="height: 300px; width: 100%;"></div>
		</section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
		<!------------------------------------------------------------------------------------------>
        <!------------------------------------------------------------------------------------------>
        <!------------------------------------------------------------------------------------------>
        <!-- InVehicle -->
		<section class="row">
		<section class="col-sm-12">
		<section class="panel">
		<section class="panel-body">
			<section class="form-group">
			<form role="form" class="cmxform form-horizontal adminex-form">
				<label class="col-lg-2 control-label">
					<s:message code="date.year" />
				</label>
				<section class="col-lg-3">
					<label>
				  		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
						<input type="text" class="form-control picker-month" readonly="readonly">
					</label>
				</section>
				<section style="float: right">
	          		<span class="btn-primary" style="width: 60px;">
	          			<a href="${pageContext.request.contextPath }/exportExcel" style="color: #fff">Export</a>
	       			</span>
	           </section>
           </form>
           </section>
		</section>
		</section><!-- End Panel -->
		</section><!-- End Col-SM -->
		<section class="col-sm-12">
		<section class="panel">
		<header class="panel-heading"><s:message code="invehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>
        </header><!-- End Panel-Heading -->
		<section class="panel-body">
			<section class="form-group">
				<form role="form" class="cmxform form-horizontal adminex-form">
	        		<label class="col-lg-2 control-label" style="margin-left: -25px">
	        			<s:message code="vehicle.code" />
	        		</label>
	        		<section class="col-lg-3">
						<input type="text" class="form-control key-invehicle-search">
					</section>
					<label class="col-lg-2 control-label" style="margin-left: -110px">
						<s:message code="vehicle.company" />
					</label>
					<section class="col-lg-3">
						<input type="text" class="form-control company">
					</section>
					<label class="col-lg-2 control-label" style="margin-left: -140px">
						<s:message code="invehicle.date" />
					</label>
					<section class="col-lg-3" style="margin-left: -15px">
	                	<label>
	                   		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
	                    	<input type="text" name="date" readonly="readonly" 
	                    		class="form-control date rp-piv-date">
                   		</label>
	                </section>
					<section class="col-lg-1" style="margin-left: -10px">
						<input type="button" class="btn-primary form-control" id="btn-search-rpiv" value="Search" />
					</section>
				</form>
			</section>
		</section><!-- End Panel-Body -->
        <section class="panel-body">
        <section class="adv-table">
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-3">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="record-rp-inv" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
									<option value="15" selected="selected">15</option>
									<option value="30">30</option>
									<option value="50">50</option>
								</select> records per page
							</label>
						</section>
					</section>
				</section>
			</section>
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <td><s:message code="vehicle.code" /></td>
			            <td><s:message code="vehicle.company" /></td>
			            <td><s:message code="invehicle.date" /></td>
						<td><s:message code="invehicle.arrival" /></td>
 						<td><s:message code="product.name" /></td>
						<td><s:message code="product.quantity" /></td>
						<td><s:message code="product.unit" /></td>
			        </tr>
		        </thead>
		        <tbody class="rowPIV_data">
		        	<c:if test="${pagePIV.size() > 0 }">
				        <c:forEach items="${pagePIV }" var="rows">
							<tr>
								<td>${rows.iv.vehicleCode }</td>
								<td>${rows.iv.company }</td>
								<td>${rows.iv.date }</td>
								<td>${rows.iv.arrivalTime }</td>
								<td>${rows.p.name }</td>
								<td>${rows.quantity }</td>
								<td>${rows.u.name }</td>
							</tr>
						</c:forEach>
					</c:if>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pagePIV.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pagePIV.size() > 0 }">
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-rpinv">
						<li class="prev"><a>←</a></li>
						<c:forEach items="${pagerI.listPage }" var="item">
							<c:choose>
								<c:when test="${item==1 }">
									<li class="page_index active" data-index="${item }"><a>${item }</a></li>
								</c:when>
								<c:otherwise>
									<li class="page_index" data-index="${item }"><a>${item }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li style="display: none" class="num-page" data-bind="${pagerI.numPage }"></li>
						<li class="next"><a>→ </a></li>
					</ul>
				</section><!-- End Pagination -->
			</c:if>
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
        <!------------------------------------------------------------------------------------------>
        <!------------------------------------------------------------------------------------------>
        <!------------------------------------------------------------------------------------------>
        <!-- OutVehicle -->
		<section class="row">
		<section class="col-sm-12">
		<section class="panel">
        <header class="panel-heading"><s:message code="outvehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>
        </header><!-- End Panel-Heading -->
		<section class="panel-body">
			<section class="form-group">
				<form role="form" class="cmxform form-horizontal adminex-form">
	        		<label class="col-lg-2 control-label" style="margin-left: -25px">
	        			<s:message code="vehicle.code" />
        			</label>
	        		<section class="col-lg-3">
						<input type="text" class="form-control key-outvehicle-search">
					</section>
					<label class="col-lg-2 control-label" style="margin-left: -110px">
						<s:message code="vehicle.company" />
					</label>
					<section class="col-lg-3">
						<input type="text" class="form-control company">
					</section>
					<label class="col-lg-2 control-label" style="margin-left: -140px">
						<s:message code="invehicle.date" />
					</label>
	                <section class="col-lg-3" style="margin-left: -15px">
	                	<label>
	                   		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
	                    	<input type="text" readonly="readonly"
	                    		name="date" class="form-control date rp-pov-date">
                   		</label>
	                </section>
					<section class="col-lg-1" style="margin-left: -10px">
						<input type="button" class="btn-primary form-control" id="btn-search-rpov" value="Search" />
					</section>
				</form>
			</section>
		</section><!-- End Panel-Body -->
        <section class="panel-body">
        <section class="adv-table">
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-3">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="record-rp-outv" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
									<option value="15" selected="selected">15</option>
									<option value="30">30</option>
									<option value="50">50</option>
								</select> records per page
							</label>
						</section>
					</section>
				</section>
			</section>
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <td><s:message code="vehicle.code" /></td>
			            <td><s:message code="vehicle.company" /></td>
			            <td><s:message code="outvehicle.date" /></td>
						<td><s:message code="outvehicle.arrival" /></td>
 						<td><s:message code="product.name" /></td>
						<td><s:message code="product.quantity" /></td>
						<td><s:message code="product.unit" /></td>
			        </tr>
		        </thead>
		        <tbody class="rowPOV_data">
		        	<c:if test="${pagePOV.size() > 0 }">
				        <c:forEach items="${pagePOV }" var="rows">
							<tr>
								<td>${rows.ov.vehicleCode }</td>
								<td>${rows.ov.company }</td>
								<td>${rows.ov.date }</td>
								<td>${rows.ov.arrivalTime }</td>
								<td>${rows.p.name }</td>
								<td>${rows.quantity }</td>
								<td>${rows.u.name }</td>
							</tr>
						</c:forEach>
					</c:if>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pagePOV.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pagePOV.size() > 0 }">
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-rpoutv">
						<li class="prev"><a>←</a></li>
						<c:forEach items="${pagerO.listPage }" var="item">
							<c:choose>
								<c:when test="${item==1 }">
									<li class="page_index active" data-index="${item }"><a>${item }</a></li>
								</c:when>
								<c:otherwise>
									<li class="page_index" data-index="${item }"><a>${item }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li style="display: none" class="num-page" data-bind="${pagerO.numPage }"></li>
						<li class="next"><a>→ </a></li>
					</ul>
				</section><!-- End Pagination -->
			</c:if>
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
        <script src="resources/js/canvasjs.min.js"></script>
		<script type="text/javascript">
		  window.onload = function () {
		    var chart = new CanvasJS.Chart("chartContainer",
		    {
		      title:{
		        text: "Vehicle Statistics"
		      },
			  axisY: {
		        title: "Number of Vehicle"
			  },
			  axisX: {
		        title: "Month"
			  },
			  toolTip: {
				content: "{name}: {y}"
			  },
			  legend:{
				fontSize: 13
			  },
		      data: [ 
		      {
				color: "#27408B",
				showInLegend: true, 
				name: "InVehicle",
		        dataPoints: [
		        {y: 30, label: "Jan"},
		        {y: 50,  label: "Feb" },
		        {y: 40,  label: "Mar"},
		        {y: 70,  label: "Apr"},
		        {y: 90,  label: "May"},
		        {y: 80, label: "Jun"},
		        {y: 50,  label: "Jul"},
		        {y: 20,  label: "Aug"},
				{y: 10,  label: "Sep"},
				{y: 30,  label: "Oct"},
				{y: 60,  label: "Nov"},
				{y: 30,  label: "Dec"},
		        ]
		      },
		      {
				color: "red",
				showInLegend: true, 
				name: "OutVehicle",
		        dataPoints: [
		        {y: 50, label: "Jan"},
		        {y: 50,  label: "Feb" },
		        {y: 50,  label: "Mar"},
		        {y: 50,  label: "Apr"},
		        {y: 50,  label: "May"},
		        {y: 50, label: "Jun"},
		        {y: 50,  label: "Jul"},
		        {y: 50,  label: "Aug"},
				{y: 50,  label: "Sep"},
				{y: 50,  label: "Oct"},
				{y: 50,  label: "Nov"},
				{y: 50,  label: "Dec"},
		        ]
		      }
		      ]
		    });
		 
		    chart.render();
		  }
	  </script>
	</section><!-- End Wraper -->
