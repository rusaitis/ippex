<%
Session("aemail")	= Request("ApplicanteMail")

'Response.Write Session("aemail")	
'Response.Write aemail2
%>
<html>

<head>
<% ' FP_ASP -- ASP Automatically generated by a Frontpage Component. Do not Edit.
FP_CharSet = "windows-1252"
FP_CodePage = 1252 %>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Plasma Science and Fusion Energy Institute for High School Physics
Teachers Application Form Search</title>
</head>

<body>

<table border="0" cellspacing="0" cellpadding="0" height="1">
  <tr>
    <td width="727" align="center" height="1">
      <font size="5">Reference Letters For:<%=Session("aemail")%></font></td>
  </tr>
  <tr>
    <td width="727" align="center" height="261" valign="top">
  &nbsp;
  <div align="center">
    <center>
    <form BOTID="0" METHOD="POST" ACTION="GetRecLet.asp">
      <table BORDER="0">
        <tr>
          <td><b>ApplicanteMail</b></td>
          <td><input TYPE="TEXT" NAME="ApplicanteMail" VALUE="<%=Request("ApplicanteMail")%>" size="20"></td>
        </tr>
      </table>
      <br>
      <input TYPE="Submit"><input TYPE="Reset"><!--webbot bot="SaveAsASP"
      CLIENTSIDE SuggestedExt="asp" PREVIEW=" " startspan --><!--webbot bot="SaveAsASP" endspan -->
      <p>&nbsp;</p>
    </form>
    <!--webbot bot="DatabaseRegionStart" startspan
    s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-columntypes="3,202,202,202,202,203" s-dataconnection="NUFP"
    b-tableformat="FALSE" b-menuformat="FALSE" s-menuchoice="ID" s-menuvalue="ID"
    b-tableborder="TRUE" b-tableexpand="TRUE" b-tableheader="TRUE"
    b-listlabels="TRUE" b-listseparator="TRUE" i-ListFormat="0"
    b-makeform="TRUE" s-recordsource="RecLetters"
    s-displaycolumns="ID,RecName,ReceMail,ApplicanteMail,RecLetter,ApplicantName"
    s-criteria="[ApplicanteMail] EQ {ApplicanteMail} +" s-order="[ID] +"
    s-sql="SELECT * FROM RecLetters WHERE (ApplicanteMail =  '::ApplicanteMail::') ORDER BY ID ASC"
    b-procedure="FALSE" clientside SuggestedExt="asp"
    s-DefaultFields="ApplicanteMail="
    s-NoRecordsFound="No Reference Letters for this Application were found. If you think this message is an error please email azwicker@pppl.gov"
    i-MaxRecords="25" i-GroupSize="0" BOTID="0"
    u-dblib="../../_fpclass/fpdblib.inc" u-dbrgn1="../../_fpclass/fpdbrgn1.inc"
    u-dbrgn2="../../_fpclass/fpdbrgn2.inc" tag="BODY"
    local_preview="&lt;table border=0 width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;Database Results regions will not preview unless this page is fetched from a Web server using a web browser. The section of the page from here to the end of the Database Results region will repeat once for each record returned by the query.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;"
    preview="&lt;table border=0 width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the start of a Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;" b-WasTableFormat="FALSE" b-ReplaceDatabaseRegion="FALSE" --><!--#include file="../../_fpclass/fpdblib.inc"-->
<%
fp_sQry="SELECT * FROM RecLetters WHERE (ApplicanteMail =  '::ApplicanteMail::') ORDER BY ID ASC"
fp_sDefault="ApplicanteMail="
fp_sNoRecords="No Reference Letters for this Application were found. If you think this message is an error please email azwicker@pppl.gov"
fp_sDataConn="NUFP"
fp_iMaxRecords=25
fp_iCommandType=1
fp_iPageSize=0
fp_fTableFormat=False
fp_fMenuFormat=False
fp_sMenuChoice="ID"
fp_sMenuValue="ID"
fp_iDisplayCols=6
fp_fCustomQuery=False
BOTID=0
fp_iRegion=BOTID
%>
<!--#include file="../../_fpclass/fpdbrgn1.inc"-->
<!--webbot bot="DatabaseRegionStart" endspan -->
    </center>
    <p align="left"><b>ID:</b>&nbsp;<!--webbot bot="DatabaseResultColumn"
    startspan s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-column="ID" b-tableformat="FALSE" b-hasHTML="FALSE" clientside
    local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ID&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
    preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ID&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"ID")%><!--webbot
    bot="DatabaseResultColumn" endspan -->
    </p>
    <p align="left"><b>Name of Reference:</b>&nbsp;<!--webbot bot="DatabaseResultColumn"
    startspan s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-column="RecName" b-tableformat="FALSE" b-hasHTML="FALSE" clientside
    local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;RecName&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
    preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;RecName&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"RecName")%><!--webbot
    bot="DatabaseResultColumn" endspan -->
    </p>
    <p align="left"><b>Email address of reference:</b>&nbsp;<!--webbot bot="DatabaseResultColumn"
    startspan s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-column="ReceMail" b-tableformat="FALSE" b-hasHTML="FALSE" clientside
    local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ReceMail&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
    preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ReceMail&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"ReceMail")%><!--webbot
    bot="DatabaseResultColumn" endspan -->
    </p>
    <p align="left"><b>Applicant's name:</b> <!--webbot
    bot="DatabaseResultColumn" startspan
    s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-column="ApplicantName" b-tableformat="FALSE" b-hasHTML="FALSE" clientside
    local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ApplicantName&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
    preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ApplicantName&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"ApplicantName")%><!--webbot
    bot="DatabaseResultColumn" endspan -->
    </p>
    <p align="left"><b>Applicant's email:</b>&nbsp;<!--webbot
    bot="DatabaseResultColumn" startspan
    s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-column="ApplicanteMail" b-tableformat="FALSE" b-hasHTML="FALSE" clientside
    local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ApplicanteMail&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
    preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ApplicanteMail&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"ApplicanteMail")%><!--webbot
    bot="DatabaseResultColumn" endspan -->
    </p>
    <p align="left"><b>Reference Letter:</b>&nbsp;<!--webbot bot="DatabaseResultColumn"
    startspan s-columnnames="ID,RecName,ReceMail,ApplicantName,ApplicanteMail,RecLetter"
    s-column="RecLetter" b-tableformat="FALSE" b-hasHTML="FALSE" clientside
    local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;RecLetter&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
    preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;RecLetter&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"RecLetter")%><!--webbot
    bot="DatabaseResultColumn" endspan -->
    </p>
    <center>
    <hr>
    <!--webbot bot="DatabaseRegionEnd" startspan b-tableformat="FALSE"
    b-menuformat="FALSE" u-dbrgn2="../../_fpclass/fpdbrgn2.inc" i-groupsize="0"
    clientside tag="BODY"
    local_preview="&lt;table border=0 width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td bgcolor=&quot;#FFFF00&quot; align=&quot;center&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;End of Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;"
    preview="&lt;table border=0 width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the end of a Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;" --><!--#include file="../../_fpclass/fpdbrgn2.inc"-->
<!--webbot bot="DatabaseRegionEnd" endspan -->
    </center>
  </div>
  <p>&nbsp;
    </td>
  </tr>
</table>

  <p>&nbsp;</p>
<p>&nbsp;</p>

</body>

</html>
