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
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <td><s:message code="indoor.name" /></td>
			            <td><s:message code="indoor.capacity" /></td>
						<td><s:message code="indoor.status" /></td>
						<td colspan="3" width="15%"><s:message code="label.option" /></td>
			        </tr>
		        </thead>
		        <tbody class="rowI_data">
			        <c:forEach items="${listIDoor }" var="rows">
						<tr>
							<td style="display: none"><span class="idInDoor" >${rows.idDoor }</span></td>
							<td><span class="nameD">${rows.nameDoor }</span></td>
							<td><span class="capD">${rows.capacity }</span></td>
							<td><span class="statusD">
									<c:if test="${rows.status == 0 }"><s:message code="inactive.status"/></c:if>
									<c:if test="${rows.status == 1 }"><s:message code="active.status"/></c:if>
								</span>
							</td>
							<td><span class="edit-indoor option" 
										data-bind="${rows.idDoor }">
									<i class="fa fa-pencil-square-o"></i>
								</span>
							</td>
							<td><span class="del-indoor option" 
										data-bind="${rows.idDoor }">
									<i class="fa fa-trash-o"></i>
								</span>
							</td>
							<td><span class="setup-cost-indoor option" 
									data-bind="${rows.idDoor }">
									<i class="fa fa-cog "></i>
								</span>
							</td>
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
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <td><s:message code="outdoor.name" /></td>
			            <td><s:message code="outdoor.capacity" /></td>
						<td><s:message code="outdoor.status" /></td>
						<td colspan="3" width="15%"><s:message code="label.option" /></td>
			        </tr>
		        </thead>
		        <tbody class="rowO_data">
			        <c:forEach items="${listODoor }" var="rows">
						<tr>
							<td style="display: none"><span class="idOutDoor" >${rows.idDoor }</span></td>
							<td><span class="nameD">${rows.nameDoor }</span></td>
							<td><span class="capD">${rows.capacity }</span></td>
							<td><span class="statusD">
									<c:if test="${rows.status == 0 }"><s:message code="inactive.status"/></c:if>
									<c:if test="${rows.status == 1 }"><s:message code="active.status"/></c:if>
								</span>
							</td>
							<td><span class="edit-outdoor option" 
										data-bind="${rows.idDoor }">
									<i class="fa fa-pencil-square-o"></i>
								</span>
							</td>
							<td><span class="del-outdoor option" 
										data-bind="${rows.idDoor }">
									<i class="fa fa-trash-o"></i>
								</span>
							</td>
							<td><span class="setup-cost-outdoor option" 
										data-bind="${rows.idDoor }">
									<i class="fa fa-cog"></i>
								</span>
							</td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        </section><!-- End Row -->
        <section class="dialog-setup-cost">
        	<section class="row">
			<section class="col-sm-12">
			<section class="panel">
			<section class="panel-body">
			<section class="adv-table">
				<table class="display table table-bordered table-striped" id="tbl-setup-cost">
					<thead>
						<tr>
							<td><s:message code="indoor.label" /></td>
				            <td><s:message code="outdoor.label" /></td>
							<td><s:message code="cost.door" /></td>
							<td colspan="2"><s:message code="label.option" /></td>	
						</tr>
					</thead>
					<tbody class="rowSetCost">
					</tbody>
				</table>		
			</section><!-- End Adv-Table -->
			</section><!-- End Panel-Body -->
			</section><!-- End Panel -->
			</section><!-- End Col-Sm-12 -->
			</section><!-- End Row -->
        </section><!-- End Dialog -->
	</section><!-- End Wraper -->
