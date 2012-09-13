<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:FormatDecimal="java://org.mat.samples.xslt.util.FormatDecimal"
                exclude-result-prefixes="FormatDecimal">
    <xsl:param name="Measure2"/>

    <xsl:output method="xml" indent="yes" />
    <xsl:template match="/" >

        <xsl:apply-templates />
    </xsl:template>
    <!-- Default template. Copies the element (with attributes)
  output record. -->
    <xsl:template match="*">
        <xsl:element name="{name()}">
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>

    <xsl:template match="date" xml:space="preserve">
<xsl:text>Measure2   : </xsl:text><xsl:value-of select="$Measure2"/>
<xsl:text>converts to</xsl:text>
<xsl:text>resultat   : </xsl:text><xsl:value-of select="FormatDecimal:convertDecimalToString('110,225',10,7)" />



  </xsl:template>
</xsl:stylesheet>
