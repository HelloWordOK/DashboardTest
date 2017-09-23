<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="col-sm-12">
	<div class="col-sm-5">
		<label class="control-label">Mail Template</label>
	</div>
	<div class="col-sm-7">
		<select class="form-control searchselect" id="template" name="templateId">
			<c:forEach items="${templateList}" var="mail">
				<option value="${mail.tempId}">${mail.tempName}</option>
			</c:forEach>
		</select>
	</div>
</div>