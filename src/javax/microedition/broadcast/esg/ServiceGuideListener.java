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

public interface ServiceGuideListener 
{

    String NEW_PROGRAM_LISTED = "NewProgramListed";
    String NEW_SERVICE_LISTED = "NewServiceListed";
    String PROGRAM_CHANGED = "ProgramChanged";
    String PROGRAM_DELETED = "ProgramDeleted";
    String PURCHASE_OBJECT_ADDED = "PurchaseObjectAdded";
    String PURCHASE_OBJECT_CHANGED = "PurchaseObjectChanged";
    String PURCHASE_OBJECT_DELETED = "PurchaseObjectDeleted";
    String SERVICE_CHANGED = "ServiceChanged";
    String SERVICE_DELETED = "ServiceDeleted";
    String SERVICE_GUIDE_BULK_CHANGED = "ServiceGuideBulkChanged";
    String SERVICE_GUIDE_DELETED = "ServiceGuideDeleted";
    String SERVICE_GUIDE_UPDATE_COMPLETED = "ServiceGuideUpdateCompleted";
    String SERVICE_GUIDE_UPDATE_FAILED = "ServiceGuideUpdateFailed";
    String SERVICE_GUIDE_UPDATE_STARTED = "ServiceGuideUpdateStarted";

    void serviceGuideUpdated(String event, ServiceGuideData serviceGuideData, Object eventData);
}