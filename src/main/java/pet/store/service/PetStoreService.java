package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData data) {
		Long petStoreId = data.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, data);
		
		PetStore savedPetStore = petStoreDao.save(petStore);
		return new PetStoreData(savedPetStore);
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData data) {
		petStore.setPetStoreName(data.getPetStoreName());
		petStore.setPetStoreAddress(data.getPetStoreAddress());
		petStore.setPetStoreCity(data.getPetStoreCity());
		petStore.setPetStoreState(data.getPetStoreState());
		petStore.setPetStoreZip(data.getPetStoreZip());
		petStore.setPetStorePhone(data.getPetStorePhone());
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return petStoreDao.findById(petStoreId).orElseThrow(() -> 
			new NoSuchElementException("Pet store with ID=" + petStoreId + " not found"));
		}
	}
}
