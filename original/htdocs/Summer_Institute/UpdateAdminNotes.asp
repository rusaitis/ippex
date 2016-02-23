<%
Session("ID")	= Request.Form("ID")
Session("NewAdmin")	= Request.Form("NewAdmin")
%>

<%	Dim loConn		' Database Connection
	Dim loRs		' Database Recordset
	Dim lsSQL		' Structured Query Language
	
	' Build SQL statement to retrieve the Users ID	
	

	adminSQL = "UPDATE TeachersApplication2002 SET TeachersApplication2002.AdminNotes='" & Session("NewAdmin") & "'" _
			&  "WHERE TeachersApplication2002.ID=" & Session("ID")

' response.write adminsql

	strCN = "driver={Microsoft Access Driver (*.mdb)};" & "dbq=" & Server.MapPath("/fpdb/ApplicationData.mdb")

   ' open the database connection
   Set objCN = Server.CreateObject("ADODB.Connection")
   objCN.Open strCN   

   ' open the recordset
   Set objRS = objCN.Execute(adminSQL)
  'Set loConn = Server.CreateObject("ADODB.Connection")
  'loConn.open "TeacherApplication"
  'Set loRs = Server.CreateObject("ADODB.Recordset")
  'Set loRs = loConn.Execute(adminSQL)

	session("redirectTo") = "http://ippex.pppl.gov/summer_institute/Application/record.asp?ID=" & session("ID") 
	Response.Redirect(session("redirectTo"))

%>