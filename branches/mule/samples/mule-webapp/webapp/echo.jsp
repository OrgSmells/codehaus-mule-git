<%@ page import="org.mule.extras.client.MuleClient,
                 org.mule.umo.UMOMessage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>Mule Echo Example</title>
</head>
<body>
<%
    String echo = request.getParameter("echo");
    if(echo!=null) {
        MuleClient client = new MuleClient();
        UMOMessage message = client.send("vm://echo", echo, null);
%>
<h3>You typed <%=message.getPayload()%>!</h3>
     <%}%>
Please enter something:
<form method="POST" name="submitEcho" action="">
	<table>
        <tr><td>
            <input type="text" name="echo"/></td><td><input type="submit" name="Go" value=" Go " />
        </td></tr>
    </table>
</form>
<p/>
<table border="1" bordercolor="#990000"  align="left">
<tr><td>For more information about Echo example go <a target="_blank" href="http://wiki.muleumo.org/display/MULE/Echo+Example">here</a>.<br/>
To view the source and configuration go <a target="_blank" href="http://cvs.sourceforge.net/viewcvs.py/mule/mule/src/samples/echo/">here</a>.</td></tr>
</table>
</body>
</html>
