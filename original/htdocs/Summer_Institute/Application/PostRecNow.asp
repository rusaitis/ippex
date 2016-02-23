<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>PostRecommendationNow</title>
</head>

<body>

<form method="POST" action="PostRecNow.asp" webbot-action="--WEBBOT-SELF--">
  <!--webbot bot="SaveDatabase" SuggestedExt="asp"
  U-ASP-Include-Url="../../../_fpclass/fpdbform.inc"
  S-DataConnection="TeacherApplication" S-RecordSource="Recommendations"
  U-Database-URL="../../fpdb/ApplicationData.mdb"
  U-Confirmation-Url="../application/ThankYouForPostingYourRec.asp"
  S-Form-Fields="RecEmail Recommendaiton ID RecName"
  S-Form-DBFields="email Recommendation StudentID Name" startspan --><input TYPE="hidden" NAME="VTI-GROUP" VALUE="0"><!--#include file="../../../_fpclass/fpdbform.inc"--><!--webbot bot="SaveDatabase" endspan i-checksum="52631" -->
  <p><nobr><!--webbot bot="DatabaseRegionStart" startspan
  s-columnnames="ID,Name,SocialSecurity,Address,Phone,eMail,School,SchoolPhone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Rec1Name,Rec1eMail,Rec2Name,Rec2eMail,Rec3Name,Rec3eMail"
  s-columntypes="3,202,202,202,202,202,202,202,202,203,203,203,3,203,203,203,203,203,203,203,203,203,202,202,202,202,202,202,202,202,202,202,202"
  s-dataconnection="TeacherApplication" b-tableformat="FALSE"
  b-menuformat="TRUE" s-menuchoice="ID" s-menuvalue="ID" b-tableborder="TRUE"
  b-tableexpand="TRUE" b-tableheader="TRUE" b-listlabels="TRUE"
  b-listseparator="TRUE" i-ListFormat="0" b-makeform="FALSE"
  s-recordsource="TeachersApplication2000" s-displaycolumns="ID,Name"
  s-criteria="{ID} EQ {ID} +" s-order="[ID] +"
  s-sql="SELECT * FROM TeachersApplication2000 WHERE (ID =  ::ID::) ORDER BY ID ASC"
  b-procedure="FALSE" clientside SuggestedExt="asp" s-DefaultFields="ID="
  s-NoRecordsFound="No records returned." i-MaxRecords="256" i-GroupSize="0"
  BOTID="0" u-dblib="../../../_fpclass/fpdblib.inc"
  u-dbrgn1="../../../_fpclass/fpdbrgn1.inc" u-dbrgn2="../../../_fpclass/fpdbrgn2.inc"
  local_preview=" &lt;span style=&quot;color: rgb(0,0,0); background-color: rgb(255,255,0)&quot;&gt;Database&lt;/span&gt; "
  preview=" &lt;span style=&quot;color: rgb(0,0,0); background-color: rgb(255,255,0)&quot;&gt;Database&lt;/span&gt; " --><!--#include file="../../../_fpclass/fpdblib.inc"-->
<%
fp_sQry="SELECT * FROM TeachersApplication2000 WHERE (ID =  ::ID::) ORDER BY ID ASC"
fp_sDefault="ID="
fp_sNoRecords="No records returned."
fp_sDataConn="TeacherApplication"
fp_iMaxRecords=256
fp_iCommandType=1
fp_iPageSize=0
fp_fTableFormat=False
fp_fMenuFormat=True
fp_sMenuChoice="ID"
fp_sMenuValue="ID"
fp_iDisplayCols=2
fp_fCustomQuery=False
BOTID=0
fp_iRegion=BOTID
%>
<!--webbot bot="DatabaseRegionStart" endspan -->
  <select NAME="ID" SIZE="1">
    <!--webbot bot="AspInclude" startspan CLIENTSIDE
    U-INCFILE="../../../_fpclass/fpdbrgn1.inc" --><!--#include file="../../../_fpclass/fpdbrgn1.inc"--><!--webbot
    bot="AspInclude" endspan -->
    <option><%=FP_FieldHTML(fp_rs,"ID")%></option>
    <!--webbot bot="AspInclude" startspan CLIENTSIDE
    U-INCFILE="../../../_fpclass/fpdbrgn2.inc" --><!--#include file="../../../_fpclass/fpdbrgn2.inc"--><!--webbot
    bot="AspInclude" endspan -->
  </select><!--webbot bot="DatabaseRegionEnd" startspan b-tableformat="FALSE"
  b-menuformat="TRUE" u-dbrgn2="../../../_fpclass/fpdbrgn2.inc" i-groupsize="0"
  clientside
  local_preview=" &lt;span style=&quot;color: rgb(0,0,0); background-color: rgb(255,255,0)&quot;&gt;Results&lt;/span&gt; "
  preview=" &lt;span style=&quot;color: rgb(0,0,0); background-color: rgb(255,255,0)&quot;&gt;Results&lt;/span&gt; " --><!--webbot
  bot="DatabaseRegionEnd" endspan -->
  </nobr></p>
  <p>Your Name: <input type="text" name="RecName" size="20"></p>
  <p>Your eMail: <input type="text" name="RecEmail" size="20"></p>
  <p>Recommendation: <br>
  <textarea rows="16" name="Recommendaiton" cols="54"></textarea></p>
  <p><input type="submit" value="Submit" name="B1"><input type="reset" value="Reset" name="B2"></p>
</form>
<p>&nbsp;</p>

</body>

</html>
