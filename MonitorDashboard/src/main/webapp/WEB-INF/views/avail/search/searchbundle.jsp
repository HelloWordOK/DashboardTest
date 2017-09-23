<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Bundle</label>
		<div class="col-sm-7">
			<select class="form-control searchselect" id="bundle" onchange="ajaxService();">
				<c:forEach items="${bundleList}" var="bundle">
					<option value="${bundle.bundleId}">${bundle.bundle}</option>
				</c:forEach>
			</select>
		</div>
	</div>
</div>