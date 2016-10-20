<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="door.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<section class="row">
		<section class="col-lg-6">
		<section class="panel">
	    <header class="panel-heading"><s:message code="indoor.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
   		<section class="panel-body pn-form-indoor">
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addID" method="post" novalidate="novalidate">
	        	<section class="form-group">
	                <label for="namedoor" class="col-lg-3 control-label"><s:message code="indoor.name" /></label>
	                <section class="col-lg-8">
	                    <input type="text" name="nameD" class="form-control" id="namedoor" 
	                    	placeholder="<s:message code="indoor.name" />"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="indoor.capacity" /></label>
	                <section class="col-lg-8">
	                    <input type="text" name="capD" placeholder="<s:message code="indoor.capacity" />" class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <section class="col-lg-offset-2 col-lg-10">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnAddID" />
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
    	<header class="panel-heading"><s:message code="indoor.title.data" /></header><!-- End Panel-Heading -->
        <section class="panel-body pn-data-indoor">
        <section class="adv-table">
	        <table class="table table-hover" id="dynamic-table">
		        <thead>
			        <tr>
			            <th><s:message code="indoor.name" /></th>
			            <th><s:message code="indoor.capacity" /></th>
						<th><s:message code="indoor.status" /></th>
						<th><s:message code="label.option" /></th>
						<!-- <th></th> -->
			        </tr>
		        </thead>
		        <tbody class="rowI_data">
			        <c:forEach items="${listIDoor }" var="rows">
						<tr>
							<td style="display: none"><span class="idInDoor" >${rows.idDoor }</span></td>
							<td><span class="nameD">${rows.nameDoor }</span></td>
							<td><span class="capD">${rows.capacity }</span></td>
							<c:set var="status" value="" />
							<c:if test="${rows.status == 0 }"><c:set var="status" value="Processing"/></c:if>
							<c:if test="${rows.status == 1 }"><c:set var="status" value="Active"/></c:if>
							<c:if test="${rows.status == 2 }"><c:set var="status" value="InActive"/></c:if>
							<c:if test="${rows.status == 3 }"><c:set var="status" value="Maintenance"/></c:if>
							<td><span class="statusD">${status }</span></td>
							<td><a href="" class="edit-indoor">Edit</a></td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        <section class="col-lg-6">
		<section class="panel">
	    <header class="panel-heading"><s:message code="outdoor.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
   		<section class="panel-body pn-form-outdoor">
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addOD" method="post">
	        	<section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="outdoor.name" /></label>
	                <section class="col-lg-8">
	                    <input type="text" name="nameD" 
		                    placeholder="<s:message code="outdoor.name" />" 
		                    class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label"><s:message code="outdoor.capacity" /></label>
	                <section class="col-lg-8">
	                    <input type="text" name="capD" 
	                    	placeholder="<s:message code="outdoor.capacity" />" 
	                    	class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <section class="col-lg-offset-2 col-lg-10">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnAddOD" />
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
    	<header class="panel-heading">
        	<s:message code="outdoor.title.data" />
		</header><!-- End Panel-Heading -->
        <section class="panel-body pn-data-outdoor">
        <section class="adv-table">
	        <table class="table table-hover" id="dynamic-table">
		        <thead>
			        <tr>
			            <th><s:message code="outdoor.name" /></th>
			            <th><s:message code="outdoor.capacity" /></th>
						<th><s:message code="outdoor.status" /></th>
						<th><s:message code="label.option" /></th>
			        </tr>
		        </thead>
		        <tbody class="rowO_data">
			        <c:forEach items="${listODoor }" var="rows">
						<tr>
							<td style="display: none"><span class="idOutDoor" >${rows.idDoor }</span></td>
							<td><span class="nameD">${rows.nameDoor }</span></td>
							<td><span class="capD">${rows.capacity }</span></td>
							<c:set var="status" value="" />
							<c:if test="${rows.status == 0 }"><c:set var="status" value="Processing"/></c:if>
							<c:if test="${rows.status == 1 }"><c:set var="status" value="Active"/></c:if>
							<c:if test="${rows.status == 2 }"><c:set var="status" value="InActive"/></c:if>
							<c:if test="${rows.status == 3 }"><c:set var="status" value="Maintenance"/></c:if>
							<td><span class="statusD">${status }</span></td>
							<td><a href="" class="edit-outdoor">Edit</a></td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        </section><!-- End Row -->
	</section><!-- End Wraper -->
