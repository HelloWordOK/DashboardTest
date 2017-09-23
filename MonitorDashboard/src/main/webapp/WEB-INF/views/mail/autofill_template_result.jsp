<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">To</label>
		<div class="col-sm-9">
			 <input type="text" class="form-control" placeholder="Use comma(,) to split multiple address" aria-describedby="sizing-addon2" id="mail_to" value="${templateContent.mailTo}">
		</div>
	</div>
</div>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Cc</label>
		<div class="col-sm-9">
			 <input type="text" class="form-control" placeholder="Use comma(,) to split multiple address" aria-describedby="sizing-addon2" id="mail_cc" value="${templateContent.mailCc}">
		</div>
	</div>
</div>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Subject</label>
		<div class="col-sm-9">
			 <input type="text" class="form-control" aria-describedby="sizing-addon2" id="mail_subject" placeholder="Input mail subject here..." value="${templateContent.mailSubject}">
		</div>
	</div>
</div>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Content</label>
		<div class="col-sm-9">
			<textarea class="form-control" placeholder="Input mail content here..." id="mail_content" rows=8>${templateContent.mailContent}</textarea>
		</div>
	</div>
</div>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Timer</label>
		<div class="col-sm-7">
			<input type="text" class="form-control" id="mail_timer" placeholder="E.g.:H H(0-7) * * *" />
		</div>
	</div>
</div>
