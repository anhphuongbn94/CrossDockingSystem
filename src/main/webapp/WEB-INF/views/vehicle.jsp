<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<section class="page-heading">
		    <h3><s:message code="vehicle.title" /></h3>
		</section><!-- End Page-Heading -->
		<section class="wrapper">
			<section class="row">
		<section class="col-sm-10">
		<section class="panel">
	    <header class="panel-heading"><s:message code="transfer.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>	    
	    </header><!-- End Header -->
   		<section class="panel-body"> 
	        <form role="form" class="form-horizontal adminex-form" id="addV" method="post">
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.code" /></label>
	                <section class="col-lg-7">
	                    <input type="text" name="vehicleCode" placeholder="<s:message code="vehicle.code" />" class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.type" /></label>
	                <section class="col-lg-7" >
	                    <input type="text" name="vehicleType" placeholder="<s:message code="vehicle.type" />" class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.year" /></label>
	                <section class="col-lg-7" >
	                    <input type="text" name="vehicleYear" placeholder="<s:message code="vehicle.year" />" class="form-control" value="1990"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.make" /></label>
	                <section class="col-lg-7" >
	                    <input type="text" name="vehicleMake" placeholder="<s:message code="vehicle.make" />" class="form-control" value="0"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.weight" /></label>
	                <section class="col-lg-7" >
	                    <input type="text" name="vehicleWeight" placeholder="<s:message code="vehicle.weight" />" class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.trailerNum" /></label>
	                <section class="col-lg-7" >
	                    <input type="text" name="vehicleTrailerNum" placeholder="<s:message code="vehicle.trailerNum" />" class="form-control" value="0"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="vehicle.des" /></label>
	                <section class="col-lg-7" >
	                    <input type="text" name="vehicleDes" placeholder="<s:message code="vehicle.des" />" class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <section class="col-lg-offset-2 col-lg-10">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnV" disabled="disabled"/>
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        <section class="col-sm-12">
        <section class="panel">
        <header class="panel-heading"><s:message code="vehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
                <!-- <a href="javascript:;" class="fa fa-times"></a>  -->
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
	       <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
						<th><s:message code="vehicle.code" /></th>
						<th><s:message code="vehicle.type" /></th>
						<th><s:message code="vehicle.year"/></th>
						<th><s:message code="vehicle.make"/></th>
						<th><s:message code="vehicle.weight"/></th>
						<th><s:message code="vehicle.trailerNum"/></th>
						<th><s:message code="vehicle.des"/></th>
			        </tr>
		        </thead>
		        <tbody class="row_data">
			        <c:forEach items="${pVehicle }" var="rows">
						<tr>
							<td>${rows.vehicleCode }</td>
							<td>${rows.vehicleType }</td>
							<td>${rows.vehicleYear }</td>
							<td>${rows.vehicleMake }</td>
							<td>${rows.vehicleWeight }</td>
							<td>${rows.vehicleTrailerNum }</td>
							<td>${rows.vehicleDes }</td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <section class="dataTables_paginate paging_bootstrap pagination">
				<ul class="pageV">
					<c:forEach begin="1" end="${numPage }" var="j">
						<li class="page_index" data-index="${j }"><a>${j }</a></li>
					</c:forEach>
				</ul>
			</section><!-- End Pagination -->
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
		</section><!-- End Wrapper -->
