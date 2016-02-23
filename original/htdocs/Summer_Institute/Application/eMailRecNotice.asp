<html>
<head>
<META HTTP-EQUIV="REFRESH" CONTENT="5; URL=FinalThankYou.asp" ;>
<title>Please Wait</title>
</head>

<body>

<%
Set Mail = Server.CreateObject("Persits.MailSender")
Mail.Host = "mail.pppl.gov" ' Specify a valid SMTP server
Mail.From = "azwicker@pppl.gov" ' Specify sender's address
Mail.FromName = "Plasma Camp Application" ' Specify sender's name

Mail.AddAddress "azwicker@pppl.gov", "Andrew Post-Zwicker"

Mail.Subject = "Plasma Camp Application"
Mail.Body = "A plasma camp application has been submitted"

On Error Resume Next
Mail.Send
If Err <> 0 Then
   Response.Write "Error encountered: " & Err.Description
End If
%> 

<p align="center">&nbsp;</p>

<p align="center">&nbsp;</p>

<p align="center">&nbsp;</p>

<p align="center"><b><font face="Arial" color="#000000" size="5">Please wait as 
we submit your application...</font></b></p>

</body>

</html>