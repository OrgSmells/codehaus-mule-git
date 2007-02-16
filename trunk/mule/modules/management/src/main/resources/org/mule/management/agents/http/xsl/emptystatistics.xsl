<?xml version="1.0"?>
<!--
 $Id$
 
 Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 
 The software in this package is published under the terms of the MuleSource MPL
 license, a copy of which has been included with this distribution in the
 LICENSE.txt file.
 -->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
   <xsl:output method="html" indent="yes" encoding="ISO-8859-1"/>
   <xsl:include href="common.xsl"/>

   <xsl:param name="html.stylesheet">stylesheet.css</xsl:param>
   <xsl:param name="html.stylesheet.type">text/css</xsl:param>
   <xsl:param name="head.title">emptymbean.title</xsl:param>

   <!-- Main template -->
   <xsl:template match="/">
      <html>
         <xsl:call-template name="head"/>
         <script type="text/javascript">
         function queryStatistics()
         {
            var objectname = document.getElementsByName("objectname")
            var s = objectname[0].value + ":type=org.mule.Statistics,name=AllStatistics"
            objectname[0].value = s
            var form = document.getElementsByName("getattribute")
            form[0].submit()
         }
         </script>
         <body>
            <table width="100%" cellpadding="0" cellspacing="0" border="0">
               <tr width="100%">
                  <td>
                     <xsl:call-template name="toprow"/>
                     <xsl:call-template name="tabs">
                        <xsl:with-param name="selection">statistics</xsl:with-param>
                     </xsl:call-template>
                     <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tr>
                           <td class="page_title">
                              <xsl:call-template name="str">
                                 <xsl:with-param name="id">emptymbean.subtitle</xsl:with-param>
                              </xsl:call-template>
                           </td>
                        </tr>
                        <tr>
                           <form name="getattribute" action="/getattribute">
                              <td class="input_title" align="right">
                                 <xsl:call-template name="str">
                                    <xsl:with-param name="id">emptystatistics.querystatistics</xsl:with-param>
                                 </xsl:call-template>
                                 <xsl:variable name="str.query">
                                    <xsl:call-template name="str">
                                       <xsl:with-param name="id">emptystatistics.query</xsl:with-param>
                                    </xsl:call-template>
                                 </xsl:variable>
                                 <input type="input" name="objectname"/>
                                 <input type="button" value="{$str.query}" onclick="queryStatistics()"/>
                                 <input type="hidden" name="attribute" value="HtmlSummary"/>
                                 <input type="hidden" name="template" value="statistics"/>
                              </td>
                           </form>
                        </tr>
                     </table>
                     <xsl:call-template name="bottom"/>
                  </td>
               </tr>
            </table>
         </body>
      </html>
   </xsl:template>
</xsl:stylesheet>

