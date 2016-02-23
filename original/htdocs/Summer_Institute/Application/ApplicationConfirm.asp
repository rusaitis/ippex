<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta http-equiv="Content-Language" content="en-us">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Plasma Science and Fusion Energy Institute for High School Physics
Teachers APPLICATION FORM COMPLETED</title>

</head>
<%
Name=request.querystring("Name")
eMail=request.querystring("email")
%>
<body>

<P ALIGN=CENTER><img alt="Princeton Plasma Physics Laboratory" src="../title_centered.gif" width="450" height="50"></P>
<%=Name%> <%=eMail%>

<form method="GET" action="eMailRecNotice.asp">


<div align="center">
  <center>
  <table border="1" width="84%" bordercolor="#DDDDDD" cellspacing="0" height="114">
    <tr>
      <td width="100%" bgcolor="#FFFFCC" valign="top" height="19"><font size="4"><b>Recommendations</b></font></td>
    </tr>
    <tr>
      <td width="100%" bgcolor="#EBEBEB" valign="top" height="1">
        <p align="center">Please enter the information for the names of up to
        three people who will be writing your letter of recommendation.</p>
        <p align="center">Name <input type="text" name="Rec1Name" size="20">&nbsp;&nbsp;&nbsp;&nbsp;
        e-mail <input type="text" name="Rec1eMail" size="20"><br>
        <br>
        Name <input type="text" name="Rec2Name" size="20">&nbsp;&nbsp;&nbsp;&nbsp;
        e-mail <input type="text" name="Rec2eMail" size="20"><br>
        <br>
        Name <input type="text" name="Rec3Name" size="20">&nbsp;&nbsp;&nbsp;&nbsp;
        e-mail <input type="text" name="Rec3eMail" size="20"></p>
        <p align="center"><input type="submit" value="Submit" name="Submit"></td>
    </tr>
  </center>
  <center>
  </center>
  <center>
  </center>
    <center>
  </center>
    <center>
  </center>
    <center>
  </table>
  </center>
</div>

</form>

<p align="center">It's Easy!&nbsp; Having trouble? eMail the webmaster <a href="mailto:azwicker@pppl.gov">here</a>.</p>

</body>

</html>
