<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="http://peoplesoft.com/MX_PURCHASE_ORDERSoapIn" xmlns:n12="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="n1 n12 xs">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/n12:Envelope">
		<MX_PS_MESSAGE>
			<header>
				<xsl:variable name="Vvar2_CONST" select="'FS8DEV'"/>
				<from_node>
					<xsl:value-of select="$Vvar2_CONST"/>
				</from_node>
				<xsl:variable name="Vvar3_CONST" select="'MX_HTTP'"/>
				<to_node>
					<xsl:value-of select="$Vvar3_CONST"/>
				</to_node>
				<xsl:variable name="Vvar4_CONST" select="'FS8DEV'"/>
				<publishing_node>
					<xsl:value-of select="$Vvar4_CONST"/>
				</publishing_node>
				<xsl:variable name="Vvar5_CONST" select="'Mon, 01 Jan 2007 00:00:01 GMT123459'"/>
				<publication_id>
					<xsl:value-of select="$Vvar5_CONST"/>
				</publication_id>
				<xsl:variable name="Vvar6_CONST" select="'MX_PURCHASE_ORDER'"/>
				<subject>
					<xsl:value-of select="$Vvar6_CONST"/>
				</subject>
				<xsl:variable name="Vvar7_CONST" select="'FS8DEV'"/>
				<originating_node>
					<xsl:value-of select="$Vvar7_CONST"/>
				</originating_node>
				<xsl:variable name="Vvar8_CONST" select="'Mon, 01 Jan 2007 00:00:01 GMT'"/>
				<publish_timestamp>
					<xsl:value-of select="$Vvar8_CONST"/>
				</publish_timestamp>
				<xsl:variable name="Vvar9_CONST" select="'0'"/>
				<status>
					<xsl:value-of select="$Vvar9_CONST"/>
				</status>
				<xsl:variable name="Vvar10_CONST" select="'VERSION_1'"/>
				<default_dataversion>
					<xsl:value-of select="$Vvar10_CONST"/>
				</default_dataversion>
			</header>
			<GENERIC>
				<xsl:for-each select="n12:Body">
					<xsl:for-each select="n1:MX_PURCHASE_ORDER">
						<xsl:for-each select="n1:FieldTypes">
							<FieldTypes>
								<xsl:for-each select="n1:PO_HDR">
									<PO_HDR>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:CHNG_ORD_BATCH">
											<CHNG_ORD_BATCH>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHNG_ORD_BATCH>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_TYPE">
											<PO_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_STATUS">
											<PO_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:HOLD_STATUS">
											<HOLD_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</HOLD_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:RECV_STATUS">
											<RECV_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RECV_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:DISP_ACTION">
											<DISP_ACTION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISP_ACTION>
										</xsl:for-each>
										<xsl:for-each select="n1:DISP_METHOD">
											<DISP_METHOD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISP_METHOD>
										</xsl:for-each>
										<xsl:for-each select="n1:CHANGE_STATUS">
											<CHANGE_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHANGE_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_DT">
											<PO_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_REF">
											<PO_REF>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_REF>
										</xsl:for-each>
										<xsl:for-each select="n1:VENDOR_SETID">
											<VENDOR_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VENDOR_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:VENDOR_ID">
											<VENDOR_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VENDOR_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:VNDR_LOC">
											<VNDR_LOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VNDR_LOC>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_SETID">
											<PRICE_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_VENDOR">
											<PRICE_VENDOR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_VENDOR>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_LOC">
											<PRICE_LOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_LOC>
										</xsl:for-each>
										<xsl:for-each select="n1:PYMNT_TERMS_CD">
											<PYMNT_TERMS_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PYMNT_TERMS_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:BUYER_ID">
											<BUYER_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUYER_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:ORIGIN">
											<ORIGIN>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ORIGIN>
										</xsl:for-each>
										<xsl:for-each select="n1:CHNG_ORD_SEQ">
											<CHNG_ORD_SEQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHNG_ORD_SEQ>
										</xsl:for-each>
										<xsl:for-each select="n1:ADDRESS_SEQ_NUM">
											<ADDRESS_SEQ_NUM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ADDRESS_SEQ_NUM>
										</xsl:for-each>
										<xsl:for-each select="n1:CNTCT_SEQ_NUM">
											<CNTCT_SEQ_NUM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CNTCT_SEQ_NUM>
										</xsl:for-each>
										<xsl:for-each select="n1:SALES_CNTCT_SEQ_N">
											<SALES_CNTCT_SEQ_N>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SALES_CNTCT_SEQ_N>
										</xsl:for-each>
										<xsl:for-each select="n1:BILL_LOCATION">
											<BILL_LOCATION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BILL_LOCATION>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_EXEMPT">
											<TAX_EXEMPT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_EXEMPT>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_EXEMPT_ID">
											<TAX_EXEMPT_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_EXEMPT_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD">
											<CURRENCY_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:RT_TYPE">
											<RT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:MATCH_ACTION">
											<MATCH_ACTION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MATCH_ACTION>
										</xsl:for-each>
										<xsl:for-each select="n1:MATCH_CNTRL_ID">
											<MATCH_CNTRL_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MATCH_CNTRL_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:MATCH_STATUS_PO">
											<MATCH_STATUS_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MATCH_STATUS_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:MATCH_PROCESS_FLG">
											<MATCH_PROCESS_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MATCH_PROCESS_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:PROCESS_INSTANCE">
											<PROCESS_INSTANCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PROCESS_INSTANCE>
										</xsl:for-each>
										<xsl:for-each select="n1:APPL_JRNL_ID_ENC">
											<APPL_JRNL_ID_ENC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</APPL_JRNL_ID_ENC>
										</xsl:for-each>
										<xsl:for-each select="n1:POST_DOC">
											<POST_DOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</POST_DOC>
										</xsl:for-each>
										<xsl:for-each select="n1:DST_CNTRL_ID">
											<DST_CNTRL_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DST_CNTRL_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:OPRID_ENTERED_BY">
											<OPRID_ENTERED_BY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</OPRID_ENTERED_BY>
										</xsl:for-each>
										<xsl:for-each select="n1:ENTERED_DT">
											<ENTERED_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ENTERED_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:OPRID_APPROVED_BY">
											<OPRID_APPROVED_BY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</OPRID_APPROVED_BY>
										</xsl:for-each>
										<xsl:for-each select="n1:APPROVAL_DT">
											<APPROVAL_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</APPROVAL_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:OPRID_MODIFIED_BY">
											<OPRID_MODIFIED_BY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</OPRID_MODIFIED_BY>
										</xsl:for-each>
										<xsl:for-each select="n1:LAST_DTTM_UPDATE">
											<LAST_DTTM_UPDATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LAST_DTTM_UPDATE>
										</xsl:for-each>
										<xsl:for-each select="n1:ACCOUNTING_DT">
											<ACCOUNTING_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ACCOUNTING_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_GL">
											<BUSINESS_UNIT_GL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT_GL>
										</xsl:for-each>
										<xsl:for-each select="n1:IN_PROCESS_FLG">
											<IN_PROCESS_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</IN_PROCESS_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:ACTIVITY_DATE">
											<ACTIVITY_DATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ACTIVITY_DATE>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_POST_STATUS">
											<PO_POST_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_POST_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:NEXT_MOD_SEQ_NBR">
											<NEXT_MOD_SEQ_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</NEXT_MOD_SEQ_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:ERS_ACTION">
											<ERS_ACTION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ERS_ACTION>
										</xsl:for-each>
										<xsl:for-each select="n1:ACCRUE_USE_TAX">
											<ACCRUE_USE_TAX>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ACCRUE_USE_TAX>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD_BASE">
											<CURRENCY_CD_BASE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD_BASE>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DATE">
											<RATE_DATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DATE>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_MULT">
											<RATE_MULT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_MULT>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DIV">
											<RATE_DIV>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DIV>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_ENTITY">
											<VAT_ENTITY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_ENTITY>
										</xsl:for-each>
										<xsl:for-each select="n1:BUDGET_HDR_STATUS">
											<BUDGET_HDR_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUDGET_HDR_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_AMOUNT_TYPE">
											<KK_AMOUNT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</KK_AMOUNT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_TRAN_OVER_FLAG">
											<KK_TRAN_OVER_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</KK_TRAN_OVER_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_TRAN_OVER_OPRID">
											<KK_TRAN_OVER_OPRID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</KK_TRAN_OVER_OPRID>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_TRAN_OVER_DTTM">
											<KK_TRAN_OVER_DTTM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</KK_TRAN_OVER_DTTM>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_ID">
											<LC_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:BUDGET_HDR_STS_NP">
											<BUDGET_HDR_STS_NP>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUDGET_HDR_STS_NP>
										</xsl:for-each>
										<xsl:for-each select="n1:PREPAID_PO_FLG">
											<PREPAID_PO_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PREPAID_PO_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:PREPAID_AMT">
											<PREPAID_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PREPAID_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:PREPAID_AUTH_STAT">
											<PREPAID_AUTH_STAT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PREPAID_AUTH_STAT>
										</xsl:for-each>
										<xsl:for-each select="n1:PREPAID_STATUS_PO">
											<PREPAID_STATUS_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PREPAID_STATUS_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:PAY_TRM_BSE_DT_OPT">
											<PAY_TRM_BSE_DT_OPT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PAY_TRM_BSE_DT_OPT>
										</xsl:for-each>
										<xsl:for-each select="n1:TERMS_BASIS_DT">
											<TERMS_BASIS_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TERMS_BASIS_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:BACKORDER_STATUS">
											<BACKORDER_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BACKORDER_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:DOC_TOL_HDR_STATUS">
											<DOC_TOL_HDR_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DOC_TOL_HDR_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:MID_ROLL_STATUS">
											<MID_ROLL_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MID_ROLL_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:USER_HDR_CHAR1">
											<USER_HDR_CHAR1>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</USER_HDR_CHAR1>
										</xsl:for-each>
										<xsl:for-each select="n1:BUDGET_CHECK">
											<BUDGET_CHECK>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUDGET_CHECK>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_COMMENTS_FS">
											<PO_COMMENTS_FS>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT">
													<BUSINESS_UNIT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_ID">
													<PO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:OPRID">
													<OPRID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</OPRID>
												</xsl:for-each>
												<xsl:for-each select="n1:COMMENT_ID">
													<COMMENT_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</COMMENT_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:RANDOM_CMMT_NBR">
													<RANDOM_CMMT_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RANDOM_CMMT_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:PUBLIC_FLG">
													<PUBLIC_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PUBLIC_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:SOURCE_FROM">
													<SOURCE_FROM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SOURCE_FROM>
												</xsl:for-each>
												<xsl:for-each select="n1:SOURCE_BU_SETID">
													<SOURCE_BU_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SOURCE_BU_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:SOURCE_ID">
													<SOURCE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SOURCE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:SOURCE_LINE_NBR">
													<SOURCE_LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SOURCE_LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:STATUS">
													<STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:ALLOW_MODIFY">
													<ALLOW_MODIFY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ALLOW_MODIFY>
												</xsl:for-each>
												<xsl:for-each select="n1:FILENAME">
													<FILENAME>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</FILENAME>
												</xsl:for-each>
												<xsl:for-each select="n1:FILE_EXTENSION">
													<FILE_EXTENSION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</FILE_EXTENSION>
												</xsl:for-each>
												<xsl:for-each select="n1:RECV_VIEW_FLG">
													<RECV_VIEW_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RECV_VIEW_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VCHR_VIEW_FLG">
													<VCHR_VIEW_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VCHR_VIEW_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:COMMENT_TYPE">
													<COMMENT_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</COMMENT_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIPTO_ID">
													<SHIPTO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SHIPTO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:LINE_NBR">
													<LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:COMMENTS_2000">
													<COMMENTS_2000>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</COMMENTS_2000>
												</xsl:for-each>
											</PO_COMMENTS_FS>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_LINE">
											<PO_LINE>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT">
													<BUSINESS_UNIT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_ID">
													<PO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:LINE_NBR">
													<LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:CANCEL_STATUS">
													<CANCEL_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CANCEL_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:CHANGE_STATUS">
													<CHANGE_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHANGE_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:ITM_SETID">
													<ITM_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ITM_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:INV_ITEM_ID">
													<INV_ITEM_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</INV_ITEM_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:ITM_ID_VNDR">
													<ITM_ID_VNDR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ITM_ID_VNDR>
												</xsl:for-each>
												<xsl:for-each select="n1:VNDR_CATALOG_ID">
													<VNDR_CATALOG_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VNDR_CATALOG_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:CATEGORY_ID">
													<CATEGORY_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CATEGORY_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:CHNG_ORD_SEQ">
													<CHNG_ORD_SEQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHNG_ORD_SEQ>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_OF_MEASURE">
													<UNIT_OF_MEASURE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</UNIT_OF_MEASURE>
												</xsl:for-each>
												<xsl:for-each select="n1:QTY_TYPE">
													<QTY_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</QTY_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_DT_TYPE">
													<PRICE_DT_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_DT_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:MFG_ID">
													<MFG_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</MFG_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:MFG_ITM_ID">
													<MFG_ITM_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</MFG_ITM_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:CNTRCT_SETID">
													<CNTRCT_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CNTRCT_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:CNTRCT_ID">
													<CNTRCT_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CNTRCT_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:CNTRCT_LINE_NBR">
													<CNTRCT_LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CNTRCT_LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:RELEASE_NBR">
													<RELEASE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RELEASE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:MILESTONE_NBR">
													<MILESTONE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MILESTONE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:CNTRCT_RATE_MULT">
													<CNTRCT_RATE_MULT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CNTRCT_RATE_MULT>
												</xsl:for-each>
												<xsl:for-each select="n1:CNTRCT_RATE_DIV">
													<CNTRCT_RATE_DIV>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CNTRCT_RATE_DIV>
												</xsl:for-each>
												<xsl:for-each select="n1:RFQ_ID">
													<RFQ_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</RFQ_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:RFQ_LINE_NBR">
													<RFQ_LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RFQ_LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:INSPECT_CD">
													<INSPECT_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</INSPECT_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:ROUTING_ID">
													<ROUTING_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ROUTING_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:RECV_REQ">
													<RECV_REQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RECV_REQ>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_CAN_CHANGE">
													<PRICE_CAN_CHANGE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_CAN_CHANGE>
												</xsl:for-each>
												<xsl:for-each select="n1:WTHD_SW">
													<WTHD_SW>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</WTHD_SW>
												</xsl:for-each>
												<xsl:for-each select="n1:WTHD_CD">
													<WTHD_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</WTHD_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:CONFIG_CODE">
													<CONFIG_CODE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CONFIG_CODE>
												</xsl:for-each>
												<xsl:for-each select="n1:CP_TEMPLATE_ID">
													<CP_TEMPLATE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CP_TEMPLATE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:DESCR254_MIXED">
													<DESCR254_MIXED>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DESCR254_MIXED>
												</xsl:for-each>
												<xsl:for-each select="n1:PACKING_WEIGHT">
													<PACKING_WEIGHT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PACKING_WEIGHT>
												</xsl:for-each>
												<xsl:for-each select="n1:PACKING_VOLUME">
													<PACKING_VOLUME>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PACKING_VOLUME>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_MEASURE_WT">
													<UNIT_MEASURE_WT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</UNIT_MEASURE_WT>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_MEASURE_VOL">
													<UNIT_MEASURE_VOL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</UNIT_MEASURE_VOL>
												</xsl:for-each>
												<xsl:for-each select="n1:REPLEN_OPT">
													<REPLEN_OPT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</REPLEN_OPT>
												</xsl:for-each>
												<xsl:for-each select="n1:AMT_ONLY_FLG">
													<AMT_ONLY_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</AMT_ONLY_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:USER_LINE_CHAR1">
													<USER_LINE_CHAR1>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</USER_LINE_CHAR1>
												</xsl:for-each>
												<xsl:for-each select="n1:GPO_ID">
													<GPO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</GPO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:GPO_CNTRCT_NBR">
													<GPO_CNTRCT_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</GPO_CNTRCT_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:MX_ITM_CAT">
													<MX_ITM_CAT>
														<xsl:for-each select="@class">
															<xsl:attribute name="class">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="n1:CATEGORY_CD">
															<CATEGORY_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CATEGORY_CD>
														</xsl:for-each>
													</MX_ITM_CAT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_LINE_SHIP">
													<PO_LINE_SHIP>
														<xsl:for-each select="@class">
															<xsl:attribute name="class">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT">
															<BUSINESS_UNIT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUSINESS_UNIT>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_ID">
															<PO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:LINE_NBR">
															<LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:SCHED_NBR">
															<SCHED_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SCHED_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:CANCEL_STATUS">
															<CANCEL_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CANCEL_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:BAL_STATUS">
															<BAL_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BAL_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:CHANGE_STATUS">
															<CHANGE_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CHANGE_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:CHNG_ORD_SEQ">
															<CHNG_ORD_SEQ>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CHNG_ORD_SEQ>
														</xsl:for-each>
														<xsl:for-each select="n1:PRICE_PO">
															<PRICE_PO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PRICE_PO>
														</xsl:for-each>
														<xsl:for-each select="n1:PRICE_PO_BSE">
															<PRICE_PO_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PRICE_PO_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:CUSTOM_PRICE">
															<CUSTOM_PRICE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CUSTOM_PRICE>
														</xsl:for-each>
														<xsl:for-each select="n1:ZERO_PRICE_IND">
															<ZERO_PRICE_IND>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ZERO_PRICE_IND>
														</xsl:for-each>
														<xsl:for-each select="n1:DUE_DT">
															<DUE_DT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DUE_DT>
														</xsl:for-each>
														<xsl:for-each select="n1:DUE_TIME">
															<DUE_TIME>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</DUE_TIME>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIPTO_SETID">
															<SHIPTO_SETID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SHIPTO_SETID>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIPTO_ID">
															<SHIPTO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SHIPTO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:ORIG_PROM_DT">
															<ORIG_PROM_DT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ORIG_PROM_DT>
														</xsl:for-each>
														<xsl:for-each select="n1:QTY_PO">
															<QTY_PO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</QTY_PO>
														</xsl:for-each>
														<xsl:for-each select="n1:CURRENCY_CD">
															<CURRENCY_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CURRENCY_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:MERCHANDISE_AMT">
															<MERCHANDISE_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MERCHANDISE_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:CURRENCY_CD_BASE">
															<CURRENCY_CD_BASE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CURRENCY_CD_BASE>
														</xsl:for-each>
														<xsl:for-each select="n1:MERCH_AMT_BSE">
															<MERCH_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MERCH_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:FREIGHT_TERMS">
															<FREIGHT_TERMS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FREIGHT_TERMS>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIP_TYPE_ID">
															<SHIP_TYPE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SHIP_TYPE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:DISTRIB_MTHD_FLG">
															<DISTRIB_MTHD_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DISTRIB_MTHD_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_PRC_TOL">
															<UNIT_PRC_TOL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</UNIT_PRC_TOL>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_PRC_TOL_BSE">
															<UNIT_PRC_TOL_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</UNIT_PRC_TOL_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:PCT_UNIT_PRC_TOL">
															<PCT_UNIT_PRC_TOL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PCT_UNIT_PRC_TOL>
														</xsl:for-each>
														<xsl:for-each select="n1:EXT_PRC_TOL">
															<EXT_PRC_TOL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</EXT_PRC_TOL>
														</xsl:for-each>
														<xsl:for-each select="n1:EXT_PRC_TOL_BSE">
															<EXT_PRC_TOL_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</EXT_PRC_TOL_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:PCT_EXT_PRC_TOL">
															<PCT_EXT_PRC_TOL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PCT_EXT_PRC_TOL>
														</xsl:for-each>
														<xsl:for-each select="n1:QTY_RECV_TOL_PCT">
															<QTY_RECV_TOL_PCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</QTY_RECV_TOL_PCT>
														</xsl:for-each>
														<xsl:for-each select="n1:PCT_UNDER_QTY">
															<PCT_UNDER_QTY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PCT_UNDER_QTY>
														</xsl:for-each>
														<xsl:for-each select="n1:MATCH_STATUS_LN_PO">
															<MATCH_STATUS_LN_PO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MATCH_STATUS_LN_PO>
														</xsl:for-each>
														<xsl:for-each select="n1:MATCH_LINE_OPT">
															<MATCH_LINE_OPT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MATCH_LINE_OPT>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_OM">
															<BUSINESS_UNIT_OM>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUSINESS_UNIT_OM>
														</xsl:for-each>
														<xsl:for-each select="n1:ORDER_NO">
															<ORDER_NO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ORDER_NO>
														</xsl:for-each>
														<xsl:for-each select="n1:ORDER_INT_LINE_NO">
															<ORDER_INT_LINE_NO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ORDER_INT_LINE_NO>
														</xsl:for-each>
														<xsl:for-each select="n1:SCHED_LINE_NBR">
															<SCHED_LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SCHED_LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIP_TO_CUST_ID">
															<SHIP_TO_CUST_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SHIP_TO_CUST_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_IN">
															<BUSINESS_UNIT_IN>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUSINESS_UNIT_IN>
														</xsl:for-each>
														<xsl:for-each select="n1:PRODUCTION_ID">
															<PRODUCTION_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PRODUCTION_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:OP_SEQUENCE">
															<OP_SEQUENCE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</OP_SEQUENCE>
														</xsl:for-each>
														<xsl:for-each select="n1:FROZEN_FLG">
															<FROZEN_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FROZEN_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:PLAN_CHANGE_FLG">
															<PLAN_CHANGE_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PLAN_CHANGE_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:NET_CHANGE_EP">
															<NET_CHANGE_EP>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</NET_CHANGE_EP>
														</xsl:for-each>
														<xsl:for-each select="n1:CARRIER_ID">
															<CARRIER_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CARRIER_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:SUT_BASE_ID">
															<SUT_BASE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SUT_BASE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_CD_SUT">
															<TAX_CD_SUT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</TAX_CD_SUT>
														</xsl:for-each>
														<xsl:for-each select="n1:ULTIMATE_USE_CD">
															<ULTIMATE_USE_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ULTIMATE_USE_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:SUT_EXCPTN_TYPE">
															<SUT_EXCPTN_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SUT_EXCPTN_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:SUT_EXCPTN_CERTIF">
															<SUT_EXCPTN_CERTIF>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SUT_EXCPTN_CERTIF>
														</xsl:for-each>
														<xsl:for-each select="n1:SUT_APPLICABILITY">
															<SUT_APPLICABILITY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SUT_APPLICABILITY>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RCRD_INPT_FLG">
															<VAT_RCRD_INPT_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RCRD_INPT_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RCRD_OUTPT_FLG">
															<VAT_RCRD_OUTPT_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RCRD_OUTPT_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_DCLRTN_POINT">
															<VAT_DCLRTN_POINT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_DCLRTN_POINT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_CALC_GROSS_NET">
															<VAT_CALC_GROSS_NET>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_CALC_GROSS_NET>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_CALC_FRGHT_FLG">
															<VAT_CALC_FRGHT_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_CALC_FRGHT_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_CALC_MISC_FLG">
															<VAT_CALC_MISC_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_CALC_MISC_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RECALC_FLG">
															<VAT_RECALC_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RECALC_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_TREATMENT_PUR">
															<VAT_TREATMENT_PUR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_TREATMENT_PUR>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_EXCPTN_TYPE">
															<VAT_EXCPTN_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_EXCPTN_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_EXCPTN_CERTIF">
															<VAT_EXCPTN_CERTIF>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_EXCPTN_CERTIF>
														</xsl:for-each>
														<xsl:for-each select="n1:COUNTRY_SHIP_TO">
															<COUNTRY_SHIP_TO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</COUNTRY_SHIP_TO>
														</xsl:for-each>
														<xsl:for-each select="n1:COUNTRY_SHIP_FROM">
															<COUNTRY_SHIP_FROM>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</COUNTRY_SHIP_FROM>
														</xsl:for-each>
														<xsl:for-each select="n1:COUNTRY_VAT_SHIPTO">
															<COUNTRY_VAT_SHIPTO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</COUNTRY_VAT_SHIPTO>
														</xsl:for-each>
														<xsl:for-each select="n1:COUNTRY_VAT_BILLTO">
															<COUNTRY_VAT_BILLTO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</COUNTRY_VAT_BILLTO>
														</xsl:for-each>
														<xsl:for-each select="n1:COUNTRY_VAT_BILLFR">
															<COUNTRY_VAT_BILLFR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</COUNTRY_VAT_BILLFR>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RGSTRN_SELLER">
															<VAT_RGSTRN_SELLER>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_RGSTRN_SELLER>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
															<VAT_TXN_TYPE_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_TXN_TYPE_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_APPLICABILITY">
															<VAT_APPLICABILITY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_APPLICABILITY>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_CD_VAT">
															<TAX_CD_VAT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</TAX_CD_VAT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_USE_ID">
															<VAT_USE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_USE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:IST_TXN_FLG">
															<IST_TXN_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</IST_TXN_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_CALC_TYPE">
															<VAT_CALC_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_CALC_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_RTV">
															<BUSINESS_UNIT_RTV>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUSINESS_UNIT_RTV>
														</xsl:for-each>
														<xsl:for-each select="n1:RTV_ID">
															<RTV_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</RTV_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:RTV_LN_NBR">
															<RTV_LN_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RTV_LN_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:RTV_VERIFIED">
															<RTV_VERIFIED>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</RTV_VERIFIED>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIP_DATE">
															<SHIP_DATE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SHIP_DATE>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_PRC_TOL_L">
															<UNIT_PRC_TOL_L>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</UNIT_PRC_TOL_L>
														</xsl:for-each>
														<xsl:for-each select="n1:PCT_UNIT_PRC_TOL_L">
															<PCT_UNIT_PRC_TOL_L>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PCT_UNIT_PRC_TOL_L>
														</xsl:for-each>
														<xsl:for-each select="n1:EXT_PRC_TOL_L">
															<EXT_PRC_TOL_L>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</EXT_PRC_TOL_L>
														</xsl:for-each>
														<xsl:for-each select="n1:PCT_EXT_PRC_TOL_L">
															<PCT_EXT_PRC_TOL_L>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PCT_EXT_PRC_TOL_L>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_PRC_TOL_BSE_L">
															<UNIT_PRC_TOL_BSE_L>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</UNIT_PRC_TOL_BSE_L>
														</xsl:for-each>
														<xsl:for-each select="n1:EXT_PRC_TOL_BSE_L">
															<EXT_PRC_TOL_BSE_L>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</EXT_PRC_TOL_BSE_L>
														</xsl:for-each>
														<xsl:for-each select="n1:RJCT_OVER_TOL_FLAG">
															<RJCT_OVER_TOL_FLAG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RJCT_OVER_TOL_FLAG>
														</xsl:for-each>
														<xsl:for-each select="n1:REJECT_DAYS">
															<REJECT_DAYS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</REJECT_DAYS>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_VAT_FLG">
															<TAX_VAT_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</TAX_VAT_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_FRGHT_FLG">
															<TAX_FRGHT_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</TAX_FRGHT_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_MISC_FLG">
															<TAX_MISC_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</TAX_MISC_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:TRFT_RULE_CD">
															<TRFT_RULE_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</TRFT_RULE_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:QTY_RFQ">
															<QTY_RFQ>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</QTY_RFQ>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIP_ID_EST">
															<SHIP_ID_EST>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SHIP_ID_EST>
														</xsl:for-each>
														<xsl:for-each select="n1:X_VENDOR_SETID">
															<X_VENDOR_SETID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</X_VENDOR_SETID>
														</xsl:for-each>
														<xsl:for-each select="n1:X_VENDOR_ID">
															<X_VENDOR_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</X_VENDOR_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:X_VNDR_LOC">
															<X_VNDR_LOC>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</X_VNDR_LOC>
														</xsl:for-each>
														<xsl:for-each select="n1:FRT_CHRG_METHOD">
															<FRT_CHRG_METHOD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</FRT_CHRG_METHOD>
														</xsl:for-each>
														<xsl:for-each select="n1:FRT_CHRG_OVERRIDE">
															<FRT_CHRG_OVERRIDE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FRT_CHRG_OVERRIDE>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_ROUND_RULE">
															<VAT_ROUND_RULE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_ROUND_RULE>
														</xsl:for-each>
														<xsl:for-each select="n1:REVISION">
															<REVISION>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</REVISION>
														</xsl:for-each>
														<xsl:for-each select="n1:PUBLISHED_SHIPTO">
															<PUBLISHED_SHIPTO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PUBLISHED_SHIPTO>
														</xsl:for-each>
														<xsl:for-each select="n1:BCKORD_ORG_SCHED">
															<BCKORD_ORG_SCHED>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BCKORD_ORG_SCHED>
														</xsl:for-each>
														<xsl:for-each select="n1:USER_SCHED_CHAR1">
															<USER_SCHED_CHAR1>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</USER_SCHED_CHAR1>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_LINE_DISTRIB">
															<PO_LINE_DISTRIB>
																<xsl:for-each select="@class">
																	<xsl:attribute name="class">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT">
																	<BUSINESS_UNIT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BUSINESS_UNIT>
																</xsl:for-each>
																<xsl:for-each select="n1:PO_ID">
																	<PO_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PO_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:LINE_NBR">
																	<LINE_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</LINE_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:SCHED_NBR">
																	<SCHED_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SCHED_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:DST_ACCT_TYPE">
																	<DST_ACCT_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DST_ACCT_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:DISTRIB_LINE_NUM">
																	<DISTRIB_LINE_NUM>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DISTRIB_LINE_NUM>
																</xsl:for-each>
																<xsl:for-each select="n1:QTY_PO">
																	<QTY_PO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</QTY_PO>
																</xsl:for-each>
																<xsl:for-each select="n1:CURRENCY_CD">
																	<CURRENCY_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CURRENCY_CD>
																</xsl:for-each>
																<xsl:for-each select="n1:MERCHANDISE_AMT">
																	<MERCHANDISE_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MERCHANDISE_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:CURRENCY_CD_BASE">
																	<CURRENCY_CD_BASE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CURRENCY_CD_BASE>
																</xsl:for-each>
																<xsl:for-each select="n1:MERCH_AMT_BSE">
																	<MERCH_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MERCH_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:LOCATION">
																	<LOCATION>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</LOCATION>
																</xsl:for-each>
																<xsl:for-each select="n1:ACCOUNT">
																	<ACCOUNT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</ACCOUNT>
																</xsl:for-each>
																<xsl:for-each select="n1:ALTACCT">
																	<ALTACCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</ALTACCT>
																</xsl:for-each>
																<xsl:for-each select="n1:DEPTID">
																	<DEPTID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DEPTID>
																</xsl:for-each>
																<xsl:for-each select="n1:OPERATING_UNIT">
																	<OPERATING_UNIT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</OPERATING_UNIT>
																</xsl:for-each>
																<xsl:for-each select="n1:PRODUCT">
																	<PRODUCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PRODUCT>
																</xsl:for-each>
																<xsl:for-each select="n1:FUND_CODE">
																	<FUND_CODE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</FUND_CODE>
																</xsl:for-each>
																<xsl:for-each select="n1:CLASS_FLD">
																	<CLASS_FLD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</CLASS_FLD>
																</xsl:for-each>
																<xsl:for-each select="n1:PROGRAM_CODE">
																	<PROGRAM_CODE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PROGRAM_CODE>
																</xsl:for-each>
																<xsl:for-each select="n1:BUDGET_REF">
																	<BUDGET_REF>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUDGET_REF>
																</xsl:for-each>
																<xsl:for-each select="n1:AFFILIATE">
																	<AFFILIATE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</AFFILIATE>
																</xsl:for-each>
																<xsl:for-each select="n1:AFFILIATE_INTRA1">
																	<AFFILIATE_INTRA1>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</AFFILIATE_INTRA1>
																</xsl:for-each>
																<xsl:for-each select="n1:AFFILIATE_INTRA2">
																	<AFFILIATE_INTRA2>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</AFFILIATE_INTRA2>
																</xsl:for-each>
																<xsl:for-each select="n1:CHARTFIELD1">
																	<CHARTFIELD1>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</CHARTFIELD1>
																</xsl:for-each>
																<xsl:for-each select="n1:CHARTFIELD2">
																	<CHARTFIELD2>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</CHARTFIELD2>
																</xsl:for-each>
																<xsl:for-each select="n1:CHARTFIELD3">
																	<CHARTFIELD3>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</CHARTFIELD3>
																</xsl:for-each>
																<xsl:for-each select="n1:PROJECT_ID">
																	<PROJECT_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PROJECT_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:STATISTICS_CODE">
																	<STATISTICS_CODE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</STATISTICS_CODE>
																</xsl:for-each>
																<xsl:for-each select="n1:STATISTIC_AMOUNT">
																	<STATISTIC_AMOUNT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</STATISTIC_AMOUNT>
																</xsl:for-each>
																<xsl:for-each select="n1:CHARTFIELD_STATUS">
																	<CHARTFIELD_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CHARTFIELD_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_GL">
																	<BUSINESS_UNIT_GL>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BUSINESS_UNIT_GL>
																</xsl:for-each>
																<xsl:for-each select="n1:DISTRIB_LN_STATUS">
																	<DISTRIB_LN_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DISTRIB_LN_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:DIST_PROCESSED_FLG">
																	<DIST_PROCESSED_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DIST_PROCESSED_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:SYSTEM_SOURCE">
																	<SYSTEM_SOURCE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SYSTEM_SOURCE>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_REQ">
																	<BUSINESS_UNIT_REQ>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BUSINESS_UNIT_REQ>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_PC">
																	<BUSINESS_UNIT_PC>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUSINESS_UNIT_PC>
																</xsl:for-each>
																<xsl:for-each select="n1:ACTIVITY_ID">
																	<ACTIVITY_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</ACTIVITY_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:ANALYSIS_TYPE">
																	<ANALYSIS_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</ANALYSIS_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:RESOURCE_TYPE">
																	<RESOURCE_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</RESOURCE_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:RESOURCE_CATEGORY">
																	<RESOURCE_CATEGORY>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</RESOURCE_CATEGORY>
																</xsl:for-each>
																<xsl:for-each select="n1:RESOURCE_SUB_CAT">
																	<RESOURCE_SUB_CAT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</RESOURCE_SUB_CAT>
																</xsl:for-each>
																<xsl:for-each select="n1:PROCESS_INSTANCE">
																	<PROCESS_INSTANCE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PROCESS_INSTANCE>
																</xsl:for-each>
																<xsl:for-each select="n1:PROCESS_MAN_CLOSE">
																	<PROCESS_MAN_CLOSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PROCESS_MAN_CLOSE>
																</xsl:for-each>
																<xsl:for-each select="n1:REQ_ID">
																	<REQ_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</REQ_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:REQ_LINE_NBR">
																	<REQ_LINE_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</REQ_LINE_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:REQ_SCHED_NBR">
																	<REQ_SCHED_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</REQ_SCHED_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:REQ_DISTRIB_NBR">
																	<REQ_DISTRIB_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</REQ_DISTRIB_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:PO_POST_AMT">
																	<PO_POST_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PO_POST_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:PO_POST_AMT_BSE">
																	<PO_POST_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PO_POST_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:PC_DISTRIB_STATUS">
																	<PC_DISTRIB_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PC_DISTRIB_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:PC_DISTRIB_AMT">
																	<PC_DISTRIB_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PC_DISTRIB_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:PC_DISTRIB_AMT_BSE">
																	<PC_DISTRIB_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PC_DISTRIB_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:PROFILE_ID">
																	<PROFILE_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PROFILE_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:TAG_NUMBER">
																	<TAG_NUMBER>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</TAG_NUMBER>
																</xsl:for-each>
																<xsl:for-each select="n1:CAP_NUM">
																	<CAP_NUM>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</CAP_NUM>
																</xsl:for-each>
																<xsl:for-each select="n1:CAP_SEQUENCE">
																	<CAP_SEQUENCE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CAP_SEQUENCE>
																</xsl:for-each>
																<xsl:for-each select="n1:EMPLID">
																	<EMPLID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</EMPLID>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_AM">
																	<BUSINESS_UNIT_AM>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUSINESS_UNIT_AM>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_IN">
																	<BUSINESS_UNIT_IN>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUSINESS_UNIT_IN>
																</xsl:for-each>
																<xsl:for-each select="n1:PO_POST_STATUS">
																	<PO_POST_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PO_POST_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:CLOSE_AMOUNT">
																	<CLOSE_AMOUNT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CLOSE_AMOUNT>
																</xsl:for-each>
																<xsl:for-each select="n1:CLOSE_AMOUNT_BSE">
																	<CLOSE_AMOUNT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CLOSE_AMOUNT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:QTY_REQ">
																	<QTY_REQ>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</QTY_REQ>
																</xsl:for-each>
																<xsl:for-each select="n1:DISTRIB_TYPE">
																	<DISTRIB_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</DISTRIB_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:DISTRIB_PCT">
																	<DISTRIB_PCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DISTRIB_PCT>
																</xsl:for-each>
																<xsl:for-each select="n1:INVOICE_CLOSE_IND">
																	<INVOICE_CLOSE_IND>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</INVOICE_CLOSE_IND>
																</xsl:for-each>
																<xsl:for-each select="n1:FINANCIAL_ASSET_SW">
																	<FINANCIAL_ASSET_SW>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FINANCIAL_ASSET_SW>
																</xsl:for-each>
																<xsl:for-each select="n1:COST_TYPE">
																	<COST_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</COST_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_CD_SUT">
																	<TAX_CD_SUT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</TAX_CD_SUT>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_CD_SUT_PCT">
																	<TAX_CD_SUT_PCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</TAX_CD_SUT_PCT>
																</xsl:for-each>
																<xsl:for-each select="n1:SALETX_AMT">
																	<SALETX_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SALETX_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:SALETX_AMT_BSE">
																	<SALETX_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SALETX_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:USETAX_AMT">
																	<USETAX_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</USETAX_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:USETAX_AMT_BSE">
																	<USETAX_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</USETAX_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:SUT_APPLICABILITY">
																	<SUT_APPLICABILITY>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SUT_APPLICABILITY>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
																	<VAT_TXN_TYPE_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_TXN_TYPE_CD>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_APPLICABILITY">
																	<VAT_APPLICABILITY>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_APPLICABILITY>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_CD_VAT">
																	<TAX_CD_VAT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</TAX_CD_VAT>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_CD_VAT_PCT">
																	<TAX_CD_VAT_PCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</TAX_CD_VAT_PCT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_AMT">
																	<VAT_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_AMT_BASE">
																	<VAT_AMT_BASE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_AMT_BASE>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_BASIS_AMT">
																	<VAT_BASIS_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_BASIS_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_BASIS_AMT_BSE">
																	<VAT_BASIS_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_BASIS_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RECOVERY_PCT">
																	<VAT_RECOVERY_PCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RECOVERY_PCT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RCVRY_AMT">
																	<VAT_RCVRY_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RCVRY_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RCVRY_AMT_BSE">
																	<VAT_RCVRY_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RCVRY_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_REBATE_PCT">
																	<VAT_REBATE_PCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_REBATE_PCT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_REBATE_AMT">
																	<VAT_REBATE_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_REBATE_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_REBATE_AMT_BSE">
																	<VAT_REBATE_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_REBATE_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_USE_ID">
																	<VAT_USE_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_USE_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:RT_TYPE">
																	<RT_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</RT_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:RATE_MULT">
																	<RATE_MULT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</RATE_MULT>
																</xsl:for-each>
																<xsl:for-each select="n1:RATE_DIV">
																	<RATE_DIV>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</RATE_DIV>
																</xsl:for-each>
																<xsl:for-each select="n1:FREIGHT_AMT">
																	<FREIGHT_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FREIGHT_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:FREIGHT_AMT_BSE">
																	<FREIGHT_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FREIGHT_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:FREIGHT_AMT_NP">
																	<FREIGHT_AMT_NP>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FREIGHT_AMT_NP>
																</xsl:for-each>
																<xsl:for-each select="n1:FREIGHT_AMT_NP_BSE">
																	<FREIGHT_AMT_NP_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FREIGHT_AMT_NP_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:MISC_AMT">
																	<MISC_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MISC_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:MISC_AMT_BSE">
																	<MISC_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MISC_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:MISC_AMT_NP">
																	<MISC_AMT_NP>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MISC_AMT_NP>
																</xsl:for-each>
																<xsl:for-each select="n1:MISC_AMT_NP_BSE">
																	<MISC_AMT_NP_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MISC_AMT_NP_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:MONETARY_AMOUNT">
																	<MONETARY_AMOUNT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MONETARY_AMOUNT>
																</xsl:for-each>
																<xsl:for-each select="n1:MONETARY_AMT_BSE">
																	<MONETARY_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MONETARY_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:BUDGET_DT">
																	<BUDGET_DT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BUDGET_DT>
																</xsl:for-each>
																<xsl:for-each select="n1:BUDGET_LINE_STATUS">
																	<BUDGET_LINE_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BUDGET_LINE_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:KK_CLOSE_FLAG">
																	<KK_CLOSE_FLAG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</KK_CLOSE_FLAG>
																</xsl:for-each>
																<xsl:for-each select="n1:KK_PROCESS_PRIOR">
																	<KK_PROCESS_PRIOR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</KK_PROCESS_PRIOR>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_CALC_TYPE">
																	<VAT_CALC_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_CALC_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:SALETX_PRORATE_FLG">
																	<SALETX_PRORATE_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SALETX_PRORATE_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:USETAX_PRORATE_FLG">
																	<USETAX_PRORATE_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</USETAX_PRORATE_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_NRCVR_PRO_FLG">
																	<VAT_NRCVR_PRO_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_NRCVR_PRO_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_PRORATE_FLG">
																	<VAT_PRORATE_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_PRORATE_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:CONSIGNED_FLAG">
																	<CONSIGNED_FLAG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CONSIGNED_FLAG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RCVRY_PCT_SRC">
																	<VAT_RCVRY_PCT_SRC>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RCVRY_PCT_SRC>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_REBATE_PCT_SRC">
																	<VAT_REBATE_PCT_SRC>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_REBATE_PCT_SRC>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_TRANS_AMT">
																	<VAT_TRANS_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_TRANS_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_TRANS_AMT_BSE">
																	<VAT_TRANS_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_TRANS_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:PUBLISHED_IBU">
																	<PUBLISHED_IBU>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PUBLISHED_IBU>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_APORT_CNTRL">
																	<VAT_APORT_CNTRL>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_APORT_CNTRL>
																</xsl:for-each>
																<xsl:for-each select="n1:KK_CLOSE_PRIOR">
																	<KK_CLOSE_PRIOR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</KK_CLOSE_PRIOR>
																</xsl:for-each>
																<xsl:for-each select="n1:DOC_TOL_LN_STATUS">
																	<DOC_TOL_LN_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DOC_TOL_LN_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:ROLL_STAT_R">
																	<ROLL_STAT_R>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</ROLL_STAT_R>
																</xsl:for-each>
																<xsl:for-each select="n1:USER_DIST_CHAR1">
																	<USER_DIST_CHAR1>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</USER_DIST_CHAR1>
																</xsl:for-each>
																<xsl:for-each select="n1:ENTRY_EVENT">
																	<ENTRY_EVENT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</ENTRY_EVENT>
																</xsl:for-each>
															</PO_LINE_DISTRIB>
														</xsl:for-each>
													</PO_LINE_SHIP>
												</xsl:for-each>
											</PO_LINE>
										</xsl:for-each>
									</PO_HDR>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_HDR_MISC">
									<PO_HDR_MISC>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_CHARGE_SETID">
											<MISC_CHARGE_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MISC_CHARGE_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_CHARGE_CODE">
											<MISC_CHARGE_CODE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MISC_CHARGE_CODE>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_FLAG">
											<LC_COMP_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_ACCRUAL_FLAG">
											<LC_ACCRUAL_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_ACCRUAL_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:DST_ACCT_TYPE">
											<DST_ACCT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DST_ACCT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_ALLOC">
											<LC_COMP_ALLOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_ALLOC>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_ON_PO">
											<LC_COMP_ON_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_ON_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:CHARGE_AMOUNT">
											<CHARGE_AMOUNT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CHARGE_AMOUNT>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD">
											<CURRENCY_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VENDOR_SETID">
											<X_VENDOR_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VENDOR_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VENDOR_ID">
											<X_VENDOR_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VENDOR_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VNDR_LOC">
											<X_VNDR_LOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VNDR_LOC>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_RTV_CREDIT">
											<LC_RTV_CREDIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_RTV_CREDIT>
										</xsl:for-each>
										<xsl:for-each select="n1:CHARGE_AMT_BSE">
											<CHARGE_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CHARGE_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD_BASE">
											<CURRENCY_CD_BASE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD_BASE>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DATE">
											<RATE_DATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DATE>
										</xsl:for-each>
										<xsl:for-each select="n1:RT_TYPE">
											<RT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_MULT">
											<RATE_MULT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_MULT>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DIV">
											<RATE_DIV>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DIV>
										</xsl:for-each>
									</PO_HDR_MISC>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_COMMENTS_FS">
									<PO_COMMENTS_FS>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:OPRID">
											<OPRID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</OPRID>
										</xsl:for-each>
										<xsl:for-each select="n1:COMMENT_ID">
											<COMMENT_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</COMMENT_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:RANDOM_CMMT_NBR">
											<RANDOM_CMMT_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RANDOM_CMMT_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:PUBLIC_FLG">
											<PUBLIC_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PUBLIC_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:SOURCE_FROM">
											<SOURCE_FROM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SOURCE_FROM>
										</xsl:for-each>
										<xsl:for-each select="n1:SOURCE_BU_SETID">
											<SOURCE_BU_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SOURCE_BU_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:SOURCE_ID">
											<SOURCE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SOURCE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:SOURCE_LINE_NBR">
											<SOURCE_LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SOURCE_LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:STATUS">
											<STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:ALLOW_MODIFY">
											<ALLOW_MODIFY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ALLOW_MODIFY>
										</xsl:for-each>
										<xsl:for-each select="n1:FILENAME">
											<FILENAME>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FILENAME>
										</xsl:for-each>
										<xsl:for-each select="n1:FILE_EXTENSION">
											<FILE_EXTENSION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FILE_EXTENSION>
										</xsl:for-each>
										<xsl:for-each select="n1:RECV_VIEW_FLG">
											<RECV_VIEW_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RECV_VIEW_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VCHR_VIEW_FLG">
											<VCHR_VIEW_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VCHR_VIEW_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:COMMENT_TYPE">
											<COMMENT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</COMMENT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIPTO_ID">
											<SHIPTO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SHIPTO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:LINE_NBR">
											<LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:COMMENTS_2000">
											<COMMENTS_2000>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</COMMENTS_2000>
										</xsl:for-each>
									</PO_COMMENTS_FS>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_DISPATCHED">
									<PO_DISPATCHED>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:PROCESS_INSTANCE">
											<PROCESS_INSTANCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PROCESS_INSTANCE>
										</xsl:for-each>
										<xsl:for-each select="n1:EIP_CTL_ID">
											<EIP_CTL_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</EIP_CTL_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:VENDOR_ID">
											<VENDOR_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VENDOR_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:VNDR_LOC">
											<VNDR_LOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VNDR_LOC>
										</xsl:for-each>
										<xsl:for-each select="n1:CHNG_ORD_BATCH">
											<CHNG_ORD_BATCH>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHNG_ORD_BATCH>
										</xsl:for-each>
										<xsl:for-each select="n1:DISP_METHOD">
											<DISP_METHOD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISP_METHOD>
										</xsl:for-each>
										<xsl:for-each select="n1:DATETIME_DISP">
											<DATETIME_DISP>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</DATETIME_DISP>
										</xsl:for-each>
										<xsl:for-each select="n1:OPRID">
											<OPRID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</OPRID>
										</xsl:for-each>
										<xsl:for-each select="n1:FAX_SERVER_DIR">
											<FAX_SERVER_DIR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FAX_SERVER_DIR>
										</xsl:for-each>
										<xsl:for-each select="n1:FILENAME">
											<FILENAME>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FILENAME>
										</xsl:for-each>
										<xsl:for-each select="n1:EMAIL_SERVER_DIR">
											<EMAIL_SERVER_DIR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</EMAIL_SERVER_DIR>
										</xsl:for-each>
										<xsl:for-each select="n1:PATHNAME">
											<PATHNAME>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PATHNAME>
										</xsl:for-each>
									</PO_DISPATCHED>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_LINE">
									<PO_LINE>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:LINE_NBR">
											<LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:CANCEL_STATUS">
											<CANCEL_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CANCEL_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:CHANGE_STATUS">
											<CHANGE_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHANGE_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:ITM_SETID">
											<ITM_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ITM_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:INV_ITEM_ID">
											<INV_ITEM_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</INV_ITEM_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:ITM_ID_VNDR">
											<ITM_ID_VNDR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ITM_ID_VNDR>
										</xsl:for-each>
										<xsl:for-each select="n1:VNDR_CATALOG_ID">
											<VNDR_CATALOG_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VNDR_CATALOG_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:CATEGORY_ID">
											<CATEGORY_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CATEGORY_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:CHNG_ORD_SEQ">
											<CHNG_ORD_SEQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHNG_ORD_SEQ>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_OF_MEASURE">
											<UNIT_OF_MEASURE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</UNIT_OF_MEASURE>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_TYPE">
											<QTY_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_DT_TYPE">
											<PRICE_DT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_DT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:MFG_ID">
											<MFG_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MFG_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:MFG_ITM_ID">
											<MFG_ITM_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MFG_ITM_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:CNTRCT_SETID">
											<CNTRCT_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CNTRCT_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:CNTRCT_ID">
											<CNTRCT_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CNTRCT_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:CNTRCT_LINE_NBR">
											<CNTRCT_LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CNTRCT_LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:RELEASE_NBR">
											<RELEASE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RELEASE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:MILESTONE_NBR">
											<MILESTONE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MILESTONE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:CNTRCT_RATE_MULT">
											<CNTRCT_RATE_MULT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CNTRCT_RATE_MULT>
										</xsl:for-each>
										<xsl:for-each select="n1:CNTRCT_RATE_DIV">
											<CNTRCT_RATE_DIV>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CNTRCT_RATE_DIV>
										</xsl:for-each>
										<xsl:for-each select="n1:RFQ_ID">
											<RFQ_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</RFQ_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:RFQ_LINE_NBR">
											<RFQ_LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RFQ_LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:INSPECT_CD">
											<INSPECT_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</INSPECT_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:ROUTING_ID">
											<ROUTING_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ROUTING_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:RECV_REQ">
											<RECV_REQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RECV_REQ>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_CAN_CHANGE">
											<PRICE_CAN_CHANGE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_CAN_CHANGE>
										</xsl:for-each>
										<xsl:for-each select="n1:WTHD_SW">
											<WTHD_SW>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</WTHD_SW>
										</xsl:for-each>
										<xsl:for-each select="n1:WTHD_CD">
											<WTHD_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</WTHD_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:CONFIG_CODE">
											<CONFIG_CODE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CONFIG_CODE>
										</xsl:for-each>
										<xsl:for-each select="n1:CP_TEMPLATE_ID">
											<CP_TEMPLATE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CP_TEMPLATE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:DESCR254_MIXED">
											<DESCR254_MIXED>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DESCR254_MIXED>
										</xsl:for-each>
										<xsl:for-each select="n1:PACKING_WEIGHT">
											<PACKING_WEIGHT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PACKING_WEIGHT>
										</xsl:for-each>
										<xsl:for-each select="n1:PACKING_VOLUME">
											<PACKING_VOLUME>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PACKING_VOLUME>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_MEASURE_WT">
											<UNIT_MEASURE_WT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</UNIT_MEASURE_WT>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_MEASURE_VOL">
											<UNIT_MEASURE_VOL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</UNIT_MEASURE_VOL>
										</xsl:for-each>
										<xsl:for-each select="n1:REPLEN_OPT">
											<REPLEN_OPT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</REPLEN_OPT>
										</xsl:for-each>
										<xsl:for-each select="n1:AMT_ONLY_FLG">
											<AMT_ONLY_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</AMT_ONLY_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:USER_LINE_CHAR1">
											<USER_LINE_CHAR1>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</USER_LINE_CHAR1>
										</xsl:for-each>
										<xsl:for-each select="n1:GPO_ID">
											<GPO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</GPO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:GPO_CNTRCT_NBR">
											<GPO_CNTRCT_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</GPO_CNTRCT_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:MX_ITM_CAT">
											<MX_ITM_CAT>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:CATEGORY_CD">
													<CATEGORY_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CATEGORY_CD>
												</xsl:for-each>
											</MX_ITM_CAT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_LINE_SHIP">
											<PO_LINE_SHIP>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT">
													<BUSINESS_UNIT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_ID">
													<PO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:LINE_NBR">
													<LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:SCHED_NBR">
													<SCHED_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SCHED_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:CANCEL_STATUS">
													<CANCEL_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CANCEL_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:BAL_STATUS">
													<BAL_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BAL_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:CHANGE_STATUS">
													<CHANGE_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHANGE_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:CHNG_ORD_SEQ">
													<CHNG_ORD_SEQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHNG_ORD_SEQ>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_PO">
													<PRICE_PO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_PO>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_PO_BSE">
													<PRICE_PO_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_PO_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:CUSTOM_PRICE">
													<CUSTOM_PRICE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CUSTOM_PRICE>
												</xsl:for-each>
												<xsl:for-each select="n1:ZERO_PRICE_IND">
													<ZERO_PRICE_IND>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ZERO_PRICE_IND>
												</xsl:for-each>
												<xsl:for-each select="n1:DUE_DT">
													<DUE_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DUE_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:DUE_TIME">
													<DUE_TIME>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</DUE_TIME>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIPTO_SETID">
													<SHIPTO_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SHIPTO_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIPTO_ID">
													<SHIPTO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SHIPTO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:ORIG_PROM_DT">
													<ORIG_PROM_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ORIG_PROM_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:QTY_PO">
													<QTY_PO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</QTY_PO>
												</xsl:for-each>
												<xsl:for-each select="n1:CURRENCY_CD">
													<CURRENCY_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CURRENCY_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:MERCHANDISE_AMT">
													<MERCHANDISE_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MERCHANDISE_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:CURRENCY_CD_BASE">
													<CURRENCY_CD_BASE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CURRENCY_CD_BASE>
												</xsl:for-each>
												<xsl:for-each select="n1:MERCH_AMT_BSE">
													<MERCH_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MERCH_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:FREIGHT_TERMS">
													<FREIGHT_TERMS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FREIGHT_TERMS>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIP_TYPE_ID">
													<SHIP_TYPE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SHIP_TYPE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:DISTRIB_MTHD_FLG">
													<DISTRIB_MTHD_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DISTRIB_MTHD_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_PRC_TOL">
													<UNIT_PRC_TOL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</UNIT_PRC_TOL>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_PRC_TOL_BSE">
													<UNIT_PRC_TOL_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</UNIT_PRC_TOL_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:PCT_UNIT_PRC_TOL">
													<PCT_UNIT_PRC_TOL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PCT_UNIT_PRC_TOL>
												</xsl:for-each>
												<xsl:for-each select="n1:EXT_PRC_TOL">
													<EXT_PRC_TOL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</EXT_PRC_TOL>
												</xsl:for-each>
												<xsl:for-each select="n1:EXT_PRC_TOL_BSE">
													<EXT_PRC_TOL_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</EXT_PRC_TOL_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:PCT_EXT_PRC_TOL">
													<PCT_EXT_PRC_TOL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PCT_EXT_PRC_TOL>
												</xsl:for-each>
												<xsl:for-each select="n1:QTY_RECV_TOL_PCT">
													<QTY_RECV_TOL_PCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</QTY_RECV_TOL_PCT>
												</xsl:for-each>
												<xsl:for-each select="n1:PCT_UNDER_QTY">
													<PCT_UNDER_QTY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PCT_UNDER_QTY>
												</xsl:for-each>
												<xsl:for-each select="n1:MATCH_STATUS_LN_PO">
													<MATCH_STATUS_LN_PO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MATCH_STATUS_LN_PO>
												</xsl:for-each>
												<xsl:for-each select="n1:MATCH_LINE_OPT">
													<MATCH_LINE_OPT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MATCH_LINE_OPT>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_OM">
													<BUSINESS_UNIT_OM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUSINESS_UNIT_OM>
												</xsl:for-each>
												<xsl:for-each select="n1:ORDER_NO">
													<ORDER_NO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ORDER_NO>
												</xsl:for-each>
												<xsl:for-each select="n1:ORDER_INT_LINE_NO">
													<ORDER_INT_LINE_NO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ORDER_INT_LINE_NO>
												</xsl:for-each>
												<xsl:for-each select="n1:SCHED_LINE_NBR">
													<SCHED_LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SCHED_LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIP_TO_CUST_ID">
													<SHIP_TO_CUST_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SHIP_TO_CUST_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_IN">
													<BUSINESS_UNIT_IN>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUSINESS_UNIT_IN>
												</xsl:for-each>
												<xsl:for-each select="n1:PRODUCTION_ID">
													<PRODUCTION_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PRODUCTION_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:OP_SEQUENCE">
													<OP_SEQUENCE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</OP_SEQUENCE>
												</xsl:for-each>
												<xsl:for-each select="n1:FROZEN_FLG">
													<FROZEN_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FROZEN_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:PLAN_CHANGE_FLG">
													<PLAN_CHANGE_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PLAN_CHANGE_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:NET_CHANGE_EP">
													<NET_CHANGE_EP>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</NET_CHANGE_EP>
												</xsl:for-each>
												<xsl:for-each select="n1:CARRIER_ID">
													<CARRIER_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CARRIER_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:SUT_BASE_ID">
													<SUT_BASE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SUT_BASE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_CD_SUT">
													<TAX_CD_SUT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TAX_CD_SUT>
												</xsl:for-each>
												<xsl:for-each select="n1:ULTIMATE_USE_CD">
													<ULTIMATE_USE_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ULTIMATE_USE_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:SUT_EXCPTN_TYPE">
													<SUT_EXCPTN_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SUT_EXCPTN_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:SUT_EXCPTN_CERTIF">
													<SUT_EXCPTN_CERTIF>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SUT_EXCPTN_CERTIF>
												</xsl:for-each>
												<xsl:for-each select="n1:SUT_APPLICABILITY">
													<SUT_APPLICABILITY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SUT_APPLICABILITY>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RCRD_INPT_FLG">
													<VAT_RCRD_INPT_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RCRD_INPT_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RCRD_OUTPT_FLG">
													<VAT_RCRD_OUTPT_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RCRD_OUTPT_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_DCLRTN_POINT">
													<VAT_DCLRTN_POINT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_DCLRTN_POINT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_CALC_GROSS_NET">
													<VAT_CALC_GROSS_NET>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_CALC_GROSS_NET>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_CALC_FRGHT_FLG">
													<VAT_CALC_FRGHT_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_CALC_FRGHT_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_CALC_MISC_FLG">
													<VAT_CALC_MISC_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_CALC_MISC_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RECALC_FLG">
													<VAT_RECALC_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RECALC_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_TREATMENT_PUR">
													<VAT_TREATMENT_PUR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_TREATMENT_PUR>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_EXCPTN_TYPE">
													<VAT_EXCPTN_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_EXCPTN_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_EXCPTN_CERTIF">
													<VAT_EXCPTN_CERTIF>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_EXCPTN_CERTIF>
												</xsl:for-each>
												<xsl:for-each select="n1:COUNTRY_SHIP_TO">
													<COUNTRY_SHIP_TO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</COUNTRY_SHIP_TO>
												</xsl:for-each>
												<xsl:for-each select="n1:COUNTRY_SHIP_FROM">
													<COUNTRY_SHIP_FROM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</COUNTRY_SHIP_FROM>
												</xsl:for-each>
												<xsl:for-each select="n1:COUNTRY_VAT_SHIPTO">
													<COUNTRY_VAT_SHIPTO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</COUNTRY_VAT_SHIPTO>
												</xsl:for-each>
												<xsl:for-each select="n1:COUNTRY_VAT_BILLTO">
													<COUNTRY_VAT_BILLTO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</COUNTRY_VAT_BILLTO>
												</xsl:for-each>
												<xsl:for-each select="n1:COUNTRY_VAT_BILLFR">
													<COUNTRY_VAT_BILLFR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</COUNTRY_VAT_BILLFR>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RGSTRN_SELLER">
													<VAT_RGSTRN_SELLER>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_RGSTRN_SELLER>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
													<VAT_TXN_TYPE_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_TXN_TYPE_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_APPLICABILITY">
													<VAT_APPLICABILITY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_APPLICABILITY>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_CD_VAT">
													<TAX_CD_VAT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TAX_CD_VAT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_USE_ID">
													<VAT_USE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_USE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:IST_TXN_FLG">
													<IST_TXN_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</IST_TXN_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_CALC_TYPE">
													<VAT_CALC_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_CALC_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_RTV">
													<BUSINESS_UNIT_RTV>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUSINESS_UNIT_RTV>
												</xsl:for-each>
												<xsl:for-each select="n1:RTV_ID">
													<RTV_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</RTV_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:RTV_LN_NBR">
													<RTV_LN_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RTV_LN_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:RTV_VERIFIED">
													<RTV_VERIFIED>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</RTV_VERIFIED>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIP_DATE">
													<SHIP_DATE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SHIP_DATE>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_PRC_TOL_L">
													<UNIT_PRC_TOL_L>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</UNIT_PRC_TOL_L>
												</xsl:for-each>
												<xsl:for-each select="n1:PCT_UNIT_PRC_TOL_L">
													<PCT_UNIT_PRC_TOL_L>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PCT_UNIT_PRC_TOL_L>
												</xsl:for-each>
												<xsl:for-each select="n1:EXT_PRC_TOL_L">
													<EXT_PRC_TOL_L>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</EXT_PRC_TOL_L>
												</xsl:for-each>
												<xsl:for-each select="n1:PCT_EXT_PRC_TOL_L">
													<PCT_EXT_PRC_TOL_L>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PCT_EXT_PRC_TOL_L>
												</xsl:for-each>
												<xsl:for-each select="n1:UNIT_PRC_TOL_BSE_L">
													<UNIT_PRC_TOL_BSE_L>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</UNIT_PRC_TOL_BSE_L>
												</xsl:for-each>
												<xsl:for-each select="n1:EXT_PRC_TOL_BSE_L">
													<EXT_PRC_TOL_BSE_L>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</EXT_PRC_TOL_BSE_L>
												</xsl:for-each>
												<xsl:for-each select="n1:RJCT_OVER_TOL_FLAG">
													<RJCT_OVER_TOL_FLAG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RJCT_OVER_TOL_FLAG>
												</xsl:for-each>
												<xsl:for-each select="n1:REJECT_DAYS">
													<REJECT_DAYS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</REJECT_DAYS>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_VAT_FLG">
													<TAX_VAT_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_VAT_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_FRGHT_FLG">
													<TAX_FRGHT_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_FRGHT_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_MISC_FLG">
													<TAX_MISC_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_MISC_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:TRFT_RULE_CD">
													<TRFT_RULE_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TRFT_RULE_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:QTY_RFQ">
													<QTY_RFQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</QTY_RFQ>
												</xsl:for-each>
												<xsl:for-each select="n1:SHIP_ID_EST">
													<SHIP_ID_EST>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</SHIP_ID_EST>
												</xsl:for-each>
												<xsl:for-each select="n1:X_VENDOR_SETID">
													<X_VENDOR_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</X_VENDOR_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:X_VENDOR_ID">
													<X_VENDOR_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</X_VENDOR_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:X_VNDR_LOC">
													<X_VNDR_LOC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</X_VNDR_LOC>
												</xsl:for-each>
												<xsl:for-each select="n1:FRT_CHRG_METHOD">
													<FRT_CHRG_METHOD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</FRT_CHRG_METHOD>
												</xsl:for-each>
												<xsl:for-each select="n1:FRT_CHRG_OVERRIDE">
													<FRT_CHRG_OVERRIDE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FRT_CHRG_OVERRIDE>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_ROUND_RULE">
													<VAT_ROUND_RULE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_ROUND_RULE>
												</xsl:for-each>
												<xsl:for-each select="n1:REVISION">
													<REVISION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</REVISION>
												</xsl:for-each>
												<xsl:for-each select="n1:PUBLISHED_SHIPTO">
													<PUBLISHED_SHIPTO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PUBLISHED_SHIPTO>
												</xsl:for-each>
												<xsl:for-each select="n1:BCKORD_ORG_SCHED">
													<BCKORD_ORG_SCHED>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BCKORD_ORG_SCHED>
												</xsl:for-each>
												<xsl:for-each select="n1:USER_SCHED_CHAR1">
													<USER_SCHED_CHAR1>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</USER_SCHED_CHAR1>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_LINE_DISTRIB">
													<PO_LINE_DISTRIB>
														<xsl:for-each select="@class">
															<xsl:attribute name="class">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT">
															<BUSINESS_UNIT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUSINESS_UNIT>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_ID">
															<PO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:LINE_NBR">
															<LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:SCHED_NBR">
															<SCHED_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SCHED_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:DST_ACCT_TYPE">
															<DST_ACCT_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DST_ACCT_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:DISTRIB_LINE_NUM">
															<DISTRIB_LINE_NUM>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DISTRIB_LINE_NUM>
														</xsl:for-each>
														<xsl:for-each select="n1:QTY_PO">
															<QTY_PO>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</QTY_PO>
														</xsl:for-each>
														<xsl:for-each select="n1:CURRENCY_CD">
															<CURRENCY_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CURRENCY_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:MERCHANDISE_AMT">
															<MERCHANDISE_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MERCHANDISE_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:CURRENCY_CD_BASE">
															<CURRENCY_CD_BASE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CURRENCY_CD_BASE>
														</xsl:for-each>
														<xsl:for-each select="n1:MERCH_AMT_BSE">
															<MERCH_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MERCH_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:LOCATION">
															<LOCATION>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</LOCATION>
														</xsl:for-each>
														<xsl:for-each select="n1:ACCOUNT">
															<ACCOUNT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ACCOUNT>
														</xsl:for-each>
														<xsl:for-each select="n1:ALTACCT">
															<ALTACCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ALTACCT>
														</xsl:for-each>
														<xsl:for-each select="n1:DEPTID">
															<DEPTID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DEPTID>
														</xsl:for-each>
														<xsl:for-each select="n1:OPERATING_UNIT">
															<OPERATING_UNIT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</OPERATING_UNIT>
														</xsl:for-each>
														<xsl:for-each select="n1:PRODUCT">
															<PRODUCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PRODUCT>
														</xsl:for-each>
														<xsl:for-each select="n1:FUND_CODE">
															<FUND_CODE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</FUND_CODE>
														</xsl:for-each>
														<xsl:for-each select="n1:CLASS_FLD">
															<CLASS_FLD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CLASS_FLD>
														</xsl:for-each>
														<xsl:for-each select="n1:PROGRAM_CODE">
															<PROGRAM_CODE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PROGRAM_CODE>
														</xsl:for-each>
														<xsl:for-each select="n1:BUDGET_REF">
															<BUDGET_REF>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUDGET_REF>
														</xsl:for-each>
														<xsl:for-each select="n1:AFFILIATE">
															<AFFILIATE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</AFFILIATE>
														</xsl:for-each>
														<xsl:for-each select="n1:AFFILIATE_INTRA1">
															<AFFILIATE_INTRA1>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</AFFILIATE_INTRA1>
														</xsl:for-each>
														<xsl:for-each select="n1:AFFILIATE_INTRA2">
															<AFFILIATE_INTRA2>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</AFFILIATE_INTRA2>
														</xsl:for-each>
														<xsl:for-each select="n1:CHARTFIELD1">
															<CHARTFIELD1>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CHARTFIELD1>
														</xsl:for-each>
														<xsl:for-each select="n1:CHARTFIELD2">
															<CHARTFIELD2>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CHARTFIELD2>
														</xsl:for-each>
														<xsl:for-each select="n1:CHARTFIELD3">
															<CHARTFIELD3>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CHARTFIELD3>
														</xsl:for-each>
														<xsl:for-each select="n1:PROJECT_ID">
															<PROJECT_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PROJECT_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:STATISTICS_CODE">
															<STATISTICS_CODE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</STATISTICS_CODE>
														</xsl:for-each>
														<xsl:for-each select="n1:STATISTIC_AMOUNT">
															<STATISTIC_AMOUNT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</STATISTIC_AMOUNT>
														</xsl:for-each>
														<xsl:for-each select="n1:CHARTFIELD_STATUS">
															<CHARTFIELD_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CHARTFIELD_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_GL">
															<BUSINESS_UNIT_GL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUSINESS_UNIT_GL>
														</xsl:for-each>
														<xsl:for-each select="n1:DISTRIB_LN_STATUS">
															<DISTRIB_LN_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DISTRIB_LN_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:DIST_PROCESSED_FLG">
															<DIST_PROCESSED_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DIST_PROCESSED_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:SYSTEM_SOURCE">
															<SYSTEM_SOURCE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SYSTEM_SOURCE>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_REQ">
															<BUSINESS_UNIT_REQ>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUSINESS_UNIT_REQ>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_PC">
															<BUSINESS_UNIT_PC>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUSINESS_UNIT_PC>
														</xsl:for-each>
														<xsl:for-each select="n1:ACTIVITY_ID">
															<ACTIVITY_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ACTIVITY_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:ANALYSIS_TYPE">
															<ANALYSIS_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ANALYSIS_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:RESOURCE_TYPE">
															<RESOURCE_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</RESOURCE_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:RESOURCE_CATEGORY">
															<RESOURCE_CATEGORY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</RESOURCE_CATEGORY>
														</xsl:for-each>
														<xsl:for-each select="n1:RESOURCE_SUB_CAT">
															<RESOURCE_SUB_CAT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</RESOURCE_SUB_CAT>
														</xsl:for-each>
														<xsl:for-each select="n1:PROCESS_INSTANCE">
															<PROCESS_INSTANCE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PROCESS_INSTANCE>
														</xsl:for-each>
														<xsl:for-each select="n1:PROCESS_MAN_CLOSE">
															<PROCESS_MAN_CLOSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PROCESS_MAN_CLOSE>
														</xsl:for-each>
														<xsl:for-each select="n1:REQ_ID">
															<REQ_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</REQ_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:REQ_LINE_NBR">
															<REQ_LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</REQ_LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:REQ_SCHED_NBR">
															<REQ_SCHED_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</REQ_SCHED_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:REQ_DISTRIB_NBR">
															<REQ_DISTRIB_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</REQ_DISTRIB_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_POST_AMT">
															<PO_POST_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_POST_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_POST_AMT_BSE">
															<PO_POST_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_POST_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:PC_DISTRIB_STATUS">
															<PC_DISTRIB_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PC_DISTRIB_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:PC_DISTRIB_AMT">
															<PC_DISTRIB_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PC_DISTRIB_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:PC_DISTRIB_AMT_BSE">
															<PC_DISTRIB_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PC_DISTRIB_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:PROFILE_ID">
															<PROFILE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PROFILE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:TAG_NUMBER">
															<TAG_NUMBER>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</TAG_NUMBER>
														</xsl:for-each>
														<xsl:for-each select="n1:CAP_NUM">
															<CAP_NUM>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CAP_NUM>
														</xsl:for-each>
														<xsl:for-each select="n1:CAP_SEQUENCE">
															<CAP_SEQUENCE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CAP_SEQUENCE>
														</xsl:for-each>
														<xsl:for-each select="n1:EMPLID">
															<EMPLID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</EMPLID>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_AM">
															<BUSINESS_UNIT_AM>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUSINESS_UNIT_AM>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT_IN">
															<BUSINESS_UNIT_IN>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</BUSINESS_UNIT_IN>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_POST_STATUS">
															<PO_POST_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_POST_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:CLOSE_AMOUNT">
															<CLOSE_AMOUNT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CLOSE_AMOUNT>
														</xsl:for-each>
														<xsl:for-each select="n1:CLOSE_AMOUNT_BSE">
															<CLOSE_AMOUNT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CLOSE_AMOUNT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:QTY_REQ">
															<QTY_REQ>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</QTY_REQ>
														</xsl:for-each>
														<xsl:for-each select="n1:DISTRIB_TYPE">
															<DISTRIB_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</DISTRIB_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:DISTRIB_PCT">
															<DISTRIB_PCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DISTRIB_PCT>
														</xsl:for-each>
														<xsl:for-each select="n1:INVOICE_CLOSE_IND">
															<INVOICE_CLOSE_IND>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</INVOICE_CLOSE_IND>
														</xsl:for-each>
														<xsl:for-each select="n1:FINANCIAL_ASSET_SW">
															<FINANCIAL_ASSET_SW>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FINANCIAL_ASSET_SW>
														</xsl:for-each>
														<xsl:for-each select="n1:COST_TYPE">
															<COST_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</COST_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_CD_SUT">
															<TAX_CD_SUT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</TAX_CD_SUT>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_CD_SUT_PCT">
															<TAX_CD_SUT_PCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</TAX_CD_SUT_PCT>
														</xsl:for-each>
														<xsl:for-each select="n1:SALETX_AMT">
															<SALETX_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SALETX_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:SALETX_AMT_BSE">
															<SALETX_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SALETX_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:USETAX_AMT">
															<USETAX_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</USETAX_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:USETAX_AMT_BSE">
															<USETAX_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</USETAX_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:SUT_APPLICABILITY">
															<SUT_APPLICABILITY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SUT_APPLICABILITY>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
															<VAT_TXN_TYPE_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_TXN_TYPE_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_APPLICABILITY">
															<VAT_APPLICABILITY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_APPLICABILITY>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_CD_VAT">
															<TAX_CD_VAT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</TAX_CD_VAT>
														</xsl:for-each>
														<xsl:for-each select="n1:TAX_CD_VAT_PCT">
															<TAX_CD_VAT_PCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</TAX_CD_VAT_PCT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_AMT">
															<VAT_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_AMT_BASE">
															<VAT_AMT_BASE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_AMT_BASE>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_BASIS_AMT">
															<VAT_BASIS_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_BASIS_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_BASIS_AMT_BSE">
															<VAT_BASIS_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_BASIS_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RECOVERY_PCT">
															<VAT_RECOVERY_PCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RECOVERY_PCT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RCVRY_AMT">
															<VAT_RCVRY_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RCVRY_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RCVRY_AMT_BSE">
															<VAT_RCVRY_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RCVRY_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_REBATE_PCT">
															<VAT_REBATE_PCT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_REBATE_PCT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_REBATE_AMT">
															<VAT_REBATE_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_REBATE_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_REBATE_AMT_BSE">
															<VAT_REBATE_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_REBATE_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_USE_ID">
															<VAT_USE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VAT_USE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:RT_TYPE">
															<RT_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RT_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:RATE_MULT">
															<RATE_MULT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RATE_MULT>
														</xsl:for-each>
														<xsl:for-each select="n1:RATE_DIV">
															<RATE_DIV>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RATE_DIV>
														</xsl:for-each>
														<xsl:for-each select="n1:FREIGHT_AMT">
															<FREIGHT_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FREIGHT_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:FREIGHT_AMT_BSE">
															<FREIGHT_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FREIGHT_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:FREIGHT_AMT_NP">
															<FREIGHT_AMT_NP>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FREIGHT_AMT_NP>
														</xsl:for-each>
														<xsl:for-each select="n1:FREIGHT_AMT_NP_BSE">
															<FREIGHT_AMT_NP_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</FREIGHT_AMT_NP_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:MISC_AMT">
															<MISC_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MISC_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:MISC_AMT_BSE">
															<MISC_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MISC_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:MISC_AMT_NP">
															<MISC_AMT_NP>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MISC_AMT_NP>
														</xsl:for-each>
														<xsl:for-each select="n1:MISC_AMT_NP_BSE">
															<MISC_AMT_NP_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MISC_AMT_NP_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:MONETARY_AMOUNT">
															<MONETARY_AMOUNT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MONETARY_AMOUNT>
														</xsl:for-each>
														<xsl:for-each select="n1:MONETARY_AMT_BSE">
															<MONETARY_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MONETARY_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:BUDGET_DT">
															<BUDGET_DT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUDGET_DT>
														</xsl:for-each>
														<xsl:for-each select="n1:BUDGET_LINE_STATUS">
															<BUDGET_LINE_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUDGET_LINE_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:KK_CLOSE_FLAG">
															<KK_CLOSE_FLAG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</KK_CLOSE_FLAG>
														</xsl:for-each>
														<xsl:for-each select="n1:KK_PROCESS_PRIOR">
															<KK_PROCESS_PRIOR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</KK_PROCESS_PRIOR>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_CALC_TYPE">
															<VAT_CALC_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_CALC_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:SALETX_PRORATE_FLG">
															<SALETX_PRORATE_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SALETX_PRORATE_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:USETAX_PRORATE_FLG">
															<USETAX_PRORATE_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</USETAX_PRORATE_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_NRCVR_PRO_FLG">
															<VAT_NRCVR_PRO_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_NRCVR_PRO_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_PRORATE_FLG">
															<VAT_PRORATE_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_PRORATE_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:CONSIGNED_FLAG">
															<CONSIGNED_FLAG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CONSIGNED_FLAG>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_RCVRY_PCT_SRC">
															<VAT_RCVRY_PCT_SRC>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_RCVRY_PCT_SRC>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_REBATE_PCT_SRC">
															<VAT_REBATE_PCT_SRC>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_REBATE_PCT_SRC>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_TRANS_AMT">
															<VAT_TRANS_AMT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_TRANS_AMT>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_TRANS_AMT_BSE">
															<VAT_TRANS_AMT_BSE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_TRANS_AMT_BSE>
														</xsl:for-each>
														<xsl:for-each select="n1:PUBLISHED_IBU">
															<PUBLISHED_IBU>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</PUBLISHED_IBU>
														</xsl:for-each>
														<xsl:for-each select="n1:VAT_APORT_CNTRL">
															<VAT_APORT_CNTRL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VAT_APORT_CNTRL>
														</xsl:for-each>
														<xsl:for-each select="n1:KK_CLOSE_PRIOR">
															<KK_CLOSE_PRIOR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</KK_CLOSE_PRIOR>
														</xsl:for-each>
														<xsl:for-each select="n1:DOC_TOL_LN_STATUS">
															<DOC_TOL_LN_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DOC_TOL_LN_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:ROLL_STAT_R">
															<ROLL_STAT_R>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ROLL_STAT_R>
														</xsl:for-each>
														<xsl:for-each select="n1:USER_DIST_CHAR1">
															<USER_DIST_CHAR1>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</USER_DIST_CHAR1>
														</xsl:for-each>
														<xsl:for-each select="n1:ENTRY_EVENT">
															<ENTRY_EVENT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ENTRY_EVENT>
														</xsl:for-each>
													</PO_LINE_DISTRIB>
												</xsl:for-each>
											</PO_LINE_SHIP>
										</xsl:for-each>
									</PO_LINE>
								</xsl:for-each>
								<xsl:for-each select="n1:MX_ITM_CAT">
									<MX_ITM_CAT>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:CATEGORY_CD">
											<CATEGORY_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CATEGORY_CD>
										</xsl:for-each>
									</MX_ITM_CAT>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_LINE_SHIP">
									<PO_LINE_SHIP>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:LINE_NBR">
											<LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:SCHED_NBR">
											<SCHED_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SCHED_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:CANCEL_STATUS">
											<CANCEL_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CANCEL_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:BAL_STATUS">
											<BAL_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BAL_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:CHANGE_STATUS">
											<CHANGE_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHANGE_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:CHNG_ORD_SEQ">
											<CHNG_ORD_SEQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHNG_ORD_SEQ>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_PO">
											<PRICE_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:PRICE_PO_BSE">
											<PRICE_PO_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PRICE_PO_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:CUSTOM_PRICE">
											<CUSTOM_PRICE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CUSTOM_PRICE>
										</xsl:for-each>
										<xsl:for-each select="n1:ZERO_PRICE_IND">
											<ZERO_PRICE_IND>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ZERO_PRICE_IND>
										</xsl:for-each>
										<xsl:for-each select="n1:DUE_DT">
											<DUE_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DUE_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:DUE_TIME">
											<DUE_TIME>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</DUE_TIME>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIPTO_SETID">
											<SHIPTO_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SHIPTO_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIPTO_ID">
											<SHIPTO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SHIPTO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:ORIG_PROM_DT">
											<ORIG_PROM_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ORIG_PROM_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_PO">
											<QTY_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD">
											<CURRENCY_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:MERCHANDISE_AMT">
											<MERCHANDISE_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MERCHANDISE_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD_BASE">
											<CURRENCY_CD_BASE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD_BASE>
										</xsl:for-each>
										<xsl:for-each select="n1:MERCH_AMT_BSE">
											<MERCH_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MERCH_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:FREIGHT_TERMS">
											<FREIGHT_TERMS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FREIGHT_TERMS>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIP_TYPE_ID">
											<SHIP_TYPE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SHIP_TYPE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:DISTRIB_MTHD_FLG">
											<DISTRIB_MTHD_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISTRIB_MTHD_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_PRC_TOL">
											<UNIT_PRC_TOL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</UNIT_PRC_TOL>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_PRC_TOL_BSE">
											<UNIT_PRC_TOL_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</UNIT_PRC_TOL_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:PCT_UNIT_PRC_TOL">
											<PCT_UNIT_PRC_TOL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PCT_UNIT_PRC_TOL>
										</xsl:for-each>
										<xsl:for-each select="n1:EXT_PRC_TOL">
											<EXT_PRC_TOL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</EXT_PRC_TOL>
										</xsl:for-each>
										<xsl:for-each select="n1:EXT_PRC_TOL_BSE">
											<EXT_PRC_TOL_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</EXT_PRC_TOL_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:PCT_EXT_PRC_TOL">
											<PCT_EXT_PRC_TOL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PCT_EXT_PRC_TOL>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_RECV_TOL_PCT">
											<QTY_RECV_TOL_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_RECV_TOL_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:PCT_UNDER_QTY">
											<PCT_UNDER_QTY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PCT_UNDER_QTY>
										</xsl:for-each>
										<xsl:for-each select="n1:MATCH_STATUS_LN_PO">
											<MATCH_STATUS_LN_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MATCH_STATUS_LN_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:MATCH_LINE_OPT">
											<MATCH_LINE_OPT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MATCH_LINE_OPT>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_OM">
											<BUSINESS_UNIT_OM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUSINESS_UNIT_OM>
										</xsl:for-each>
										<xsl:for-each select="n1:ORDER_NO">
											<ORDER_NO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ORDER_NO>
										</xsl:for-each>
										<xsl:for-each select="n1:ORDER_INT_LINE_NO">
											<ORDER_INT_LINE_NO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ORDER_INT_LINE_NO>
										</xsl:for-each>
										<xsl:for-each select="n1:SCHED_LINE_NBR">
											<SCHED_LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SCHED_LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIP_TO_CUST_ID">
											<SHIP_TO_CUST_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SHIP_TO_CUST_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_IN">
											<BUSINESS_UNIT_IN>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUSINESS_UNIT_IN>
										</xsl:for-each>
										<xsl:for-each select="n1:PRODUCTION_ID">
											<PRODUCTION_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PRODUCTION_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:OP_SEQUENCE">
											<OP_SEQUENCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</OP_SEQUENCE>
										</xsl:for-each>
										<xsl:for-each select="n1:FROZEN_FLG">
											<FROZEN_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FROZEN_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:PLAN_CHANGE_FLG">
											<PLAN_CHANGE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PLAN_CHANGE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:NET_CHANGE_EP">
											<NET_CHANGE_EP>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</NET_CHANGE_EP>
										</xsl:for-each>
										<xsl:for-each select="n1:CARRIER_ID">
											<CARRIER_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CARRIER_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:SUT_BASE_ID">
											<SUT_BASE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SUT_BASE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD_SUT">
											<TAX_CD_SUT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TAX_CD_SUT>
										</xsl:for-each>
										<xsl:for-each select="n1:ULTIMATE_USE_CD">
											<ULTIMATE_USE_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ULTIMATE_USE_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:SUT_EXCPTN_TYPE">
											<SUT_EXCPTN_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SUT_EXCPTN_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:SUT_EXCPTN_CERTIF">
											<SUT_EXCPTN_CERTIF>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SUT_EXCPTN_CERTIF>
										</xsl:for-each>
										<xsl:for-each select="n1:SUT_APPLICABILITY">
											<SUT_APPLICABILITY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SUT_APPLICABILITY>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RCRD_INPT_FLG">
											<VAT_RCRD_INPT_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RCRD_INPT_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RCRD_OUTPT_FLG">
											<VAT_RCRD_OUTPT_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RCRD_OUTPT_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_DCLRTN_POINT">
											<VAT_DCLRTN_POINT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_DCLRTN_POINT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_CALC_GROSS_NET">
											<VAT_CALC_GROSS_NET>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_CALC_GROSS_NET>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_CALC_FRGHT_FLG">
											<VAT_CALC_FRGHT_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_CALC_FRGHT_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_CALC_MISC_FLG">
											<VAT_CALC_MISC_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_CALC_MISC_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RECALC_FLG">
											<VAT_RECALC_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RECALC_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_TREATMENT_PUR">
											<VAT_TREATMENT_PUR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_TREATMENT_PUR>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_EXCPTN_TYPE">
											<VAT_EXCPTN_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_EXCPTN_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_EXCPTN_CERTIF">
											<VAT_EXCPTN_CERTIF>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_EXCPTN_CERTIF>
										</xsl:for-each>
										<xsl:for-each select="n1:COUNTRY_SHIP_TO">
											<COUNTRY_SHIP_TO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</COUNTRY_SHIP_TO>
										</xsl:for-each>
										<xsl:for-each select="n1:COUNTRY_SHIP_FROM">
											<COUNTRY_SHIP_FROM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</COUNTRY_SHIP_FROM>
										</xsl:for-each>
										<xsl:for-each select="n1:COUNTRY_VAT_SHIPTO">
											<COUNTRY_VAT_SHIPTO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</COUNTRY_VAT_SHIPTO>
										</xsl:for-each>
										<xsl:for-each select="n1:COUNTRY_VAT_BILLTO">
											<COUNTRY_VAT_BILLTO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</COUNTRY_VAT_BILLTO>
										</xsl:for-each>
										<xsl:for-each select="n1:COUNTRY_VAT_BILLFR">
											<COUNTRY_VAT_BILLFR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</COUNTRY_VAT_BILLFR>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RGSTRN_SELLER">
											<VAT_RGSTRN_SELLER>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_RGSTRN_SELLER>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
											<VAT_TXN_TYPE_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_TXN_TYPE_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_APPLICABILITY">
											<VAT_APPLICABILITY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_APPLICABILITY>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD_VAT">
											<TAX_CD_VAT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TAX_CD_VAT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_USE_ID">
											<VAT_USE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_USE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:IST_TXN_FLG">
											<IST_TXN_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</IST_TXN_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_CALC_TYPE">
											<VAT_CALC_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_CALC_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_RTV">
											<BUSINESS_UNIT_RTV>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUSINESS_UNIT_RTV>
										</xsl:for-each>
										<xsl:for-each select="n1:RTV_ID">
											<RTV_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</RTV_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:RTV_LN_NBR">
											<RTV_LN_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RTV_LN_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:RTV_VERIFIED">
											<RTV_VERIFIED>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</RTV_VERIFIED>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIP_DATE">
											<SHIP_DATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SHIP_DATE>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_PRC_TOL_L">
											<UNIT_PRC_TOL_L>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</UNIT_PRC_TOL_L>
										</xsl:for-each>
										<xsl:for-each select="n1:PCT_UNIT_PRC_TOL_L">
											<PCT_UNIT_PRC_TOL_L>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PCT_UNIT_PRC_TOL_L>
										</xsl:for-each>
										<xsl:for-each select="n1:EXT_PRC_TOL_L">
											<EXT_PRC_TOL_L>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</EXT_PRC_TOL_L>
										</xsl:for-each>
										<xsl:for-each select="n1:PCT_EXT_PRC_TOL_L">
											<PCT_EXT_PRC_TOL_L>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PCT_EXT_PRC_TOL_L>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_PRC_TOL_BSE_L">
											<UNIT_PRC_TOL_BSE_L>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</UNIT_PRC_TOL_BSE_L>
										</xsl:for-each>
										<xsl:for-each select="n1:EXT_PRC_TOL_BSE_L">
											<EXT_PRC_TOL_BSE_L>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</EXT_PRC_TOL_BSE_L>
										</xsl:for-each>
										<xsl:for-each select="n1:RJCT_OVER_TOL_FLAG">
											<RJCT_OVER_TOL_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RJCT_OVER_TOL_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:REJECT_DAYS">
											<REJECT_DAYS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</REJECT_DAYS>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_VAT_FLG">
											<TAX_VAT_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_VAT_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_FRGHT_FLG">
											<TAX_FRGHT_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_FRGHT_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_MISC_FLG">
											<TAX_MISC_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_MISC_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:TRFT_RULE_CD">
											<TRFT_RULE_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TRFT_RULE_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_RFQ">
											<QTY_RFQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_RFQ>
										</xsl:for-each>
										<xsl:for-each select="n1:SHIP_ID_EST">
											<SHIP_ID_EST>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</SHIP_ID_EST>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VENDOR_SETID">
											<X_VENDOR_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VENDOR_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VENDOR_ID">
											<X_VENDOR_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VENDOR_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VNDR_LOC">
											<X_VNDR_LOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VNDR_LOC>
										</xsl:for-each>
										<xsl:for-each select="n1:FRT_CHRG_METHOD">
											<FRT_CHRG_METHOD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FRT_CHRG_METHOD>
										</xsl:for-each>
										<xsl:for-each select="n1:FRT_CHRG_OVERRIDE">
											<FRT_CHRG_OVERRIDE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FRT_CHRG_OVERRIDE>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_ROUND_RULE">
											<VAT_ROUND_RULE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_ROUND_RULE>
										</xsl:for-each>
										<xsl:for-each select="n1:REVISION">
											<REVISION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</REVISION>
										</xsl:for-each>
										<xsl:for-each select="n1:PUBLISHED_SHIPTO">
											<PUBLISHED_SHIPTO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PUBLISHED_SHIPTO>
										</xsl:for-each>
										<xsl:for-each select="n1:BCKORD_ORG_SCHED">
											<BCKORD_ORG_SCHED>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BCKORD_ORG_SCHED>
										</xsl:for-each>
										<xsl:for-each select="n1:USER_SCHED_CHAR1">
											<USER_SCHED_CHAR1>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</USER_SCHED_CHAR1>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_LINE_DISTRIB">
											<PO_LINE_DISTRIB>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT">
													<BUSINESS_UNIT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_ID">
													<PO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:LINE_NBR">
													<LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:SCHED_NBR">
													<SCHED_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SCHED_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:DST_ACCT_TYPE">
													<DST_ACCT_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DST_ACCT_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:DISTRIB_LINE_NUM">
													<DISTRIB_LINE_NUM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DISTRIB_LINE_NUM>
												</xsl:for-each>
												<xsl:for-each select="n1:QTY_PO">
													<QTY_PO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</QTY_PO>
												</xsl:for-each>
												<xsl:for-each select="n1:CURRENCY_CD">
													<CURRENCY_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CURRENCY_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:MERCHANDISE_AMT">
													<MERCHANDISE_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MERCHANDISE_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:CURRENCY_CD_BASE">
													<CURRENCY_CD_BASE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CURRENCY_CD_BASE>
												</xsl:for-each>
												<xsl:for-each select="n1:MERCH_AMT_BSE">
													<MERCH_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MERCH_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:LOCATION">
													<LOCATION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LOCATION>
												</xsl:for-each>
												<xsl:for-each select="n1:ACCOUNT">
													<ACCOUNT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ACCOUNT>
												</xsl:for-each>
												<xsl:for-each select="n1:ALTACCT">
													<ALTACCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ALTACCT>
												</xsl:for-each>
												<xsl:for-each select="n1:DEPTID">
													<DEPTID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DEPTID>
												</xsl:for-each>
												<xsl:for-each select="n1:OPERATING_UNIT">
													<OPERATING_UNIT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</OPERATING_UNIT>
												</xsl:for-each>
												<xsl:for-each select="n1:PRODUCT">
													<PRODUCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PRODUCT>
												</xsl:for-each>
												<xsl:for-each select="n1:FUND_CODE">
													<FUND_CODE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</FUND_CODE>
												</xsl:for-each>
												<xsl:for-each select="n1:CLASS_FLD">
													<CLASS_FLD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CLASS_FLD>
												</xsl:for-each>
												<xsl:for-each select="n1:PROGRAM_CODE">
													<PROGRAM_CODE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PROGRAM_CODE>
												</xsl:for-each>
												<xsl:for-each select="n1:BUDGET_REF">
													<BUDGET_REF>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUDGET_REF>
												</xsl:for-each>
												<xsl:for-each select="n1:AFFILIATE">
													<AFFILIATE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</AFFILIATE>
												</xsl:for-each>
												<xsl:for-each select="n1:AFFILIATE_INTRA1">
													<AFFILIATE_INTRA1>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</AFFILIATE_INTRA1>
												</xsl:for-each>
												<xsl:for-each select="n1:AFFILIATE_INTRA2">
													<AFFILIATE_INTRA2>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</AFFILIATE_INTRA2>
												</xsl:for-each>
												<xsl:for-each select="n1:CHARTFIELD1">
													<CHARTFIELD1>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CHARTFIELD1>
												</xsl:for-each>
												<xsl:for-each select="n1:CHARTFIELD2">
													<CHARTFIELD2>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CHARTFIELD2>
												</xsl:for-each>
												<xsl:for-each select="n1:CHARTFIELD3">
													<CHARTFIELD3>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CHARTFIELD3>
												</xsl:for-each>
												<xsl:for-each select="n1:PROJECT_ID">
													<PROJECT_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PROJECT_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:STATISTICS_CODE">
													<STATISTICS_CODE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</STATISTICS_CODE>
												</xsl:for-each>
												<xsl:for-each select="n1:STATISTIC_AMOUNT">
													<STATISTIC_AMOUNT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</STATISTIC_AMOUNT>
												</xsl:for-each>
												<xsl:for-each select="n1:CHARTFIELD_STATUS">
													<CHARTFIELD_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHARTFIELD_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_GL">
													<BUSINESS_UNIT_GL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT_GL>
												</xsl:for-each>
												<xsl:for-each select="n1:DISTRIB_LN_STATUS">
													<DISTRIB_LN_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DISTRIB_LN_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:DIST_PROCESSED_FLG">
													<DIST_PROCESSED_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DIST_PROCESSED_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:SYSTEM_SOURCE">
													<SYSTEM_SOURCE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SYSTEM_SOURCE>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_REQ">
													<BUSINESS_UNIT_REQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT_REQ>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_PC">
													<BUSINESS_UNIT_PC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUSINESS_UNIT_PC>
												</xsl:for-each>
												<xsl:for-each select="n1:ACTIVITY_ID">
													<ACTIVITY_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ACTIVITY_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:ANALYSIS_TYPE">
													<ANALYSIS_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ANALYSIS_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:RESOURCE_TYPE">
													<RESOURCE_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</RESOURCE_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:RESOURCE_CATEGORY">
													<RESOURCE_CATEGORY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</RESOURCE_CATEGORY>
												</xsl:for-each>
												<xsl:for-each select="n1:RESOURCE_SUB_CAT">
													<RESOURCE_SUB_CAT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</RESOURCE_SUB_CAT>
												</xsl:for-each>
												<xsl:for-each select="n1:PROCESS_INSTANCE">
													<PROCESS_INSTANCE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PROCESS_INSTANCE>
												</xsl:for-each>
												<xsl:for-each select="n1:PROCESS_MAN_CLOSE">
													<PROCESS_MAN_CLOSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PROCESS_MAN_CLOSE>
												</xsl:for-each>
												<xsl:for-each select="n1:REQ_ID">
													<REQ_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</REQ_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:REQ_LINE_NBR">
													<REQ_LINE_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</REQ_LINE_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:REQ_SCHED_NBR">
													<REQ_SCHED_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</REQ_SCHED_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:REQ_DISTRIB_NBR">
													<REQ_DISTRIB_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</REQ_DISTRIB_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_POST_AMT">
													<PO_POST_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_POST_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_POST_AMT_BSE">
													<PO_POST_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_POST_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:PC_DISTRIB_STATUS">
													<PC_DISTRIB_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PC_DISTRIB_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:PC_DISTRIB_AMT">
													<PC_DISTRIB_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PC_DISTRIB_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:PC_DISTRIB_AMT_BSE">
													<PC_DISTRIB_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PC_DISTRIB_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:PROFILE_ID">
													<PROFILE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PROFILE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:TAG_NUMBER">
													<TAG_NUMBER>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TAG_NUMBER>
												</xsl:for-each>
												<xsl:for-each select="n1:CAP_NUM">
													<CAP_NUM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</CAP_NUM>
												</xsl:for-each>
												<xsl:for-each select="n1:CAP_SEQUENCE">
													<CAP_SEQUENCE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CAP_SEQUENCE>
												</xsl:for-each>
												<xsl:for-each select="n1:EMPLID">
													<EMPLID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</EMPLID>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_AM">
													<BUSINESS_UNIT_AM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUSINESS_UNIT_AM>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_IN">
													<BUSINESS_UNIT_IN>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</BUSINESS_UNIT_IN>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_POST_STATUS">
													<PO_POST_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_POST_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:CLOSE_AMOUNT">
													<CLOSE_AMOUNT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CLOSE_AMOUNT>
												</xsl:for-each>
												<xsl:for-each select="n1:CLOSE_AMOUNT_BSE">
													<CLOSE_AMOUNT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CLOSE_AMOUNT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:QTY_REQ">
													<QTY_REQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</QTY_REQ>
												</xsl:for-each>
												<xsl:for-each select="n1:DISTRIB_TYPE">
													<DISTRIB_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</DISTRIB_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:DISTRIB_PCT">
													<DISTRIB_PCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DISTRIB_PCT>
												</xsl:for-each>
												<xsl:for-each select="n1:INVOICE_CLOSE_IND">
													<INVOICE_CLOSE_IND>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</INVOICE_CLOSE_IND>
												</xsl:for-each>
												<xsl:for-each select="n1:FINANCIAL_ASSET_SW">
													<FINANCIAL_ASSET_SW>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FINANCIAL_ASSET_SW>
												</xsl:for-each>
												<xsl:for-each select="n1:COST_TYPE">
													<COST_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</COST_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_CD_SUT">
													<TAX_CD_SUT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TAX_CD_SUT>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_CD_SUT_PCT">
													<TAX_CD_SUT_PCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_CD_SUT_PCT>
												</xsl:for-each>
												<xsl:for-each select="n1:SALETX_AMT">
													<SALETX_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SALETX_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:SALETX_AMT_BSE">
													<SALETX_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SALETX_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:USETAX_AMT">
													<USETAX_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</USETAX_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:USETAX_AMT_BSE">
													<USETAX_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</USETAX_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:SUT_APPLICABILITY">
													<SUT_APPLICABILITY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SUT_APPLICABILITY>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
													<VAT_TXN_TYPE_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_TXN_TYPE_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_APPLICABILITY">
													<VAT_APPLICABILITY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_APPLICABILITY>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_CD_VAT">
													<TAX_CD_VAT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TAX_CD_VAT>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_CD_VAT_PCT">
													<TAX_CD_VAT_PCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_CD_VAT_PCT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_AMT">
													<VAT_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_AMT_BASE">
													<VAT_AMT_BASE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_AMT_BASE>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_BASIS_AMT">
													<VAT_BASIS_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_BASIS_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_BASIS_AMT_BSE">
													<VAT_BASIS_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_BASIS_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RECOVERY_PCT">
													<VAT_RECOVERY_PCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RECOVERY_PCT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RCVRY_AMT">
													<VAT_RCVRY_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RCVRY_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RCVRY_AMT_BSE">
													<VAT_RCVRY_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RCVRY_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_REBATE_PCT">
													<VAT_REBATE_PCT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_REBATE_PCT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_REBATE_AMT">
													<VAT_REBATE_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_REBATE_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_REBATE_AMT_BSE">
													<VAT_REBATE_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_REBATE_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_USE_ID">
													<VAT_USE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_USE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:RT_TYPE">
													<RT_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RT_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:RATE_MULT">
													<RATE_MULT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RATE_MULT>
												</xsl:for-each>
												<xsl:for-each select="n1:RATE_DIV">
													<RATE_DIV>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RATE_DIV>
												</xsl:for-each>
												<xsl:for-each select="n1:FREIGHT_AMT">
													<FREIGHT_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FREIGHT_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:FREIGHT_AMT_BSE">
													<FREIGHT_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FREIGHT_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:FREIGHT_AMT_NP">
													<FREIGHT_AMT_NP>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FREIGHT_AMT_NP>
												</xsl:for-each>
												<xsl:for-each select="n1:FREIGHT_AMT_NP_BSE">
													<FREIGHT_AMT_NP_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</FREIGHT_AMT_NP_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:MISC_AMT">
													<MISC_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MISC_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:MISC_AMT_BSE">
													<MISC_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MISC_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:MISC_AMT_NP">
													<MISC_AMT_NP>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MISC_AMT_NP>
												</xsl:for-each>
												<xsl:for-each select="n1:MISC_AMT_NP_BSE">
													<MISC_AMT_NP_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MISC_AMT_NP_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:MONETARY_AMOUNT">
													<MONETARY_AMOUNT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MONETARY_AMOUNT>
												</xsl:for-each>
												<xsl:for-each select="n1:MONETARY_AMT_BSE">
													<MONETARY_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MONETARY_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:BUDGET_DT">
													<BUDGET_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUDGET_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:BUDGET_LINE_STATUS">
													<BUDGET_LINE_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUDGET_LINE_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_CLOSE_FLAG">
													<KK_CLOSE_FLAG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</KK_CLOSE_FLAG>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_PROCESS_PRIOR">
													<KK_PROCESS_PRIOR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</KK_PROCESS_PRIOR>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_CALC_TYPE">
													<VAT_CALC_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_CALC_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:SALETX_PRORATE_FLG">
													<SALETX_PRORATE_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SALETX_PRORATE_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:USETAX_PRORATE_FLG">
													<USETAX_PRORATE_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</USETAX_PRORATE_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_NRCVR_PRO_FLG">
													<VAT_NRCVR_PRO_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_NRCVR_PRO_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_PRORATE_FLG">
													<VAT_PRORATE_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_PRORATE_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:CONSIGNED_FLAG">
													<CONSIGNED_FLAG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CONSIGNED_FLAG>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_RCVRY_PCT_SRC">
													<VAT_RCVRY_PCT_SRC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_RCVRY_PCT_SRC>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_REBATE_PCT_SRC">
													<VAT_REBATE_PCT_SRC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_REBATE_PCT_SRC>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_TRANS_AMT">
													<VAT_TRANS_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_TRANS_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_TRANS_AMT_BSE">
													<VAT_TRANS_AMT_BSE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_TRANS_AMT_BSE>
												</xsl:for-each>
												<xsl:for-each select="n1:PUBLISHED_IBU">
													<PUBLISHED_IBU>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PUBLISHED_IBU>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_APORT_CNTRL">
													<VAT_APORT_CNTRL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VAT_APORT_CNTRL>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_CLOSE_PRIOR">
													<KK_CLOSE_PRIOR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</KK_CLOSE_PRIOR>
												</xsl:for-each>
												<xsl:for-each select="n1:DOC_TOL_LN_STATUS">
													<DOC_TOL_LN_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DOC_TOL_LN_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:ROLL_STAT_R">
													<ROLL_STAT_R>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ROLL_STAT_R>
												</xsl:for-each>
												<xsl:for-each select="n1:USER_DIST_CHAR1">
													<USER_DIST_CHAR1>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</USER_DIST_CHAR1>
												</xsl:for-each>
												<xsl:for-each select="n1:ENTRY_EVENT">
													<ENTRY_EVENT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</ENTRY_EVENT>
												</xsl:for-each>
											</PO_LINE_DISTRIB>
										</xsl:for-each>
									</PO_LINE_SHIP>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_VAL_ADJ">
									<PO_VAL_ADJ>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:LINE_NBR">
											<LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:SCHED_NBR">
											<SCHED_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SCHED_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_IMPACT_TYPE">
											<ADJ_IMPACT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_IMPACT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_NBR">
											<ADJ_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_TYPE">
											<ADJ_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:BASE_PRICE_TYPE">
											<BASE_PRICE_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BASE_PRICE_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_TBL_UOM_FLG">
											<ADJ_TBL_UOM_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_TBL_UOM_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_TBL_SHIPTO_FLG">
											<ADJ_TBL_SHIPTO_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_TBL_SHIPTO_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_MIN">
											<QTY_MIN>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</QTY_MIN>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_PO">
											<QTY_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD">
											<TAX_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TAX_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_TYPE_OTHER">
											<ADJ_TYPE_OTHER>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_TYPE_OTHER>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_METH">
											<ADJ_METH>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_METH>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD">
											<CURRENCY_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_AMT">
											<ADJ_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD_BASE">
											<CURRENCY_CD_BASE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD_BASE>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_AMT_BSE">
											<ADJ_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_PCT">
											<ADJ_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:ADJ_PCT_TYPE">
											<ADJ_PCT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ADJ_PCT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:APPLY_ADJ">
											<APPLY_ADJ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</APPLY_ADJ>
										</xsl:for-each>
										<xsl:for-each select="n1:NOT_APPLY_REASON">
											<NOT_APPLY_REASON>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</NOT_APPLY_REASON>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_CHARGE_CODE">
											<MISC_CHARGE_CODE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MISC_CHARGE_CODE>
										</xsl:for-each>
										<xsl:for-each select="n1:DST_ACCT_TYPE">
											<DST_ACCT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DST_ACCT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VENDOR_SETID">
											<X_VENDOR_SETID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VENDOR_SETID>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VENDOR_ID">
											<X_VENDOR_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VENDOR_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:X_VNDR_LOC">
											<X_VNDR_LOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</X_VNDR_LOC>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_FLAG">
											<LC_COMP_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_ACCRUAL_FLAG">
											<LC_ACCRUAL_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_ACCRUAL_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_CALC">
											<LC_COMP_CALC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_CALC>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_ALLOC">
											<LC_COMP_ALLOC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_ALLOC>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_COMP_ON_PO">
											<LC_COMP_ON_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_COMP_ON_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_PRICE">
											<UNIT_PRICE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</UNIT_PRICE>
										</xsl:for-each>
										<xsl:for-each select="n1:FLAT_AMOUNT">
											<FLAT_AMOUNT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FLAT_AMOUNT>
										</xsl:for-each>
										<xsl:for-each select="n1:PCT_VALUE">
											<PCT_VALUE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PCT_VALUE>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_OF_MEASURE">
											<UNIT_OF_MEASURE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</UNIT_OF_MEASURE>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_CHARGE_ORIGN">
											<MISC_CHARGE_ORIGN>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MISC_CHARGE_ORIGN>
										</xsl:for-each>
										<xsl:for-each select="n1:UNIT_PRICE_LC">
											<UNIT_PRICE_LC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</UNIT_PRICE_LC>
										</xsl:for-each>
										<xsl:for-each select="n1:SALETX_PRORATE_FLG">
											<SALETX_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SALETX_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:USETAX_PRORATE_FLG">
											<USETAX_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</USETAX_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_NRCVR_PRO_FLG">
											<VAT_NRCVR_PRO_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_NRCVR_PRO_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_PRORATE_FLG">
											<VAT_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:FRGHT_PRORATE_FLG">
											<FRGHT_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FRGHT_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_PRORATE_FLG">
											<MISC_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MISC_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:ACTUAL_CHRG_FLG">
											<ACTUAL_CHRG_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ACTUAL_CHRG_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:LC_RTV_CREDIT">
											<LC_RTV_CREDIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</LC_RTV_CREDIT>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DATE">
											<RATE_DATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DATE>
										</xsl:for-each>
										<xsl:for-each select="n1:RT_TYPE">
											<RT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_MULT">
											<RATE_MULT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_MULT>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DIV">
											<RATE_DIV>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DIV>
										</xsl:for-each>
									</PO_VAL_ADJ>
								</xsl:for-each>
								<xsl:for-each select="n1:PO_LINE_DISTRIB">
									<PO_LINE_DISTRIB>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT">
											<BUSINESS_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_ID">
											<PO_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:LINE_NBR">
											<LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:SCHED_NBR">
											<SCHED_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SCHED_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:DST_ACCT_TYPE">
											<DST_ACCT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DST_ACCT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:DISTRIB_LINE_NUM">
											<DISTRIB_LINE_NUM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISTRIB_LINE_NUM>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_PO">
											<QTY_PO>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_PO>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD">
											<CURRENCY_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:MERCHANDISE_AMT">
											<MERCHANDISE_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MERCHANDISE_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:CURRENCY_CD_BASE">
											<CURRENCY_CD_BASE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CURRENCY_CD_BASE>
										</xsl:for-each>
										<xsl:for-each select="n1:MERCH_AMT_BSE">
											<MERCH_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MERCH_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:LOCATION">
											<LOCATION>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LOCATION>
										</xsl:for-each>
										<xsl:for-each select="n1:ACCOUNT">
											<ACCOUNT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ACCOUNT>
										</xsl:for-each>
										<xsl:for-each select="n1:ALTACCT">
											<ALTACCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ALTACCT>
										</xsl:for-each>
										<xsl:for-each select="n1:DEPTID">
											<DEPTID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DEPTID>
										</xsl:for-each>
										<xsl:for-each select="n1:OPERATING_UNIT">
											<OPERATING_UNIT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</OPERATING_UNIT>
										</xsl:for-each>
										<xsl:for-each select="n1:PRODUCT">
											<PRODUCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PRODUCT>
										</xsl:for-each>
										<xsl:for-each select="n1:FUND_CODE">
											<FUND_CODE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</FUND_CODE>
										</xsl:for-each>
										<xsl:for-each select="n1:CLASS_FLD">
											<CLASS_FLD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CLASS_FLD>
										</xsl:for-each>
										<xsl:for-each select="n1:PROGRAM_CODE">
											<PROGRAM_CODE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PROGRAM_CODE>
										</xsl:for-each>
										<xsl:for-each select="n1:BUDGET_REF">
											<BUDGET_REF>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUDGET_REF>
										</xsl:for-each>
										<xsl:for-each select="n1:AFFILIATE">
											<AFFILIATE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</AFFILIATE>
										</xsl:for-each>
										<xsl:for-each select="n1:AFFILIATE_INTRA1">
											<AFFILIATE_INTRA1>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</AFFILIATE_INTRA1>
										</xsl:for-each>
										<xsl:for-each select="n1:AFFILIATE_INTRA2">
											<AFFILIATE_INTRA2>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</AFFILIATE_INTRA2>
										</xsl:for-each>
										<xsl:for-each select="n1:CHARTFIELD1">
											<CHARTFIELD1>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CHARTFIELD1>
										</xsl:for-each>
										<xsl:for-each select="n1:CHARTFIELD2">
											<CHARTFIELD2>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CHARTFIELD2>
										</xsl:for-each>
										<xsl:for-each select="n1:CHARTFIELD3">
											<CHARTFIELD3>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CHARTFIELD3>
										</xsl:for-each>
										<xsl:for-each select="n1:PROJECT_ID">
											<PROJECT_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PROJECT_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:STATISTICS_CODE">
											<STATISTICS_CODE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</STATISTICS_CODE>
										</xsl:for-each>
										<xsl:for-each select="n1:STATISTIC_AMOUNT">
											<STATISTIC_AMOUNT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</STATISTIC_AMOUNT>
										</xsl:for-each>
										<xsl:for-each select="n1:CHARTFIELD_STATUS">
											<CHARTFIELD_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CHARTFIELD_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_GL">
											<BUSINESS_UNIT_GL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT_GL>
										</xsl:for-each>
										<xsl:for-each select="n1:DISTRIB_LN_STATUS">
											<DISTRIB_LN_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISTRIB_LN_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:DIST_PROCESSED_FLG">
											<DIST_PROCESSED_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DIST_PROCESSED_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:SYSTEM_SOURCE">
											<SYSTEM_SOURCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SYSTEM_SOURCE>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_REQ">
											<BUSINESS_UNIT_REQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUSINESS_UNIT_REQ>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_PC">
											<BUSINESS_UNIT_PC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUSINESS_UNIT_PC>
										</xsl:for-each>
										<xsl:for-each select="n1:ACTIVITY_ID">
											<ACTIVITY_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ACTIVITY_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:ANALYSIS_TYPE">
											<ANALYSIS_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ANALYSIS_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:RESOURCE_TYPE">
											<RESOURCE_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</RESOURCE_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:RESOURCE_CATEGORY">
											<RESOURCE_CATEGORY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</RESOURCE_CATEGORY>
										</xsl:for-each>
										<xsl:for-each select="n1:RESOURCE_SUB_CAT">
											<RESOURCE_SUB_CAT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</RESOURCE_SUB_CAT>
										</xsl:for-each>
										<xsl:for-each select="n1:PROCESS_INSTANCE">
											<PROCESS_INSTANCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PROCESS_INSTANCE>
										</xsl:for-each>
										<xsl:for-each select="n1:PROCESS_MAN_CLOSE">
											<PROCESS_MAN_CLOSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PROCESS_MAN_CLOSE>
										</xsl:for-each>
										<xsl:for-each select="n1:REQ_ID">
											<REQ_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</REQ_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:REQ_LINE_NBR">
											<REQ_LINE_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</REQ_LINE_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:REQ_SCHED_NBR">
											<REQ_SCHED_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</REQ_SCHED_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:REQ_DISTRIB_NBR">
											<REQ_DISTRIB_NBR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</REQ_DISTRIB_NBR>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_POST_AMT">
											<PO_POST_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_POST_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_POST_AMT_BSE">
											<PO_POST_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_POST_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:PC_DISTRIB_STATUS">
											<PC_DISTRIB_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PC_DISTRIB_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:PC_DISTRIB_AMT">
											<PC_DISTRIB_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PC_DISTRIB_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:PC_DISTRIB_AMT_BSE">
											<PC_DISTRIB_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PC_DISTRIB_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:PROFILE_ID">
											<PROFILE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PROFILE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:TAG_NUMBER">
											<TAG_NUMBER>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TAG_NUMBER>
										</xsl:for-each>
										<xsl:for-each select="n1:CAP_NUM">
											<CAP_NUM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</CAP_NUM>
										</xsl:for-each>
										<xsl:for-each select="n1:CAP_SEQUENCE">
											<CAP_SEQUENCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CAP_SEQUENCE>
										</xsl:for-each>
										<xsl:for-each select="n1:EMPLID">
											<EMPLID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</EMPLID>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_AM">
											<BUSINESS_UNIT_AM>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUSINESS_UNIT_AM>
										</xsl:for-each>
										<xsl:for-each select="n1:BUSINESS_UNIT_IN">
											<BUSINESS_UNIT_IN>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</BUSINESS_UNIT_IN>
										</xsl:for-each>
										<xsl:for-each select="n1:PO_POST_STATUS">
											<PO_POST_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PO_POST_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:CLOSE_AMOUNT">
											<CLOSE_AMOUNT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CLOSE_AMOUNT>
										</xsl:for-each>
										<xsl:for-each select="n1:CLOSE_AMOUNT_BSE">
											<CLOSE_AMOUNT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CLOSE_AMOUNT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:QTY_REQ">
											<QTY_REQ>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</QTY_REQ>
										</xsl:for-each>
										<xsl:for-each select="n1:DISTRIB_TYPE">
											<DISTRIB_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</DISTRIB_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:DISTRIB_PCT">
											<DISTRIB_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DISTRIB_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:INVOICE_CLOSE_IND">
											<INVOICE_CLOSE_IND>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</INVOICE_CLOSE_IND>
										</xsl:for-each>
										<xsl:for-each select="n1:FINANCIAL_ASSET_SW">
											<FINANCIAL_ASSET_SW>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FINANCIAL_ASSET_SW>
										</xsl:for-each>
										<xsl:for-each select="n1:COST_TYPE">
											<COST_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</COST_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD_SUT">
											<TAX_CD_SUT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TAX_CD_SUT>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD_SUT_PCT">
											<TAX_CD_SUT_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_CD_SUT_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:SALETX_AMT">
											<SALETX_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SALETX_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:SALETX_AMT_BSE">
											<SALETX_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SALETX_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:USETAX_AMT">
											<USETAX_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</USETAX_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:USETAX_AMT_BSE">
											<USETAX_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</USETAX_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:SUT_APPLICABILITY">
											<SUT_APPLICABILITY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SUT_APPLICABILITY>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
											<VAT_TXN_TYPE_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_TXN_TYPE_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_APPLICABILITY">
											<VAT_APPLICABILITY>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_APPLICABILITY>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD_VAT">
											<TAX_CD_VAT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</TAX_CD_VAT>
										</xsl:for-each>
										<xsl:for-each select="n1:TAX_CD_VAT_PCT">
											<TAX_CD_VAT_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</TAX_CD_VAT_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_AMT">
											<VAT_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_AMT_BASE">
											<VAT_AMT_BASE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_AMT_BASE>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_BASIS_AMT">
											<VAT_BASIS_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_BASIS_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_BASIS_AMT_BSE">
											<VAT_BASIS_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_BASIS_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RECOVERY_PCT">
											<VAT_RECOVERY_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RECOVERY_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RCVRY_AMT">
											<VAT_RCVRY_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RCVRY_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RCVRY_AMT_BSE">
											<VAT_RCVRY_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RCVRY_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_REBATE_PCT">
											<VAT_REBATE_PCT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_REBATE_PCT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_REBATE_AMT">
											<VAT_REBATE_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_REBATE_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_REBATE_AMT_BSE">
											<VAT_REBATE_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_REBATE_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_USE_ID">
											<VAT_USE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</VAT_USE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:RT_TYPE">
											<RT_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RT_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_MULT">
											<RATE_MULT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_MULT>
										</xsl:for-each>
										<xsl:for-each select="n1:RATE_DIV">
											<RATE_DIV>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</RATE_DIV>
										</xsl:for-each>
										<xsl:for-each select="n1:FREIGHT_AMT">
											<FREIGHT_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FREIGHT_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:FREIGHT_AMT_BSE">
											<FREIGHT_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FREIGHT_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:FREIGHT_AMT_NP">
											<FREIGHT_AMT_NP>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FREIGHT_AMT_NP>
										</xsl:for-each>
										<xsl:for-each select="n1:FREIGHT_AMT_NP_BSE">
											<FREIGHT_AMT_NP_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</FREIGHT_AMT_NP_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_AMT">
											<MISC_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MISC_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_AMT_BSE">
											<MISC_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MISC_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_AMT_NP">
											<MISC_AMT_NP>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MISC_AMT_NP>
										</xsl:for-each>
										<xsl:for-each select="n1:MISC_AMT_NP_BSE">
											<MISC_AMT_NP_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MISC_AMT_NP_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:MONETARY_AMOUNT">
											<MONETARY_AMOUNT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MONETARY_AMOUNT>
										</xsl:for-each>
										<xsl:for-each select="n1:MONETARY_AMT_BSE">
											<MONETARY_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</MONETARY_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:BUDGET_DT">
											<BUDGET_DT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUDGET_DT>
										</xsl:for-each>
										<xsl:for-each select="n1:BUDGET_LINE_STATUS">
											<BUDGET_LINE_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BUDGET_LINE_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_CLOSE_FLAG">
											<KK_CLOSE_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</KK_CLOSE_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_PROCESS_PRIOR">
											<KK_PROCESS_PRIOR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</KK_PROCESS_PRIOR>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_CALC_TYPE">
											<VAT_CALC_TYPE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_CALC_TYPE>
										</xsl:for-each>
										<xsl:for-each select="n1:SALETX_PRORATE_FLG">
											<SALETX_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</SALETX_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:USETAX_PRORATE_FLG">
											<USETAX_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</USETAX_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_NRCVR_PRO_FLG">
											<VAT_NRCVR_PRO_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_NRCVR_PRO_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_PRORATE_FLG">
											<VAT_PRORATE_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_PRORATE_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:CONSIGNED_FLAG">
											<CONSIGNED_FLAG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</CONSIGNED_FLAG>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_RCVRY_PCT_SRC">
											<VAT_RCVRY_PCT_SRC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_RCVRY_PCT_SRC>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_REBATE_PCT_SRC">
											<VAT_REBATE_PCT_SRC>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_REBATE_PCT_SRC>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_TRANS_AMT">
											<VAT_TRANS_AMT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_TRANS_AMT>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_TRANS_AMT_BSE">
											<VAT_TRANS_AMT_BSE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_TRANS_AMT_BSE>
										</xsl:for-each>
										<xsl:for-each select="n1:PUBLISHED_IBU">
											<PUBLISHED_IBU>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PUBLISHED_IBU>
										</xsl:for-each>
										<xsl:for-each select="n1:VAT_APORT_CNTRL">
											<VAT_APORT_CNTRL>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</VAT_APORT_CNTRL>
										</xsl:for-each>
										<xsl:for-each select="n1:KK_CLOSE_PRIOR">
											<KK_CLOSE_PRIOR>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</KK_CLOSE_PRIOR>
										</xsl:for-each>
										<xsl:for-each select="n1:DOC_TOL_LN_STATUS">
											<DOC_TOL_LN_STATUS>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</DOC_TOL_LN_STATUS>
										</xsl:for-each>
										<xsl:for-each select="n1:ROLL_STAT_R">
											<ROLL_STAT_R>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</ROLL_STAT_R>
										</xsl:for-each>
										<xsl:for-each select="n1:USER_DIST_CHAR1">
											<USER_DIST_CHAR1>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</USER_DIST_CHAR1>
										</xsl:for-each>
										<xsl:for-each select="n1:ENTRY_EVENT">
											<ENTRY_EVENT>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</ENTRY_EVENT>
										</xsl:for-each>
									</PO_LINE_DISTRIB>
								</xsl:for-each>
								<xsl:for-each select="n1:PSCAMA">
									<PSCAMA>
										<xsl:for-each select="@class">
											<xsl:attribute name="class">
												<xsl:value-of select="."/>
											</xsl:attribute>
										</xsl:for-each>
										<xsl:for-each select="n1:LANGUAGE_CD">
											<LANGUAGE_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</LANGUAGE_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:AUDIT_ACTN">
											<AUDIT_ACTN>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</AUDIT_ACTN>
										</xsl:for-each>
										<xsl:for-each select="n1:BASE_LANGUAGE_CD">
											<BASE_LANGUAGE_CD>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</BASE_LANGUAGE_CD>
										</xsl:for-each>
										<xsl:for-each select="n1:MSG_SEQ_FLG">
											<MSG_SEQ_FLG>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MSG_SEQ_FLG>
										</xsl:for-each>
										<xsl:for-each select="n1:PROCESS_INSTANCE">
											<PROCESS_INSTANCE>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="@IsChanged">
													<xsl:attribute name="IsChanged">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:value-of select="."/>
											</PROCESS_INSTANCE>
										</xsl:for-each>
										<xsl:for-each select="n1:PUBLISH_RULE_ID">
											<PUBLISH_RULE_ID>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</PUBLISH_RULE_ID>
										</xsl:for-each>
										<xsl:for-each select="n1:MSGNODENAME">
											<MSGNODENAME>
												<xsl:for-each select="@type">
													<xsl:attribute name="type">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
											</MSGNODENAME>
										</xsl:for-each>
									</PSCAMA>
								</xsl:for-each>
							</FieldTypes>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="n12:Body">
					<xsl:for-each select="n1:MX_PURCHASE_ORDER">
						<xsl:for-each select="n1:MsgData">
							<MsgData>
								<xsl:for-each select="n1:Transaction">
									<Transaction>
										<xsl:for-each select="n1:PO_HDR">
											<PO_HDR>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT">
													<BUSINESS_UNIT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_ID">
													<PO_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:CHNG_ORD_BATCH">
													<CHNG_ORD_BATCH>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHNG_ORD_BATCH>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_TYPE">
													<PO_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_STATUS">
													<PO_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:HOLD_STATUS">
													<HOLD_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</HOLD_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:RECV_STATUS">
													<RECV_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RECV_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:DISP_ACTION">
													<DISP_ACTION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DISP_ACTION>
												</xsl:for-each>
												<xsl:for-each select="n1:DISP_METHOD">
													<DISP_METHOD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DISP_METHOD>
												</xsl:for-each>
												<xsl:for-each select="n1:CHANGE_STATUS">
													<CHANGE_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHANGE_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_DT">
													<PO_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_REF">
													<PO_REF>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_REF>
												</xsl:for-each>
												<xsl:for-each select="n1:VENDOR_SETID">
													<VENDOR_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VENDOR_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:VENDOR_ID">
													<VENDOR_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VENDOR_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:VNDR_LOC">
													<VNDR_LOC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</VNDR_LOC>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_SETID">
													<PRICE_SETID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_SETID>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_VENDOR">
													<PRICE_VENDOR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_VENDOR>
												</xsl:for-each>
												<xsl:for-each select="n1:PRICE_LOC">
													<PRICE_LOC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PRICE_LOC>
												</xsl:for-each>
												<xsl:for-each select="n1:PYMNT_TERMS_CD">
													<PYMNT_TERMS_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PYMNT_TERMS_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:BUYER_ID">
													<BUYER_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUYER_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:ORIGIN">
													<ORIGIN>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ORIGIN>
												</xsl:for-each>
												<xsl:for-each select="n1:CHNG_ORD_SEQ">
													<CHNG_ORD_SEQ>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CHNG_ORD_SEQ>
												</xsl:for-each>
												<xsl:for-each select="n1:ADDRESS_SEQ_NUM">
													<ADDRESS_SEQ_NUM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ADDRESS_SEQ_NUM>
												</xsl:for-each>
												<xsl:for-each select="n1:CNTCT_SEQ_NUM">
													<CNTCT_SEQ_NUM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CNTCT_SEQ_NUM>
												</xsl:for-each>
												<xsl:for-each select="n1:SALES_CNTCT_SEQ_N">
													<SALES_CNTCT_SEQ_N>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</SALES_CNTCT_SEQ_N>
												</xsl:for-each>
												<xsl:for-each select="n1:BILL_LOCATION">
													<BILL_LOCATION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BILL_LOCATION>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_EXEMPT">
													<TAX_EXEMPT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_EXEMPT>
												</xsl:for-each>
												<xsl:for-each select="n1:TAX_EXEMPT_ID">
													<TAX_EXEMPT_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</TAX_EXEMPT_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:CURRENCY_CD">
													<CURRENCY_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CURRENCY_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:RT_TYPE">
													<RT_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RT_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:MATCH_ACTION">
													<MATCH_ACTION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MATCH_ACTION>
												</xsl:for-each>
												<xsl:for-each select="n1:MATCH_CNTRL_ID">
													<MATCH_CNTRL_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MATCH_CNTRL_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:MATCH_STATUS_PO">
													<MATCH_STATUS_PO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MATCH_STATUS_PO>
												</xsl:for-each>
												<xsl:for-each select="n1:MATCH_PROCESS_FLG">
													<MATCH_PROCESS_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MATCH_PROCESS_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:PROCESS_INSTANCE">
													<PROCESS_INSTANCE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PROCESS_INSTANCE>
												</xsl:for-each>
												<xsl:for-each select="n1:APPL_JRNL_ID_ENC">
													<APPL_JRNL_ID_ENC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</APPL_JRNL_ID_ENC>
												</xsl:for-each>
												<xsl:for-each select="n1:POST_DOC">
													<POST_DOC>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</POST_DOC>
												</xsl:for-each>
												<xsl:for-each select="n1:DST_CNTRL_ID">
													<DST_CNTRL_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DST_CNTRL_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:OPRID_ENTERED_BY">
													<OPRID_ENTERED_BY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</OPRID_ENTERED_BY>
												</xsl:for-each>
												<xsl:for-each select="n1:ENTERED_DT">
													<ENTERED_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ENTERED_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:OPRID_APPROVED_BY">
													<OPRID_APPROVED_BY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</OPRID_APPROVED_BY>
												</xsl:for-each>
												<xsl:for-each select="n1:APPROVAL_DT">
													<APPROVAL_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</APPROVAL_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:OPRID_MODIFIED_BY">
													<OPRID_MODIFIED_BY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</OPRID_MODIFIED_BY>
												</xsl:for-each>
												<xsl:for-each select="n1:LAST_DTTM_UPDATE">
													<LAST_DTTM_UPDATE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LAST_DTTM_UPDATE>
												</xsl:for-each>
												<xsl:for-each select="n1:ACCOUNTING_DT">
													<ACCOUNTING_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ACCOUNTING_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:BUSINESS_UNIT_GL">
													<BUSINESS_UNIT_GL>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUSINESS_UNIT_GL>
												</xsl:for-each>
												<xsl:for-each select="n1:IN_PROCESS_FLG">
													<IN_PROCESS_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</IN_PROCESS_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:ACTIVITY_DATE">
													<ACTIVITY_DATE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ACTIVITY_DATE>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_POST_STATUS">
													<PO_POST_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PO_POST_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:NEXT_MOD_SEQ_NBR">
													<NEXT_MOD_SEQ_NBR>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</NEXT_MOD_SEQ_NBR>
												</xsl:for-each>
												<xsl:for-each select="n1:ERS_ACTION">
													<ERS_ACTION>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ERS_ACTION>
												</xsl:for-each>
												<xsl:for-each select="n1:ACCRUE_USE_TAX">
													<ACCRUE_USE_TAX>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</ACCRUE_USE_TAX>
												</xsl:for-each>
												<xsl:for-each select="n1:CURRENCY_CD_BASE">
													<CURRENCY_CD_BASE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</CURRENCY_CD_BASE>
												</xsl:for-each>
												<xsl:for-each select="n1:RATE_DATE">
													<RATE_DATE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RATE_DATE>
												</xsl:for-each>
												<xsl:for-each select="n1:RATE_MULT">
													<RATE_MULT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RATE_MULT>
												</xsl:for-each>
												<xsl:for-each select="n1:RATE_DIV">
													<RATE_DIV>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</RATE_DIV>
												</xsl:for-each>
												<xsl:for-each select="n1:VAT_ENTITY">
													<VAT_ENTITY>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</VAT_ENTITY>
												</xsl:for-each>
												<xsl:for-each select="n1:BUDGET_HDR_STATUS">
													<BUDGET_HDR_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUDGET_HDR_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_AMOUNT_TYPE">
													<KK_AMOUNT_TYPE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</KK_AMOUNT_TYPE>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_TRAN_OVER_FLAG">
													<KK_TRAN_OVER_FLAG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</KK_TRAN_OVER_FLAG>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_TRAN_OVER_OPRID">
													<KK_TRAN_OVER_OPRID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</KK_TRAN_OVER_OPRID>
												</xsl:for-each>
												<xsl:for-each select="n1:KK_TRAN_OVER_DTTM">
													<KK_TRAN_OVER_DTTM>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</KK_TRAN_OVER_DTTM>
												</xsl:for-each>
												<xsl:for-each select="n1:LC_ID">
													<LC_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</LC_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:BUDGET_HDR_STS_NP">
													<BUDGET_HDR_STS_NP>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUDGET_HDR_STS_NP>
												</xsl:for-each>
												<xsl:for-each select="n1:PREPAID_PO_FLG">
													<PREPAID_PO_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PREPAID_PO_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:PREPAID_AMT">
													<PREPAID_AMT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PREPAID_AMT>
												</xsl:for-each>
												<xsl:for-each select="n1:PREPAID_AUTH_STAT">
													<PREPAID_AUTH_STAT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PREPAID_AUTH_STAT>
												</xsl:for-each>
												<xsl:for-each select="n1:PREPAID_STATUS_PO">
													<PREPAID_STATUS_PO>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PREPAID_STATUS_PO>
												</xsl:for-each>
												<xsl:for-each select="n1:PAY_TRM_BSE_DT_OPT">
													<PAY_TRM_BSE_DT_OPT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PAY_TRM_BSE_DT_OPT>
												</xsl:for-each>
												<xsl:for-each select="n1:TERMS_BASIS_DT">
													<TERMS_BASIS_DT>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</TERMS_BASIS_DT>
												</xsl:for-each>
												<xsl:for-each select="n1:BACKORDER_STATUS">
													<BACKORDER_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BACKORDER_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:DOC_TOL_HDR_STATUS">
													<DOC_TOL_HDR_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</DOC_TOL_HDR_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:MID_ROLL_STATUS">
													<MID_ROLL_STATUS>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</MID_ROLL_STATUS>
												</xsl:for-each>
												<xsl:for-each select="n1:USER_HDR_CHAR1">
													<USER_HDR_CHAR1>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</USER_HDR_CHAR1>
												</xsl:for-each>
												<xsl:for-each select="n1:BUDGET_CHECK">
													<BUDGET_CHECK>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BUDGET_CHECK>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_COMMENTS_FS">
													<PO_COMMENTS_FS>
														<xsl:for-each select="@class">
															<xsl:attribute name="class">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT">
															<BUSINESS_UNIT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUSINESS_UNIT>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_ID">
															<PO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:OPRID">
															<OPRID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</OPRID>
														</xsl:for-each>
														<xsl:for-each select="n1:COMMENT_ID">
															<COMMENT_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</COMMENT_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:RANDOM_CMMT_NBR">
															<RANDOM_CMMT_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RANDOM_CMMT_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:PUBLIC_FLG">
															<PUBLIC_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PUBLIC_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:SOURCE_FROM">
															<SOURCE_FROM>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SOURCE_FROM>
														</xsl:for-each>
														<xsl:for-each select="n1:SOURCE_BU_SETID">
															<SOURCE_BU_SETID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SOURCE_BU_SETID>
														</xsl:for-each>
														<xsl:for-each select="n1:SOURCE_ID">
															<SOURCE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</SOURCE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:SOURCE_LINE_NBR">
															<SOURCE_LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SOURCE_LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:STATUS">
															<STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:ALLOW_MODIFY">
															<ALLOW_MODIFY>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ALLOW_MODIFY>
														</xsl:for-each>
														<xsl:for-each select="n1:FILENAME">
															<FILENAME>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</FILENAME>
														</xsl:for-each>
														<xsl:for-each select="n1:FILE_EXTENSION">
															<FILE_EXTENSION>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</FILE_EXTENSION>
														</xsl:for-each>
														<xsl:for-each select="n1:RECV_VIEW_FLG">
															<RECV_VIEW_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RECV_VIEW_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:VCHR_VIEW_FLG">
															<VCHR_VIEW_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</VCHR_VIEW_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:COMMENT_TYPE">
															<COMMENT_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</COMMENT_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:SHIPTO_ID">
															<SHIPTO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</SHIPTO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:LINE_NBR">
															<LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:COMMENTS_2000">
															<COMMENTS_2000>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</COMMENTS_2000>
														</xsl:for-each>
													</PO_COMMENTS_FS>
												</xsl:for-each>
												<xsl:for-each select="n1:PO_LINE">
													<PO_LINE>
														<xsl:for-each select="@class">
															<xsl:attribute name="class">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="n1:BUSINESS_UNIT">
															<BUSINESS_UNIT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</BUSINESS_UNIT>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_ID">
															<PO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:LINE_NBR">
															<LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:CANCEL_STATUS">
															<CANCEL_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CANCEL_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:CHANGE_STATUS">
															<CHANGE_STATUS>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CHANGE_STATUS>
														</xsl:for-each>
														<xsl:for-each select="n1:ITM_SETID">
															<ITM_SETID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</ITM_SETID>
														</xsl:for-each>
														<xsl:for-each select="n1:INV_ITEM_ID">
															<INV_ITEM_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</INV_ITEM_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:ITM_ID_VNDR">
															<ITM_ID_VNDR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ITM_ID_VNDR>
														</xsl:for-each>
														<xsl:for-each select="n1:VNDR_CATALOG_ID">
															<VNDR_CATALOG_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</VNDR_CATALOG_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:CATEGORY_ID">
															<CATEGORY_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CATEGORY_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:CHNG_ORD_SEQ">
															<CHNG_ORD_SEQ>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CHNG_ORD_SEQ>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_OF_MEASURE">
															<UNIT_OF_MEASURE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</UNIT_OF_MEASURE>
														</xsl:for-each>
														<xsl:for-each select="n1:QTY_TYPE">
															<QTY_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</QTY_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:PRICE_DT_TYPE">
															<PRICE_DT_TYPE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PRICE_DT_TYPE>
														</xsl:for-each>
														<xsl:for-each select="n1:MFG_ID">
															<MFG_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</MFG_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:MFG_ITM_ID">
															<MFG_ITM_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</MFG_ITM_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:CNTRCT_SETID">
															<CNTRCT_SETID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CNTRCT_SETID>
														</xsl:for-each>
														<xsl:for-each select="n1:CNTRCT_ID">
															<CNTRCT_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CNTRCT_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:CNTRCT_LINE_NBR">
															<CNTRCT_LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CNTRCT_LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:RELEASE_NBR">
															<RELEASE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RELEASE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:MILESTONE_NBR">
															<MILESTONE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</MILESTONE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:CNTRCT_RATE_MULT">
															<CNTRCT_RATE_MULT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CNTRCT_RATE_MULT>
														</xsl:for-each>
														<xsl:for-each select="n1:CNTRCT_RATE_DIV">
															<CNTRCT_RATE_DIV>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</CNTRCT_RATE_DIV>
														</xsl:for-each>
														<xsl:for-each select="n1:RFQ_ID">
															<RFQ_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</RFQ_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:RFQ_LINE_NBR">
															<RFQ_LINE_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RFQ_LINE_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:INSPECT_CD">
															<INSPECT_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</INSPECT_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:ROUTING_ID">
															<ROUTING_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</ROUTING_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:RECV_REQ">
															<RECV_REQ>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</RECV_REQ>
														</xsl:for-each>
														<xsl:for-each select="n1:PRICE_CAN_CHANGE">
															<PRICE_CAN_CHANGE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PRICE_CAN_CHANGE>
														</xsl:for-each>
														<xsl:for-each select="n1:WTHD_SW">
															<WTHD_SW>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</WTHD_SW>
														</xsl:for-each>
														<xsl:for-each select="n1:WTHD_CD">
															<WTHD_CD>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</WTHD_CD>
														</xsl:for-each>
														<xsl:for-each select="n1:CONFIG_CODE">
															<CONFIG_CODE>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CONFIG_CODE>
														</xsl:for-each>
														<xsl:for-each select="n1:CP_TEMPLATE_ID">
															<CP_TEMPLATE_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</CP_TEMPLATE_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:DESCR254_MIXED">
															<DESCR254_MIXED>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</DESCR254_MIXED>
														</xsl:for-each>
														<xsl:for-each select="n1:PACKING_WEIGHT">
															<PACKING_WEIGHT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PACKING_WEIGHT>
														</xsl:for-each>
														<xsl:for-each select="n1:PACKING_VOLUME">
															<PACKING_VOLUME>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</PACKING_VOLUME>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_MEASURE_WT">
															<UNIT_MEASURE_WT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</UNIT_MEASURE_WT>
														</xsl:for-each>
														<xsl:for-each select="n1:UNIT_MEASURE_VOL">
															<UNIT_MEASURE_VOL>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</UNIT_MEASURE_VOL>
														</xsl:for-each>
														<xsl:for-each select="n1:REPLEN_OPT">
															<REPLEN_OPT>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</REPLEN_OPT>
														</xsl:for-each>
														<xsl:for-each select="n1:AMT_ONLY_FLG">
															<AMT_ONLY_FLG>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</AMT_ONLY_FLG>
														</xsl:for-each>
														<xsl:for-each select="n1:USER_LINE_CHAR1">
															<USER_LINE_CHAR1>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="@IsChanged">
																	<xsl:attribute name="IsChanged">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:value-of select="."/>
															</USER_LINE_CHAR1>
														</xsl:for-each>
														<xsl:for-each select="n1:GPO_ID">
															<GPO_ID>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</GPO_ID>
														</xsl:for-each>
														<xsl:for-each select="n1:GPO_CNTRCT_NBR">
															<GPO_CNTRCT_NBR>
																<xsl:for-each select="@type">
																	<xsl:attribute name="type">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
															</GPO_CNTRCT_NBR>
														</xsl:for-each>
														<xsl:for-each select="n1:MX_ITM_CAT">
															<MX_ITM_CAT>
																<xsl:for-each select="@class">
																	<xsl:attribute name="class">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="n1:CATEGORY_CD">
																	<CATEGORY_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CATEGORY_CD>
																</xsl:for-each>
															</MX_ITM_CAT>
														</xsl:for-each>
														<xsl:for-each select="n1:PO_LINE_SHIP">
															<PO_LINE_SHIP>
																<xsl:for-each select="@class">
																	<xsl:attribute name="class">
																		<xsl:value-of select="."/>
																	</xsl:attribute>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT">
																	<BUSINESS_UNIT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BUSINESS_UNIT>
																</xsl:for-each>
																<xsl:for-each select="n1:PO_ID">
																	<PO_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PO_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:LINE_NBR">
																	<LINE_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</LINE_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:SCHED_NBR">
																	<SCHED_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SCHED_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:CANCEL_STATUS">
																	<CANCEL_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CANCEL_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:BAL_STATUS">
																	<BAL_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BAL_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:CHANGE_STATUS">
																	<CHANGE_STATUS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CHANGE_STATUS>
																</xsl:for-each>
																<xsl:for-each select="n1:CHNG_ORD_SEQ">
																	<CHNG_ORD_SEQ>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CHNG_ORD_SEQ>
																</xsl:for-each>
																<xsl:for-each select="n1:PRICE_PO">
																	<PRICE_PO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PRICE_PO>
																</xsl:for-each>
																<xsl:for-each select="n1:PRICE_PO_BSE">
																	<PRICE_PO_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PRICE_PO_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:CUSTOM_PRICE">
																	<CUSTOM_PRICE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CUSTOM_PRICE>
																</xsl:for-each>
																<xsl:for-each select="n1:ZERO_PRICE_IND">
																	<ZERO_PRICE_IND>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</ZERO_PRICE_IND>
																</xsl:for-each>
																<xsl:for-each select="n1:DUE_DT">
																	<DUE_DT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DUE_DT>
																</xsl:for-each>
																<xsl:for-each select="n1:DUE_TIME">
																	<DUE_TIME>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</DUE_TIME>
																</xsl:for-each>
																<xsl:for-each select="n1:SHIPTO_SETID">
																	<SHIPTO_SETID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SHIPTO_SETID>
																</xsl:for-each>
																<xsl:for-each select="n1:SHIPTO_ID">
																	<SHIPTO_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SHIPTO_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:ORIG_PROM_DT">
																	<ORIG_PROM_DT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</ORIG_PROM_DT>
																</xsl:for-each>
																<xsl:for-each select="n1:QTY_PO">
																	<QTY_PO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</QTY_PO>
																</xsl:for-each>
																<xsl:for-each select="n1:CURRENCY_CD">
																	<CURRENCY_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CURRENCY_CD>
																</xsl:for-each>
																<xsl:for-each select="n1:MERCHANDISE_AMT">
																	<MERCHANDISE_AMT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MERCHANDISE_AMT>
																</xsl:for-each>
																<xsl:for-each select="n1:CURRENCY_CD_BASE">
																	<CURRENCY_CD_BASE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</CURRENCY_CD_BASE>
																</xsl:for-each>
																<xsl:for-each select="n1:MERCH_AMT_BSE">
																	<MERCH_AMT_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MERCH_AMT_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:FREIGHT_TERMS">
																	<FREIGHT_TERMS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FREIGHT_TERMS>
																</xsl:for-each>
																<xsl:for-each select="n1:SHIP_TYPE_ID">
																	<SHIP_TYPE_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SHIP_TYPE_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:DISTRIB_MTHD_FLG">
																	<DISTRIB_MTHD_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</DISTRIB_MTHD_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:UNIT_PRC_TOL">
																	<UNIT_PRC_TOL>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</UNIT_PRC_TOL>
																</xsl:for-each>
																<xsl:for-each select="n1:UNIT_PRC_TOL_BSE">
																	<UNIT_PRC_TOL_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</UNIT_PRC_TOL_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:PCT_UNIT_PRC_TOL">
																	<PCT_UNIT_PRC_TOL>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PCT_UNIT_PRC_TOL>
																</xsl:for-each>
																<xsl:for-each select="n1:EXT_PRC_TOL">
																	<EXT_PRC_TOL>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</EXT_PRC_TOL>
																</xsl:for-each>
																<xsl:for-each select="n1:EXT_PRC_TOL_BSE">
																	<EXT_PRC_TOL_BSE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</EXT_PRC_TOL_BSE>
																</xsl:for-each>
																<xsl:for-each select="n1:PCT_EXT_PRC_TOL">
																	<PCT_EXT_PRC_TOL>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PCT_EXT_PRC_TOL>
																</xsl:for-each>
																<xsl:for-each select="n1:QTY_RECV_TOL_PCT">
																	<QTY_RECV_TOL_PCT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</QTY_RECV_TOL_PCT>
																</xsl:for-each>
																<xsl:for-each select="n1:PCT_UNDER_QTY">
																	<PCT_UNDER_QTY>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PCT_UNDER_QTY>
																</xsl:for-each>
																<xsl:for-each select="n1:MATCH_STATUS_LN_PO">
																	<MATCH_STATUS_LN_PO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MATCH_STATUS_LN_PO>
																</xsl:for-each>
																<xsl:for-each select="n1:MATCH_LINE_OPT">
																	<MATCH_LINE_OPT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</MATCH_LINE_OPT>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_OM">
																	<BUSINESS_UNIT_OM>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUSINESS_UNIT_OM>
																</xsl:for-each>
																<xsl:for-each select="n1:ORDER_NO">
																	<ORDER_NO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</ORDER_NO>
																</xsl:for-each>
																<xsl:for-each select="n1:ORDER_INT_LINE_NO">
																	<ORDER_INT_LINE_NO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</ORDER_INT_LINE_NO>
																</xsl:for-each>
																<xsl:for-each select="n1:SCHED_LINE_NBR">
																	<SCHED_LINE_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SCHED_LINE_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:SHIP_TO_CUST_ID">
																	<SHIP_TO_CUST_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</SHIP_TO_CUST_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_IN">
																	<BUSINESS_UNIT_IN>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUSINESS_UNIT_IN>
																</xsl:for-each>
																<xsl:for-each select="n1:PRODUCTION_ID">
																	<PRODUCTION_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PRODUCTION_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:OP_SEQUENCE">
																	<OP_SEQUENCE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</OP_SEQUENCE>
																</xsl:for-each>
																<xsl:for-each select="n1:FROZEN_FLG">
																	<FROZEN_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FROZEN_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:PLAN_CHANGE_FLG">
																	<PLAN_CHANGE_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PLAN_CHANGE_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:NET_CHANGE_EP">
																	<NET_CHANGE_EP>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</NET_CHANGE_EP>
																</xsl:for-each>
																<xsl:for-each select="n1:CARRIER_ID">
																	<CARRIER_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</CARRIER_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:SUT_BASE_ID">
																	<SUT_BASE_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SUT_BASE_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_CD_SUT">
																	<TAX_CD_SUT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</TAX_CD_SUT>
																</xsl:for-each>
																<xsl:for-each select="n1:ULTIMATE_USE_CD">
																	<ULTIMATE_USE_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</ULTIMATE_USE_CD>
																</xsl:for-each>
																<xsl:for-each select="n1:SUT_EXCPTN_TYPE">
																	<SUT_EXCPTN_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SUT_EXCPTN_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:SUT_EXCPTN_CERTIF">
																	<SUT_EXCPTN_CERTIF>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</SUT_EXCPTN_CERTIF>
																</xsl:for-each>
																<xsl:for-each select="n1:SUT_APPLICABILITY">
																	<SUT_APPLICABILITY>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</SUT_APPLICABILITY>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RCRD_INPT_FLG">
																	<VAT_RCRD_INPT_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RCRD_INPT_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RCRD_OUTPT_FLG">
																	<VAT_RCRD_OUTPT_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RCRD_OUTPT_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_DCLRTN_POINT">
																	<VAT_DCLRTN_POINT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_DCLRTN_POINT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_CALC_GROSS_NET">
																	<VAT_CALC_GROSS_NET>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_CALC_GROSS_NET>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_CALC_FRGHT_FLG">
																	<VAT_CALC_FRGHT_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_CALC_FRGHT_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_CALC_MISC_FLG">
																	<VAT_CALC_MISC_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_CALC_MISC_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RECALC_FLG">
																	<VAT_RECALC_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_RECALC_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_TREATMENT_PUR">
																	<VAT_TREATMENT_PUR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_TREATMENT_PUR>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_EXCPTN_TYPE">
																	<VAT_EXCPTN_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_EXCPTN_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_EXCPTN_CERTIF">
																	<VAT_EXCPTN_CERTIF>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_EXCPTN_CERTIF>
																</xsl:for-each>
																<xsl:for-each select="n1:COUNTRY_SHIP_TO">
																	<COUNTRY_SHIP_TO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</COUNTRY_SHIP_TO>
																</xsl:for-each>
																<xsl:for-each select="n1:COUNTRY_SHIP_FROM">
																	<COUNTRY_SHIP_FROM>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</COUNTRY_SHIP_FROM>
																</xsl:for-each>
																<xsl:for-each select="n1:COUNTRY_VAT_SHIPTO">
																	<COUNTRY_VAT_SHIPTO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</COUNTRY_VAT_SHIPTO>
																</xsl:for-each>
																<xsl:for-each select="n1:COUNTRY_VAT_BILLTO">
																	<COUNTRY_VAT_BILLTO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</COUNTRY_VAT_BILLTO>
																</xsl:for-each>
																<xsl:for-each select="n1:COUNTRY_VAT_BILLFR">
																	<COUNTRY_VAT_BILLFR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</COUNTRY_VAT_BILLFR>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_RGSTRN_SELLER">
																	<VAT_RGSTRN_SELLER>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_RGSTRN_SELLER>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
																	<VAT_TXN_TYPE_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_TXN_TYPE_CD>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_APPLICABILITY">
																	<VAT_APPLICABILITY>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_APPLICABILITY>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_CD_VAT">
																	<TAX_CD_VAT>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</TAX_CD_VAT>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_USE_ID">
																	<VAT_USE_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_USE_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:IST_TXN_FLG">
																	<IST_TXN_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</IST_TXN_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_CALC_TYPE">
																	<VAT_CALC_TYPE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</VAT_CALC_TYPE>
																</xsl:for-each>
																<xsl:for-each select="n1:BUSINESS_UNIT_RTV">
																	<BUSINESS_UNIT_RTV>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</BUSINESS_UNIT_RTV>
																</xsl:for-each>
																<xsl:for-each select="n1:RTV_ID">
																	<RTV_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</RTV_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:RTV_LN_NBR">
																	<RTV_LN_NBR>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</RTV_LN_NBR>
																</xsl:for-each>
																<xsl:for-each select="n1:RTV_VERIFIED">
																	<RTV_VERIFIED>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</RTV_VERIFIED>
																</xsl:for-each>
																<xsl:for-each select="n1:SHIP_DATE">
																	<SHIP_DATE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</SHIP_DATE>
																</xsl:for-each>
																<xsl:for-each select="n1:UNIT_PRC_TOL_L">
																	<UNIT_PRC_TOL_L>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</UNIT_PRC_TOL_L>
																</xsl:for-each>
																<xsl:for-each select="n1:PCT_UNIT_PRC_TOL_L">
																	<PCT_UNIT_PRC_TOL_L>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PCT_UNIT_PRC_TOL_L>
																</xsl:for-each>
																<xsl:for-each select="n1:EXT_PRC_TOL_L">
																	<EXT_PRC_TOL_L>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</EXT_PRC_TOL_L>
																</xsl:for-each>
																<xsl:for-each select="n1:PCT_EXT_PRC_TOL_L">
																	<PCT_EXT_PRC_TOL_L>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</PCT_EXT_PRC_TOL_L>
																</xsl:for-each>
																<xsl:for-each select="n1:UNIT_PRC_TOL_BSE_L">
																	<UNIT_PRC_TOL_BSE_L>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</UNIT_PRC_TOL_BSE_L>
																</xsl:for-each>
																<xsl:for-each select="n1:EXT_PRC_TOL_BSE_L">
																	<EXT_PRC_TOL_BSE_L>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</EXT_PRC_TOL_BSE_L>
																</xsl:for-each>
																<xsl:for-each select="n1:RJCT_OVER_TOL_FLAG">
																	<RJCT_OVER_TOL_FLAG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</RJCT_OVER_TOL_FLAG>
																</xsl:for-each>
																<xsl:for-each select="n1:REJECT_DAYS">
																	<REJECT_DAYS>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</REJECT_DAYS>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_VAT_FLG">
																	<TAX_VAT_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</TAX_VAT_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_FRGHT_FLG">
																	<TAX_FRGHT_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</TAX_FRGHT_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:TAX_MISC_FLG">
																	<TAX_MISC_FLG>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</TAX_MISC_FLG>
																</xsl:for-each>
																<xsl:for-each select="n1:TRFT_RULE_CD">
																	<TRFT_RULE_CD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</TRFT_RULE_CD>
																</xsl:for-each>
																<xsl:for-each select="n1:QTY_RFQ">
																	<QTY_RFQ>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</QTY_RFQ>
																</xsl:for-each>
																<xsl:for-each select="n1:SHIP_ID_EST">
																	<SHIP_ID_EST>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</SHIP_ID_EST>
																</xsl:for-each>
																<xsl:for-each select="n1:X_VENDOR_SETID">
																	<X_VENDOR_SETID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</X_VENDOR_SETID>
																</xsl:for-each>
																<xsl:for-each select="n1:X_VENDOR_ID">
																	<X_VENDOR_ID>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</X_VENDOR_ID>
																</xsl:for-each>
																<xsl:for-each select="n1:X_VNDR_LOC">
																	<X_VNDR_LOC>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</X_VNDR_LOC>
																</xsl:for-each>
																<xsl:for-each select="n1:FRT_CHRG_METHOD">
																	<FRT_CHRG_METHOD>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</FRT_CHRG_METHOD>
																</xsl:for-each>
																<xsl:for-each select="n1:FRT_CHRG_OVERRIDE">
																	<FRT_CHRG_OVERRIDE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="@IsChanged">
																			<xsl:attribute name="IsChanged">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</FRT_CHRG_OVERRIDE>
																</xsl:for-each>
																<xsl:for-each select="n1:VAT_ROUND_RULE">
																	<VAT_ROUND_RULE>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</VAT_ROUND_RULE>
																</xsl:for-each>
																<xsl:for-each select="n1:REVISION">
																	<REVISION>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</REVISION>
																</xsl:for-each>
																<xsl:for-each select="n1:PUBLISHED_SHIPTO">
																	<PUBLISHED_SHIPTO>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</PUBLISHED_SHIPTO>
																</xsl:for-each>
																<xsl:for-each select="n1:BCKORD_ORG_SCHED">
																	<BCKORD_ORG_SCHED>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:value-of select="."/>
																	</BCKORD_ORG_SCHED>
																</xsl:for-each>
																<xsl:for-each select="n1:USER_SCHED_CHAR1">
																	<USER_SCHED_CHAR1>
																		<xsl:for-each select="@type">
																			<xsl:attribute name="type">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																	</USER_SCHED_CHAR1>
																</xsl:for-each>
																<xsl:for-each select="n1:PO_LINE_DISTRIB">
																	<PO_LINE_DISTRIB>
																		<xsl:for-each select="@class">
																			<xsl:attribute name="class">
																				<xsl:value-of select="."/>
																			</xsl:attribute>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUSINESS_UNIT">
																			<BUSINESS_UNIT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</BUSINESS_UNIT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PO_ID">
																			<PO_ID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PO_ID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:LINE_NBR">
																			<LINE_NBR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</LINE_NBR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:SCHED_NBR">
																			<SCHED_NBR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</SCHED_NBR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DST_ACCT_TYPE">
																			<DST_ACCT_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DST_ACCT_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DISTRIB_LINE_NUM">
																			<DISTRIB_LINE_NUM>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DISTRIB_LINE_NUM>
																		</xsl:for-each>
																		<xsl:for-each select="n1:QTY_PO">
																			<QTY_PO>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</QTY_PO>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CURRENCY_CD">
																			<CURRENCY_CD>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CURRENCY_CD>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MERCHANDISE_AMT">
																			<MERCHANDISE_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MERCHANDISE_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CURRENCY_CD_BASE">
																			<CURRENCY_CD_BASE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CURRENCY_CD_BASE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MERCH_AMT_BSE">
																			<MERCH_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MERCH_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:LOCATION">
																			<LOCATION>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</LOCATION>
																		</xsl:for-each>
																		<xsl:for-each select="n1:ACCOUNT">
																			<ACCOUNT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</ACCOUNT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:ALTACCT">
																			<ALTACCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</ALTACCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DEPTID">
																			<DEPTID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DEPTID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:OPERATING_UNIT">
																			<OPERATING_UNIT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</OPERATING_UNIT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PRODUCT">
																			<PRODUCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</PRODUCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:FUND_CODE">
																			<FUND_CODE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</FUND_CODE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CLASS_FLD">
																			<CLASS_FLD>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</CLASS_FLD>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PROGRAM_CODE">
																			<PROGRAM_CODE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</PROGRAM_CODE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUDGET_REF">
																			<BUDGET_REF>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</BUDGET_REF>
																		</xsl:for-each>
																		<xsl:for-each select="n1:AFFILIATE">
																			<AFFILIATE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</AFFILIATE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:AFFILIATE_INTRA1">
																			<AFFILIATE_INTRA1>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</AFFILIATE_INTRA1>
																		</xsl:for-each>
																		<xsl:for-each select="n1:AFFILIATE_INTRA2">
																			<AFFILIATE_INTRA2>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</AFFILIATE_INTRA2>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CHARTFIELD1">
																			<CHARTFIELD1>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</CHARTFIELD1>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CHARTFIELD2">
																			<CHARTFIELD2>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</CHARTFIELD2>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CHARTFIELD3">
																			<CHARTFIELD3>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</CHARTFIELD3>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PROJECT_ID">
																			<PROJECT_ID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</PROJECT_ID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:STATISTICS_CODE">
																			<STATISTICS_CODE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</STATISTICS_CODE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:STATISTIC_AMOUNT">
																			<STATISTIC_AMOUNT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</STATISTIC_AMOUNT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CHARTFIELD_STATUS">
																			<CHARTFIELD_STATUS>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CHARTFIELD_STATUS>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUSINESS_UNIT_GL">
																			<BUSINESS_UNIT_GL>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</BUSINESS_UNIT_GL>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DISTRIB_LN_STATUS">
																			<DISTRIB_LN_STATUS>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DISTRIB_LN_STATUS>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DIST_PROCESSED_FLG">
																			<DIST_PROCESSED_FLG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DIST_PROCESSED_FLG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:SYSTEM_SOURCE">
																			<SYSTEM_SOURCE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</SYSTEM_SOURCE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUSINESS_UNIT_REQ">
																			<BUSINESS_UNIT_REQ>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</BUSINESS_UNIT_REQ>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUSINESS_UNIT_PC">
																			<BUSINESS_UNIT_PC>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</BUSINESS_UNIT_PC>
																		</xsl:for-each>
																		<xsl:for-each select="n1:ACTIVITY_ID">
																			<ACTIVITY_ID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</ACTIVITY_ID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:ANALYSIS_TYPE">
																			<ANALYSIS_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</ANALYSIS_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:RESOURCE_TYPE">
																			<RESOURCE_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</RESOURCE_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:RESOURCE_CATEGORY">
																			<RESOURCE_CATEGORY>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</RESOURCE_CATEGORY>
																		</xsl:for-each>
																		<xsl:for-each select="n1:RESOURCE_SUB_CAT">
																			<RESOURCE_SUB_CAT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</RESOURCE_SUB_CAT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PROCESS_INSTANCE">
																			<PROCESS_INSTANCE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PROCESS_INSTANCE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PROCESS_MAN_CLOSE">
																			<PROCESS_MAN_CLOSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PROCESS_MAN_CLOSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:REQ_ID">
																			<REQ_ID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</REQ_ID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:REQ_LINE_NBR">
																			<REQ_LINE_NBR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</REQ_LINE_NBR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:REQ_SCHED_NBR">
																			<REQ_SCHED_NBR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</REQ_SCHED_NBR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:REQ_DISTRIB_NBR">
																			<REQ_DISTRIB_NBR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</REQ_DISTRIB_NBR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PO_POST_AMT">
																			<PO_POST_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PO_POST_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PO_POST_AMT_BSE">
																			<PO_POST_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PO_POST_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PC_DISTRIB_STATUS">
																			<PC_DISTRIB_STATUS>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PC_DISTRIB_STATUS>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PC_DISTRIB_AMT">
																			<PC_DISTRIB_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PC_DISTRIB_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PC_DISTRIB_AMT_BSE">
																			<PC_DISTRIB_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PC_DISTRIB_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PROFILE_ID">
																			<PROFILE_ID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</PROFILE_ID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:TAG_NUMBER">
																			<TAG_NUMBER>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</TAG_NUMBER>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CAP_NUM">
																			<CAP_NUM>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</CAP_NUM>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CAP_SEQUENCE">
																			<CAP_SEQUENCE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CAP_SEQUENCE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:EMPLID">
																			<EMPLID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</EMPLID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUSINESS_UNIT_AM">
																			<BUSINESS_UNIT_AM>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</BUSINESS_UNIT_AM>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUSINESS_UNIT_IN">
																			<BUSINESS_UNIT_IN>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</BUSINESS_UNIT_IN>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PO_POST_STATUS">
																			<PO_POST_STATUS>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</PO_POST_STATUS>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CLOSE_AMOUNT">
																			<CLOSE_AMOUNT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CLOSE_AMOUNT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CLOSE_AMOUNT_BSE">
																			<CLOSE_AMOUNT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CLOSE_AMOUNT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:QTY_REQ">
																			<QTY_REQ>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</QTY_REQ>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DISTRIB_TYPE">
																			<DISTRIB_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</DISTRIB_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DISTRIB_PCT">
																			<DISTRIB_PCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DISTRIB_PCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:INVOICE_CLOSE_IND">
																			<INVOICE_CLOSE_IND>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</INVOICE_CLOSE_IND>
																		</xsl:for-each>
																		<xsl:for-each select="n1:FINANCIAL_ASSET_SW">
																			<FINANCIAL_ASSET_SW>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</FINANCIAL_ASSET_SW>
																		</xsl:for-each>
																		<xsl:for-each select="n1:COST_TYPE">
																			<COST_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</COST_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:TAX_CD_SUT">
																			<TAX_CD_SUT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</TAX_CD_SUT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:TAX_CD_SUT_PCT">
																			<TAX_CD_SUT_PCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</TAX_CD_SUT_PCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:SALETX_AMT">
																			<SALETX_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</SALETX_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:SALETX_AMT_BSE">
																			<SALETX_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</SALETX_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:USETAX_AMT">
																			<USETAX_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</USETAX_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:USETAX_AMT_BSE">
																			<USETAX_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</USETAX_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:SUT_APPLICABILITY">
																			<SUT_APPLICABILITY>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</SUT_APPLICABILITY>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_TXN_TYPE_CD">
																			<VAT_TXN_TYPE_CD>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</VAT_TXN_TYPE_CD>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_APPLICABILITY">
																			<VAT_APPLICABILITY>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</VAT_APPLICABILITY>
																		</xsl:for-each>
																		<xsl:for-each select="n1:TAX_CD_VAT">
																			<TAX_CD_VAT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</TAX_CD_VAT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:TAX_CD_VAT_PCT">
																			<TAX_CD_VAT_PCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</TAX_CD_VAT_PCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_AMT">
																			<VAT_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_AMT_BASE">
																			<VAT_AMT_BASE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_AMT_BASE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_BASIS_AMT">
																			<VAT_BASIS_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_BASIS_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_BASIS_AMT_BSE">
																			<VAT_BASIS_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_BASIS_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_RECOVERY_PCT">
																			<VAT_RECOVERY_PCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_RECOVERY_PCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_RCVRY_AMT">
																			<VAT_RCVRY_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_RCVRY_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_RCVRY_AMT_BSE">
																			<VAT_RCVRY_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_RCVRY_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_REBATE_PCT">
																			<VAT_REBATE_PCT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_REBATE_PCT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_REBATE_AMT">
																			<VAT_REBATE_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_REBATE_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_REBATE_AMT_BSE">
																			<VAT_REBATE_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_REBATE_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_USE_ID">
																			<VAT_USE_ID>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</VAT_USE_ID>
																		</xsl:for-each>
																		<xsl:for-each select="n1:RT_TYPE">
																			<RT_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</RT_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:RATE_MULT">
																			<RATE_MULT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</RATE_MULT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:RATE_DIV">
																			<RATE_DIV>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</RATE_DIV>
																		</xsl:for-each>
																		<xsl:for-each select="n1:FREIGHT_AMT">
																			<FREIGHT_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</FREIGHT_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:FREIGHT_AMT_BSE">
																			<FREIGHT_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</FREIGHT_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:FREIGHT_AMT_NP">
																			<FREIGHT_AMT_NP>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</FREIGHT_AMT_NP>
																		</xsl:for-each>
																		<xsl:for-each select="n1:FREIGHT_AMT_NP_BSE">
																			<FREIGHT_AMT_NP_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</FREIGHT_AMT_NP_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MISC_AMT">
																			<MISC_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MISC_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MISC_AMT_BSE">
																			<MISC_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MISC_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MISC_AMT_NP">
																			<MISC_AMT_NP>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MISC_AMT_NP>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MISC_AMT_NP_BSE">
																			<MISC_AMT_NP_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MISC_AMT_NP_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MONETARY_AMOUNT">
																			<MONETARY_AMOUNT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MONETARY_AMOUNT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:MONETARY_AMT_BSE">
																			<MONETARY_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</MONETARY_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUDGET_DT">
																			<BUDGET_DT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</BUDGET_DT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:BUDGET_LINE_STATUS">
																			<BUDGET_LINE_STATUS>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</BUDGET_LINE_STATUS>
																		</xsl:for-each>
																		<xsl:for-each select="n1:KK_CLOSE_FLAG">
																			<KK_CLOSE_FLAG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</KK_CLOSE_FLAG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:KK_PROCESS_PRIOR">
																			<KK_PROCESS_PRIOR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</KK_PROCESS_PRIOR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_CALC_TYPE">
																			<VAT_CALC_TYPE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_CALC_TYPE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:SALETX_PRORATE_FLG">
																			<SALETX_PRORATE_FLG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</SALETX_PRORATE_FLG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:USETAX_PRORATE_FLG">
																			<USETAX_PRORATE_FLG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</USETAX_PRORATE_FLG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_NRCVR_PRO_FLG">
																			<VAT_NRCVR_PRO_FLG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_NRCVR_PRO_FLG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_PRORATE_FLG">
																			<VAT_PRORATE_FLG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_PRORATE_FLG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:CONSIGNED_FLAG">
																			<CONSIGNED_FLAG>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</CONSIGNED_FLAG>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_RCVRY_PCT_SRC">
																			<VAT_RCVRY_PCT_SRC>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_RCVRY_PCT_SRC>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_REBATE_PCT_SRC">
																			<VAT_REBATE_PCT_SRC>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_REBATE_PCT_SRC>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_TRANS_AMT">
																			<VAT_TRANS_AMT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_TRANS_AMT>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_TRANS_AMT_BSE">
																			<VAT_TRANS_AMT_BSE>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_TRANS_AMT_BSE>
																		</xsl:for-each>
																		<xsl:for-each select="n1:PUBLISHED_IBU">
																			<PUBLISHED_IBU>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</PUBLISHED_IBU>
																		</xsl:for-each>
																		<xsl:for-each select="n1:VAT_APORT_CNTRL">
																			<VAT_APORT_CNTRL>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</VAT_APORT_CNTRL>
																		</xsl:for-each>
																		<xsl:for-each select="n1:KK_CLOSE_PRIOR">
																			<KK_CLOSE_PRIOR>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</KK_CLOSE_PRIOR>
																		</xsl:for-each>
																		<xsl:for-each select="n1:DOC_TOL_LN_STATUS">
																			<DOC_TOL_LN_STATUS>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</DOC_TOL_LN_STATUS>
																		</xsl:for-each>
																		<xsl:for-each select="n1:ROLL_STAT_R">
																			<ROLL_STAT_R>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:for-each select="@IsChanged">
																					<xsl:attribute name="IsChanged">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																				<xsl:value-of select="."/>
																			</ROLL_STAT_R>
																		</xsl:for-each>
																		<xsl:for-each select="n1:USER_DIST_CHAR1">
																			<USER_DIST_CHAR1>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</USER_DIST_CHAR1>
																		</xsl:for-each>
																		<xsl:for-each select="n1:ENTRY_EVENT">
																			<ENTRY_EVENT>
																				<xsl:for-each select="@type">
																					<xsl:attribute name="type">
																						<xsl:value-of select="."/>
																					</xsl:attribute>
																				</xsl:for-each>
																			</ENTRY_EVENT>
																		</xsl:for-each>
																	</PO_LINE_DISTRIB>
																</xsl:for-each>
															</PO_LINE_SHIP>
														</xsl:for-each>
													</PO_LINE>
												</xsl:for-each>
											</PO_HDR>
										</xsl:for-each>
										<xsl:for-each select="n1:PSCAMA">
											<PSCAMA>
												<xsl:for-each select="@class">
													<xsl:attribute name="class">
														<xsl:value-of select="."/>
													</xsl:attribute>
												</xsl:for-each>
												<xsl:for-each select="n1:LANGUAGE_CD">
													<LANGUAGE_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</LANGUAGE_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:AUDIT_ACTN">
													<AUDIT_ACTN>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</AUDIT_ACTN>
												</xsl:for-each>
												<xsl:for-each select="n1:BASE_LANGUAGE_CD">
													<BASE_LANGUAGE_CD>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</BASE_LANGUAGE_CD>
												</xsl:for-each>
												<xsl:for-each select="n1:MSG_SEQ_FLG">
													<MSG_SEQ_FLG>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</MSG_SEQ_FLG>
												</xsl:for-each>
												<xsl:for-each select="n1:PROCESS_INSTANCE">
													<PROCESS_INSTANCE>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:for-each select="@IsChanged">
															<xsl:attribute name="IsChanged">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
														<xsl:value-of select="."/>
													</PROCESS_INSTANCE>
												</xsl:for-each>
												<xsl:for-each select="n1:PUBLISH_RULE_ID">
													<PUBLISH_RULE_ID>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</PUBLISH_RULE_ID>
												</xsl:for-each>
												<xsl:for-each select="n1:MSGNODENAME">
													<MSGNODENAME>
														<xsl:for-each select="@type">
															<xsl:attribute name="type">
																<xsl:value-of select="."/>
															</xsl:attribute>
														</xsl:for-each>
													</MSGNODENAME>
												</xsl:for-each>
											</PSCAMA>
										</xsl:for-each>
									</Transaction>
								</xsl:for-each>
							</MsgData>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
			</GENERIC>
		</MX_PS_MESSAGE>
	</xsl:template>
</xsl:stylesheet>