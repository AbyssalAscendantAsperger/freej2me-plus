/*
	This file is part of FreeJ2ME.

	FreeJ2ME is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	FreeJ2ME is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with FreeJ2ME.  If not, see http://www.gnu.org/licenses/
*/
package javax.microedition.broadcast.esg;

public final class CommonMetadataSet implements MetadataSet 
{

    public static final StringAttribute PROGRAM_CONTENT_AUX_CLIP = new StringAttribute("programContentAuxClip");
    public static final StringAttribute PROGRAM_CONTENT_AUX_LOGO = new StringAttribute("programContentAuxLogo");
    public static final StringAttribute PROGRAM_CONTENT_AUX_SOUND = new StringAttribute("programContentAuxSound");
    public static final StringAttribute PROGRAM_CONTENT_DESCRIPTION = new StringAttribute("programContentDescription");
    public static final StringAttribute PROGRAM_CONTENT_GENRE = new StringAttribute("programContentGenre");
    public static final StringAttribute PROGRAM_CONTENT_ID = new StringAttribute("programContentId");
    public static final StringAttribute PROGRAM_CONTENT_LOCATION = new StringAttribute("programContentLocation");
    public static final StringAttribute PROGRAM_CONTENT_NAME = new StringAttribute("programContentName");
    public static final StringAttribute PROGRAM_CONTENT_PARENTAL_RATING = new StringAttribute("programContentParentalRating");
    public static final StringAttribute PROGRAM_CONTENT_TYPE = new StringAttribute("programContentType");
    public static final StringAttribute PROGRAM_DESCRIPTION = new StringAttribute("programDescription");
    public static final DateAttribute PROGRAM_DIST_END = new DateAttribute("programDistEnd");
    public static final DateAttribute PROGRAM_DIST_START = new DateAttribute("programDistStart");
    public static final DateAttribute PROGRAM_END_TIME = new DateAttribute("programEndTime");
    public static final StringAttribute PROGRAM_ID = new StringAttribute("programId");
    public static final BooleanAttribute PROGRAM_IS_FREE = new BooleanAttribute("programIsFree");
    public static final BooleanAttribute PROGRAM_IS_PROTECTED = new BooleanAttribute("programIsProtected");
    public static final StringAttribute PROGRAM_NAME = new StringAttribute("programName");
    public static final StringAttribute PROGRAM_REL_MATERIAL = new StringAttribute("programRelMaterial");
    public static final DateAttribute PROGRAM_START_TIME = new DateAttribute("programStartTime");

    public static final StringAttribute PURCHASE_AUX_CLIP = new StringAttribute("purchaseAuxClip");
    public static final StringAttribute PURCHASE_AUX_LOGO = new StringAttribute("purchaseAuxLogo");
    public static final StringAttribute PURCHASE_AUX_SOUND = new StringAttribute("purchaseAuxSound");
    public static final StringAttribute PURCHASE_CHANNEL_CONTACT = new StringAttribute("purchaseChannelContact");
    public static final StringAttribute PURCHASE_CHANNEL_DESCRIPTION = new StringAttribute("purchaseChannelDescription");
    public static final StringAttribute PURCHASE_CHANNEL_ID = new StringAttribute("purchaseChannelId");
    public static final StringAttribute PURCHASE_CHANNEL_NAME = new StringAttribute("purchaseChannelName");
    public static final StringAttribute PURCHASE_CHANNEL_PORTAL = new StringAttribute("purchaseChannelPortal");
    public static final StringAttribute PURCHASE_CURRENCY = new StringAttribute("purchaseCurrency");
    public static final StringAttribute PURCHASE_DESCRIPTION = new StringAttribute("purchaseDescription");
    public static final StringAttribute PURCHASE_ID = new StringAttribute("purchaseId");
    public static final StringAttribute PURCHASE_ITEM_DESCRIPTION = new StringAttribute("purchaseItemDescription");
    public static final StringAttribute PURCHASE_ITEM_ID = new StringAttribute("purchaseItemId");
    public static final StringAttribute PURCHASE_ITEM_NAME = new StringAttribute("purchaseItemName");
    public static final StringAttribute PURCHASE_ITEM_PARENTAL_RATING = new StringAttribute("purchaseItemParentalRating");
    public static final StringAttribute PURCHASE_ITEM_REL_MATERIAL = new StringAttribute("purchaseItemRelMaterial");
    public static final StringAttribute PURCHASE_PRICE = new StringAttribute("purchasePrice");
    public static final DateAttribute PURCHASE_VALID_FROM = new DateAttribute("purchaseValidFrom");
    public static final DateAttribute PURCHASE_VALID_TO = new DateAttribute("purchaseValidTo");

    public static final StringAttribute SERVICE_AUX_CLIP = new StringAttribute("serviceAuxClip");
    public static final StringAttribute SERVICE_AUX_LOGO = new StringAttribute("serviceAuxLogo");
    public static final StringAttribute SERVICE_AUX_SOUND = new StringAttribute("serviceAuxSound");
    public static final StringAttribute SERVICE_COMPONENT_ACCESS_APP_TYPE = new StringAttribute("serviceComponentAccessAppType");
    public static final StringAttribute SERVICE_COMPONENT_ACCESS_ID = new StringAttribute("serviceComponentAccessId");
    public static final StringAttribute SERVICE_COMPONENT_AUD_LANG = new StringAttribute("serviceComponentAudLang");
    public static final NumericAttribute SERVICE_COMPONENT_AVERAGE_AUD_RATE = new NumericAttribute("serviceComponentAverageAudRate", 0, false);
    public static final NumericAttribute SERVICE_COMPONENT_AVERAGE_VID_RATE = new NumericAttribute("serviceComponentAverageVidRate", 0, false);
    public static final StringAttribute SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG = new StringAttribute("serviceComponentClosedCaptionsLang");
    public static final StringAttribute SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT = new StringAttribute("serviceComponentDownloadFileFormat");
    public static final StringAttribute SERVICE_COMPONENT_KEY_MANAG_SYS = new StringAttribute("serviceComponentKeyManagSys");
    public static final StringAttribute SERVICE_COMPONENT_LANGUAGE = new StringAttribute("serviceComponentLanguage");
    public static final NumericAttribute SERVICE_COMPONENT_MAX_AUD_RATE = new NumericAttribute("serviceComponentMaxAudRate", 0, false);
    public static final NumericAttribute SERVICE_COMPONENT_MAX_VID_RATE = new NumericAttribute("serviceComponentMaxVidRate", 0, false);
    public static final StringAttribute SERVICE_COMPONENT_MIME_TYPE = new StringAttribute("serviceComponentMimeType");
    public static final StringAttribute SERVICE_COMPONENT_SDP_REF = new StringAttribute("serviceComponentSdpRef");
    public static final StringAttribute SERVICE_COMPONENT_SDP_STREAM = new StringAttribute("serviceComponentSdpStream");
    public static final StringAttribute SERVICE_COMPONENT_SDP_STRING = new StringAttribute("serviceComponentSdpString");
    public static final StringAttribute SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG = new StringAttribute("serviceComponentVidOpenCaptionsLang");
    public static final StringAttribute SERVICE_COMPONENT_VID_SIGN_LANG = new StringAttribute("serviceComponentVidSignLang");
    public static final StringAttribute SERVICE_DESCRIPTION = new StringAttribute("serviceDescription");
    public static final StringAttribute SERVICE_GENRE = new StringAttribute("serviceGenre");
    public static final StringAttribute SERVICE_ID = new StringAttribute("serviceId");
    public static final BooleanAttribute SERVICE_IS_FREE = new BooleanAttribute("serviceIsFree");
    public static final BooleanAttribute SERVICE_IS_PROTECTED = new BooleanAttribute("serviceIsProtected");
    public static final StringAttribute SERVICE_NAME = new StringAttribute("serviceName");
    public static final StringAttribute SERVICE_PARENTAL_RATING = new StringAttribute("serviceParentalRating");
    public static final StringAttribute SERVICE_REL_MATERIAL = new StringAttribute("serviceRelMaterial");
    public static final StringAttribute SERVICE_TYPE = new StringAttribute("serviceType");
    public static final StringAttribute SERVICE_TYPE_NAME = new StringAttribute("serviceTypeName");

    public static CommonMetadataSet getInstance() { return new CommonMetadataSet(); }

    public String getDescription() { return "Common metadata set, JSR 272 specification version 1"; }

    public Attribute[] getValidAttributes() 
    {
        return new Attribute[] 
        {
            PROGRAM_CONTENT_AUX_CLIP,
            PROGRAM_CONTENT_AUX_LOGO,
            PROGRAM_CONTENT_AUX_SOUND,
            PROGRAM_CONTENT_DESCRIPTION,
            PROGRAM_CONTENT_GENRE,
            PROGRAM_CONTENT_ID,
            PROGRAM_CONTENT_LOCATION,
            PROGRAM_CONTENT_NAME,
            PROGRAM_CONTENT_PARENTAL_RATING,
            PROGRAM_CONTENT_TYPE,
            PROGRAM_DESCRIPTION,
            PROGRAM_DIST_END,
            PROGRAM_DIST_START,
            PROGRAM_END_TIME,
            PROGRAM_ID,
            PROGRAM_IS_FREE,
            PROGRAM_IS_PROTECTED,
            PROGRAM_NAME,
            PROGRAM_REL_MATERIAL,
            PROGRAM_START_TIME,
            PURCHASE_AUX_CLIP,
            PURCHASE_AUX_LOGO,
            PURCHASE_AUX_SOUND,
            PURCHASE_CHANNEL_CONTACT,
            PURCHASE_CHANNEL_DESCRIPTION,
            PURCHASE_CHANNEL_ID,
            PURCHASE_CHANNEL_NAME,
            PURCHASE_CHANNEL_PORTAL,
            PURCHASE_CURRENCY,
            PURCHASE_DESCRIPTION,
            PURCHASE_ID,
            PURCHASE_ITEM_DESCRIPTION,
            PURCHASE_ITEM_ID,
            PURCHASE_ITEM_NAME,
            PURCHASE_ITEM_PARENTAL_RATING,
            PURCHASE_ITEM_REL_MATERIAL,
            PURCHASE_PRICE,
            PURCHASE_VALID_FROM,
            PURCHASE_VALID_TO,
            SERVICE_AUX_CLIP,
            SERVICE_AUX_LOGO,
            SERVICE_AUX_SOUND,
            SERVICE_COMPONENT_ACCESS_APP_TYPE,
            SERVICE_COMPONENT_ACCESS_ID,
            SERVICE_COMPONENT_AUD_LANG,
            SERVICE_COMPONENT_AVERAGE_AUD_RATE,
            SERVICE_COMPONENT_AVERAGE_VID_RATE,
            SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG,
            SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT,
            SERVICE_COMPONENT_KEY_MANAG_SYS,
            SERVICE_COMPONENT_LANGUAGE,
            SERVICE_COMPONENT_MAX_AUD_RATE,
            SERVICE_COMPONENT_MAX_VID_RATE,
            SERVICE_COMPONENT_MIME_TYPE,
            SERVICE_COMPONENT_SDP_REF,
            SERVICE_COMPONENT_SDP_STREAM,
            SERVICE_COMPONENT_SDP_STRING,
            SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG,
            SERVICE_COMPONENT_VID_SIGN_LANG,
            SERVICE_DESCRIPTION,
            SERVICE_GENRE,
            SERVICE_ID,
            SERVICE_IS_FREE,
            SERVICE_IS_PROTECTED,
            SERVICE_NAME,
            SERVICE_PARENTAL_RATING,
            SERVICE_REL_MATERIAL,
            SERVICE_TYPE,
            SERVICE_TYPE_NAME
        };
    }

    public Attribute[] getValidProgramAttributes() 
    {
        return new Attribute[] 
        {
            PROGRAM_CONTENT_AUX_CLIP,
            PROGRAM_CONTENT_AUX_LOGO,
            PROGRAM_CONTENT_AUX_SOUND,
            PROGRAM_CONTENT_DESCRIPTION,
            PROGRAM_CONTENT_GENRE,
            PROGRAM_CONTENT_ID,
            PROGRAM_CONTENT_LOCATION,
            PROGRAM_CONTENT_NAME,
            PROGRAM_CONTENT_PARENTAL_RATING,
            PROGRAM_CONTENT_TYPE,
            PROGRAM_DESCRIPTION,
            PROGRAM_DIST_END,
            PROGRAM_DIST_START,
            PROGRAM_END_TIME,
            PROGRAM_ID,
            PROGRAM_IS_FREE,
            PROGRAM_IS_PROTECTED,
            PROGRAM_NAME,
            PROGRAM_REL_MATERIAL,
            PROGRAM_START_TIME
        };
    }

    public Attribute[] getValidPurchaseAttributes() 
    {
        return new Attribute[] 
        {
            PURCHASE_AUX_CLIP,
            PURCHASE_AUX_LOGO,
            PURCHASE_AUX_SOUND,
            PURCHASE_CHANNEL_CONTACT,
            PURCHASE_CHANNEL_DESCRIPTION,
            PURCHASE_CHANNEL_ID,
            PURCHASE_CHANNEL_NAME,
            PURCHASE_CHANNEL_PORTAL,
            PURCHASE_CURRENCY,
            PURCHASE_DESCRIPTION,
            PURCHASE_ID,
            PURCHASE_ITEM_DESCRIPTION,
            PURCHASE_ITEM_ID,
            PURCHASE_ITEM_NAME,
            PURCHASE_ITEM_PARENTAL_RATING,
            PURCHASE_ITEM_REL_MATERIAL,
            PURCHASE_PRICE,
            PURCHASE_VALID_FROM,
            PURCHASE_VALID_TO
        };
    }

    public Attribute[] getValidServiceAttributes() 
    {
        return new Attribute[] 
        {
            SERVICE_AUX_CLIP,
            SERVICE_AUX_LOGO,
            SERVICE_AUX_SOUND,
            SERVICE_DESCRIPTION,
            SERVICE_GENRE,
            SERVICE_ID,
            SERVICE_IS_FREE,
            SERVICE_IS_PROTECTED,
            SERVICE_NAME,
            SERVICE_PARENTAL_RATING,
            SERVICE_REL_MATERIAL,
            SERVICE_TYPE,
            SERVICE_TYPE_NAME
        };
    }

    public Attribute[] getValidServiceComponentAttributes() 
    {
        return new Attribute[] 
        {
            SERVICE_COMPONENT_ACCESS_APP_TYPE,
            SERVICE_COMPONENT_ACCESS_ID,
            SERVICE_COMPONENT_AUD_LANG,
            SERVICE_COMPONENT_AVERAGE_AUD_RATE,
            SERVICE_COMPONENT_AVERAGE_VID_RATE,
            SERVICE_COMPONENT_CLOSED_CAPTIONS_LANG,
            SERVICE_COMPONENT_DOWNLOAD_FILE_FORMAT,
            SERVICE_COMPONENT_KEY_MANAG_SYS,
            SERVICE_COMPONENT_LANGUAGE,
            SERVICE_COMPONENT_MAX_AUD_RATE,
            SERVICE_COMPONENT_MAX_VID_RATE,
            SERVICE_COMPONENT_MIME_TYPE,
            SERVICE_COMPONENT_SDP_REF,
            SERVICE_COMPONENT_SDP_STREAM,
            SERVICE_COMPONENT_SDP_STRING,
            SERVICE_COMPONENT_VID_OPEN_CAPTIONS_LANG,
            SERVICE_COMPONENT_VID_SIGN_LANG
        };
    }
}