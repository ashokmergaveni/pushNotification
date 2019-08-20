<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>
<div class="container">
	<form:form method="post" action="/warden-insert" modelAttribute="todo">
		<form:hidden path="eventId" />
		<fieldset class="form-group">
			<form:label path="eventId">Warden Id</form:label>
			<form:input path="eventId" type="text" class="form-control"
				required="required" />
			<form:errors path="eventId" cssClass="text-warning" />
		</fieldset>
		<fieldset class="form-group">
			<form:label path="venue">venue</form:label>
			<form:input path="venue" type="text" class="form-control"
				required="required" />
			<form:errors path="venue" cssClass="text-warning" />
		</fieldset>

		<fieldset class="form-group">
			<form:label path="name">name</form:label>
			<form:input path="name" type="text" class="form-control"
				required="required" />
			<form:errors path="name" cssClass="text-warning" />
		</fieldset>
		<fieldset class="form-group">
			<form:label path="alert_type">floor</form:label>
			<form:input path="alert_type" type="text" class="form-control"
				required="required" />
			<form:errors path="alert_type" cssClass="text-warning" />
		</fieldset>
		<fieldset class="form-group">
			<form:label path="description">Muster location</form:label>
			<form:input path="description" type="text" class="form-control"
				required="required" />
			<form:errors path="description" cssClass="text-warning" />
		</fieldset>

		<button type="submit" style="width:95px;" class="btn btn-success">Add</button>
	</form:form>
</div>
<%@ include file="common/footer.jspf" %>