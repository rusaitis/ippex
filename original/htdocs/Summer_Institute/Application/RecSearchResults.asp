<html>

<head>
<% ' FP_ASP -- ASP Automatically generated by a Frontpage Component. Do not Edit.
FP_CharSet = "windows-1252"
FP_CodePage = 1252 %>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Name</title>
</head>

<body>

<table width="100%" border="1">
  <thead>
    <tr>
      <td><b>Name</b></td>
      <td><b>ID</b></td>
    </tr>
  </thead>
  <tbody>
    <!--webbot bot="DatabaseRegionStart" startspan
    s-columnnames="ID,Name,SocialSecurity,Address,Phone,eMail,School,SchoolPhone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Rec1Name,Rec1eMail,Rec2Name,Rec2eMail,Rec3Name,Rec3eMail"
    s-columntypes="3,202,202,202,202,202,202,202,202,203,203,203,3,203,203,203,203,203,203,203,203,203,202,202,202,202,202,202,202,202,202,202,202"
    s-dataconnection="TeacherApplication" b-tableformat="TRUE"
    b-menuformat="FALSE" s-menuchoice s-menuvalue b-tableborder="TRUE"
    b-tableexpand="TRUE" b-tableheader="TRUE" b-listlabels="TRUE"
    b-listseparator="TRUE" i-ListFormat="0" b-makeform="FALSE"
    s-recordsource="TeachersApplication2000" s-displaycolumns="Name,ID"
    s-criteria="[Name] EQ { } +" s-order
    s-sql="SELECT * FROM TeachersApplication2000 WHERE (Name =  ':: ::')"
    b-procedure="FALSE" clientside SuggestedExt="asp" s-DefaultFields=" ="
    s-NoRecordsFound="No student found with recommendation due.  Please Go Back and try again."
    i-MaxRecords="256" i-GroupSize="5" BOTID="0"
    u-dblib="../../../_fpclass/fpdblib.inc" u-dbrgn1="../../../_fpclass/fpdbrgn1.inc"
    u-dbrgn2="../../../_fpclass/fpdbrgn2.inc" tag="TBODY"
    local_preview="&lt;tr&gt;&lt;td colspan=64 bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot; width=&quot;100%&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;Database Results regions will not preview unless this page is fetched from a Web server with a web browser. The following table row will repeat once for every record returned by the query.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;"
    preview="&lt;tr&gt;&lt;td colspan=64 bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot; width=&quot;100%&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the start of a Database Results region. The page must be fetched from a web server with a web browser to display correctly; the current web is stored on your local disk or network.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;" --><!--#include file="../../../_fpclass/fpdblib.inc"-->
<%
fp_sQry="SELECT * FROM TeachersApplication2000 WHERE (Name =  ':: ::')"
fp_sDefault=" ="
fp_sNoRecords="<tr><td colspan=2 align=left width=""100%"">No student found with recommendation due.  Please Go Back and try again.</td></tr>"
fp_sDataConn="TeacherApplication"
fp_iMaxRecords=256
fp_iCommandType=1
fp_iPageSize=5
fp_fTableFormat=True
fp_fMenuFormat=False
fp_sMenuChoice=""
fp_sMenuValue=""
fp_iDisplayCols=2
fp_fCustomQuery=False
BOTID=0
fp_iRegion=BOTID
%>
<!--#include file="../../../_fpclass/fpdbrgn1.inc"-->
<!--webbot bot="DatabaseRegionStart" endspan -->
    <tr>
      <td><a href="PostRecNow.asp?ID=<%=FP_FieldURL(fp_rs,"ID")%>"><!--webbot
        bot="DatabaseResultColumn" startspan
        s-columnnames="ID,Name,SocialSecurity,Address,Phone,eMail,School,SchoolPhone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Rec1Name,Rec1eMail"
        s-column="Name" b-tableformat="TRUE" b-hasHTML="FALSE" clientside
        local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Name&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
        preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;Name&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"Name")%><!--webbot
        bot="DatabaseResultColumn" endspan -->
        </a></td>
      <td><!--webbot bot="DatabaseResultColumn" startspan
        s-columnnames="ID,Name,SocialSecurity,Address,Phone,eMail,School,SchoolPhone,PrincipalName,CollegesAttended,UndergradCourses,GradCourses,YearsExp,TeachingAssign,FutureTeachings,ProDev,LeadershipActivities,Publications,TeachingPhilosophy,EquiptExperience,CompExpierence,IntExpierence,HowDidYouLearnAboutUs,Complete,City,State,Zip,Rec1Name,Rec1eMail,Rec2Name,Rec2eMail,Rec3Name,Rec3eMail"
        s-column="ID" b-tableformat="TRUE" b-hasHTML="FALSE" clientside
        local_preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ID&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;"
        preview="&lt;font size=&quot;-1&quot;&gt;&amp;lt;&amp;lt;&lt;/font&gt;ID&lt;font size=&quot;-1&quot;&gt;&amp;gt;&amp;gt;&lt;/font&gt;" --><%=FP_FieldVal(fp_rs,"ID")%><!--webbot
        bot="DatabaseResultColumn" endspan -->
      </td>
    </tr>
    <!--webbot bot="DatabaseRegionEnd" startspan b-tableformat="TRUE"
    b-menuformat="FALSE" u-dbrgn2="../../../_fpclass/fpdbrgn2.inc" i-groupsize="5"
    clientside tag="TBODY"
    local_preview="&lt;tr&gt;&lt;td colspan=64 bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot; width=&quot;100%&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the end of a Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;TR&gt;&lt;TD ALIGN=LEFT VALIGN=MIDDLE COLSPAN=64&gt;&lt;FORM&gt;&lt;NOBR&gt;&lt;INPUT TYPE=Button VALUE=&quot;  |&lt;  &quot;&gt;&lt;INPUT TYPE=Button VALUE=&quot;   &lt;  &quot;&gt;&lt;INPUT TYPE=Button VALUE=&quot;  &gt;   &quot;&gt;&lt;INPUT TYPE=Button VALUE=&quot;  &gt;|  &quot;&gt;  [1/5]&lt;/NOBR&gt;&lt;/FORM&gt;&lt;/td&gt;&lt;/tr&gt;"
    preview="&lt;tr&gt;&lt;td colspan=64 bgcolor=&quot;#FFFF00&quot; align=&quot;left&quot; width=&quot;100%&quot;&gt;&lt;font color=&quot;#000000&quot;&gt;This is the end of a Database Results region.&lt;/font&gt;&lt;/td&gt;&lt;/tr&gt;&lt;TR&gt;&lt;TD ALIGN=LEFT VALIGN=MIDDLE COLSPAN=64&gt;&lt;NOBR&gt;&lt;INPUT TYPE=Button VALUE=&quot;  |&lt;  &quot;&gt;&lt;INPUT TYPE=Button VALUE=&quot;   &lt;  &quot;&gt;&lt;INPUT TYPE=Button VALUE=&quot;  &gt;   &quot;&gt;&lt;INPUT TYPE=Button VALUE=&quot;  &gt;|  &quot;&gt;  [1/5]&lt;/NOBR&gt;&lt;BR&gt;&lt;/td&gt;&lt;/tr&gt;" --><!--#include file="../../../_fpclass/fpdbrgn2.inc"-->
<!--webbot bot="DatabaseRegionEnd" endspan -->
  </tbody>
</table>

</body>

</html>
