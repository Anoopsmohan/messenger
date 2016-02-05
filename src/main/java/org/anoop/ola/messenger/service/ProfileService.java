package org.anoop.ola.messenger.service;

import java.util.List;
import java.util.logging.Logger;

import org.anoop.ola.messenger.dto.ProfileDTO;
import org.anoop.ola.messenger.dto.ProfileDTOImpl;
import org.anoop.ola.messenger.model.Profile;

import javassist.bytecode.stackmap.TypeData.ClassName;

public class ProfileService {
	private static final Logger log = Logger.getLogger(ClassName.class.getName());
	
	public List<Profile> getAllProfiles(){
		ProfileDTO profileDTO = new ProfileDTOImpl();
		return profileDTO.getAllProfiles();
	}
	
	public Profile addProfile(Profile profile) {
		ProfileDTO profileDTO = new ProfileDTOImpl();
		return profileDTO.insertProfile(profile);
	}
	
	public Profile updateProfile(Profile profile, String profileName) {
		ProfileDTO profileDTO = new ProfileDTOImpl();
		return profileDTO.updateProfile(profile, profileName);
	}
	
	public Profile getProfile(String profileName) {		
		ProfileDTO profileDTO = new ProfileDTOImpl();
		return profileDTO.getProfile(profileName);
	}
	
	public void removePrrofile(String profileName){
		ProfileDTO profileDTO = new ProfileDTOImpl();
		profileDTO.removeProfile(profileName);
	}
}
