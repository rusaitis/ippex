<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>New Page 1</title>
</head>

<body>
<%
Session("ID")	= Request.Form("ID")
Session("NewAdmin")	= Request.Form("NewAdmin")
%>

<%	Dim loConn		' Database Connection
	Dim loRs		' Database Recordset
	Dim lsSQL		' Structured Query Language
	
	' Build SQL statement to retrieve the Users ID	
	

	adminSQL = "UPDATE TeachersApplication2000 SET TeachersApplication2000.AdminNotes='" & Session("NewAdmin") _
			&  "' WHERE Offers.ID=" & Session("ID")


	'response.write lssql
	' Open a database connection

	' WARNING:
	'	The connection Open method needs to be changed so that it will
	'	Connect to your own database.  This is just a sample.

	Set loConn = Server.CreateObject("ADODB.Connection")
	loConn.open "SummerInstituteForm"
	Set loRs = Server.CreateObject("ADODB.Recordset")
		 'Response.Write "After Open2 "&lsSQL
	Set loRs = loConn.Execute(adminSQL)

	session("redirectTo") = "http://ippex.pppl.gov/summer_institute/Application/record.asp?ID=" & session("ID") 
	Response.Redirect(session("redirectTo"))

%>
</body>

</html>
