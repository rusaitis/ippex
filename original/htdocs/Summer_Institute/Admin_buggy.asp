

<html>

<head>
<% ' FP_ASP -- ASP Automatically generated by a Frontpage Component. Do not Edit.
FP_CharSet = "windows-1252"
FP_CodePage = 1252 %>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Plasma Science and Fusion Energy Institute for High School Physics
Teachers Application Form Search</title>
</head>

<body>
<%@ LANGUAGE="VBSCRIPT" %>
<% Response.Buffer = True %>
<% STATUS = Request("STATUS") %>
<% PASSWORD = Request("PASSWORD") %>
<% If STATUS = "CHECKEM" Then %>
      <% If PASSWORD = "pcamp2004" THEN %>
            <% Session("PASSWORDACCESS") = "Yes" %>
      <% End If %>
<% End If %>

<% If Session("PASSWORDACCESS") <> "Yes" Then %>

      <form method="POST" action="Admin_buggy.asp">
        <div align="center"><center><p><input type="password" name="PASSWORD" size="10"><br>
          <input type="hidden" value="CHECKEM" Name="STATUS" >
          <input type="submit" value="Login"></p>
          </center></div>
      </form>
     <% Response.End %>
<% End If %>
<!--webbot bot="DatabaseRegionStart" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-columntypes="3,202,202,202,202,202,202,203,203,203,202,203,203,203,203,203,203,203,203,203,203,202,202,202,202,135,203,202,202,202" s-dataconnection="PlasmaCamp" b-tableformat="FALSE" b-menuformat="FALSE" s-menuchoice s-menuvalue b-tableborder="TRUE" b-tableexpand="FALSE" b-tableheader="TRUE" b-listlabels="TRUE" b-listseparator="FALSE" i-listformat="0" b-makeform="TRUE" s-recordsource="TeachersApplication2004" s-displaycolumns="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-criteria s-order s-sql="SELECT * FROM TeachersApplication2004" b-procedure="FALSE" clientside suggestedext="asp" s-defaultfields s-norecordsfound="Please be less specific in your search.  If you think this message is an error please email Scott @ PPPLHelp@BeginYour.com" i-maxrecords="0" i-groupsize="0" botid="0" u-dblib="../../_fpclass/fpdblib.inc" u-dbrgn1="../../_fpclass/fpdbrgn1.inc" u-dbrgn2="../../_fpclass/fpdbrgn2.inc" tag="BODY" preview="&lt;table border=0 width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the start of a Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;" startspan --><!--#include file="../../_fpclass/fpdblib.inc"-->
<% if 0 then %>
<SCRIPT Language="JavaScript">
document.write("<div style='background: yellow; color: black;'>The Database Results component on this page is unable to display database content. The page must have a filename ending in '.asp', and the web must be hosted on a server that supports Active Server Pages.</div>");
</SCRIPT>
<% end if %>
<%
fp_sQry="SELECT * FROM TeachersApplication2004"
fp_sDefault=""
fp_sNoRecords="Please be less specific in your search.  If you think this message is an error please email Scott @ PPPLHelp@BeginYour.com"
fp_sDataConn="PlasmaCamp"
fp_iMaxRecords=0
fp_iCommandType=1
fp_iPageSize=0
fp_fTableFormat=False
fp_fMenuFormat=False
fp_sMenuChoice=""
fp_sMenuValue=""
fp_iDisplayCols=30
fp_fCustomQuery=False
BOTID=0
fp_iRegion=BOTID
%>
<!--#include file="../../_fpclass/fpdbrgn1.inc"-->
<!--webbot bot="DatabaseRegionStart" endspan --><p><b>ID:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="ID" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ID&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"ID")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Name:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Name" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Name&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Name")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Address:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Address" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Address&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Address")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Phone:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Phone" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Phone&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Phone")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>eMail:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="eMail" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;eMail&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"eMail")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>School2Phone:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="School2Phone" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;School2Phone&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"School2Phone")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>PrincipalName:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="PrincipalName" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;PrincipalName&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"PrincipalName")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>CollegesAttended:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="CollegesAttended" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;CollegesAttended&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"CollegesAttended")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>UndergradCourses:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="UndergradCourses" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;UndergradCourses&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"UndergradCourses")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>GradCourses:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="GradCourses" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;GradCourses&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"GradCourses")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>YearsExp:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="YearsExp" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;YearsExp&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"YearsExp")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>TeachingAssign:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="TeachingAssign" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;TeachingAssign&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"TeachingAssign")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>FutureTeachings:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="FutureTeachings" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;FutureTeachings&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"FutureTeachings")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>ProDev:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="ProDev" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ProDev&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"ProDev")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>LeadershipActivities:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="LeadershipActivities" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;LeadershipActivities&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"LeadershipActivities")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Publications:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Publications" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Publications&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Publications")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>TeachingPhilosophy:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="TeachingPhilosophy" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;TeachingPhilosophy&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"TeachingPhilosophy")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>EquiptExperience:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="EquiptExperience" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;EquiptExperience&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"EquiptExperience")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>CompExpierence:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="CompExpierence" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;CompExpierence&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"CompExpierence")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>IntExpierence:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="IntExpierence" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;IntExpierence&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"IntExpierence")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>HowDidYouLearnAboutUs:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="HowDidYouLearnAboutUs" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;HowDidYouLearnAboutUs&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"HowDidYouLearnAboutUs")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Complete:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Complete" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Complete&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Complete")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>City:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="City" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;City&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"City")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>State:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="State" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;State&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"State")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Zip:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Zip" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Zip&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Zip")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Timestamp:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Timestamp" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Timestamp&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Timestamp")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>School2:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="School2" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;School2&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"School2")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Remote_computer_name1:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Remote_computer_name1" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Remote_computer_name1&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Remote_computer_name1")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>AdminNotes:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="AdminNotes" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;AdminNotes&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"AdminNotes")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<p><b>Browser_type1:</b>&nbsp;<!--webbot bot="DatabaseResultColumn" s-columnnames="ID,Name,Address,Phone,eMail,School2Phone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Timestamp,School2,Remote_computer_name1,AdminNotes,Browser_type1" s-column="Browser_type1" b-tableformat="FALSE" b-hashtml="FALSE" b-makelink="FALSE" clientside b-MenuFormat preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Browser_type1&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" startspan --><%=FP_FieldVal(fp_rs,"Browser_type1")%><!--webbot bot="DatabaseResultColumn" endspan --></p>
<!--webbot bot="DatabaseRegionEnd" b-tableformat="FALSE" b-menuformat="FALSE" u-dbrgn2="../../_fpclass/fpdbrgn2.inc" i-groupsize="0" clientside tag="BODY" preview="&lt;table border=0 width=&quot;100%&quot;&gt;&lt;tr&gt;&lt;td bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the end of a Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;" startspan --><!--#include file="../../_fpclass/fpdbrgn2.inc"-->
<!--webbot bot="DatabaseRegionEnd" endspan --><p><nobr></nobr></p>

  </body>

</html>