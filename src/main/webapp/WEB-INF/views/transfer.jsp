<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<section class="page-heading">
		    <h3><s:message code="transfer.title" /></h3>
		</section><!-- End Page-Heading -->
		<section class="wrapper">
		<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        	<div id="dynamic-table_length" class="dataTables_length">
				<label>Date:
					<%-- <input class="default-date-picker date-picker" 
						size="30" type="text" value=""
						readonly="readonly"> 
					<span class="input-group-btn add-on">
						<button class="btn btn-primary" type="button"><i class="fa fa-calendar"></i></button>
					</span> --%>
                    <input type="text" value="${currentDate }" size="30" 
                    		class="default-date-picker date-picker">
                   	<span class="btn-primary"><i class="fa fa-calendar"></i></span>
				</label>
			</div>
		</section>
		<section class="row">
		<section class="col-sm-6">
		<section class="panel">
	    <header class="panel-heading"><s:message code="transfer.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>	    
	    </header><!-- End Header -->
   		<section class="panel-body">
	        <form role="form" class="form-horizontal adminex-form" id="addTF" method="post">
	            <section class="form-group">
	                <label class="col-lg-3 control-label">InVehicle</label>
	                <section class="col-lg-7">
	                	<section class="data-select-in-vehicle">
		                    <select class="s-vehicle" name="idInVehicle">
		                    	<c:forEach items="${listIV }" var="rows">
		                    		<option value="${rows.idInVehicle }">${rows.vehicleCode }</option>
		                    	</c:forEach>
			              	</select>
		              	</section>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label">OutVehicle</label>
	                <section class="col-lg-7" >
	                	<section class="data-select-out-vehicle">
		                    <select class="s-vehicle" name="idOutVehicle">
		                    	<c:forEach items="${listOV }" var="rows">
		                    		<option value="${rows.idOutVehicle }">${rows.vehicleCode }</option>
		                    	</c:forEach>
			              	</select>
		              	</section>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-3 control-label">Transfer Time</label>
	                <section class="col-lg-7">
	                    <input type="text" name="tranferTime" placeholder="Transfer Time" class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <section class="col-lg-offset-2 col-lg-10">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnTF" disabled="disabled"/>
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        <section class="col-sm-6">
        <section class="panel">
        <header class="panel-heading"><s:message code="transfer.title.data" />
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
						<th><s:message code="invehicle.code" /></th>
						<th><s:message code="outvehicle.code" /></th>
						<th><s:message code="label.transfer"/></th>
						<!-- <th></th> -->
			        </tr>
		        </thead>
		        <tbody class="row_data">
			        <c:forEach items="${listPT }" var="rows">
						<tr>
							<td>${rows.iVehicle.vehicleCode }</td>
							<td>${rows.oVehicle.vehicleCode }</td>
							<td>${rows.transfer }</td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
		</section><!-- End Wrapper -->
	