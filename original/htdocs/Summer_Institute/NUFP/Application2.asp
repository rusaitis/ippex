<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<title>Application: Part2</title>
</head>

<body>

<p align="center"><b><font size="3">National Undergraduate Fellowship Application: Part 2</font></b></p>
<div align="center">
  <center>
  <table border="1" width="84%" bordercolor="#DDDDDD" cellspacing="0" height="135">
    <tr>
      <td width="100%" bgcolor="#FFFFCC" valign="top" height="19"><font size="4"><b>References</b></font></td>
    </tr>
        </center>
    <tr>
      <td width="100%" bgcolor="#FFFFFF" valign="top" height="1">
        <table>
          <tr>
            <td bgcolor="#FFFFFF">
              <p align="left"><font face="geneva,arial,verdana,sans serif" size="2"><b>The
              previous page of the application simply stored the names and email
              addresses of your references in our database.&nbsp; This form will
              send each reference an email requesting a letter supporting your
              application.</b></font>
              <p align="left"><font face="geneva,arial,verdana,sans serif" size="2"><b>It
              is YOUR responsibility to make sure that these letters are
              submitted before the February 18, 2005 deadline.</b></font>
              <p align="left"><font face="geneva,arial,verdana,sans serif" size="2" color="#FF0000"><b>Be
              VERY careful that you type the correct email address for each
              reference.&nbsp; If you make a mistake, your reference will not
              receive the request.</b></font>
              <p align="left"><font face="geneva,arial,verdana,sans serif" size="2" color="#FF0000"><b>TWO
              references are REQUIRED.&nbsp; Do NOT submit this form without
              filling in the name and email of both references.</b></font>
              <center>
              <p align="center"><font face="geneva,arial,verdana,sans serif" size="2"><b>Write
              the names and email addresses of your references below:</b></font></p>
              </td>
              </tr>
              </table>
              <!--webbot BOT="GeneratedScript" PREVIEW=" " startspan --><script Language="JavaScript" Type="text/javascript"><!--
function FrontPage_Form1_Validator(theForm)
{

  if (theForm.eMail.value == "")
  {
    alert("Please enter a value for the \"eMail Address\" field.");
    theForm.eMail.focus();
    return (false);
  }

  var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzƒŠŒŽšœžŸÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ0123456789-@. \t\r\n\f";
  var checkStr = theForm.eMail.value;
  var allValid = true;
  var validGroups = true;
  for (i = 0;  i < checkStr.length;  i++)
  {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++)
      if (ch == checkOK.charAt(j))
        break;
    if (j == checkOK.length)
    {
      allValid = false;
      break;
    }
  }
  if (!allValid)
  {
    alert("Please enter only letter, digit, whitespace and \"@.\" characters in the \"eMail Address\" field.");
    theForm.eMail.focus();
    return (false);
  }

  if (theForm.Name.value == "")
  {
    alert("Please enter a value for the \"eMail Address\" field.");
    theForm.Name.focus();
    return (false);
  }

  var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzƒŠŒŽšœžŸÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ0123456789-@. \t\r\n\f";
  var checkStr = theForm.Name.value;
  var allValid = true;
  var validGroups = true;
  for (i = 0;  i < checkStr.length;  i++)
  {
    ch = checkStr.charAt(i);
    for (j = 0;  j < checkOK.length;  j++)
      if (ch == checkOK.charAt(j))
        break;
    if (j == checkOK.length)
    {
      allValid = false;
      break;
    }
  }
  if (!allValid)
  {
    alert("Please enter only letter, digit, whitespace and \"@.\" characters in the \"eMail Address\" field.");
    theForm.Name.focus();
    return (false);
  }
  return (true);
}
//--></script><!--webbot BOT="GeneratedScript" endspan --><form method="POST" action="eMailRecNotice.asp" onsubmit="return FrontPage_Form1_Validator(this)" name="FrontPage_Form1" language="JavaScript">

                <table border="0" width="100%" cellspacing="0" cellpadding="0" height="48">
                  <tr>
                    <td width="19%" align="right" height="23"><b>Name&nbsp;</b></td>
                    <td width="31%" height="23"><b><input type="text" name="Rec1Name" size="20"></b></td>
                    <td width="10%" align="right" height="23"><b>
        email</b></td>
                    <td width="40%" height="23"><b><input type="text" name="Rec1eMail" size="20"></b></td>
                  </tr>
                  <tr>
                    <td width="19%" align="right" height="25"><b>
        Name&nbsp;</b></td>
                    <td width="31%" bgcolor="#FFFFFF" height="25"><b><input type="text" name="Rec2Name" size="20"></b></td>
                    <td width="10%" align="right" height="25"><b>email</b></td>
                    <td width="40%" height="25"><b><input type="text" name="Rec2eMail" size="20"></b></td>
                  </tr>
                  <tr>
                    <td width="50%" align="right" height="25" colspan="2">
                      <p align="left">&nbsp;</p>
                      <p align="left"><b>Please re-enter YOUR email address from the
              previous page.&nbsp;&nbsp;<br>
                      </b>
                      <!--webbot bot="Validation" s-display-name="eMail Address" s-data-type="String" b-allow-letters="TRUE" b-allow-digits="TRUE" b-allow-whitespace="TRUE" s-allow-other-chars="@." b-value-required="TRUE" --><INPUT NAME="eMail" TYPE="text" SIZE="29">
                      <br>
                      <b><font color="#FF0000">Make sure that this is
                    EXACTLY the same as the email you submitted on page 1 of the
                    application.</font>
                      </b>
                      </p>
                      <p align="left"><b>Please enter your first and last name so that the reference 
                  would recognize it.: <br>
                      </b>
                      <!--webbot bot="Validation" s-display-name="eMail Address" s-data-type="String" b-allow-letters="TRUE" b-allow-digits="TRUE" b-allow-whitespace="TRUE" s-allow-other-chars="@." b-value-required="TRUE" --><INPUT NAME="Name" TYPE="text" SIZE="29"></p>
                      <p align="left">&nbsp;</td>
                      <td width="50%" align="right" height="25" colspan="2">
                        <p align="left"></td>
                      </tr>
                      <tr>
                        <td width="100%" align="right" height="25" colspan="4">
                          <p align="center"><br>
                          <input type="submit" value="Submit" name="Submit"><input type="reset" value="Reset" name="Reset"></td>
                        </tr>
                      </table>
                      <input type="hidden" name="DueDate" value="2/20/2004">
                    </form>
                  </td>
                </tr>
              </table>
              </div>

              </body>

            </html>