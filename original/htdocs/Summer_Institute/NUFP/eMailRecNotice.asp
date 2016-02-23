<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<META HTTP-EQUIV="REFRESH" CONTENT = "5; URL=thanks.html">
<title>Processing Application!</title>

</head>
<%

Session("Rec1Name")	= Request.Form("Rec1Name")
'Response.Write Session("Rec1Name")
Session("Rec1eMail")	= Request.Form("Rec1eMail")
'Response.Write Session("Rec1eMail")
Session("Rec2Name")	= Request.Form("Rec2Name")
'Response.Write Session("Rec2Name")
Session("Rec2eMail")	= Request.Form("Rec2eMail")
'Response.Write Session("Rec2eMail")
Session("DueDate")	= Request.Form("DueDate")
Session("Name")	= Request.Form("Name")
'Response.Write Session("Name")
Session("eMail")	= Request.Form("eMail")

%> 

<%
Set Mail = Server.CreateObject("Persits.MailSender")
Mail.Host = "odyssey.pppl.gov" ' Specify a valid SMTP server
Mail.From = "jmorgan@pppl.gov" ' Specify sender's address
Mail.FromName = "NUF Program in Plasma Physics" ' Specify sender's name

Mail.AddAddress "jmorgan@pppl.gov"
Mail.AddAddress Session("Rec1eMail")
Mail.AddAddress Session("Rec2eMail")
Mail.AddReplyTo "jmorgan@pppl.gov"

Mail.Subject = "National Undergraduate Fellowship Program"
Mail.Body = "Dear Reference:" & Chr(13) & Chr(10) & _
     ""& Chr(13) & Chr(10) & _
"Recently " & Session("Name") & " filled out an application for the National Undergraduate Fellowship Program in Plasma Physics and Fusion Energy Sciences. Your name was listed as a reference.  This is a highly selective program that brings some of the best students in the country together to perform research at one of our participating universities or national laboratories.  Your letter is extremely important.  Please visit: http://ippex.pppl.gov/WriteRec.asp?email=" & Session("eMail") & " and follow the instructions listed by February 18, 2005." & Chr(13) & Chr(10) & _ 
 ""& Chr(13) & Chr(10) & _
"Please do not hesitate to contact me with any questions."& Chr(13) & Chr(10) & _
 ""& Chr(13) & Chr(10) & _
 ""& Chr(13) & Chr(10) & _
"Sincerely yours,"& Chr(13) & Chr(10) & _
"James Morgan, NUF Program Administrator"& Chr(13) & Chr(10) & _
"609-243-2116"
 On Error Resume Next
Mail.Send
If Err <> 0 Then
   Response.Write "Error encountered: " & Err.Description
End If
%> 




<body>

<p align="center">&nbsp;</p>
<p align="center"><font face="Arial" color="#000000" size="1"><a href="http://www.animfactory.com/af_dividers_animals_variant_page_dog_div.html"><br>
</a>Application being processed.<br>
Please wait.</font>
</p>


</body>

</html>