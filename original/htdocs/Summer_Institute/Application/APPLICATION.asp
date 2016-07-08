<%' FP_ASP ASP Automatically generated by a Frontpage Component. Do not Edit.

On Error Resume Next
Session("FP_OldCodePage") = Session.CodePage
Session("FP_OldLCID") = Session.LCID
Session.CodePage = 1252
Err.Clear

strErrorUrl = ""

If Request.ServerVariables("REQUEST_METHOD") = "POST" Then
If Request.Form("VTI-GROUP") = "0" Then
	Err.Clear

	Set fp_conn =  Server.CreateObject("ADODB.Connection")
	FP_DumpError strErrorUrl, "Cannot create connection"

	Set fp_rs = Server.CreateObject("ADODB.Recordset")
	FP_DumpError strErrorUrl, "Cannot create record set"

	fp_conn.Open Application("PlasmaCamp_ConnectionString")
	FP_DumpError strErrorUrl, "Cannot open database"

	fp_rs.Open "TeachersApplication2004", fp_conn, 1, 3, 2 ' adOpenKeySet, adLockOptimistic, adCmdTable
	FP_DumpError strErrorUrl, "Cannot open record set"

	fp_rs.AddNew
	FP_DumpError strErrorUrl, "Cannot add new record set to the database"
	Dim arFormFields0(25)
	Dim arFormDBFields0(25)
	Dim arFormValues0(25)

	arFormFields0(0) = "CollegesAttended"
	arFormDBFields0(0) = "CollegesAttended"
	arFormValues0(0) = Request("CollegesAttended")
	arFormFields0(1) = "School"
	arFormDBFields0(1) = "School2"
	arFormValues0(1) = Request("School")
	arFormFields0(2) = "EquiptExperience"
	arFormDBFields0(2) = "EquiptExperience"
	arFormValues0(2) = Request("EquiptExperience")
	arFormFields0(3) = "CompExpierence"
	arFormDBFields0(3) = "CompExpierence"
	arFormValues0(3) = Request("CompExpierence")
	arFormFields0(4) = "HowLearn"
	arFormDBFields0(4) = "HowDidYouLearnAboutUs"
	arFormValues0(4) = Request("HowLearn")
	arFormFields0(5) = "GradCourses"
	arFormDBFields0(5) = "GradCourses"
	arFormValues0(5) = Request("GradCourses")
	arFormFields0(6) = "TeachingAssign"
	arFormDBFields0(6) = "TeachingAssign"
	arFormValues0(6) = Request("TeachingAssign")
	arFormFields0(7) = "TeachingPhilosophy"
	arFormDBFields0(7) = "TeachingPhilosophy"
	arFormValues0(7) = Request("TeachingPhilosophy")
	arFormFields0(8) = "Complete"
	arFormDBFields0(8) = "Complete"
	arFormValues0(8) = Request("Complete")
	arFormFields0(9) = "SchoolPhone"
	arFormDBFields0(9) = "School2Phone"
	arFormValues0(9) = Request("SchoolPhone")
	arFormFields0(10) = "UndergradCourses"
	arFormDBFields0(10) = "UndergradCourses"
	arFormValues0(10) = Request("UndergradCourses")
	arFormFields0(11) = "email"
	arFormDBFields0(11) = "eMail"
	arFormValues0(11) = Request("email")
	arFormFields0(12) = "phone"
	arFormDBFields0(12) = "Phone"
	arFormValues0(12) = Request("phone")
	arFormFields0(13) = "PrincipalName"
	arFormDBFields0(13) = "PrincipalName"
	arFormValues0(13) = Request("PrincipalName")
	arFormFields0(14) = "ProDev"
	arFormDBFields0(14) = "ProDev"
	arFormValues0(14) = Request("ProDev")
	arFormFields0(15) = "IntExpierence"
	arFormDBFields0(15) = "IntExpierence"
	arFormValues0(15) = Request("IntExpierence")
	arFormFields0(16) = "City"
	arFormDBFields0(16) = "City"
	arFormValues0(16) = Request("City")
	arFormFields0(17) = "FutureTeachings"
	arFormDBFields0(17) = "FutureTeachings"
	arFormValues0(17) = Request("FutureTeachings")
	arFormFields0(18) = "Zip"
	arFormDBFields0(18) = "Zip"
	arFormValues0(18) = Request("Zip")
	arFormFields0(19) = "LeadershipActivities"
	arFormDBFields0(19) = "LeadershipActivities"
	arFormValues0(19) = Request("LeadershipActivities")
	arFormFields0(20) = "name"
	arFormDBFields0(20) = "Name"
	arFormValues0(20) = Request("name")
	arFormFields0(21) = "YearsExp"
	arFormDBFields0(21) = "YearsExp"
	arFormValues0(21) = Request("YearsExp")
	arFormFields0(22) = "Publications"
	arFormDBFields0(22) = "Publications"
	arFormValues0(22) = Request("Publications")
	arFormFields0(23) = "Address"
	arFormDBFields0(23) = "Address"
	arFormValues0(23) = Request("Address")
	arFormFields0(24) = "State"
	arFormDBFields0(24) = "State"
	arFormValues0(24) = Request("State")

	FP_SaveFormFields fp_rs, arFormFields0, arFormDBFields0

	If Request.ServerVariables("REMOTE_HOST") <> "" Then
		FP_SaveFieldToDB fp_rs, Request.ServerVariables("REMOTE_HOST"), "Remote_computer_name1"
	End If
	If Request.ServerVariables("HTTP_USER_AGENT") <> "" Then
		FP_SaveFieldToDB fp_rs, Request.ServerVariables("HTTP_USER_AGENT"), "Browser_type1"
	End If
	FP_SaveFieldToDB fp_rs, Now, "Timestamp"

	fp_rs.Update
	FP_DumpError strErrorUrl, "Cannot update the database"

	fp_rs.Close
	fp_conn.Close

	Session("FP_SavedFields")=arFormFields0
	Session("FP_SavedValues")=arFormValues0
	Session.CodePage = Session("FP_OldCodePage")
	Session.LCID = Session("FP_OldLCID")
	Response.Redirect "eMailRecNotice.asp"

End If
End If

Session.CodePage = Session("FP_OldCodePage")
Session.LCID = Session("FP_OldLCID")

%>
<HTML>
<HEAD>
  <META NAME="GENERATOR" CONTENT="Microsoft FrontPage 5.0">
  <TITLE>Application form for Plasma Science Fusion Energy Institute</TITLE>
</HEAD>
<BODY>

<P ALIGN=CENTER><img alt="Princeton Plasma Physics Laboratory" src="title_centered.gif" width="450" height="50"></P>

<!--webbot BOT="GeneratedScript" PREVIEW=" " startspan --><script Language="JavaScript" Type="text/javascript"><!--
function FrontPage_Form1_Validator(theForm)
{

  if (theForm.name.value == "")
  {
    alert("Please enter a value for the \"name\" field.");
    theForm.name.focus();
    return (false);
  }

  if (theForm.name.value.length > 100)
  {
    alert("Please enter at most 100 characters in the \"name\" field.");
    theForm.name.focus();
    return (false);
  }

  if (theForm.phone.value == "")
  {
    alert("Please enter a value for the \"phone\" field.");
    theForm.phone.focus();
    return (false);
  }

  if (theForm.phone.value.length < 12)
  {
    alert("Please enter at least 12 characters in the \"phone\" field.");
    theForm.phone.focus();
    return (false);
  }

  if (theForm.email.value == "")
  {
    alert("Please enter a value for the \"email\" field.");
    theForm.email.focus();
    return (false);
  }

  if (theForm.email.value.length > 25)
  {
    alert("Please enter at most 25 characters in the \"email\" field.");
    theForm.email.focus();
    return (false);
  }
  return (true);
}
//--></script><!--webbot BOT="GeneratedScript" endspan --><form method="POST" action="APPLICATION.asp" onsubmit="return FrontPage_Form1_Validator(this)" name="FrontPage_Form1" language="JavaScript" webbot-action="--WEBBOT-SELF--">
  <!--webbot bot="SaveDatabase" SuggestedExt="asp" U-ASP-Include-Url="../_fpclass/fpdbform.inc" S-DataConnection="PlasmaCamp" S-RecordSource="TeachersApplication2004" U-Database-URL="../../fpdb/ApplicationData.mdb" U-Confirmation-Url="eMailRecNotice.asp" S-Builtin-Fields="REMOTE_HOST HTTP_USER_AGENT Timestamp" S-Builtin-DBFields="Remote_computer_name1 Browser_type1 Timestamp" S-Form-Fields="CollegesAttended School EquiptExperience CompExpierence HowLearn GradCourses TeachingAssign TeachingPhilosophy Complete SchoolPhone UndergradCourses email phone PrincipalName ProDev IntExpierence City FutureTeachings Zip LeadershipActivities name YearsExp Publications Address State" S-Form-DBFields="CollegesAttended School2 EquiptExperience CompExpierence HowDidYouLearnAboutUs GradCourses TeachingAssign TeachingPhilosophy Complete School2Phone UndergradCourses eMail Phone PrincipalName ProDev IntExpierence City FutureTeachings Zip LeadershipActivities Name YearsExp Publications Address State" startspan --><input TYPE="hidden" NAME="VTI-GROUP" VALUE="0"><!--#include file="../_fpclass/fpdbform.inc"--><!--webbot bot="SaveDatabase" i-checksum="34604" endspan -->

  <div align="center">
    <center>
    <table border="1" width="84%" bordercolor="#DDDDDD" cellspacing="0" height="582">
      <tr>
        <td width="100%" bgcolor="#EBEBEB" colspan="2" height="2">


          <P ALIGN=CENTER><B><FONT SIZE=+1>Plasma Science and Fusion Energy Institute<br>
for High School Physics Teachers<br>
July 26-July 30, 2004<br>
          </FONT>APPLICATION FORM<FONT COLOR="#FF0000"><br>
          Deadline for receipt of applications is April 30, 2004<br>
          </FONT></B>(Any problems with this form, please contact <a href="mailto:azwicker@pppl.gov"> Andrew Post-Zwicker</a>
@ 609-243-2150
          </P>

        </td>
      </tr>
      <tr>
        <td width="100%" bgcolor="#F8F8F8" colspan="2" height="95">

          <P ALIGN=CENTER><b><span style="background-color: #00FF00">READ THIS BEFORE
BEGINNING!</span></b></P>

          <P ALIGN=left>It is <B>strongly suggested</B> that you answer the longer
sections of the application form in a separate document that you<B> save
          </B>on your hard drive and then cut and paste this information into the
form. This is to minimize the possibility of losing any of your work while
filling out the form.  The SUBMIT button at the bottom of this page sends
the complete application.  Please make sure that all information is accurate before
submitting it. <i><u>NOTE: Click on the submit button ONCE and wait until you 
          receive confirmation of receipt of your application.&nbsp; Depending 
          upon your connection speed, this may take up to one minute.</u></i></P>

        </td>
      </tr>
      <tr>
        <td width="100%" bgcolor="#FFFFCC" valign="top" colspan="2" height="16"><b><font size="4">Personal
        Information</font></b></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>Name</b></td>
        <td width="71%" height="19">

          <P>
          <!--webbot bot="Validation" b-value-required="TRUE" i-maximum-length="100" --><INPUT NAME="name" TYPE="text" SIZE="29" tabindex="1" maxlength="100"></P>

        </td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="10" align="right"><b>Home
        Address</b></td>
        <td width="71%" height="10">
          <INPUT NAME="Address" TYPE="text" SIZE="29" tabindex="2"></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="13" align="right"><b>City</b></td>
        <td width="71%" height="13">
          <INPUT NAME="City" TYPE="text" SIZE="29" tabindex="3"></td>
      </tr>
    </center>
    <tr>
                <td width="35%" height="21" bgcolor="#EBEBEB">
                  <p align="right"><b>State:</b></p>
                </td>
      <center>
                <td width="65%" height="21">
                  <input type="text" name="State" size="20" tabindex="4"></td>
              </tr>    <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="14" align="right"><b>Zip</b></td>
        <td width="71%" height="14">
          <INPUT NAME="Zip" TYPE="text" SIZE="7" tabindex="5"></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>Phone</b></td>
        <td width="71%" height="19"><font size="1">
          <!--webbot bot="Validation" b-value-required="TRUE" i-minimum-length="12" --><INPUT NAME="phone" TYPE="text" SIZE="29" tabindex="6">(i.e.
        609-555-1212)</font></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>email</b></td>
        <td width="71%" height="19">
          <!--webbot bot="Validation" b-value-required="TRUE" i-maximum-length="25" --><INPUT NAME="email" TYPE="text" SIZE="29" tabindex="7" maxlength="25"></td>
      </tr>
      <tr>
        <td width="100%" bgcolor="#FFFFCC" valign="top" height="19" colspan="2"><b><font size="4">Teaching
        Information</font></b></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>School
        Name &amp; Address </b>(where presently teaching)</td>
        <td width="71%" height="19">

          <P><textarea rows="2" name="School" cols="56" tabindex="8"></textarea></P>

        </td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>School
        Phone #&nbsp;</b></td>
        <td width="71%" height="19"> 
          <INPUT NAME="SchoolPhone" TYPE="text" SIZE="29" tabindex="9"></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>Principal's
        Name</b></td>
        <td width="71%" height="19">
          <INPUT NAME="PrincipalName" TYPE="text" SIZE="29" tabindex="10"></td>
      </tr>
      <tr>
        <td width="100%" bgcolor="#FFFFCC" valign="top" height="19" colspan="2"><font size="4"><b>Educational
        Information</b></font></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right">

          <P><b>List in reverse chronological order the colleges/universities attended
where a formal degree was earned.<br>
          </b>(University,<B> </B>Highest<B> </B>Degree Earned,<B> </B>Major<B>,</B>
Award Date)&nbsp;</P>

        </td>
        <td width="71%" height="19">
          <TEXTAREA NAME="CollegesAttended" ROWS="6" COLS="55" tabindex="11"
></TEXTAREA></td>
      </tr>
    </center>
    <tr>
      <td width="100%" bgcolor="#FFFFCC" valign="top" height="19" align="right" colspan="2">
        <p align="left"><font size="4"><b>Educational </b></font><font size="4"><b>Experience</b></font></td>
      </tr>
      <center>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>List undergraduate science/math/technology courses taken.</b></td>
        <td width="71%" height="19">
          <TEXTAREA NAME="UndergradCourses" ROWS="4" COLS="55" tabindex="12"
></TEXTAREA></td>
      </tr>
      <tr>
        <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b>List graduate science/math/technology courses taken.</b></td>
        <td width="71%" height="19">
          <TEXTAREA NAME="GradCourses" ROWS="4" COLS="55" tabindex="13"
></TEXTAREA></td>
      </tr>
      </center>
      <tr>
        <td width="100%" bgcolor="#FFFFCC" valign="top" height="19" align="right" colspan="2">
          <p align="left"><font size="4"><b>Professional Experience</b></font></td>
        </tr>
        <center>
        <tr>
          <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> Number of years you have taught
      science / math / technology classe</b>s&nbsp;<br>
 (include
the sum of part-time teaching assignments.)</td>
          <td width="71%" height="19">
            <INPUT NAME="YearsExp"
TYPE="text" SIZE="29" tabindex="14"></td>
        </tr>
        <tr>
          <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> Teaching assignments this school year
            </b> (include grade levels for each</td>
          <td width="71%" height="19">
            <TEXTAREA NAME="TeachingAssign" ROWS="4" COLS="56" tabindex="15"
></TEXTAREA></td>
        </tr>
        <tr>
          <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> Anticipated teaching assignments next year&nbsp;</b><br>
 (if different from above):</td>
          <td width="71%" height="19">
            <TEXTAREA NAME="FutureTeachings" ROWS="4" COLS="56" tabindex="16"
></TEXTAREA></td>
        </tr>
        <tr>
          <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> How have you advanced your professional development</b>&nbsp;<br>
 (e.g., workshops,
institutes, conferences, research experience) during the last 3 to 5 years</td>
          <td width="71%" height="19">
            <TEXTAREA NAME="ProDev" ROWS="4" COLS="56" tabindex="17"
></TEXTAREA></td>
        </tr>
        </center>
        <tr>
          <td width="29%" bgcolor="#EBEBEB" valign="top" height="19">
            <p align="right"><b> Describe your leadership activities, contributions to science, math
or technology education </b> (in-class or out of class),<b> or other outstanding
accomplishments during the last 3 to 5 years.</b></td>
            <center>
            <td width="71%" height="19">
              <TEXTAREA NAME="LeadershipActivities" ROWS="4" COLS="56" tabindex="18"
></TEXTAREA></td>
            </tr>
          </center>
          <tr>
            <td width="29%" bgcolor="#EBEBEB" valign="top" height="19">
              <p align="right"><b> List any relevant publications that you may have.</b></td>
              <center>
              <td width="71%" height="19">
                <TEXTAREA NAME="Publications" ROWS="4" COLS="56" tabindex="19"
></TEXTAREA></td>
              </tr>
            </center>
            <tr>
              <td width="29%" bgcolor="#EBEBEB" valign="top" height="19">
                <p align="right"><b> Briefly describe your teaching philosophy. Include how your teaching
is innovative and describe your goals when teaching. Include a lesson plan,
if available. If necessary, this can be faxed instead of included here</b> (609-243-2112).</td>
                <center>
                <td width="71%" height="19">
                  <TEXTAREA NAME="TeachingPhilosophy" ROWS="4" COLS="56" tabindex="20"
></TEXTAREA></td>
                </tr>
                <tr>
                  <td width="100%" bgcolor="#FFFFCC" valign="top" height="19" colspan="2"><font size="4"><b>Equipment
        Experience</b></font></td>
                </tr>
                <tr>
                  <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> List scientific or electronic equipment experience and the extent
of this experience. Have you ever worked with high voltage power supplies?
Other electrical equipment? Vacuum pumps? Optics/Spectrometers? Lasers?</b></td>
                  <td width="71%" height="19">
                    <TEXTAREA NAME="EquiptExperience" ROWS="4" COLS="56" tabindex="21"
></TEXTAREA></td>
                </tr>
                <tr>
                  <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> List computer hardware and software experience,  your level of proficiency
and length of experience</b></td>
                  <td width="71%" height="19">
                    <TEXTAREA NAME="CompExpierence" ROWS="4" COLS="56" tabindex="22"
></TEXTAREA></td>
                </tr>
                <tr>
                  <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right">

                    <P><b>4. Do you use the Internet with your students? How? Do you use email
regularly?</b></P>

                    <p>&nbsp;</td>
                    <td width="71%" height="19">
                      <TEXTAREA NAME="IntExpierence" ROWS="4" COLS="56" tabindex="23"
></TEXTAREA></td>
                  </tr>
                  <tr>
                    <td width="29%" bgcolor="#EBEBEB" valign="top" height="19" align="right"><b> How did you learn about the Institute?</b></td>
                    <td width="71%" height="19">

                      <P>
                      <textarea rows="2" name="HowLearn" cols="56" tabindex="24"></textarea></P>

                    </td>
                  </tr>
                  <tr>
                    <td width="100%" bgcolor="#FFFFCC" valign="top" height="19" colspan="2"><font size="4"><b>Application
        Certification</b></font></td>
                  </tr>
                  <tr>
                    <td width="100%" bgcolor="#EBEBEB" valign="top" height="9" colspan="2">
                      <p align="center">I certify that the information contained herein is true, complete, and
correct, and that I plan <B>to return to the classroom as a teacher for
the following school year.</B></p>
                      <p align="center"><INPUT TYPE="checkbox" NAME="Complete" VALUE=
"I Agree">By checking this box, I acknowledge that my application is complete and
ready for review.</p>
                      <p align="center"><INPUT NAME="Application submitted" TYPE="submit" VALUE=
"Submit" style="font-family: Arial Black; color: #FF0000; background-color: #EBEBEB"></td>
                    </tr>
                  </table>
                </center>
              </div>

            </form>

          </form>

          <P>&nbsp;</P>

        </FORM>
      </BODY>
    </HTML>