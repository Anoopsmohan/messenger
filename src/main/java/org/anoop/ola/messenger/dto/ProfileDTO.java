package org.anoop.ola.messenger.dto;

import java.util.List;

import org.anoop.ola.messenger.model.Profile;

public interface ProfileDTO {
	public List<Profile> getAllProfiles();
	public Profile getProfile(String profileName);
	public Profile insertProfile(Profile profile);
	public Profile updateProfile(Profile profile, String profileName);
	public void removeProfile(String profileName);
}
