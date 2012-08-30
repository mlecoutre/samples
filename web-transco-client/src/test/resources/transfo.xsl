<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                xmlns:Transco="java://am.projects.webtransco.client.TranscoXSLExt" exclude-result-prefixes="Transco">


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
<xsl:text>res   : </xsl:text><xsl:value-of select="Transco:callTransco('Transco_TBO', 'Metris.DetermineWebServiceUrl.METRISTransformTransco001','TRUE', 'APP', 'ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent')" />

  </xsl:template>
</xsl:stylesheet>
