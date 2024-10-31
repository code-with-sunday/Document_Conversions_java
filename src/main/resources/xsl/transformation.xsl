<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">

    <!-- Match the root of your XML document -->
    <xsl:template match="/">
        <fo:root>
            <!-- Define page layout -->
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-portrait"
                                       page-width="21.0cm"
                                       page-height="29.7cm"
                                       margin="2cm">
                    <fo:region-body margin="1.5cm"/>
                    <fo:region-before extent="2cm"/>
                    <fo:region-after extent="1.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <!-- Page sequence to control page layout -->
            <fo:page-sequence master-reference="A4-portrait">
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-size="14pt" font-weight="bold" text-align="center">STATEMENT REPORT</fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <fo:table table-layout="fixed" width="100%" border-collapse="collapse">
                        <!-- Table header -->
                        <fo:table-header>
                            <fo:table-row>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">Name</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">Time</fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block font-weight="bold">Comment</fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <!-- Table body -->
                        <fo:table-body>
                            <xsl:apply-templates select="mydata/guestbook/entry"/>
                        </fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <!-- Template for each guestbook entry -->
    <xsl:template match="mydata/guestbook/entry">
        <fo:table-row>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="name"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="time"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="comment"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

</xsl:stylesheet>
